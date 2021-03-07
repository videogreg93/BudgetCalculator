package com.example.demo.controllers

import com.example.demo.models.Category
import com.example.demo.models.Expense
import com.example.demo.models.ExpenseResult
import com.example.demo.models.fromName
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ListChangeListener
import tornadofx.Controller
import tornadofx.observable
import java.io.File
import java.time.LocalDate

class ListController : Controller() {
    val file = File("./assets/test.csv")
    private val items = csvReader().readAllWithHeader(file).map { item ->
        toExpense(item)
    }.filterData()
    val expenses = items.observable().apply {
        addListener(ListChangeListener<Expense> { c -> updateResults(c.list.toList()) })
    }
    val results = mutableListOf<ExpenseResult>().observable()

    val selectedExpense = SimpleObjectProperty(expenses.first())

    init {
        updateResults(items)
    }

    private fun toExpense(item: Map<String, String>) = Expense(
        LocalDate.parse(item.getValue("Date")),
        item.getValue("Description"),
        fromName(item.getValue("Category")),
        item.getValue("Cost").toDouble(),
        item.getValue("Currency"),
        item.getValue("Gregory Fournier").toDouble(),
        item.getValue("Marie Maxime Lavallée").toDouble()
    )

    fun load(file: File) {
        expenses.setAll(csvReader().readAllWithHeader(file).map {
            toExpense(it)
        }.filterData())
    }

    fun save() {
        val toSave = listOf(
            listOf(
                "Date",
                "Description",
                "Category",
                "Cost",
                "Currency",
                "Gregory Fournier",
                "Marie Maxime Lavallée"
            )
        ) + expenses.map { it.toCsv() }
        csvWriter().writeAll(toSave, "./assets/test2.csv")
    }

    private fun List<Expense>.filterData(): List<Expense> {
        return this
//        return filter { expense ->
//            expense.date.year == 2021
//        }
    }

    private fun updateResults(values: List<Expense>) {
        val itemsPerMonth = values.groupBy { it.date.month }.values.map {
            it.sumByDouble { it.cost }
        }.toTypedArray()
        val items = Category.values().map { category ->
            val categoryItems = values.filter { it.category == category }
            val sumsPerMonth = categoryItems.groupBy { it.date.month }.values.map {
                it.sumByDouble { it.cost }
            }.toTypedArray()
            ExpenseResult(
                category.csvName,
                categoryItems.sumByDouble { it.cost },
                sumsPerMonth.average()
            )
        } + ExpenseResult(
            "All",
            values.sumByDouble { it.cost },
            itemsPerMonth.average()
        )
        results.setAll(items)
    }
}