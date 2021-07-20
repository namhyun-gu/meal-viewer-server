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
package io.github.namhyungu.mealviewer.controller

import io.github.namhyungu.mealviewer.model.MealResponse
import io.github.namhyungu.mealviewer.service.MealService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/meal")
class MealController
@Autowired
constructor(
  val mealService: MealService,
) {
  private val logger = LoggerFactory.getLogger(this.javaClass)

  @GetMapping
  suspend fun getMeal(
    @RequestParam("orgCode", defaultValue = "") orgCode: String,
    @RequestParam("schoolCode", defaultValue = "") schoolCode: String,
    @RequestParam("date", defaultValue = "") date: String,
    @RequestParam("type", defaultValue = "") type: String,
    @RequestParam("force", required = false) force: Boolean = false
  ): ResponseEntity<MealResponse> {
    if (orgCode.isEmpty() || schoolCode.isEmpty() || date.isEmpty() || type.isEmpty()) {
      throw IllegalArgumentException()
    }

    val cache = mealService.fetchFromCache(orgCode, schoolCode, date, type)
    val meal =
      if (force || cache == null) {
        logger.info("getMeal() requested fetch from origin")

        mealService.fetchFromOrigin(orgCode, schoolCode, date, type).also {
          mealService.saveToCache(orgCode, schoolCode, date, type, it)
        }
      } else {
        logger.info("getMeal() requested fetch from cache")

        cache
      }
    return ResponseEntity.ok(meal)
  }
}
