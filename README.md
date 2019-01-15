CSP Framework
=============

This framework allows solving arbitrary Constraint Satisfaction Problems (CSP) with finite domain.

## Getting started

### Add the dependency

#### Gradle
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    implementation 'TODO'
}
```

#### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>TODO</groupId>
    <artifactId>TODO</artifactId>
    <version>TODO</version>
</dependency>
```

### Solve a problem
```kotlin
import com.tsovedenski.csp.*
import com.tsovedenski.csp.strategies.Backtracking

fun main(args: Array<String>) {
    val problem = Problem(
        variables = listOf('a', 'b', 'c'),
        domain = listOf(1, 2, 3, 4, 5),
        constraints = listOf(
            UnaryConstraint('a') { it in 3..4 },
            BinaryConstraint('a', 'b') { a, b -> a != b },
            BinaryConstraint('b', 'c') { b, c -> b + c > 4 }
        )
    )

    val solution = problem.solve(Backtracking())
    when (solution) {
        is NoSolution -> println("No solution found")
        is Solved     -> solution.assignment.print()
    }
    
    // a -> 3
    // b -> 1
    // c -> 4
}
```

### Constraints
A constraint is a predicate of `Assignment`.
There are several constraints:
* `UnaryConstraint` - constraint on a single variable.
* `BinaryConstraint` - constraint between two variables.
* `AllConstraint` - constraint between all variables.
    * `AllDiffConstraint` - all variables' values should be different.
* `GeneralConstraint` - constraint that allows arbitrary logic with arbitrary number of variables.

### Heuristics
`Solvable::solve` accepts a `Strategy` as a parameter.
`Strategy` is responsible for solving the problem.
With this framework there are two strategies included: backtracking and generate-and-test.
`Backtracking` accepts a number of parameters, some of which are _variable ordering_ and _pruning scheme_.

#### Variable Ordering
By default, the order in which variables are defined is used.
You can alter the way variables are selected by passing one of the 4 options to `variableOrdering` parameter:
* `SmallestDomainVariable` - select the variable with smallest domain.
* `BiggestDomainVariable` - select the variable with biggest domain.
* `MostFrequentVariable` - select the variable which is used in most constraints.
* `LeastFrequentVariable` - select the variable used in smallest amount of constraints.

#### Pruning Scheme
By default no pruning is done.
You can select a pruning scheme by passing one of the 3 options to `pruneScheme` parameter:
* `ForwardChecking`
* `PartialLookAhead`
* `FullLookAhead`



## Defining own problem
If you want to create your own class that can be solved, you have to implement `Solvable`.
In other words - define a CSP problem: variables, domains and constraints.
`Problem` can be defined in 3 ways (constructors):
* `(domains, constraints)` - the canonical representation, where `domains` is a map from variable to a list of values.
* `(variables, domain, constraints)` - here `domain` gets copied to each variable.
* `(variables, constraints, domainMapper)` - here the function `domainMapper` allows arbitrary logic that maps each variable to a domain.

For examples, see `csp-sudoku`, `csp-nqueens`, `csp-wordsum` and `csp-scheduling`.


## API Documentation
In order to generate the API documentation, run `./gradlew dokka`.
The result will be in `$project/build/javadoc`.
Alternatively, run `./gradlew $project:dokka` to generate the docs for `$project`.
_(There is documentation for `csp-framework` only.)_


## Benchmarking
If you want to see which strategy (together with its parameters) is better for a given problem, you can use `Benchmark`.
It runs the problem several times and takes the average amount of checks and time it's taken to solve.

For example, to see the difference between pruning schemes, we'd write the following:

```kotlin
val runs = 5
val warmup = 2 // run 2 times beforehand for each strategy
val benchmark = Benchmark(problem, runs, warmup, mapOf(
    "no prune" to Backtracking(),
    "FC"       to Backtracking(pruneSchema = ForwardChecking()),
    "PLA"      to Backtracking(pruneSchema = PartialLookAhead()),
    "FLA"      to Backtracking(pruneSchema = FullLookAhead())
))

benchmark.execute().prettyPrint()
```
