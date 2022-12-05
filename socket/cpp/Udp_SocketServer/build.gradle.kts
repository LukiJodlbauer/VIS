plugins {
    `cpp-application`
}

val port: String = "6661"

tasks.register("run", Exec::class) {
    dependsOn("kill")
    dependsOn("build")         // make sure project has been built
    group = "application"                // set task group
    standardInput = System.`in`       // enable commandline input
    val exeDir: String = "${buildDir}/exe/main/debug/"
    val exeFile: String = "Udp_SocketServer"
    commandLine(exeDir+exeFile, port) // start command in cmd shell
}

tasks.register("kill", Exec::class) {
    group = "application"
    commandLine("bash", "-c", "lsof -nti:${port} | xargs kill -9")
}
