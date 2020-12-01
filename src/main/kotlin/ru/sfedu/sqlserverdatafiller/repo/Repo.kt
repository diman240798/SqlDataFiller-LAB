package ru.sfedu.sqlserverdatafiller.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.sfedu.sqlserverdatafiller.model.*
import java.util.*

interface ChampionRepo : JpaRepository<Champion, String>

interface ChampionsPickRepo : JpaRepository<ChampionsPick, UUID>

interface GoldRepo : JpaRepository<Gold, UUID>

interface MatchRepo : JpaRepository<Match, UUID>

interface MatchResultRepo : JpaRepository<MatchResult, UUID>

interface MonsterRepo : JpaRepository<Monster, UUID>

interface TakenMonsterRepo : JpaRepository<TakenMonster, UUID>

interface TeamRepo : JpaRepository<Team, UUID> {
    fun getByTournamentAndTeamName(tournament: Tournament, redTeamTag: String): Team?
}

interface TournamentRepo : JpaRepository<Tournament, UUID> {
    fun getByLeagueAndYear(league: String, year: Int): Tournament?
}