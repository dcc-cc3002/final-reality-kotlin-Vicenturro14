/*
 * "Final Reality" (c) by R8V and ~Your name~
 * "Final Reality" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import java.util.Objects
import java.util.concurrent.BlockingQueue

/**
 * A `Knight` is a type of [PlayerCharacter] that can equip `Sword`s, `Axe`s and
 * `Knife`s.
 *
 * @param name
 *   The character's name
 * @param maxHp
 *   The character's maximum health points
 * @param defense
 *   The character's defense
 * @param turnsQueue
 *   The queue with the characters waiting for their turn
 * @constructor
 *   Creates a new Knight.
 *
 * @property currentHp
 *   The current HP of the character.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
class Knight(
    name: String,
    maxHp: Int,
    defense: Int,
    turnsQueue: BlockingQueue<GameCharacter>
) : AbstractPlayerCharacter(name, maxHp, defense, turnsQueue) {
    override fun equals(other: Any?) = when {
        this === other -> true
        other !is Knight -> false
        hashCode() != other.hashCode() -> false
        name != other.name -> false
        maxHp != other.maxHp -> false
        currentHp != other.currentHp -> false
        defense != other.defense -> false
        else -> true
    }

    override fun hashCode() = Objects.hash(this::class, name, maxHp, currentHp, defense)

    override fun toString() = "Knight(" +
        "name: '$name', " +
        "maxHp: $maxHp, " +
        "currentHp: $currentHp, " +
        "defense: $defense, " +
        "equippedWeapon = ${this.equippedWeapon}" +
        "}"
}
