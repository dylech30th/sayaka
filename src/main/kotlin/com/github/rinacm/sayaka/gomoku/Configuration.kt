package com.github.rinacm.sayaka.gomoku

object GameConfiguration {
    var gameRootDirectory = "gomoku-data"
    var gameRecordDirectory: (String) -> String = { "$gameRootDirectory\\data\\image\\$it" }
}