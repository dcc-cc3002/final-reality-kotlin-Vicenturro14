package cl.uchile.dcc.finalreality.model.character

/**
 * This represents a character from the game.
 * A character can be controlled by the player or by the CPU (an enemy).
 *
 * @author
 *   <a href="https://github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
interface GameCharacter {

    /**
     * Sets a scheduled executor to make this character (thread) wait for `speed / 10`
     * seconds before adding the character to the queue.
     */
    fun waitTurn()

    /**
     * Attacks another [GameCharacter], by getting the attackers damage and calling the
     * receiveAttack method from the target.
     *  @param target
     *  The [GameCharacter] that receives the attack.
     */    /**

     * Receive an attack, subtracting (damage - defense / 2) / 10 from the character's currentHp,
     * where defense is the defense from the character receiving the attack.
     *  @param damage
     *  The damage done by the attacker
     */
    fun receiveAttack(damage: Int)
}
