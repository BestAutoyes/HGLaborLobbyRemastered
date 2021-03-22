package net.axay.hglaborlobby.hgqueue

import de.hglabor.utils.noriskutils.jedis.JChannels
import de.hglabor.utils.noriskutils.queue.hg.HGGameInfo
import net.axay.hglaborlobby.gui.guis.HGQueueGUI
import net.axay.hglaborlobby.main.gson
import net.axay.kspigot.gui.ForInventoryThreeByNine
import net.axay.kspigot.gui.elements.GUICompoundElement
import org.bukkit.inventory.ItemStack
import redis.clients.jedis.JedisPubSub
import java.nio.charset.StandardCharsets

object HGInformationListenerV2 : JedisPubSub() {
    override fun onMessage(channel: String?, message: String?) {
        when (channel) {
            JChannels.HGQUEUE_INFO -> {
                val hgInfos = gson.fromJson(message, HGGameInfo::class.java)
                val items = mutableListOf<ItemStack>()
                val compoundElements = mutableListOf<GUICompoundElement<ForInventoryThreeByNine>>()

                //TODO nur das eine halt updaten lel
                hgInfos.sortedWith(compareBy<HGInfo> { it.gameState }.thenByDescending { it.onlinePlayers }.thenBy { it.serverName })
                        .forEach { hgInfo ->
                            hgInfo.item = hgInfo.getNewItem()
                            items.add(hgInfo.item!!)
                            HGInfo.infos[hgInfo.serverName!!] = hgInfo
                        }
                items.forEach { compoundElements.add(HGQueueGUI.HGQueueGUICompoundElement(it)) }
                HGQueueGUI.setContent(compoundElements)

                println(hgInfos.toString())
            }
        }
    }
}
