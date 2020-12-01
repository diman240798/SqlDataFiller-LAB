package ru.sfedu.sqlserverdatafiller.sql

import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.StandardOpenOption

import javax.persistence.EntityManager

@Component
class SqlRunner(private val entityManager: EntityManager) {

    fun run(request: String, file: File) {
        val nativeQuery = entityManager.createNativeQuery(request)
        val start = System.currentTimeMillis()
        val resultList = nativeQuery.resultList
        val end = System.currentTimeMillis()
        val time = end - start
        Files.writeString(file.toPath(), "$time,", StandardOpenOption.APPEND)
    }
}

