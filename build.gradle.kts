import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
	kotlin("plugin.jpa") version "1.4.21"
	kotlin("plugin.allopen") version "1.3.61"
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

group = "com.spring_boot"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("junit:junit:4.13")
	testImplementation("com.tngtech.java:junit-dataprovider:1.13.1")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("com.ninja-squad:springmockk:3.0.1")
	runtimeOnly("com.h2database:h2")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.6.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("com.google.code.gson:gson:2.8.5")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.valiktor:valiktor-core:0.12.0")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
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