plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    //https://mvnrepository.com/artifact/org.eclipse.persistence/org.eclipse.persistence.moxy
    implementation("org.eclipse.persistence:org.eclipse.persistence.moxy:4.0.0-RC2")
    // https://mvnrepository.com/artifact/jakarta.json/jakarta.json-api
    implementation("jakarta.json:jakarta.json-api:2.1.1")
    // https://mvnrepository.com/artifact/org.eclipse.parsson/parsson
    implementation("org.eclipse.parsson:parsson:1.1.1")
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.jaxb.json.main.Main")
}
