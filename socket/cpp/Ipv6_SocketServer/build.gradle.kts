plugins {
    `cpp-application`
}
// task to run cpp program
tasks.register("run", Exec::class) {
    dependsOn("build")         // make sure project has been built
    group = "application"                // set task group
    standardInput = System.`in`       // enable commandline input
    val exeDir: String = "${buildDir}/exe/main/debug/"
    val exeFile: String = "Ipv6_SocketServer"
    commandLine( exeDir+exeFile) // start command in cmd shell
}

//tasks.register("kill", Exec::class){
//    group="application"
//
//    commandLine("fuser -k $port/tcp")
//}

//tasks["run"].dependsOn("kill")
//tasks["clean"].dependsOn("kill")