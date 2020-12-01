package ru.sfedu.sqlserverdatafiller.model

import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Gold(
        @ElementCollection
        val goldPerMinute: List<Int>,
        @Id
        val id: UUID = UUID.randomUUID()
)

fun String.toGold(): Gold {
    val goldPerMinute = this.substring(1, this.length - 1).split(", ").map { it.toInt() }
    return Gold(goldPerMinute)
}