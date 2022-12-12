plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation(project(":general:EnvironmentI"))
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.main.EnvironmentClient_Main")
}

var port = "4949"
var ip   = "10.29.19.91"

tasks.named<JavaExec>("run") {
    args = listOf(port, ip)
}
