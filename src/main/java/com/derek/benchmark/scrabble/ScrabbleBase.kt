package com.derek.benchmark.scrabble

import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

data class ScrabbleBase(
    val availableWords: Array<String>,
    val scrabbleWords: Set<String>
)

fun readScrabbleBaseFrom(availablePath: String, scrabblePath: String): ScrabbleBase {
    return ScrabbleBase(
            resourceAsWords(availablePath),
            resourceAsWords(scrabblePath).asList().toHashSet()
    )
}

private fun resourceAsWords(resource: String): Array<String> {
    return IOUtils.toString(
            ScrabbleBase::class.java.getResourceAsStream(resource),
            StandardCharsets.UTF_8
    ).split("\\s+".toRegex()).toTypedArray()
}