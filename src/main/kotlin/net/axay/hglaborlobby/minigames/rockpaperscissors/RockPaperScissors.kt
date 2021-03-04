package net.axay.hglaborlobby.minigames.rockpaperscissors

import net.axay.hglaborlobby.minigames.Minigame
import net.axay.hglaborlobby.minigames.Minigames
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.gui.openGUI
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.runnables.task
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class RockPaperScissors(val playerOne: Player, val playerTwo: Player) : Minigame() {
    enum class Type(val string: String, val icon: ItemStack) {
        ROCK("${KColors.DEEPPINK}Stein", itemStack(Material.STONE) { meta { name = "${KColors.DEEPPINK}Stein" } }),
        PAPER("${KColors.PURPLE}Papier", itemStack(Material.PAPER) { meta { name = "${KColors.PURPLE}Papier" } }),
        SCISSORS("${KColors.MAGENTA}Schere", itemStack(Material.SHEARS) { meta { name = "${KColors.MAGENTA}Schere" } })
    }

    val players = arrayListOf(playerOne, playerTwo)
    val choice = hashMapOf<Player, Type>()
    var winner: Player? = null
    var loser: Player? = null

    init {
        /*players.forEach { player ->
            player.closeInventory()
            Minigames.minigamePlayers[player] = this
            player.openGUI(RockPaperScissorsGUIs.chooseGUIBuilder(this))
        }*/
    }

    fun choose(player: Player, rockPaperScissorsType: Type) {
        broadcast(player.name + "chose")
        if (!choice.containsKey(player)) {
            player.sendMessage("${KColors.GRAY}Du hast dich für ${rockPaperScissorsType.string} ${KColors.GRAY}entschieden.")
            choice[player] = rockPaperScissorsType
        } else
            player.sendMessage("${KColors.GRAY}Du hast dich bereits entschieden.")

        if (ifBothChose()) {
            task(true, 10) {
                broadcast("both chose")
                searchForWinner()
            }
        }
    }

    private fun ifBothChose() = choice.containsKey(playerOne) && choice.containsKey(playerTwo)

    private fun searchForWinner() {
        val choiceOne = choice[playerOne]
        val choiceTwo = choice[playerTwo]

        winner = if ((choiceOne == Type.ROCK && choiceTwo == Type.SCISSORS) ||
            (choiceOne == Type.PAPER && choiceTwo == Type.ROCK) ||
            (choiceOne == Type.SCISSORS && choiceTwo == Type.PAPER)
        )
            playerOne
        else if ((choiceOne == Type.SCISSORS && choiceTwo == Type.ROCK) ||
            (choiceOne == Type.ROCK && choiceTwo == Type.PAPER) ||
            (choiceOne == Type.PAPER && choiceTwo == Type.SCISSORS)
        )
            playerTwo
        else null

        if (winner == playerOne)
            loser = playerTwo
        else if (winner == playerTwo)
            loser = playerOne
        else
            loser = null

        players.forEach {
            Minigames.minigamePlayers.remove(it)
            it.openGUI(RockPaperScissorsGUIs.resultGUIBuilder(this))

            if (winner != null)
                it.sendMessage(" ${KColors.DARKGRAY}| ${KColors.MAGENTA}Minigames ${KColors.DARKGRAY}» ${choice[winner]!!.string} ${KColors.GRAY}schlägt ${choice[loser]!!.string}${KColors.GRAY}. ${KColors.MAGENTA}${winner!!.name} gewinnt${KColors.GRAY}!")
            else
                it.sendMessage(" ${KColors.DARKGRAY}| ${KColors.MAGENTA}Minigames ${KColors.DARKGRAY}» ${KColors.GRAY}Beide hatten ${choice[playerOne]!!.string}${KColors.GRAY}. ${KColors.MAGENTA}Unentschieden${KColors.GRAY}!")
        }

    }

    fun abort(player: Player) {
        players.forEach { players ->
            players.playSound(players.location, Sound.ENTITY_VILLAGER_HURT, 3f, 1f)
            players.sendMessage("${KColors.MAGENTA}${player.name} ${KColors.GRAY}hat das ${KColors.HOTPINK}Spiel ${KColors.RED}abgebrochen${KColors.GRAY}.")
            Minigames.minigamePlayers.remove(players)
            players.closeInventory()
        }
    }
}
