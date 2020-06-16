[![Build Status](https://travis-ci.org/SchwarzIT/kokain.svg?branch=master)](https://travis-ci.org/SchwarzIT/kokain)
[![codecov](https://codecov.io/gh/SchwarzIT/kokain/branch/master/graph/badge.svg)](https://codecov.io/gh/SchwarzIT/kokain)
[![jitpack](https://jitpack.io/v/SchwarzIT/kokain.svg)](https://jitpack.io/#SchwarzIT/kokain)

## Kokain

![Kokain Logo](https://raw.githubusercontent.com/schwarzit/kokain/master/android-robot-jonny-full-200x200.png)

##### This Framework is in development state and it's not ready to be used in production



## Dependency Injection? Why not? It makes things easier, right?

Yes it does! But which Framework strategy is the right for you?

### Code Generation

The Framework generates the injection code as a subclass for every class while compiling. At programing stage the entry point is always a generated shadow class.
No magical things here. Generated code can be viewed. Awesome isn't it?

"Where there is light, there is also shadow"

- there's always a shadow class needed working as proxy between implementation and injection
- error handling in code generation depends on validation in annotation processor
- in java classes they're open per default in kotlin they're not

### More Kotlin based strategy (Property delegates, Extensions)

Kotlin comes along with a lot of features which are very useful. Some DI Frameworks out there provide their service based on these functionalities.
Property delegates combined with some extension methods makes it easy to handle the value of a property in a different place which results in a wonderful api.

"Sounds fancy but are there shadows too?"

- setup instructions are required for every single component which is supposed to be injected by the framework
- it's not possible to inject the activity context


### What's the result if these approaches decide to make a baby?

![Kokain Logo](https://raw.githubusercontent.com/schwarzit/kokain/master/android-robot-jonny-half-200x200.png)

### Kokain combines code generation with property delegates, that way things become easier



## Features

* easy to use - just annotate beans and start the framework on application start

* no declaration of instance creation

* possibility to safely inject activity context if bean injected by a activity hierarchy

* different lifecycle scopes

* lightweight implementation

* pragmatic api



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
    implementation 'com.github.SchwarzIT.kokain:kokain-core-api:${latest_version}'
    implementation 'com.github.SchwarzIT.kokain:kokain-di:${latest_version}@aar'
    kapt 'com.github.SchwarzIT.kokain:kokain-processor:${latest_version}'
```


3. Add EBean annotation to your components

```
 @EBean
 open class FooBean {

    private val mContext: Context by context()

    private val mFooSingletonyBean : FooSingletonBean by inject()
    
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
