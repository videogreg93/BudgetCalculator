package com.example.demo.models

enum class Category(val csvName: String) {
    Groceries("Groceries"),
    Dining_Out("Dining Out"),
    Telecom("TV/Phone/Internet"),
    Unknown("General");

    override fun toString(): String {
        return csvName
    }
}

fun fromName(name: String): Category {
    return Category.values().firstOrNull { it.csvName.contentEquals(name) } ?: Category.Unknown
}