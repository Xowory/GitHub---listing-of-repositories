import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
		jcenter()
	}

	dependencies {
		classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
	}
}

plugins {
	id("org.springframework.boot") version "2.6.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	id ("application")
}

application{
	mainClass
}

group = "io.github.Xowory"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("junit:junit:4.13.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation( "io.github.microutils:kotlin-logging-jvm:2.1.20")
	annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.apache.httpcomponents:httpclient:4.5")
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
