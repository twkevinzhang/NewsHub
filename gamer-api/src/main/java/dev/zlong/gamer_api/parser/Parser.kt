package dev.zlong.gamer_api.parser

import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.jsoup.nodes.Element

interface Parser<T> {
    fun parse(body: ResponseBody, req: Request): T
}