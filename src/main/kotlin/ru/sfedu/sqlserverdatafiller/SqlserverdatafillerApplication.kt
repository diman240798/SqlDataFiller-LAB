package ru.sfedu.sqlserverdatafiller

import com.opencsv.bean.CsvToBeanBuilder
import com.opencsv.bean.HeaderColumnNameMappingStrategy
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import ru.sfedu.sqlserverdatafiller.csvmodel.LeagueOfLegends
import ru.sfedu.sqlserverdatafiller.model.*
import ru.sfedu.sqlserverdatafiller.repo.*
import ru.sfedu.sqlserverdatafiller.sql.NewRequests
import ru.sfedu.sqlserverdatafiller.sql.OldRequests
import ru.sfedu.sqlserverdatafiller.sql.SqlRunner
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Pattern
import javax.persistence.EntityManager


@SpringBootApplication
class SqlserverdatafillerApplication

fun main(args: Array<String>) {
//    val app = runApplication<SqlserverdatafillerApplication>(*args)


//    makeNewTimings(app)
//    fillDb(app)
    countAverage()
}

fun countAverage() {
    val OLD_TIMINGS = File("oldTimings")
    OLD_TIMINGS.mkdir()

    val oldFiles = List(10) { i ->
        File(OLD_TIMINGS, "OLD_$i.txt")
    }

    val NEW_TIMINGS = File("newTimings_ready")
    NEW_TIMINGS.mkdir()

    val newFiles = List(10) { i ->
        File(NEW_TIMINGS, "NEW_$i.txt")
    }

    for (i in 0 until 10) {
        val oldFile = oldFiles[i]
        val newFile = newFiles[i]

        val oldSum = Files.readString(oldFile.toPath()).split(",").filter { it.isNotBlank() }.map { it.toInt() }.average()
        val newSum = Files.readString(newFile.toPath()).split(",").filter { it.isNotBlank() }.map { it.toInt() }.average()

        println("Request ${i + 1}: old: $oldSum; new: $newSum, diff: ${(oldSum - newSum) / oldSum * 100}")
    }

}

private fun makeOldTimings(app: ConfigurableApplicationContext) {
    val OLD_TIMINGS = File("oldTimings")
    OLD_TIMINGS.mkdir()

    val oldFiles = List(10) { i ->
        File(OLD_TIMINGS, "OLD_$i.txt")
    }
    val requests = listOf(
            OldRequests.REQUEST_1, OldRequests.REQUEST_2,
            OldRequests.REQUEST_3, OldRequests.REQUEST_4,
            OldRequests.REQUEST_5, OldRequests.REQUEST_6,
            OldRequests.REQUEST_7, OldRequests.REQUEST_8,
            OldRequests.REQUEST_9, OldRequests.REQUEST_10
    )
    makeTimings(app, oldFiles, requests)
}

private fun makeNewTimings(app: ConfigurableApplicationContext) {
    val NEW_TIMINGS = File("newTimings")
    NEW_TIMINGS.mkdir()

    val oldFiles = List(10) { i ->
        File(NEW_TIMINGS, "NEW_$i.txt")
    }
    val requests = listOf(
            OldRequests.REQUEST_5
    )
    makeTimings(app, oldFiles, requests)
}

fun makeTimings(app: ConfigurableApplicationContext, files: List<File>, requests: List<String>) {
    val entityManager = app.getBean(EntityManager::class.java)
    val sqlRunner = SqlRunner(entityManager)

    files.forEach {
        it.createNewFile()
    }

    repeat(20) {
        for (i in 0 until requests.size) {
            sqlRunner.run(requests[i], files[i])
        }
    }
}

