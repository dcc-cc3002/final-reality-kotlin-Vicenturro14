package cl.uchile.dcc.finalreality.model.weapons

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
abstract class AbstractWeapon(
    val name: String,
    val damage: Int,
    val weight: Int
) : Weapon {

    override fun giveWeight(): Int {
        return this.weight
    }
}

