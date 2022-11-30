plugins {
    `cpp-application`
}
var port = "4949"
var ip = "127.0.0.1"
// task to run cpp program
tasks.register("run", Exec::class) {
    dependsOn("build")         // make sure project has been built
    group = "application"                // set task group
    standardInput = System.`in`       // enable commandline input
    val exeDir: String = "${buildDir}/exe/main/debug/"
    val exeFile: String = "Ipv6_SocketClient"
    commandLine( exeDir+exeFile,port,ip) // start command in cmd shell
}