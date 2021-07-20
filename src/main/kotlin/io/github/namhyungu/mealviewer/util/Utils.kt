/*
 * Copyright 2021 Namhyun, Gu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.namhyungu.mealviewer.util

fun parseAllergyInfo(dish: String): Pair<String, List<Int>> {
  val allergyInfo = mutableListOf<Int>()
  val allergyParseRegex = """(.*?)(\d{1,2}).$""".toRegex()

  var temp = dish
  var matchResult: MatchResult? = allergyParseRegex.find(temp)

  while (matchResult != null) {
    val prefix = matchResult.groupValues[1]
    val allergyIndex = matchResult.groupValues[2].toInt()

    allergyInfo.add(allergyIndex)
    temp = prefix
    matchResult = allergyParseRegex.find(temp)
  }

  if (allergyInfo.isNotEmpty()) {
    allergyInfo.sort()
  }
  return temp to allergyInfo
}

fun generateCacheKey(
  orgCode: String,
  schoolCode: String,
  date: String,
  type: String,
) = "${orgCode}_${schoolCode}_${date}_${type}"

fun executeTime(scope: () -> Unit): Long {
  val startAt = System.currentTimeMillis()
  scope()
  val endAt = System.currentTimeMillis()
  return endAt - startAt
}
