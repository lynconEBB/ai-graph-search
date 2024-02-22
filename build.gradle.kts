plugins {
    id("java")
}

group = "unioeste.sd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("io.github.spair:imgui-java-app:1.86.11")
    implementation("guru.nidi:graphviz-java:0.18.1")
}

tasks.test {
    useJUnitPlatform()
}