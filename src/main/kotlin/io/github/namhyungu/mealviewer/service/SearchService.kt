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
package io.github.namhyungu.mealviewer.service

import io.github.namhyungu.mealviewer.data.NeisService
import io.github.namhyungu.mealviewer.model.SchoolResponse
import io.github.namhyungu.mealviewer.util.InvalidResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SearchService @Autowired constructor(private val neisService: NeisService) {

  suspend fun search(keyword: String, page: Int): SchoolResponse {
    val response = neisService.searchSchool(keyword, page)

    if (!response.isValid) {
      throw InvalidResponseException()
    }

    val schoolList = response.content!![1].schoolList!!
    return SchoolResponse.of(page, schoolList)
  }
}
