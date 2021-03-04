package net.axay.hglaborlobby.minigames

import net.axay.hglaborlobby.minigames.rockpaperscissors.RockPaperScissors
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.event.listen
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent

object Minigames {
    val minigamePlayers = mutableMapOf<Player, Minigame>()

    fun enable() {
        listen<InventoryCloseEvent> {
            val player = it.player as Player
            if (!minigamePlayers.containsKey(player)) return@listen

            if (it.view.title == "${KColors.MAGENTA}Rock Paper Scissors") {
                val minigame = minigamePlayers[player] as RockPaperScissors
                if (!minigame.choice.containsKey(player)) {
                    minigame.abort(player)
                }
            }
        }
    }
}

enum class MinigameType {
    ROCK_PAPER_SCISSORS,
}

abstract class Minigame