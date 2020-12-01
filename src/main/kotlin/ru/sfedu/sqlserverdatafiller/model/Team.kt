package ru.sfedu.sqlserverdatafiller.model

import java.util.*
import javax.persistence.*

@Entity
@Table(uniqueConstraints = arrayOf(UniqueConstraint(columnNames = arrayOf("teamName", "tournament_id"))))
data class Team(
        @ManyToOne val tournament: Tournament,
        val teamName: String,
        val topPlayer: String,
        val junglePlayer: String,
        val midPlayer: String,
        val adcPlayer: String,
        val supportPlayer: String,
        @Id
        val id: UUID = UUID.randomUUID()
)
