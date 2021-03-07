package com.example.demo.view

import com.example.demo.controllers.ListController
import com.example.demo.models.Category
import com.example.demo.models.Expense
import com.example.demo.models.fromName
import javafx.util.StringConverter
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    private val controller: ListController by inject()

    override val root =
        vbox {
            button("Save") {
                action { controller.save() }
            }
            tableview(controller.data) {
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
                column("Cost", Expense::cost)
                column("Currency", Expense::currency)
                column("Paid by Gregory", Expense::gregPaid)
                column("Paid by Maxime", Expense::maximePaid)
            }
        }

}