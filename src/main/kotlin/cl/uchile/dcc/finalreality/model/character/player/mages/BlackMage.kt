/*
 * "Final Reality" (c) by R8V and ~Your name~
 * "Final Reality" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.uchile.dcc.finalreality.model.character.player.mages

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import java.util.Objects
import java.util.concurrent.BlockingQueue

/**
 * A Black Mage is a type of [MagicUser] that can cast black magic.
 *
 * @param name
 *   The character's name.
 * @param maxHp
 *   The character's maximum health points
 * @param maxMp
 *   The character's maximum magic points
 * @param defense
 *   The character's defense
 * @param turnsQueue
 *   The queue with the characters waiting for their turn
 * @constructor
 *   Creates a new Black Mage.
 *
 * @property currentMp
 *   The current MP of the character.
 * @property currentHp
 *   The current HP of the character.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
class BlackMage(
    name: String,
    maxHp: Int,
    maxMp: Int,
    defense: Int,
    turnsQueue: BlockingQueue<GameCharacter>
) : AbstractMagicPlayerCharacter(name, maxHp, maxMp, defense, turnsQueue) {

    override fun equals(other: Any?) = when {
        this === other -> true
        other !is BlackMage -> false
        hashCode() != other.hashCode() -> false
        name != other.name -> false
        maxHp != other.maxHp -> false
        currentHp != other.currentHp -> false
        maxMp != other.maxMp -> false
        currentMp != other.currentMp -> false
        defense != other.defense -> false
        else -> true
    }

    override fun hashCode() = Objects.hash(this::class, name, maxHp, maxMp, currentHp, currentMp, defense)
    override fun toString() = "BlackMage(" +
        "name = '$name', " +
        "maxMp = $maxMp, " +
        "maxHp = $maxHp, " +
        "currentHp = $currentHp, " +
        "currentMp = $currentMp, " +
        "defense = $defense, " +
        "equippedWeapon = ${this.equippedWeapon}" +
        ")"
}
