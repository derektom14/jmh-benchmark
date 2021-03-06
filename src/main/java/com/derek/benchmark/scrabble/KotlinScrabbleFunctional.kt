/*
 * Copyright (C) 2014 Jose Paumard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.derek.benchmark.scrabble

import java.util.*
import java.util.regex.Pattern

class KotlinScrabbleFunctional(val scrabbleBase: ScrabbleBase) {

    private fun nBlanks(word: String): Int {
        return word.groupingBy { it }.eachCount()
                .entries
                .sumBy { entry: Map.Entry<Char, Int> ->
                    Integer.max(
                            0, entry.value - scrabbleAvailableLetters[entry.key - 'A']
                    )
                }
    }

    private fun score2(word: String): Int {
        return word.groupingBy { it }.eachCount()
                .entries
                .sumBy { entry: Map.Entry<Char, Int> ->
                    letterScores[entry.key - 'A'] *
                            Integer.min(
                                    entry.value,
                                    scrabbleAvailableLetters[entry.key - 'A']
                            )
                }
    }


    // score of the word put on the board
    private fun score3(word: String): Int {
        return (2 * (score2(word) + bonusForDoubleLetter(word))
                + if (word.length == 7) 50 else 0)
    }

    private fun bonusForDoubleLetter(word: String): Int {
        return (if (word.length > 6) word.take(3) + word.takeLast(3) else word)
                .map { letter: Char -> letterScores[letter - 'A'] }
                .max() ?: 0
    }

    private inline fun buildHistoOnScore(score: (String) -> Int): TreeMap<Int, MutableList<String>> {
        return shakespeareWordStream()
                .filter { word: String -> scrabbleBase.scrabbleWords.contains(word) && nBlanks(word) <= 2 } // filter out the words that needs more than 2 blanks
                .groupByTo(TreeMap(Comparator.reverseOrder()), score)
    }


    fun run(): List<Map.Entry<Int, List<String>>> { // Function to compute the score of a given word

        // Placing the word on the board
// Building the streams of first and last letters
        // Stream to be maxed
        // Stream to be maxed
        // Stream to be maxed




        // best key / value pairs
        return buildHistoOnScore(::score3).entries.take(3)
    }

    companion object {
        private val letterScores = intArrayOf(
                1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
        )
        private val scrabbleAvailableLetters = intArrayOf(
                9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
        )
    }
        private val nonAlphabetRegex = Pattern.compile(".*[^A-Z].*")
        private fun isAlphabetical(word: String): Boolean {
            return !nonAlphabetRegex.matcher(word).find()
        }

        private fun shakespeareWordStream(): Sequence<String> {
            return scrabbleBase.availableWords.asSequence()
                    .map { obj: String -> obj.toUpperCase() }
                    .filter { word: String -> isAlphabetical(word) }
        }

}

fun main() {
    println(KotlinScrabbleFunctional(readScrabbleBaseFrom("/shakespeare.txt", "/scrabble.txt")).run())
}