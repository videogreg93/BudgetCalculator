package com.example.demo.controllers

import com.example.demo.models.Expense
import com.example.demo.models.fromName
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import tornadofx.Controller
import tornadofx.observable
import java.io.File
import java.time.LocalDate

class ListController: Controller() {
    val file = File("./assets/test.csv")
    val data = csvReader().readAllWithHeader(file).map { item ->
        Expense(
            LocalDate.parse(item.getValue("Date")),
            item.getValue("Description"),
            fromName(item.getValue("Category")),
            item.getValue("Cost").toDouble(),
            item.getValue("Currency"),
            item.getValue("Gregory Fournier").toDouble(),
            item.getValue("Marie Maxime Lavallée").toDouble()
        )
    }.filterData().observable()

    fun save() {
        val toSave = data.map { it.toCsv() }
        csvWriter().writeAll(toSave, "./assets/test2.csv")
    }

    private fun List<Expense>.filterData(): List<Expense> {
        return filter { expense ->
            expense.date.year == 2021
        }
    }
}