This package compares two Kotlin implementations of the Renaissance Benchmark Suite's Scrabble benchmark.

`KotlinScrabbleFunctional` uses typical functional methods on `Iterable` and `Sequence`, while `KotlinScrabbeImperative` replaces all `Sequence` uses with imperative calls, to demonstrate the performance penalty of `Sequence`s and their non-inlined lambdas.

A sample benchmark output:


```
Benchmark                                   Mode  Cnt    Score    Error  Units
ScrabbleBenchmark.kotlinScrabbleFunctional  avgt   20  627.553 ± 10.382  ms/op
ScrabbleBenchmark.kotlinScrabbleImperative  avgt   20  544.754 ± 13.472  ms/op
```