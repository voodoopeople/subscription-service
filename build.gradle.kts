plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    java
    id("com.palantir.git-version") version "0.15.0"
}

group = "org.example"
//version = gitVersion()                            // версия из последнего Git-тега
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Spring (удаляем Logback, оставляем SLF4J Simple)
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }

    // База данных
    implementation("org.postgresql:postgresql")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // SLF4J API + Simple Logger
    implementation("org.slf4j:slf4j-api:2.0.7")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.7")

    // Liquibase
    implementation("org.liquibase:liquibase-core")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    // Swagger UI (Springdoc)
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    // Тестирование
    //testImplementation("org.springframework.boot:spring-boot-starter-test") {
        //exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    //}

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        // убираем стартер-логи и JUnit Vintage
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        exclude(group = "org.junit.vintage",       module = "junit-vintage-engine")
    }



    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
