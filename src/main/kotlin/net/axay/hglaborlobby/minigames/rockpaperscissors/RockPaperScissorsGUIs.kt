package net.axay.hglaborlobby.minigames.rockpaperscissors

import net.axay.kspigot.chat.KColors
import net.axay.kspigot.gui.*
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import org.bukkit.Material
import org.bukkit.inventory.meta.SkullMeta

object RockPaperScissorsGUIs {
    fun chooseGUIBuilder(rockPaperScissors: RockPaperScissors): GUI<ForInventoryThreeByNine> {

        val gui = kSpigotGUI(GUIType.THREE_BY_NINE) {

            title = "${KColors.MAGENTA}Rock Paper Scissors"

            page(1) {

                placeholder(Slots.All, itemStack(Material.WHITE_STAINED_GLASS_PANE) { meta { name = null } })
                placeholder(Slots.RowOneSlotTwo rectTo Slots.RowThreeSlotEight,
                    itemStack(Material.BLACK_STAINED_GLASS_PANE) { meta { name = null } })

                button(Slots.RowTwoSlotThree, RockPaperScissors.Type.ROCK.icon) {
                    rockPaperScissors.choose(it.player, RockPaperScissors.Type.ROCK)
                }

                button(Slots.RowTwoSlotFive, RockPaperScissors.Type.PAPER.icon) {
                    rockPaperScissors.choose(it.player, RockPaperScissors.Type.PAPER)
                }

                button(Slots.RowTwoSlotSeven, RockPaperScissors.Type.SCISSORS.icon) {
                    rockPaperScissors.choose(it.player, RockPaperScissors.Type.SCISSORS)
                }
            }
        }
        return gui
    }

    fun resultGUIBuilder(rockPaperScissors: RockPaperScissors): GUI<ForInventoryThreeByNine> {
        val playerOne = rockPaperScissors.playerOne
        val playerTwo = rockPaperScissors.playerTwo
        val winner = rockPaperScissors.winner
        val gui = kSpigotGUI(GUIType.THREE_BY_NINE) {

            title = "${KColors.MAGENTA}Rock Paper Scissors ${KColors.DARKGRAY}| ${KColors.GRAY}Ergebnis"

            page(1) {

                placeholder(Slots.All, itemStack(Material.WHITE_STAINED_GLASS_PANE) { meta { name = null } })
                placeholder(Slots.RowOneSlotTwo rectTo Slots.RowThreeSlotEight,
                    itemStack(Material.BLACK_STAINED_GLASS_PANE) { meta { name = null } })

                placeholder(Slots.RowTwoSlotTwo, itemStack(Material.PLAYER_HEAD) {
                    meta<SkullMeta> {
                        owningPlayer = playerOne
                        name = "${KColors.DODGERBLUE}${playerOne.name}"
                    }
                })
                placeholder(Slots.RowTwoSlotThree, rockPaperScissors.choice[playerOne]!!.icon)

                placeholder(Slots.RowTwoSlotEight, itemStack(Material.PLAYER_HEAD) {
                    meta<SkullMeta> {
                        owningPlayer = playerTwo
                        name = "${KColors.DODGERBLUE}${playerTwo.name}"
                    }
                })
                placeholder(Slots.RowTwoSlotSeven, rockPaperScissors.choice[playerTwo]!!.icon)

                if (winner != null) {
                    placeholder(Slots.RowTwoSlotFive, rockPaperScissors.choice[winner]!!.icon)
                } else {
                    placeholder(
                        Slots.RowTwoSlotFive,
                        itemStack(Material.BARRIER) { meta { name = "${KColors.DODGERBLUE}Unentschieden!" } })
                }
            }
        }
        return gui
    }
}