# GNU OSU CSE Components Library

Fully compatible implementation of the OSU CSE Components Library API written in Kotlin and licensed under the GPL. (pronounced "knuckle")

## Developing

If you have the original `components.jar`, place it in `lib/libs/components.jar` and swap the comments on the test cases you're interested in to ensure compatibility. Example:

```java
import gnucl.naturalnumber.NaturalNumber;
// import components.naturalnumber.NaturalNumber;
```

```java
// import gnucl.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber;
```

Run tests with `gradle`:

```
gradle test
```
