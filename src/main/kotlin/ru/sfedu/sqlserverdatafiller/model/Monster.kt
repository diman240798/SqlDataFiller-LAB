package ru.sfedu.sqlserverdatafiller.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = arrayOf(UniqueConstraint(columnNames = arrayOf("name", "type"))))
data class Monster(
        val name: String,
        val type: String,
        @Id
        val id: UUID = UUID.randomUUID()
)