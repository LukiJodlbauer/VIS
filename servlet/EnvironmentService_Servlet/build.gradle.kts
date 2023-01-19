plugins {
    war
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation(project(":general:EnvironmentI"))
    implementation(project(":soap:dynamic:Environment_SoapClient"))
    implementation(project(":rest:Environment_RestClient"))
    implementation("org.glassfish.jersey.bundles:jaxrs-ri:3.0.8")
    implementation(project(":rest:Environment_RestServer"))
    implementation("org.glassfish.jersey.media:jersey-media-moxy:3.1.0")
    implementation("jakarta.json:jakarta.json-api:2.1.1")
    // https://mvnrepository.com/artifact/org.eclipse.parsson/parsson
    implementation("org.eclipse.parsson:parsson:1.1.1")
    implementation("org.eclipse.persistence:org.eclipse.persistence.moxy:4.0.0-RC2")
}

val nameWar: String = "EnvironmentServiceServlet"

tasks.war {
    destinationDirectory.set(file("./webapp"))
    archiveBaseName.set(nameWar)
    from("webapp/index.html") // only integrate if index.html should be provided/used
    doLast {
        copy {
            println("*** copying war to root webapps folder ... ")
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

tasks.withType<Tar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Zip> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
