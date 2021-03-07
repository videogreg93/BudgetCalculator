package com.example.demo.view

import com.example.demo.controllers.ListController
import com.example.demo.models.Category
import com.example.demo.models.Expense
import com.example.demo.models.ExpenseResult
import com.example.demo.models.fromName
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Priority
import javafx.util.StringConverter
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    private val controller: ListController by inject()
    private val fileProperty = SimpleStringProperty()
    override val root =
        vbox {
            flowpane {
                vbox {
                    hbox {
                        textfield(fileProperty) {
                            isEditable = false
                        }
                        button("...") {
                            action {
                                val file = chooseFile(filters = emptyArray()).first()
                                fileProperty.set(file.name)
                                controller.load(file)
                            }
                        }
                    }

                    button("Save") {
                        action { controller.save() }
                    }
                    tableview(controller.expenses) {
                        prefWidth = 850.0
                        useMaxWidth = true
                        isEditable = true
                        column("Dates", Expense::date)
                        column("Description", Expense::description)
                        column("Category", Expense::category) {
                            makeEditable(object : StringConverter<Category>() {
                                override fun toString(item: Category): String {
                                    return item.toString()
                                }

                                override fun fromString(string: String): Category {
                                    return fromName(string)
                                }
                            })
                        }
                        column("Cost", Expense::cost).makeEditable()
                        column("Currency", Expense::currency)
                        column("Paid by Gregory", Expense::gregPaid)
                        column("Paid by Maxime", Expense::maximePaid)
                        onSelectionChange {
                            controller.selectedExpense.set(it)
                        }
                    }
                }
                vbox {
                    vbox {
                        textfield(controller.selectedExpense, converter = object : StringConverter<Expense>() {
                            override fun toString(item: Expense): String {
                                return item.category.csvName
                            }

                            override fun fromString(string: String): Expense {
                                return controller.selectedExpense.get().apply {
                                    category = fromName(string)
                                }
                            }
                        })
                        textfield(controller.selectedExpense, converter = object : StringConverter<Expense>() {
                            override fun toString(item: Expense): String {
                                return item.description
                            }

                            override fun fromString(string: String): Expense {
                                return controller.selectedExpense.get().apply {
                                    description = string
                                }
                            }
                        })
                    }
                }
            }
            tableview(controller.results) {
                readonlyColumn("Category", ExpenseResult::category)
                readonlyColumn("Total Spent", ExpenseResult::totalSpent)
                readonlyColumn("Monthly Average", ExpenseResult::averageMonthly)
            }
        }

}