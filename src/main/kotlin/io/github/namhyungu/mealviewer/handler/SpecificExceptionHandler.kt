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
package io.github.namhyungu.mealviewer.handler

import io.github.namhyungu.mealviewer.model.ErrorResponse
import io.github.namhyungu.mealviewer.util.InvalidResponseException
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class SpecificExceptionHandler {
  private val logger = LoggerFactory.getLogger(this.javaClass)

  @ExceptionHandler(InvalidResponseException::class)
  fun handleInvalidResponseException(
    invalidResponse: InvalidResponseException
  ): ResponseEntity<ErrorResponse> {
    logger.error("Occurred InvalidResponseException")

    val response = ErrorResponse(404, "Invalid response", "E01")
    return ResponseEntity(response, HttpStatus.NOT_FOUND)
  }

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
    logger.error("Occurred IllegalArgumentException")

    val response = ErrorResponse(400, "Invalid argument", "E02")
    return ResponseEntity(response, HttpStatus.BAD_REQUEST)
  }
}
