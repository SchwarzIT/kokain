
# This Framework is in development state and it's not ready to be used in production

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


## What's the result if these approaches decide to make a baby?

# KOKAIN

Kokain combines code generation with property delegates, that way things become easier


## Features

* easy to use - just annotate beans and start the framework on application start

* no declaration of instance creation

* possibility to safely inject activity context if bean injected by a activity hierarchy

* different lifecycle scopes

* lightweight implementation

* pragmatic api



## Implementation

  
