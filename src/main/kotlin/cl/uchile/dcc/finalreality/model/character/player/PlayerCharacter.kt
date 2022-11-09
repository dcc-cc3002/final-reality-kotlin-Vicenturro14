/*
 * "Final Reality" (c) by R8V and ~Your name~
 * "Final Reality" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.model.weapons.Weapon

/**
 * A character controlled by the user.
 * @property equippedWeapon
 *   The [Weapon] equipped with the character.
 *
 * @author <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author Vicente Olivares
 */
interface PlayerCharacter {
    var equippedWeapon: Weapon

    /**
     * Equips a [Weapon] to the character only if the character can be equipped with the class of the weapon.
     * If the character can't be equipped with the weapon, an exception will be thrown.
     * @param weapon
     *   The weapon that is going to be equipped to a [PlayerCharacter].
     */
    fun equip(weapon: Weapon)
}
