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
    implementation(project(":general:EnvironmentI"))
}
application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.main.RmiServer_Main")
}

tasks.register("runServiceMgmt", JavaExec::class) {
    group = "application"
    description = "Run the main class with JavaExecTask"
    standardInput = System.`in`       // enable commandline input
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("at.fhooe.sail.vis.main.ServiceMgmt")
}

tasks.register("killRmiServer", Exec::class) {
    group = "application"
    if (project.hasProperty("pid")) {
        commandLine("/bin/sh", "-c", "kill -9 ${project.findProperty("pid")}")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
