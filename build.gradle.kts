plugins {
    id("java")
}

group = "unioeste.sd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:3.3.3"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.lwjgl:lwjgl-stb")
    implementation("io.github.spair:imgui-java-app:1.86.11")
    implementation("guru.nidi:graphviz-java:0.18.1")

}

tasks.test {
    useJUnitPlatform()
}