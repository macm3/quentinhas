package com.ufpe.if710.quentinhas.model

class Menu(
    var providerID: String? = null,
    var title: String? = null,
    var protein: ArrayList<String>? = null,
    var side: ArrayList<String>? = null,
    var size: ArrayList<String>? = null,
    var day: ArrayList<String>? = null
) {
    constructor(): this(null, null,null, null, null, null)
}