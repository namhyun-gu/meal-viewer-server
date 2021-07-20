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
package io.github.namhyungu.mealviewer.model

import io.github.namhyungu.mealviewer.data.SearchResult

data class SchoolResponse(val page: Int, val schoolList: List<School>) {
  companion object {
    fun of(page: Int, school: List<SearchResult.School>): SchoolResponse {
      return SchoolResponse(
        page,
        school.map { with(it) { School(orgCode, orgName, code, name, kind, location) } }
      )
    }
  }
}

data class School(
  val orgCode: String,
  val orgName: String,
  val code: String,
  val name: String,
  val kind: String,
  val location: String,
)