private fun fillDb(app: ConfigurableApplicationContext) {
    val championRepo = app.getBean(ChampionRepo::class.java)
    val championsPickRepo = app.getBean(ChampionsPickRepo::class.java)
    val goldRepo = app.getBean(GoldRepo::class.java)
    val matchRepo = app.getBean(MatchRepo::class.java)
    val matchResultRepo = app.getBean(MatchResultRepo::class.java)
    val monsterRepo = app.getBean(MonsterRepo::class.java)
    val takenMonsterRepo = app.getBean(TakenMonsterRepo::class.java)
    val teamRepo = app.getBean(TeamRepo::class.java)
    val tournamentRepo = app.getBean(TournamentRepo::class.java)

    val baron = Monster("Baron Nashor", "")
    val herold = Monster("Herold", "")
    val dragon = Monster("Dragon", "None")
    val dragonFire = Monster("Dragon", "Fire")
    val dragonWater = Monster("Dragon", "Water")
    val dragonEarth = Monster("Dragon", "Earth")
    val dragonAir = Monster("Dragon", "Air")
    val dragonElder = Monster("Dragon", "Elder")

    listOf(baron, herold, dragon, dragonFire, dragonWater, dragonEarth, dragonAir, dragonElder).forEach {
        monsterRepo.save(it)
    }


    val dragonsMap = hashMapOf(
            "None" to dragon,
            "'None" to dragon,
            "'FIRE_DRAGON'" to dragonFire,
            "'WATER_DRAGON'" to dragonWater,
            "'EARTH_DRAGON'" to dragonEarth,
            "'AIR_DRAGON'" to dragonAir,
            "'ELDER_DRAGON'" to dragonElder
    )

    val reader = FileReader(File(ClassLoader.getSystemResources("data/LeagueofLegends.csv").nextElement().file))


    try {
        reader.use { reader ->
            val ms = HeaderColumnNameMappingStrategy<LeagueOfLegends>()
            ms.type = LeagueOfLegends::class.java

            val cb = CsvToBeanBuilder<LeagueOfLegends>(reader)
                    .withMappingStrategy(ms)
                    .build()

            val parse = cb.parse()
            parse.forEach {

                tournamentRepo.getByLeagueAndYear(it.league, it.Year)
                var tournament = tournamentRepo.getByLeagueAndYear(it.league, it.Year)

                if (tournament == null) {
                    tournament = Tournament(it.league, it.Year)
                    tournamentRepo.save(tournament)
                }

                val redTopChamp = Champion(it.redTopChamp)
                val redJungleChamp = Champion(it.redJungleChamp)
                val redMiddleChamp = Champion(it.redMiddleChamp)
                val redADCChamp = Champion(it.redADCChamp)
                val redSupportChamp = Champion(it.redSupportChamp)

                listOf(redTopChamp, redJungleChamp, redMiddleChamp, redADCChamp, redSupportChamp).forEach {
                    championRepo.save(it)
                }

                val redPick = ChampionsPick(redTopChamp, redJungleChamp, redMiddleChamp, redADCChamp, redSupportChamp)

                championsPickRepo.save(redPick)

                val blueTopChamp = Champion(it.blueTopChamp)
                val blueJungleChamp = Champion(it.blueJungleChamp)
                val blueMiddleChamp = Champion(it.blueMiddleChamp)
                val blueADCChamp = Champion(it.blueADCChamp)
                val blueSupportChamp = Champion(it.blueSupportChamp)

                listOf(blueTopChamp, blueJungleChamp, blueMiddleChamp, blueADCChamp, blueSupportChamp).forEach {
                    championRepo.save(it)
                }

                val bluePick = ChampionsPick(blueTopChamp, blueJungleChamp, blueMiddleChamp, blueADCChamp, blueSupportChamp)

                championsPickRepo.save(bluePick)

                var rTeam = teamRepo.getByTournamentAndTeamName(tournament, it.redTeamTag)

                if (rTeam == null) {
                    rTeam = Team(tournament, it.redTeamTag, it.redTop, it.redJungle, it.redMiddle, it.redADC, it.redSupport)
                    teamRepo.save(rTeam)
                }

                var bTeam = teamRepo.getByTournamentAndTeamName(tournament, it.blueTeamTag)

                if (bTeam == null) {
                    bTeam = Team(tournament, it.blueTeamTag, it.blueTop, it.blueJungle, it.blueMiddle, it.blueADC, it.blueSupport)
                    teamRepo.save(bTeam)
                }


                val bGold = it.goldblue.toGold()
                goldRepo.save(bGold)

                val rGold = it.goldblue.toGold()
                goldRepo.save(rGold)


                val bKills = countKills(it.bKills)
                val rKills = countKills(it.rKills)

                val matchResult = MatchResult(bKills, rKills, it.bResult, it.rResult, bGold, rGold)
                matchResultRepo.save(matchResult)

                val match = Match(bluePick, redPick, rTeam, bTeam, matchResult, tournament)
                matchRepo.save(match)

                it.bHeralds.getHeraldTimes().forEach {
                    val takenMonster = TakenMonster(herold, matchResult, it, "blue")
                    takenMonsterRepo.save(takenMonster)
                }

                it.rHeralds.getHeraldTimes().forEach {
                    val takenMonster = TakenMonster(herold, matchResult, it, "red")
                    takenMonsterRepo.save(takenMonster)
                }

                it.bBarons.getHeraldTimes().forEach {
                    val takenMonster = TakenMonster(baron, matchResult, it, "blue")
                    takenMonsterRepo.save(takenMonster)
                }

                it.rBarons.getHeraldTimes().forEach {
                    val takenMonster = TakenMonster(baron, matchResult, it, "red")
                    takenMonsterRepo.save(takenMonster)
                }


                // "[[19.14, 'WATER_DRAGON'], [11.304, 'FIRE_DRAGON']
                try {
                    if (it.bDragons.length > 2) {
                        it.bDragons.substring(1, it.bDragons.length - 1).split("], ").forEach {
                            val (time, type) = it.substring(1, it.length - 1).split(", ")
                            val monster = dragonsMap.entries.first { it.key.startsWith(type) }.value
                            val takenMonster = TakenMonster(monster, matchResult, time.toDouble(), "blue")
                            takenMonsterRepo.save(takenMonster)
                        }
                    }

                    if (it.rDragons.length > 2) {
                        it.rDragons.substring(1, it.rDragons.length - 1).split("], ").forEach {
                            val (time, type) = it.substring(1, it.length - 1).split(", ")
                            val monster = dragonsMap.entries.first { it.key.startsWith(type) }.value
                            val takenMonster = TakenMonster(monster, matchResult, time.toDouble(), "blue")
                            takenMonsterRepo.save(takenMonster)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String.getHeraldTimes(): List<Double> {
    return if (this.length == 2) return emptyList() else this.substring(1, this.length - 1).map { it.toDouble() }
}

val KILLS_PATTERN = Pattern.compile("\\[\\d+.\\d+, '\\w+ \\w+', '\\w+ \\w+'")
fun countKills(kills: String): Int {
    val m = KILLS_PATTERN.matcher(kills)
    return m.results().count().toInt()
}