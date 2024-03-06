# word-counter-java

Word Counter in Java 8

- Add words
- Return word count

## Build

```shell
git clone https://github.com/rkfcheung/word-counter-java.git
cd word-counter-java
./gradlew build
```

## Usage

```java
final Translator translator = // ... implementation of Translator ...
final WordCounter wordCounter = new WordCounterImpl(translator);

// ... Add words ...
wordCounter.add("flower");
wordCounter.add("blume", "flor");

// ... Get word count ...
wordCounter.count("flower"); // Count = 3
```

## Development Information

| Item             | Value         |
|------------------|---------------|
| Operating System | MX Linux      |
| OpenJDK Version  | 1.8.0_402-402 |
| Gradle Version   | 8.6           |
