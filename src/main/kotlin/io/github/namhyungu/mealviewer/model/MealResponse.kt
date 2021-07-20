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

import io.github.namhyungu.mealviewer.data.MealResult
import io.github.namhyungu.mealviewer.util.parseAllergyInfo

data class MealResponse(
  val calorie: String,
  val dishes: List<Dish>,
  val origins: List<Origin>,
  val nutrition: List<Nutrition>
) {
  companion object {
    fun of(meal: MealResult.Meal): MealResponse {
      val dishes =
        meal.dishList.map { dish ->
          val (foodName, allergy) = parseAllergyInfo(dish)
          Dish(foodName, allergy)
        }
      val origins =
        meal.originList.map {
          val (food, origin) = it.split(" : ")
          Origin(food, origin)
        }
      val nutrition =
        meal.nutritionList.map {
          val (name, value) = it.split(" : ")
          Nutrition(name, value.toDouble())
        }

      return MealResponse(meal.calorie, dishes, origins, nutrition)
    }
  }
}

data class Dish(val name: String, val allergy: List<Int>)

data class Origin(val food: String, val origin: String)

data class Nutrition(val name: String, val value: Double)
