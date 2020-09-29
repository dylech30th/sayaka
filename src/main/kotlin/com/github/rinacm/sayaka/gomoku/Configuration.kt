package com.github.rinacm.sayaka.gomoku

object GameConfiguration {
    var gameRootDirectory = "gomoku"
    var gameRecordDirectory: (String) -> String = { "$gameRootDirectory\\data\\image\\$it" }
}