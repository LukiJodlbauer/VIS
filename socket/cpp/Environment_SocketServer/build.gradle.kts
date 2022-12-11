plugins {
    `cpp-application`
}

val port: String = "4949"

tasks.register("run", Exec::class) {
    dependsOn("build")         // make sure project has been built
    group = "application"                // set task group
    standardInput = System.`in`       // enable commandline input
    val exeDir: String = "${buildDir}/exe/main/debug/"
    val exeFile: String = "Environment_SocketServer"
    commandLine(exeDir+exeFile, port) // start command in cmd shell
}

tasks.register("kill", Exec::class) {
    group = "application"
    commandLine("sh", "-c", "fuser -k 4949/tcp")
}

