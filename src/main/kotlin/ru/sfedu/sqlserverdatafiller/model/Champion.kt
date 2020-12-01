package ru.sfedu.sqlserverdatafiller.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Champion(
        @Id
        val name: String
)