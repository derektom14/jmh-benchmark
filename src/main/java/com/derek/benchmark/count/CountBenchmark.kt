package com.derek.benchmark.count

fun countZerosBuiltIn(items: ByteArray): Int {
    return items.count { it == 0.toByte() }
}

fun countZerosAsInt(items: ByteArray): Int {
    return items.countAsInt { it == 0.toByte() }
}

fun countEvensBuiltIn(items: ByteArray): Int {
    return items.count { it.toInt() and 1 == 0 }
}

fun countEvensAsInt(items: ByteArray): Int {
    return items.countAsInt { it.toInt() and 1 == 0 }
}

private inline fun ByteArray.countAsInt(predicate: (Byte) -> Boolean): Int {
    var count = 0
    for (item in this) {
        count += if (predicate(item)) 1 else 0
    }
    return count
}
