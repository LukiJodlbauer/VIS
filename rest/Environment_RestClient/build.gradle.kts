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
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
//    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("org.glassfish.jersey.bundles:jaxrs-ri:3.0.8")
//    implementation("jakarta.json:jakarta.json-api:2.1.1")
    implementation("org.eclipse.persistence:org.eclipse.persistence.moxy:4.0.0-RC2")

    implementation(project(":general:EnvironmentI"))
    implementation(project(":rest:Environment_RestServer"))
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.main.Main")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}