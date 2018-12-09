package com.ufpe.if710.quentinhas.model

class Menu(
    var menuID: String? = null,
    var providerID: String? = null,
    var title: String? = null,
    var protein: ArrayList<String> = arrayListOf(),
    var side: ArrayList<String> = arrayListOf(),
    var size: ArrayList<String> = arrayListOf(),
    var day: String? = null
) {
    constructor(): this("", "", "", arrayListOf(), arrayListOf(), arrayListOf(), "")
}