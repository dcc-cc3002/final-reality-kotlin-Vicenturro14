package cl.uchile.dcc.finalreality.model.weapons

import java.util.Objects

/**
 * A Staff is a type of [Weapon] and [MagicWeapon].
 *
 * @param name
 *     The name of the magic weapon.
 * @param damage Int
 *     The base damage done by the magic weapon.
 * @param weight Int
 *     The weight of the magic weapon.
 * @param magicDamage Int
 *     The additional magic damage done by the magic weapon.
 *
 * @constructor
 *   Creates a new Staff.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
class Staff(
    name: String,
    damage: Int,
    weight: Int,
    magicDamage: Int
) : AbstractMagicWeapon(name, damage, weight, magicDamage) {
    override fun equals(other: Any?) = when {
        this === other -> true
        other !is Staff -> false
        hashCode() != other.hashCode() -> false
        this.name != other.name -> false
        this.damage != other.damage -> false
        this.weight != other.weight -> false
        this.magicDamage != other.magicDamage -> false
        else -> true
    }

    override fun hashCode(): Int = Objects.hash(this::class, name, damage, weight, magicDamage)

    override fun toString() = "Staff(" +
        "name = $name, " +
        "damage = $damage, " +
        "weight = $weight, " +
        "magicDamage = $magicDamage" +
        ")"
}
