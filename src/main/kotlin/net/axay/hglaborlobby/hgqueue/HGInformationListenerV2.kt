package net.axay.hglaborlobby.hgqueue

import de.hglabor.utils.noriskutils.jedis.JChannels
import de.hglabor.utils.noriskutils.queue.hg.HGGameInfo
import net.axay.hglaborlobby.main.gson
import net.axay.kspigot.gui.ForInventoryThreeByNine
import net.axay.kspigot.gui.elements.GUICompoundElement
import org.bukkit.inventory.ItemStack
import redis.clients.jedis.JedisPubSub

object HGInformationListenerV2 : JedisPubSub() {
    override fun onMessage(channel: String?, message: String?) {
        when (channel) {
            JChannels.HGQUEUE_INFO -> {
                val hgInfos = gson.fromJson(message, HGGameInfo::class.java)
                val items = mutableListOf<ItemStack>()
                val compoundElements = mutableListOf<GUICompoundElement<ForInventoryThreeByNine>>()

                //TODO nur das eine halt updaten lel
                println(hgInfos.toString())
            }
        }
    }
}
