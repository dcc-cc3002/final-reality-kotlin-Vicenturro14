package cl.uchile.dcc.finalreality.model.weapons

/**
 * This interface represents a weapon from the game.
 * A weapon can be equipped to a character controlled by the player.
 * @property weight
 *   The weight of the weapon.
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
interface Weapon {
    val weight: Int

    /**
     * Gives the weight of the weapon.
     */
}
