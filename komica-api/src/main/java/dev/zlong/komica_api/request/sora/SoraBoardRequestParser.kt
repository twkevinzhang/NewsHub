package dev.zlong.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import dev.zlong.komica_api.*
import dev.zlong.komica_api.model.KBoard

class SoraBoardRequestParser {
    private lateinit var req: Request

    fun req(req: Request): SoraBoardRequestParser {
        this.req = req
        return this
    }

    fun baseUrl(): HttpUrl {
        return req.url.newBuilder().removeFilename().build()
    }
}