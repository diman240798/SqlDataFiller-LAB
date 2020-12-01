package ru.sfedu.sqlserverdatafiller.csvmodel

data class KillsCsv(val url: String, val team: String, val teamName: String, val timeMinutes: String, val posX: Int, val posY: Int, val assists: List<String>)