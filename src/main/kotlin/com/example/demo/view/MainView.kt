package com.example.demo.view

import com.example.demo.controllers.ListController
import com.example.demo.models.Expense
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    private val controller: ListController by inject()

    override val root =
        tableview(controller.data) {
            column("Dates", Expense::date)4
            column("Description", Expense::description)
            column("Category", Expense::category) {
                makeEditable()
            }
            column("Cost", Expense::cost)
            column("Currency", Expense::currency)
            column("Paid by Gregory", Expense::gregPaid)
            column("Paid by Maxime", Expense::maximePaid)
        }

}