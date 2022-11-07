package cl.uchile.dcc.finalreality.model.weapons

import cl.uchile.dcc.finalreality.model.character.player.PlayerCharacter
import java.util.Objects

/**
 * A Sword is a type of [Weapon].
 *
 * @property name String
 *     The name of the weapon.
 * @property damage Int
 *     The base damage done by the weapon.
 * @property weight Int
 *     The weight of the weapon.
 *
 * @constructor
 *   Creates a new Sword.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
class Sword(
    name: String,
    damage: Int,
    weight: Int
) : AbstractWeapon(name, damage, weight) {

    override fun equals(other: Any?) = when {
        this === other -> true
        other !is Sword -> false
        hashCode() != other.hashCode() -> false
        this.name != other.name -> false
        this.damage != other.damage -> false
        this.weight != other.weight -> false
        else -> true
    }

    override fun hashCode(): Int = Objects.hash(this::class, name, damage, weight)

    override fun toString() = "Sword(" +
        "name = '$name', " +
        "damage = $damage, " +
        "weight = $weight" +
        ")"

    private val auxString = "swords"
    override fun equipToKnight(knight: PlayerCharacter) {
        knight.equippedWeapon = this
    }

    override fun equipToThief(thief: PlayerCharacter) {
        thief.equippedWeapon = this
    }

    override fun equipToBlackMage(blackMage: PlayerCharacter) {
        unableToEquip("Black mages", auxString)
    }

    override fun equipToEngineer(engineer: PlayerCharacter) {
        unableToEquip("Engineers", auxString)
    }

    override fun equipToWhiteMage(whiteMage: PlayerCharacter) {
        unableToEquip("White mages", auxString)
    }
}
