package ru.sfedu.sqlserverdatafiller.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = arrayOf(UniqueConstraint(columnNames = arrayOf("league", "year"))))
data class Tournament(
        val league: String,
        val year: Int,
        @Id
        val id: UUID = UUID.randomUUID()
)