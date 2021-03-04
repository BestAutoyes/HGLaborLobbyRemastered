package net.axay.hglaborlobby.minigames.tictactoe

import net.axay.hglaborlobby.minigames.Minigame
import net.axay.hglaborlobby.minigames.Minigames
import org.bukkit.entity.Player

class TicTacToe(val playerOne: Player, val playerTwo: Player) : Minigame() {
    val slot = hashMapOf<Int, Player>()
    val players = arrayListOf(playerOne, playerTwo)
    var winner: Player? = null
    var loser: Player? = null

    init {
        players.forEach { player ->
            player.closeInventory()
            Minigames.minigamePlayers[player] = this
            //player.openGUI()
        }
    }



    fun checkForWinner(): Player? {
        val winningLines = listOf(
            Triple(0, 1, 2), // -
            Triple(3, 4, 5), // -
            Triple(6, 7, 8), // -
            Triple(0, 3, 6), // |
            Triple(1, 4, 7), // |
            Triple(2, 5, 8), // |
            Triple(0, 4, 8), // \
            Triple(2, 4, 6)) // /

        var player: Player? = null
        for ((x, y, z) in winningLines) {
            if (slot[x] != null && slot[x] == slot[y] && slot[x] == slot[z])
                player = slot[x]
        }
        return player
    }
}
