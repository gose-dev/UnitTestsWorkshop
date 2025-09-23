# AGENTS.md - Guidelines for Agentic Coding Agents

## Build Commands
- Full build: `./gradlew assembleDebug` or `./gradlew assembleRelease`
- Fast build: `./gradlew assembleDebug --daemon --parallel --configure-on-demand`
- Single module build: `./gradlew :module-name:assembleDebug`

## Test Commands
- Run all tests: `./gradlew test`
- Run single test: `./gradlew :module:test --tests="*TestClassName*"`
- Run single test method: `./gradlew :module:test --tests="*TestClassName.methodName*"`
- Run unit tests: `./gradlew testDebugUnitTest`
- Run instrumentation tests: `./gradlew connectedDebugAndroidTest`

## Lint Commands
- Run lint: `./gradlew lint`
- Run lint on single module: `./gradlew :module:lint`
- Baseline update: `./gradlew lint --continue --stacktrace`

## Code Style Guidelines

### Imports
- AndroidX imports before third-party
- Third-party before project imports
- No unused imports

### Types
- Use Kotlin null safety (avoid !! operator)
- Prefer sealed classes for state
- Use data classes for immutable models
- Extension functions for utility methods

### Naming Conventions
- Classes: PascalCase
- Functions/variables: camelCase
- Constants: UPPER_SNAKE_CASE
- Packages: lowercase with no underscores

### Error Handling
- Never ignore exceptions silently
- Handle all API error cases

## Unit Tests Guidelines

### General requirements
- Unit tests should cover all significant scenarios and branches of execution
- Tests should be clear straightforward and self-explanatory
- Using the template for writing unit tests - Arrange-Act-Assert
- Do not use the `@VisibleForTesting` annotation
- Use parameterized tests to concisely perform the same type of actions for different arguments

### Isolation and mocking
- Unit tests should not depend on the Android framework, network, or database
- The tests should be completely independent, and the order in which they are run should not affect their success
- Use mockups and stubs to simulate dependencies
- The business logic should be completely separated from the UI layer for the convenience of unit testing
- Use patterns (MVVM, MVI, Clean Architecture) that simplify isolation and writing tests

### Tools used
- Based verification logic: JUnit4
- Moki: kotlin: Mockito-kotlin / java: - Mockito
- Kotlin Flow: Turbine

### Structure, naming, and location of tests
- Class names: the name of the class under test + the suffix Test
- The unit test path: <module>/src/test/java/<namespace>/<Class test package>/<Class>Test
    - Example:
        - Class: `odnoklassniki-auth/src/main/java/ru/ok/android/auth/ClassicLoginRepository`
        - Test: `odnoklassniki-auth/src/test/java/ru/ok/android/auth/ClassicLoginRepositoryTest`
- Methods name: ``` `The methodundertest what does the expected result do`() ```
    - Example
        - ```kotlin
      @Test
      fun `login with valid credentials returns success`()
      ```