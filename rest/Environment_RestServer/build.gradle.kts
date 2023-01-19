plugins {
    id("java")
    application
    war
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
    implementation(project(":general:EnvironmentI"))

}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.main.EnvironmentRestServer")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
val nameWar: String = "EnvironmentRestServer"

tasks.war {
    destinationDirectory.set(file("./webapp"))
    archiveBaseName.set(nameWar)
// from("webapp/index.html") // remove for "landing" page
    doLast {
        copy {
            println("*** copying war to root webapp folder ... ")
            val fromS: String = "${project.projectDir.absolutePath}\\webapp\\$nameWar.war"
            val intoS: String = "${rootProject.projectDir.absolutePath}\\webapps"
            from(fromS)
            into(intoS)
        }
    }
}
tasks.clean {
    val fN_a: String = "${project.projectDir.absolutePath}\\webapp\\$nameWar.war"
    val fN_b: String = "${rootProject.projectDir.absolutePath}\\webapps\\$nameWar.war"
    delete(files(fN_a))
    delete(files(fN_b))
}