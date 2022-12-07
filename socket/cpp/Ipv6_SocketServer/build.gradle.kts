plugins {
    `cpp-application`
}

val port: String = "4949"
val ip:   String = "::1"

tasks.register("run", Exec::class) {
    dependsOn("build")         // make sure project has been built
    group = "application"                // set task group
    standardInput = System.`in`       // enable commandline input
    val exeDir: String = "${buildDir}/exe/main/debug/"
    val exeFile: String = "Ipv6_SocketServer"
    commandLine(exeDir+exeFile, port, ip) // start command in cmd shell
}

tasks.register("kill", Exec::class) {
    group = "application"
    commandLine("bash", "-c", "lsof -nti:${port} | xargs kill -9")
}

tasks["clean"].dependsOn("kill")