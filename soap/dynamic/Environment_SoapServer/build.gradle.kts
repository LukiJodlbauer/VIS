plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("jakarta.xml.ws:jakarta.xml.ws-api:4.0.0")
    implementation("com.sun.xml.ws:jaxws-rt:4.0.0")

    implementation("org.eclipse.parsson:parsson:1.1.1")
    implementation("jakarta.json:jakarta.json-api:2.1.1")
    implementation("org.eclipse.persistence:org.eclipse.persistence.moxy:4.0.0-RC2")

    implementation(project(":general:EnvironmentI"))
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.soap.dynamic.environment.Main")
}
