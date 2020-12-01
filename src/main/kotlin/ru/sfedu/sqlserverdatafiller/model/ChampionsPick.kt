package ru.sfedu.sqlserverdatafiller.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class ChampionsPick(
        @OneToOne val top: Champion,
        @OneToOne val jungle: Champion,
        @OneToOne val mid: Champion,
        @OneToOne val adc: Champion,
        @OneToOne val support: Champion,
        @Id
        val id: UUID = UUID.randomUUID()
)
