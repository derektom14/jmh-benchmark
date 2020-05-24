package com.derek.benchmark.scrabble

data class Count(var count: Int)

fun String.counts(): Map<Char, Int> {
    val map = mutableMapOf<Char, Count>()
    forEach { item ->
        map.getOrPut(item) { Count(0) }.count++
    }
    return map.mapValuesInPlace { it.value.count }
}

@Suppress("UNCHECKED_CAST")
inline fun <K, V, R> MutableMap<K, V>.mapValuesInPlace(f: (Map.Entry<K, V>) -> R): MutableMap<K, R> {
    entries.forEach {
        (it as MutableMap.MutableEntry<K, R>).setValue(f(it))
    }
    return (this as MutableMap<K, R>)
}