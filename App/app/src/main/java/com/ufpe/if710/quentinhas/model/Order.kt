package com.ufpe.if710.quentinhas.model

class Order (
    var orderID: String? = null,
    var clientID: String? = null,
    var providerID: String? = null,
    var protein: String? = null,
    var side: ArrayList<String> = arrayListOf(),
    var size: String? = null,
    var urlQrCode: String? = null

){
    constructor(): this( "", "", "", "", arrayListOf(), null, null)
}


