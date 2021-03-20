package net.axay.hglaborlobby.config

import net.axay.blueutils.database.DatabaseLoginInformation
import net.axay.hglaborlobby.data.config.IPServiceConfig
import net.axay.hglaborlobby.main.Manager
import net.axay.kspigot.config.PluginFile
import net.axay.kspigot.config.kSpigotJsonConfig

object ConfigManager {

    fun enable() {
        val plugin = Manager
        plugin.config.addDefault("redispw", "password")
        plugin.config.options().copyDefaults(true)
        plugin.saveConfig()
    }

    val databaseLoginInformation
            by kSpigotJsonConfig(PluginFile("databaseLoginInformation.json")) {
                DatabaseLoginInformation.NOTSET_DEFAULT
            }

    val ipServiceConfig
            by kSpigotJsonConfig(PluginFile("ipServiceConfig.json"), true) {
                IPServiceConfig()
            }

    fun getInteger(key: String) = Manager.config.getInt(key)
    fun getDouble(key: String) = Manager.config.getDouble(key)
    fun getString(key: String) = Manager.config.getString(key)
    fun getLong(key: String) = Manager.config.getLong(key)
    fun getLocation(key: String) = Manager.config.getLocation(key)
}
