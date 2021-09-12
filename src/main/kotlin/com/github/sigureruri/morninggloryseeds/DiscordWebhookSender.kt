package com.github.sigureruri.morninggloryseeds

import com.github.shur.skystory.HttpClient
import com.github.shur.skystory.HttpConnectionException
import java.net.URL

class DiscordWebhookSender(private val webhookUrl: URL) {

    private val httpClient = HttpClient()

    fun sendMessage(message: String) {
        val headers = mapOf("User-Agent" to "MorningGlorySeeds")
        val json = """{"username":"MorningGlorySeeds","content":"$message"}""".replace("\n", "\\n")

        try {
            httpClient.postWithJson(webhookUrl, headers, json)
        } catch (e: HttpConnectionException) {
            e.originalException.printStackTrace()
        }
    }

}