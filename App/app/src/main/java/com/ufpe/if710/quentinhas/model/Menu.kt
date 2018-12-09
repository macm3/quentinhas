package com.ufpe.if710.quentinhas.model

class Menu(
    var providerID: String? = null,
    var title: String? = null,
    var protein: ArrayList<String> = arrayListOf(),
    var side: ArrayList<String> = arrayListOf(),
    var size: ArrayList<String> = arrayListOf(),
    var day: ArrayList<String> = arrayListOf()
) {
    constructor(): this("", "", arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf())
}