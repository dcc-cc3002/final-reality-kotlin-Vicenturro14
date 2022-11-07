package cl.uchile.dcc.finalreality.model.weapons

import cl.uchile.dcc.finalreality.exceptions.UnableToEquipException

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
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
abstract class AbstractWeapon(
    val name: String,
    val damage: Int,
    override val weight: Int
) : Weapon {
    protected fun unableToEquip(characterString: String, weaponString: String) {
        throw UnableToEquipException("$characterString can't be equipped with $weaponString.")
    }
}
