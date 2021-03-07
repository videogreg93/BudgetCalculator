package com.example.demo.models

data class Expense(
    var date: String,
    var description: String,
    var category: Category,
    var cost: Double,
    var currency: String,
    var gregPaid: Double,
    var maximePaid: Double
)
