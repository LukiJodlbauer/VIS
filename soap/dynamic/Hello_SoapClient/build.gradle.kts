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
    compileOnly(project(":soap:dynamic:Hello_SoapServer"))
    // https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.0")
    // https://mvnrepository.com/artifact/jakarta.xml.ws/jakarta.xml.ws-api
    implementation("jakarta.xml.ws:jakarta.xml.ws-api:4.0.0")
    // https://mvnrepository.com/artifact/com.sun.xml.ws/jaxws-rt
    implementation("com.sun.xml.ws:jaxws-rt:4.0.0")
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.soap.dynamic.hellosoap.main.Main")
}
