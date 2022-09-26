package cl.uchile.dcc.finalreality.model

import java.util.Objects

/**
 * A class that holds all the information of a weapon.
 *
 * @property name String
 *     The name of the weapon.
 * @property damage Int
 *     The base damage done by the weapon.
 * @property weight Int
 *     The weight of the weapon.
 *
 * @constructor Creates a weapon with a name, a base damage and speed.
 *
 * @author <a href="https://www.github.com/r8vnhill">R8V</a>
 * @author ~Your name~
 */
class AbstractWeapon(
    val name: String,
    val damage: Int,
    val weight: Int
) {
    override fun equals(other: Any?) = when {
        this === other                 -> true
        other !is AbstractWeapon       -> false
        hashCode() != other.hashCode() -> false
        name != other.name             -> false
        damage != other.damage         -> false
        weight != other.weight         -> false
        else                           -> true
    }

    override fun hashCode() = Objects.hash(this::class, name, damage, weight)

    override fun toString() = "Weapon { name: $name, damage: $damage, weight: $weight)"
}

