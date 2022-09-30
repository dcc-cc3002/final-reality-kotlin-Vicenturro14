package cl.uchile.dcc.finalreality.model.weapons

/**
 * This interface represents a weapon from the game.
 * A weapon can be equipped to a character controlled by the player.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
interface Weapon {

    /**
     * Gives the weight of the weapon.
     */
    fun giveWeight():Int
}