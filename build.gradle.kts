plugins {
    java
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.springai"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring AI models
    implementation("org.springframework.ai:spring-ai-starter-model-openai")
    implementation("org.springframework.ai:spring-ai-starter-model-anthropic")

    // Vector Stores
    implementation("org.springframework.ai:spring-ai-advisors-vector-store")
    // Note: Redis vector store dependency should be added only when needed:
    // implementation("org.springframework.ai:spring-ai-starter-vector-store-redis")

    // Document Readers
    implementation("org.springframework.ai:spring-ai-jsoup-document-reader")
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")

    // MCP (Model Context Protocol) Support
    implementation("org.springframework.ai:spring-ai-starter-mcp-client")
    implementation("org.springframework.ai:spring-ai-starter-mcp-server")
    implementation("org.springframework.ai:spring-ai-starter-mcp-server-webmvc")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        // Exclude the outdated android-json to avoid conflict with the newer org.json:json
        exclude(group = "com.vaadin.external.google", module = "android-json")
    }
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs = listOf("-Xshare:off", "-XX:+EnableDynamicAgentLoading")
}
