package com.example.demo.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.property
import java.time.LocalDate

class Expense(
    _date: LocalDate,
    _description: String,
    _category: Category,
    _cost: Double,
    _currency: String,
    _gregPaid: Double,
    _maximePaid: Double
) {

    var date: LocalDate by property(_date)
    var description: String by property(_description)
    var category: Category by property(_category)
    var cost: Double by property(_cost)
    var currency: String by property(_currency)
    var gregPaid: Double by property(_gregPaid)
    var maximePaid: Double by property(_maximePaid)

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
