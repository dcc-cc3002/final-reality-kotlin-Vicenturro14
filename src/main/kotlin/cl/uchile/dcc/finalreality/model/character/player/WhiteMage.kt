/*
 * "Final Reality" (c) by R8V and ~Your name~
 * "Final Reality" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import java.util.concurrent.BlockingQueue

/**
 * A White Mage is a type of [PlayerCharacter] that can cast white magic.
 *
 * @param name        the character's name
 * @param maxHp       the character's maximum health points
 * @param maxMp       the character's maximum magic points
 * @param defense     the character's defense
 * @param turnsQueue  the queue with the characters waiting for their turn
 * @constructor Creates a new Black Mage.
 *
 * @property currentMp The current MP of the character.
 * @property currentHp The current HP of the character.
 *
 * @author <a href="https://www.github.com/r8vnhill">R8V</a>
 * @author ~Your name~
 */
class WhiteMage(
  name: String,
  maxHp: Int,
  maxMp: Int,
  defense: Int,
  turnsQueue: BlockingQueue<GameCharacter>
) : AbstractMagicPlayerCharacter(name, maxHp, maxMp, defense, turnsQueue) {

    override fun toString() = "WhiteMage(" +
        "name = '$name', " +
        "maxHp = $maxHp, " +
        "maxMp = $maxMp, " +
        "currentHp = $currentHp, " +
        "currentMp = $currentMp, " +
        "defense = $defense" +
        ")"
}