package com.github.rinacm.sayaka.gomoku

object GameConfiguration {
    var gameRootDirectory = "gomoku"
    var gameRecordDirectory: (String) -> String = { "$gameRootDirectory\\data\\image\\$it" }
    var bridgingPort = 12345
    var bridgingUrlBase = { "http://127.0.0.1:$bridgingPort" }
}