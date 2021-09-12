package com.github.sigureruri.morninggloryseeds

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.net.URL

class MorningGlorySeeds : JavaPlugin() {

    private lateinit var webhookSender: DiscordWebhookSender

    override fun onEnable() {
        saveDefaultConfig()

        runCatching {
            val url = config.getString("webhook")
            URL(url)
        }.fold (
            onSuccess = { webhookSender = DiscordWebhookSender(it) },
            onFailure = {
                logger.severe("Webhook URL was not found, or we weren't able to set it.")
                logger.severe(it.toString())
                server.pluginManager.disablePlugin(this)
            }
        )
    }

    override fun onDisable() {
        val worlds = Bukkit.getWorlds()
        if (worlds.isNotEmpty()) {
            val message = worlds.joinToString("\n") { "${it.name}: ${it.seed}" }
            webhookSender.sendMessage("Worlds' Seeds\n$message")
        }
    }

}