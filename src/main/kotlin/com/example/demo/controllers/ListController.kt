package com.example.demo.controllers

import com.example.demo.models.Expense
import com.example.demo.models.fromName
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import tornadofx.Controller
import tornadofx.observable
import java.io.File

class ListController: Controller() {
    val file = File("./assets/test.csv")
    val data = csvReader().readAllWithHeader(file).map { item ->
        Expense(
            item.getValue("Date"),
            item.getValue("Description"),
            fromName(item.getValue("Category")),
            item.getValue("Cost").toDouble(),
            item.getValue("Currency"),
            item.getValue("Gregory Fournier").toDouble(),
            item.getValue("Marie Maxime Lavallée").toDouble()
        )
    }.observable()
}