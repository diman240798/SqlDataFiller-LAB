package ru.sfedu.sqlserverdatafiller.csvmodel

data class MatchInfoCsv(
        val league: String,
        val year: Int,
        val season: String,
        val type: String,
        val blueTeamName: String,

        val blueResult: Int,
        val redResult: Int,

        val redTeamName: String,
        val gamelengthMinutes: Int,

        val blueTop: String,
        val blueTopChamp: String,
        val blueJungle: String,
        val blueJungleChamp: String,
        val blueMiddle: String,
        val blueMiddleChamp: String,
        val blueADC: String,
        val blueADCChamp: String,
        val blueSupport: String,
        val blueSupportChamp: String,

        val redTop: String,
        val redTopChamp: String,
        val redJungle: String,
        val redJungleChamp: String,
        val redMiddle: String,
        val redMiddleChamp: String,
        val redADC: String,
        val redADCChamp: String,
        val redSupport: String,
        val redSupportChamp: String,

        val url: String
)