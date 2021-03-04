package net.axay.hglaborlobby.minigames.tictactoe

import net.axay.kspigot.gui.*

object TicTacToeGUI {
    fun guiBuilder(ticTacToe: TicTacToe): GUI<ForInventoryFiveByNine> {
        val gui = kSpigotGUI(GUIType.FIVE_BY_NINE) {

        }
        return gui
    }
}