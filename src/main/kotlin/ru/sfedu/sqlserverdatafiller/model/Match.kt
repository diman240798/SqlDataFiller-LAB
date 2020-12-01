package ru.sfedu.sqlserverdatafiller.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class Match(
        @OneToOne val bComp: ChampionsPick,
        @OneToOne val rComp: ChampionsPick,
        @OneToOne val rTeam: Team,
        @OneToOne val bTeam: Team,
        @OneToOne val matchResult: MatchResult,
        @OneToOne val tournament: Tournament,
        @Id
        val id: UUID = UUID.randomUUID()
)