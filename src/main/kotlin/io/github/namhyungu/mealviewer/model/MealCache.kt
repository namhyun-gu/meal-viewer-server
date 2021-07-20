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

import com.azure.spring.data.cosmos.core.mapping.Container
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue
import com.azure.spring.data.cosmos.core.mapping.PartitionKey
import org.springframework.data.annotation.Id

@Container(containerName = "Cache")
data class MealCache(
  @Id @GeneratedValue val id: String? = null,
  @PartitionKey val cacheKey: String,
  val orgCode: String,
  val schoolCode: String,
  val date: String,
  val type: String,
  val value: MealResponse
)
