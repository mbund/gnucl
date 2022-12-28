# GNU OSU CSE Components Library

Fully compatible implementation of the OSU CSE Components Library API written in Kotlin and licensed under the GPL. (pronounced "knuckle")

## Motivation

Drop-in replacement library which is redistributable under a free license. For those who are comfortable with and enjoy the Java library they learned at OSU and would like to include it in their own projects ethically.

## Developing

If you have the original `components.jar`, place it in `lib/libs/components.jar` and swap the comments on the test cases you're interested in to ensure compatibility. Example:

```java
import gnucl.naturalnumber.*;
// import components.naturalnumber.*;
```

```java
// import gnucl.naturalnumber.*;
import components.naturalnumber.*;
```

Run tests with `gradle`:

```
gradle test
```
