package com.example.demo.models

import java.time.LocalDate

data class Expense(
    var date: LocalDate,
    var description: String,
    var category: Category,
    var cost: Double,
    var currency: String,
    var gregPaid: Double,
    var maximePaid: Double
) {
    fun toCsv(): List<String> {
        return listOf(
            date.toString(),
            description,
            category.csvName,
            cost.toString(),
            currency,
            gregPaid.toString(),
            maximePaid.toString()
        )
    }
}
