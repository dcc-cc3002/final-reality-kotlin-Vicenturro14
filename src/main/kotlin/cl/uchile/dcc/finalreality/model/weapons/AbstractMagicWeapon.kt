package cl.uchile.dcc.finalreality.model.weapons

/**
 * A class that holds all the information of a magic weapon.
 *
 * @param name
 *     The name of the magic weapon.
 * @param damage Int
 *     The base damage done by the magic weapon.
 * @param weight Int
 *     The weight of the magic weapon.
 * @param magicDamage Int
 *     The additional magic damage done by the magic weapon.
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
abstract class AbstractMagicWeapon(
    name: String,
    damage: Int,
    weight: Int,
    override val magicDamage: Int
) : AbstractWeapon(name, damage, weight), MagicWeapon
