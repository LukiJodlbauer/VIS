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
    implementation(project(":rmi:HelloWorld_RmiInterface"))
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.main.Main")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}