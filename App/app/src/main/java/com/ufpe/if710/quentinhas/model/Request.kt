package com.ufpe.if710.quentinhas.model

class Request (
    var clientId: String? = null,
    var providerID: String? = null,
    var protein: ArrayList<String> = arrayListOf(),
    var side: ArrayList<String> = arrayListOf(),
    var size: Int? = null,
    var urlPic: String? = null,
    var urlQrCode: String? = null

){
    constructor(): this("", "", arrayListOf(), arrayListOf(), null, null, null )
}


