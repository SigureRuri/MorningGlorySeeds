package com.github.sigureruri.morninggloryseeds

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.net.URL

class MorningGlorySeeds : JavaPlugin() {

    private lateinit var webhookSender: DiscordWebhookSender

    override fun onEnable() {
        saveDefaultConfig()

        val webhookUrl = try {
            URL(config.getString("webhook"))
        } catch (e: Exception) {
            logger.warning("webhook url is not found")
            server.pluginManager.disablePlugin(this)
            return
        }
        webhookSender = DiscordWebhookSender(webhookUrl)
    }

    override fun onDisable() {
        val seeds = Bukkit.getWorlds().joinToString() { "${it.name}:${it.seed}" }
        webhookSender.sendMessage(seeds)
    }

}