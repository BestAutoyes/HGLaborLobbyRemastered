package net.axay.hglaborlobby.main

import de.hglabor.utils.noriskutils.jedis.JChannels
import de.hglabor.utils.noriskutils.jedis.JedisUtils
import net.axay.hglaborlobby.chat.ChatFormatter
import net.axay.hglaborlobby.config.ConfigManager
import net.axay.hglaborlobby.damager.DamageCommand
import net.axay.hglaborlobby.damager.Damager
import net.axay.hglaborlobby.data.database.ServerWarpPluginMessageListener
import net.axay.hglaborlobby.data.database.holder.PlayerSettingsHolder
import net.axay.hglaborlobby.database.DatabaseManager
import net.axay.hglaborlobby.eventmanager.joinserver.OnJoinManager
import net.axay.hglaborlobby.eventmanager.leaveserver.KickMessageListener
import net.axay.hglaborlobby.eventmanager.leaveserver.OnLeaveManager
import net.axay.hglaborlobby.functionality.ElytraLauncher
import net.axay.hglaborlobby.functionality.LobbyItems
import net.axay.hglaborlobby.functionality.SoupHealing
import net.axay.hglaborlobby.gui.guis.*
import net.axay.hglaborlobby.hgqueue.HGInformationListener
import net.axay.hglaborlobby.hgqueue.HGInformationListenerV2
import net.axay.hglaborlobby.hgqueue.HG_QUEUE
import net.axay.hglaborlobby.protection.ServerProtection
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.bukkit.info
import net.axay.kspigot.extensions.bukkit.register
import net.axay.kspigot.extensions.bukkit.success
import net.axay.kspigot.extensions.console
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.main.KSpigot
import net.axay.kspigot.sound.sound
import org.bukkit.Sound
import org.bukkit.plugin.messaging.StandardMessenger

class InternalMainClass : KSpigot() {

    companion object {
        lateinit var INSTANCE: InternalMainClass; private set
    }

    override fun load() {

        INSTANCE = this

        console.info("Loading Lobby plugin...")

    }

    override fun startup() {
        ConfigManager.enable()

        ServerProtection.enable()

        PlayerSettingsHolder.enable()

        SoupHealing.enable()
        OnJoinManager.enable()
        LobbyItems.enable()
        OnLeaveManager.enable()
        KickMessageListener.enable()
        ChatFormatter.enable()
        Damager.enable()
        ElytraLauncher.enable()

        AdminGUI.register("admingui")
        DamageCommand.register("damage")


        // Main GUI
        MainGUI.enable()
        ServerWarpsGUI.enable()
        WarpGUI.enable()
        HGQueueGUI.enable()
        PlayerVisiblityGUI.enable()
        //PrivacySettingsGUI.enable()

        server.messenger.registerIncomingPluginChannel(
                this,
                StandardMessenger.validateAndCorrectChannel("hglabor:hginformation"),
                HGInformationListener
        )
        server.messenger.registerOutgoingPluginChannel(this, HG_QUEUE)
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        server.messenger.registerIncomingPluginChannel(this, "BungeeCord", ServerWarpPluginMessageListener)
        JedisUtils.init(ConfigManager.getString("redispw"))
        JedisUtils.subscribe(HGInformationListenerV2, JChannels.HGQUEUE_INFO)

        broadcast("${KColors.MEDIUMSPRINGGREEN}-> ENABLED PLUGIN")
        onlinePlayers.forEach { it.sound(Sound.BLOCK_BEACON_ACTIVATE) }

        console.success("Lobby plugin enabled.")

    }

    override fun shutdown() {

        console.info("Shutting down Lobby plugin...")

        DatabaseManager.mongoDB.close()
        JedisUtils.closePool()

        broadcast("${KColors.TOMATO}-> DISABLING PLUGIN ${KColors.DARKGRAY}(maybe a reload)")
        onlinePlayers.forEach { it.sound(Sound.BLOCK_BEACON_DEACTIVATE) }

        console.success("Shut down Lobby plugin.")

    }

}

val Manager by lazy { InternalMainClass.INSTANCE }
