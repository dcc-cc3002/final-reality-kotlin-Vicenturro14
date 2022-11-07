package cl.uchile.dcc.finalreality.model.weapons

import cl.uchile.dcc.finalreality.model.character.player.PlayerCharacter
import java.util.Objects

/**
 * An Axe is a type of [Weapon].
 *
 * @property name String
 *     The name of the weapon.
 * @property damage Int
 *     The base damage done by the weapon.
 * @property weight Int
 *     The weight of the weapon.
 *
 * @constructor
 *   Creates a new Axe.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
class Axe(
    name: String,
    damage: Int,
    weight: Int
) : AbstractWeapon(name, damage, weight) {
    override fun equals(other: Any?) = when {
        this === other -> true
        other !is Axe -> false
        hashCode() != other.hashCode() -> false
        this.name != other.name -> false
        this.damage != other.damage -> false
        this.weight != other.weight -> false
        else -> true
    }

    override fun hashCode(): Int = Objects.hash(this::class, name, damage, weight)

    override fun toString() = "Axe(" +
        "name = '$name', " +
        "damage = $damage, " +
        "weight = $weight" +
        ")"

    override fun equipToEngineer(engineer: PlayerCharacter) {
        engineer.equippedWeapon = this
    }

    override fun equipToKnight(knight: PlayerCharacter) {
        knight.equippedWeapon = this
    }
    private val auxString = "axes"
    override fun equipToBlackMage(blackMage: PlayerCharacter) {
        unableToEquip("Black mages", auxString)
    }

    override fun equipToWhiteMage(whiteMage: PlayerCharacter) {
        unableToEquip("White mages", auxString)
    }

    override fun equipToThief(thief: PlayerCharacter) {
        unableToEquip("Thieves", auxString)
    }
}
