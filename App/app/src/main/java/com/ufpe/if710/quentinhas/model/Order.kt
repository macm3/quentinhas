package com.ufpe.if710.quentinhas.model

class Order (
    var clientID: String? = null,
    var providerID: String? = null,
    var protein: String? = null,
    var side: ArrayList<String> = arrayListOf(),
    var size: Int? = null,
    var urlQrCode: String? = null

){
    constructor(): this("", "", "", arrayListOf(), null, null)
}


