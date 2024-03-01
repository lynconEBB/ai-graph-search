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

    implementation("ch.qos.logback:logback-classic:1.5.0")
    implementation("guru.nidi:graphviz-java:0.18.1")
    implementation("org.graalvm.js:js:23.0.2")
    
    implementation("io.github.spair:imgui-java-app:1.86.11")
    implementation("org.lwjgl:lwjgl-stb")
    runtimeOnly("org.lwjgl:lwjgl-stb::natives-windows")

}

tasks.test {
    useJUnitPlatform()
}