plugins {
    `cpp-application`
}

val ip: String   = "127.0.0.1"
val port: String = "4949"

// task to run cpp program
tasks.register("run", Exec::class) {
    dependsOn("build")         // make sure project has been built
    group = "application"                // set task group
    standardInput = System.`in`       // enable commandline input
    val exeDir: String = "${buildDir}/exe/main/debug/"
    val exeFile: String = "Primitive_SocketClient"
    commandLine(exeDir+exeFile, port, ip) // start command in cmd shell
}
