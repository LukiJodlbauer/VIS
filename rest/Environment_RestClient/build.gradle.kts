plugins {
    id("java")
    application
}

group = "at.fhooe.sail.vis.main"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    // https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
// https://mvnrepository.com/artifact/org.glassfish.jersey.bundles/jaxrs-ri
    implementation("org.glassfish.jersey.bundles:jaxrs-ri:3.0.8")
// Moxy-JAXB (JSON)
// https://mvnrepository.com/artifact/org.glassfish.jersey.media/jersey-media-moxy
    implementation("org.glassfish.jersey.media:jersey-media-moxy:3.1.0")
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.main.Main")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}