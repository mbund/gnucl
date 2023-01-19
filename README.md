# GNU OSU CSE Components Library

Fully compatible implementation of the OSU CSE Components Library API written in Kotlin and licensed under the GPL. (pronounced "knuckle")

## Motivation

Drop-in replacement library which is redistributable under a free license. For those who are comfortable with and enjoy the Java library they learned at OSU and would like to include it in their own projects ethically.

## Todo

- [ ] AMPMClock :stop_sign:
- [ ] BinaryTree
- [ ] List
- [ ] Map
- [x] NaturalNumber
- [ ] Program :stop_sign:
- [x] Queue
- [ ] Random
- [ ] Sequence :construction:
- [ ] Set
- [ ] SimpleReader
- [ ] SimpleWriter
- [ ] SortingMachine
- [ ] Stack
- [x] Standard
- [ ] Statement :stop_sign:
- [ ] Stopwatch
- [ ] Tree
- [ ] Utilities FormatChecker
- [ ] Utilities Reporter
- [ ] Utilities Tokenizer
- [ ] XMLTree

:construction: under construction

:stop_sign: won't implement

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

[Reference docs](http://web.cse.ohio-state.edu/software/common/doc)

Run tests with `gradle`:

```
gradle test
```
