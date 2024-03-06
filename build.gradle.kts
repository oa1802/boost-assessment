plugins {
    id("java")
}

group = "com.boostb2b"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.18.1")
    implementation("commons-io:commons-io:2.15.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.18.1")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")

}

tasks.test {
    useJUnitPlatform()
}