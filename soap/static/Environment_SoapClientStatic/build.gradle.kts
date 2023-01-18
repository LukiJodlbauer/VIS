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

sourceSets { // add generated WSDL-based code to the main source set
    main {
        java.srcDir("src/generated/java")
    }
}

tasks["compileJava"].dependsOn("compileWSDL")

tasks.register("compileWSDL", Exec::class) { // task to parse WSDL
    group = "JAXWS"
    val wsdlUrl: String = "http://localhost:8081/Environment_SoapServer?wsdl"
    val packageDir: String = "at.fhooe.sail.vis.soap.generated"
    workingDir("${projectDir}/src/generated/java")
    // command varies based on OS
    commandLine("sh", "-c", "wsimport -d . -keep -p $packageDir $wsdlUrl")

    workingDir("${projectDir}/src/generated/java/at/fhooe/sail/vis/soap/generated")
    var javaxToJakarta =
        "grep -rli 'javax.xml.bind' * | xargs -I@ sed -i '' -e 's/javax.xml.bind/jakarta.xml.bind/g' @ && " +
                "grep -rli 'javax.jws' * | xargs -I@ sed -i '' -e 's/javax.jws/jakarta.jws/g' @ && " +
                "grep -rli 'javax.xml.ws' * | xargs -I@ sed -i '' -e 's/javax.xml.ws/jakarta.xml.ws/g' @"
    commandLine("sh", "-c", javaxToJakarta)
}

tasks.clean {
    val ft: ConfigurableFileTree =
        fileTree("${project.projectDir.absolutePath}/src/generated/java")
    ft.visit {
        delete(this.file)
    }
}

application {
    // Define the main class for the application.
    mainClass.set("at.fhooe.sail.vis.soap.sstatic.environment.Main")
}
