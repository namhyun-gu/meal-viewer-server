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
package io.github.namhyungu.mealviewer.configuration

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.namhyungu.mealviewer.data.NeisService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Configuration
class RetrofitConfiguration {
  @Value("\${service.api-key}") private lateinit var apiKey: String

  private fun authInterceptor(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val newUrl =
      request
        .url()
        .newBuilder()
        .addQueryParameter("TYPE", "json")
        .addQueryParameter("KEY", apiKey)
        .build()
    val newRequest = request.newBuilder().url(newUrl).build()
    return chain.proceed(newRequest)
  }

  @Bean
  fun buildClient(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(::authInterceptor).build()
  }

  @Bean
  fun buildRetrofit(client: OkHttpClient): Retrofit {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    return Retrofit.Builder()
      .baseUrl("https://open.neis.go.kr")
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
  }

  @Bean
  fun buildNeisService(retrofit: Retrofit): NeisService {
    return retrofit.create()
  }
}
