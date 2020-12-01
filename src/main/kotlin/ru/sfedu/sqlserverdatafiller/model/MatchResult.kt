package ru.sfedu.sqlserverdatafiller.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class MatchResult(
        val bKills: Int,
        val rKills: Int,
        val bResult: Int,
        val rResult: Int,
        @OneToOne val bGold: Gold,
        @OneToOne val rGold: Gold,
        @Id
        val id: UUID = UUID.randomUUID()
)