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
package io.github.namhyungu.mealviewer.aspect

import io.github.namhyungu.mealviewer.util.executeTime
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {
  private val logger = LoggerFactory.getLogger(this.javaClass)

  @Around(
    "within(io.github.namhyungu.mealviewer.controller..*) || within(io.github.namhyungu.mealviewer.service..*)"
  )
  fun loggingController(joinPoint: ProceedingJoinPoint): Any? {
    var result: Any? = null
    logger.info("----------> Executed ${joinPoint.signature.name}")
    val time = executeTime { result = joinPoint.proceed() }
    logger.info("----------> Finished ${joinPoint.signature.name} ({} ms)", time)
    return result
  }
}
