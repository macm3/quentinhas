package com.ufpe.if710.quentinhas.model

class User(
        var restaurant: String? = null,
        var name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var paymentMethods: ArrayList<PaymentMethod>? = null,
        var endTime: String? = null,
        var pickupTime:  String? = null,
        var address: String? = null,
        var menus: ArrayList<Menu>? = null,
        var provider: Boolean? = null
) {
        constructor(): this("", "", "", "", null, "", "", "", null, null)

        enum class PaymentMethod {
                CASH, PAGSEGURO
        }
}