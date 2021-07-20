import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.asciidoctor.convert") version "1.5.8"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.5.20"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.5.20"
	kotlin("jvm") version "1.5.20"
	kotlin("plugin.spring") version "1.5.20"
	id("com.diffplug.spotless") version "5.14.1"
}

group = "io.github.namhyungu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")
extra["azureVersion"] = "3.6.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// Azure
	implementation("com.azure.spring:azure-spring-boot-starter")
	implementation("com.azure.spring:azure-spring-boot-starter-cosmos")

	// Retrofit
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

	// Moshi
	implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
	implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.batch:spring-batch-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
	imports {
		mavenBom("com.azure.spring:azure-spring-boot-bom:${property("azureVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	project.property("snippetsDir")?.let {
		outputs.dir(it)
	}
}

tasks.asciidoctor {
	project.property("snippetsDir")?.let {
		inputs.dir(it)
	}
	dependsOn(tasks.test)
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
	kotlin {
		target("src/**/*.kt")
		ktfmt().googleStyle()
		licenseHeaderFile("${rootDir}/spotless.license.kt")
	}
}