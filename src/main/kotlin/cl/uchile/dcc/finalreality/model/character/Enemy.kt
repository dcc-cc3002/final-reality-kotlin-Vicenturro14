package cl.uchile.dcc.finalreality.model.character

import cl.uchile.dcc.finalreality.exceptions.Require
import java.util.Objects
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * A class that holds all the information of a single enemy of the game.
 *
 * @param name
 *   The name of this enemy.
 * @param weight
 *   The weight of this enemy.
 * @param maxHp
 *   The maximum health points of this enemy.
 * @param defense
 *   The defense of this enemy.
 * @param attack
 *   The attack of this enemy.
 * @param turnsQueue
 *   The queue with the characters waiting for their turn.
 *
 * @constructor
 *   Creates a new enemy with a name, a weight, a defense and the queue with the characters ready to
 *  play.
 *
 * @property currentHp
 *   The current health points of this enemy.
 *
 * @author
 *   <a href="https://github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
class Enemy(
    name: String,
    weight: Int,
    maxHp: Int,
    defense: Int,
    attack: Int,
    turnsQueue: BlockingQueue<GameCharacter>
) : AbstractCharacter(name, maxHp, defense, turnsQueue) {
    val weight = Require.Stat(weight, "Weight") atLeast 1

    val attack = Require.Stat(attack, "Attack") atLeast 1

    override fun equals(other: Any?) = when {
        this === other -> true
        other !is Enemy -> false
        hashCode() != other.hashCode() -> false
        name != other.name -> false
        weight != other.weight -> false
        maxHp != other.maxHp -> false
        currentHp != other.currentHp -> false
        defense != other.defense -> false
        attack != other.attack -> false
        else -> true
    }

    override fun hashCode() = Objects.hash(Enemy::class, name, weight, maxHp, currentHp, defense, attack)

    override fun toString() = "Enemy(" +
        "name = '$name', " +
        "weight = $weight, " +
        "maxHp = $maxHp, " +
        "currentHp = $currentHp, " +
        "defense = $defense, " +
        "attack = $attack" +
        ")"

    override fun waitTurn() {
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor()
        scheduledExecutor.schedule(
            /* command = */ ::addToQueue,
            /* delay = */ (this.weight / 10).toLong(),
            /* unit = */ TimeUnit.SECONDS
        )
    }

    override fun attackCharacter(target: GameCharacter) {
        target.receiveAttack(this.attack)
    }
}
