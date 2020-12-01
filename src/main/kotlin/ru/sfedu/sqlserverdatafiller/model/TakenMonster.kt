package ru.sfedu.sqlserverdatafiller.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class TakenMonster(
        @OneToOne val monster: Monster,
        @OneToOne val matchResult: MatchResult,
        val time: Double,
        val teamColor: String,
        @Id
        val id: UUID = UUID.randomUUID()
)