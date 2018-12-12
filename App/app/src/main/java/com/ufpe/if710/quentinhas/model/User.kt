package com.ufpe.if710.quentinhas.model

class User(
        var userID: String? = null,
        var restaurant: String? = null,
        var name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var paymentMethods: ArrayList<PaymentMethod> = arrayListOf(),
        var endTime: String? = null,
        var pickupTime:  String? = null,
        var address: String? = null,
        var menus: ArrayList<String> = arrayListOf(),
        var provider: Boolean? = null
) {
        constructor(): this("",null, "", "", "", arrayListOf(), null, null, null, arrayListOf(), null)

        enum class PaymentMethod {
                CASH, PAGSEGURO
        }
}