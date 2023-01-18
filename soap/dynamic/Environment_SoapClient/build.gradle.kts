plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.0")
    implementation("jakarta.xml.ws:jakarta.xml.ws-api:4.0.0")
    implementation("com.sun.xml.ws:jaxws-rt:4.0.0")

    implementation(project(":general:EnvironmentI"))
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.soap.dynamic.environment.Main")
}
