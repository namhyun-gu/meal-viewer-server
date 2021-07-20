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

import io.github.namhyungu.mealviewer.data.MealCacheRepository
import io.github.namhyungu.mealviewer.data.NeisService
import io.github.namhyungu.mealviewer.model.MealCache
import io.github.namhyungu.mealviewer.model.MealResponse
import io.github.namhyungu.mealviewer.util.InvalidResponseException
import io.github.namhyungu.mealviewer.util.generateCacheKey
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MealService
@Autowired
constructor(
  private val neisService: NeisService,
  private val mealCacheRepository: MealCacheRepository,
) {
  suspend fun fetchFromOrigin(
    orgCode: String,
    schoolCode: String,
    date: String,
    type: String,
  ): MealResponse {
    val response = neisService.getMeal(orgCode, schoolCode, date, type)

    if (!response.isValid) {
      throw InvalidResponseException()
    }

    val meal = response.content!![1].mealList!!.first()
    return MealResponse.of(meal)
  }

  suspend fun fetchFromCache(
    orgCode: String,
    schoolCode: String,
    date: String,
    type: String,
  ): MealResponse? {
    val cacheKey = generateCacheKey(orgCode, schoolCode, date, type)
    val cache = mealCacheRepository.findByCacheKey(cacheKey) ?: return null
    return cache.value
  }

  suspend fun saveToCache(
    orgCode: String,
    schoolCode: String,
    date: String,
    type: String,
    mealResponse: MealResponse
  ) {
    val cacheKey = generateCacheKey(orgCode, schoolCode, date, type)

    mealCacheRepository.save(
      MealCache(
        cacheKey = cacheKey,
        orgCode = orgCode,
        schoolCode = schoolCode,
        date = date,
        type = type,
        value = mealResponse
      )
    )
  }
}
