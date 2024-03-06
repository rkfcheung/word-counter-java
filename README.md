# word-counter-java

Word Counter in Java 8

* Add words
    - Ignore non-alphabet characters
    - Handle different languages
* Return word count

## Build

```shell
git clone https://github.com/rkfcheung/word-counter-java.git
cd word-counter-java
./gradlew build
```

## Usage

```java
final Translator translator= // ... implementation of Translator ...
final WordCounter wordCounter=new WordCounterImpl(translator);

// ... Add words ...
        wordCounter.add("flower");
        wordCounter.add("blume","flor");

// ... Get word count ...
        wordCounter.count("flower"); // Count = 3
```

### Usage with REST APIs

1. Start Spring Boot Application
    ```shell
    # Go to the WordCounter folder
    ./gradlew bootRunse
    ```
2. Use [httpie](https://httpie.io/) to add words
    ```shell
    http POST localhost:8080/add words:='["bonjour", "hello"]'
    ```

    ```json
    {
      "status": 200
    }
    ```
3. Get word count
    ```shell
    http localhost:8080/count/hello
    ```

   ```json
   {
      "count": 2
   }
   ```

## Development Information

| Item             | Value         |
|------------------|---------------|
| Operating System | MX Linux      |
| OpenJDK Version  | 1.8.0_402-402 |
| Gradle Version   | 8.6           |

## Design Principles

1. [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single_responsibility_principle): `WordCounter` has a
   single responsibility, which is to count words. Unrelated tasks, like translation, are isolated.
2. [Open/Close Principle](https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle): `WordCounter` is open for
   extension (e.g., adding more features) but closed for modification. In other words, the word counting logic can be
   enhanced without changing existing code.
3. [Dependency Inversion Principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle): `WordCounter` depends
   on `Translator` rather than its concrete implementation. This allows the use of different translation services
   without
   modifying the existing `WordCounter`.
4. [Test Driven Development](https://en.wikipedia.org/wiki/Test-driven_development): Writing tests before writing the
   actual code makes it more robust and ensures new code has test coverage.

## Complexity

| Method                            | Time Complexity | Space Complexity |
|-----------------------------------|-----------------|------------------|
| `void add(final String... words)` | O(n)            | O(n)             |
| `long count(final String word)`   | O(1)            | O(1)             |