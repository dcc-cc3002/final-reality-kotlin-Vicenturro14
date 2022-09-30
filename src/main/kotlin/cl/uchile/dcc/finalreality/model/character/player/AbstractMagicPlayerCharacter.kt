package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.exceptions.Require
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import java.util.concurrent.BlockingQueue

/**
 * A class that holds the information of a magic player-controlled character in the game.
 *
 * @param name
 *   The character's name.
 * @param maxHp
 *   The character's maximum health points.
 * @param maxMp
 *   The character's maximum mana points.
 * @param defense
 *   The character's defense.
 * @param turnsQueue
 *   The queue with the characters waiting for their turn.
 * @property currentMp
 *   The character's current mana points.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
abstract class AbstractMagicPlayerCharacter(
    name: String,
    maxHp: Int,
    maxMp: Int,
    defense: Int,
    turnsQueue: BlockingQueue<GameCharacter>
) : AbstractPlayerCharacter(name, maxHp, defense, turnsQueue), MagicUser {
    val maxMp = Require.Stat(maxMp, "Max MP") atLeast 0
    var currentMp: Int = maxMp
        set(value) {
            field = Require.Stat(value, "Current MP") inRange 0..maxMp
        }
}
