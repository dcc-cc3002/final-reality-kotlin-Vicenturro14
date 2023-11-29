package cl.uchile.dcc.finalreality.model.weapons

import cl.uchile.dcc.finalreality.exceptions.UnableToEquipException
import cl.uchile.dcc.finalreality.model.character.player.PlayerCharacter
import java.util.Objects

/**
 * A Knife is a type of [Weapon].
 *
 * @property name String
 *     The name of the weapon.
 * @property damage Int
 *     The base damage done by the weapon.
 * @property weight Int
 *     The weight of the weapon.
 *
 * @constructor
 *   Creates a new Knife.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
class Knife(
    name: String,
    damage: Int,
    weight: Int
) : AbstractWeapon(name, damage, weight) {

    override fun equals(other: Any?) = when {
        this === other -> true
        other !is Knife -> false
        hashCode() != other.hashCode() -> false
        this.name != other.name -> false
        this.damage != other.damage -> false
        this.weight != other.weight -> false
        else -> true
    }

    override fun hashCode(): Int = Objects.hash(this::class, name, damage, weight)

    override fun toString() = "Knife(" +
        "name = '$name', " +
        "damage = $damage, " +
        "weight = $weight" +
        ")"

    override fun equipToBlackMage(blackMage: PlayerCharacter) {
        blackMage.equippedWeapon = this
    }

    override fun equipToKnight(knight: PlayerCharacter) {
        knight.equippedWeapon = this
    }

    override fun equipToThief(thief: PlayerCharacter) {
        thief.equippedWeapon = this
    }

    private val suffix = "can't be equipped with knives"
    override fun equipToWhiteMage(whiteMage: PlayerCharacter) {
        throw UnableToEquipException("White mages $suffix")
    }

    override fun equipToEngineer(engineer: PlayerCharacter) {
        throw UnableToEquipException("Engineers $suffix")
    }
}
