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
package io.github.namhyungu.mealviewer.data

import com.squareup.moshi.Json

sealed class Response<T>(open val content: List<T>? = null) {
  val isValid: Boolean
    get() = content != null && content!!.size >= 2
}

data class Result(
  @Json(name = "CODE") val code: String? = null,
  @Json(name = "MESSAGE") val message: String? = null,
)

data class Head(
  @Json(name = "list_total_count") val listTotalCount: Int? = null,
  @Json(name = "RESULT") val result: Result? = null,
)

data class MealResult(
  @Json(name = "mealServiceDietInfo") override val content: List<MealContent>? = null,
) : Response<MealResult.MealContent>(content) {

  data class MealContent(
    @Json(name = "head") val head: List<Head>? = null,
    @Json(name = "row") val mealList: List<Meal>? = null,
  )

  data class Meal(
    /** 요리명 */
    @Json(name = "DDISH_NM") private val dishInfo: String,
    /** 원산지정보 */
    @Json(name = "ORPLC_INFO") private val originInfo: String,
    /** 칼로리정보 */
    @Json(name = "CAL_INFO") val calorie: String,
    /** 영양정보 */
    @Json(name = "NTR_INFO") private val nutritionInfo: String,
  ) {
    val dishList: List<String> = dishInfo.split("<br/>")

    val originList: List<String> = originInfo.split("<br/>")

    val nutritionList: List<String> = nutritionInfo.split("<br/>")
  }
}

data class SearchResult(
  @Json(name = "schoolInfo") override val content: List<SearchContent>? = null,
) : Response<SearchResult.SearchContent>(content) {

  data class SearchContent(
    @Json(name = "head") val head: List<Head>? = null,
    @Json(name = "row") val schoolList: List<School>? = null,
  )

  data class School(
    /** 시도교육청코드 */
    @Json(name = "ATPT_OFCDC_SC_CODE") val orgCode: String,
    /** 시도교육청명 */
    @Json(name = "ATPT_OFCDC_SC_NM") val orgName: String,
    /** 표준학교코드 */
    @Json(name = "SD_SCHUL_CODE") val code: String,
    /** 학교명 */
    @Json(name = "SCHUL_NM") val name: String,
    /** 학교종류명 */
    @Json(name = "SCHUL_KND_SC_NM") val kind: String,
    /** 소재지명 */
    @Json(name = "LCTN_SC_NM") val location: String,
  )
}
