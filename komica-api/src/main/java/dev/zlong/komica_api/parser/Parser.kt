package dev.zlong.komica_api.parser

import org.jsoup.nodes.Element

interface Parser<T> {
    fun parse(source: Element, url: String): T
}