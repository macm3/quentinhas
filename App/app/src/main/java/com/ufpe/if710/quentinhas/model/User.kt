package com.ufpe.if710.quentinhas.model

class User(
        var restaurant: String? = null,
        var name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var provider: Boolean? = null
) {
        constructor(): this("", "", "", "", null)
}