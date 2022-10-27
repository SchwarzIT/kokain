![Build Status](https://github.com/SchwarzIT/kokain/actions/workflows/android_pre_hook.yml/badge.svg)
[![codecov](https://codecov.io/gh/SchwarzIT/kokain/branch/main/graph/badge.svg)](https://codecov.io/gh/SchwarzIT/kokain)
[![jitpack](https://jitpack.io/v/SchwarzIT/kokain.svg)](https://jitpack.io/#SchwarzIT/kokain)

![Kokain Logo](https://raw.githubusercontent.com/schwarzit/kokain/main/kokain-new-martin-268x303.png)



## What is Kokain

Kokain is pragmatic lightweight dependency injection framework for Kotlin based Applications.
The Framework is reduced to the core functionalities of DI. 
Therefore the setup is pretty easy and producing nearly zero overhead.

### Kokain combines code generation with property delegates, that way things become easier

## Features

* easy to use - just annotate beans and start the framework on application start

* no declaration of instance creation

* possibility to safely inject activity context if bean injected by a activity hierarchy

* different lifecycle scopes

* lightweight implementation

* pragmatic api

### Features Android only

* inject android context in a lifecycle aware matter

* inject systemservices


## Implementation

1. Add it in your root build.gradle at the end of repositories:

```
 buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
  }
  ...
  allprojects {
    repositories {
	maven { url 'https://jitpack.io' }
    }
  }
```

2. Add gradle dependency

```
    apply plugin: 'kotlin-allopen'
    apply plugin:"com.google.devtools.ksp"
    .
    .
    .
    
    implementation 'com.github.SchwarzIT.kokain:kokain-core-api:${latest_version}'
    implementation 'com.github.SchwarzIT.kokain:kokain-core-lib:${latest_version}'
    //for non android project use 'com.github.SchwarzIT.kokain:kokain-di-jvm' instead
    implementation 'com.github.SchwarzIT.kokain:kokain-di:${latest_version}@aar'
    // kapt plugin is the old fashion way and might be deleted in future releases use ksp plugin instead
    //kapt 'com.github.SchwarzIT.kokain:kokain-processor:${latest_version}'
    ksp "com.github.SchwarzIT.kokain:kokain-ksp:${kokain_version}"
    .
    .
    .
    
    allOpen {
       annotation("com.schwarz.kokain.api.EBean")
    }
```


3. Add EBean annotation to your components

```
 @EBean
 open class FooBean : FooBeanInterface {

    private val mContext: Context by context()

    private val mFooSingletonyBean : FooSingletonBean by inject()
    
    override fun saySomething(): String {
    	return "something"
    }
    
 }
```

4. Add EFactory annotation to your Application and start kokain (GeneratedFactory got automaticaly generated)

```
@EFactory(additionalFactories = [com.example.demolibrary.GeneratedFactory::class])
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KokainInstance.start(Kokain.create(GeneratedFactory(), this))
    }
}
```

hint: For multimodule projects we need to add the GeneratedFactories for each module via the additionalFactories attribute

5. Usage Overview

```
@EBean
class UsageOverview {

    // no difference where the component comes from always use "by inject()"
    private val mFooBean: FooBean by inject()

    private val mSingletonBean: FooSingletonBean by inject()

    private val mClassFromAnotherLibrary : ClassFromAnotherLibrary by inject()
    
    // inject as Interface
    private val mFooBeanInterface : FooBeanInterface by inject(FooBean::class)

    // inject all sorts of systemservices
    private val layoutInflater : LayoutInflater? by systemService()

    //inject context (kokain injects activity context if it's save to do so otherwise it injects application context)
    private val context : Context by context()

    private fun doSomething(){
        val bean = get<FooBean>()
        bean.saySomething()
    }

}
```

## Testing your code becomes easy like 123

Example based on Mockk

1.) create a simple extension function 

```
/**
 * Sets up Kokain to use [mock] whenever an object of its type needs to be injected.
 */
inline fun <reified T : Any> Kokain.setMock(mock: T) {
    every {
        create<T>(any(), T::class)
    } returns mock
}
```

2.) do some tests

```
class MySuperFancyControllerTest {

    @RelaxedMockK
    private lateinit var myBean1: BeanWithCode

    @RelaxedMockK
    lateinit var kokain: Kokain

    @BeforeEach
    fun setUp() {
        kokain.setMock(myBean1)
    

        KokainInstance.start(kokain)
    }

    @AfterEach
    fun tearDown() {
        KokainInstance.stop()
        unmockkAll()
    }
    
    @Test
    fun `do my test magic here without care about kokain`() {
    
    }
```
