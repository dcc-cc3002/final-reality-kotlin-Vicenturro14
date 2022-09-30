package cl.uchile.dcc.finalreality.model.character

import cl.uchile.dcc.finalreality.exceptions.Require
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ScheduledExecutorService

/**
 * An abstract class that holds the common behaviour of all the characters in the game.
 *
 * @property name
 *   The character's name.
 * @property maxHp
 *   The character's maximum health points.
 * @property defense
 *   The character's defense.
 * @property turnsQueue
 *   The queue with the characters waiting for their turn.
 * @property currentHp
 *   The character's current health points.
 * @property scheduledExecutor
 *   A tool that can schedule commands to run after a given delay, or to execute periodically.
 *
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
abstract class AbstractCharacter(
    val name: String,
    maxHp: Int,
    defense: Int,
    private val turnsQueue: BlockingQueue<GameCharacter>,
) : GameCharacter {

    protected lateinit var scheduledExecutor: ScheduledExecutorService
    val maxHp = Require.Stat(maxHp, "Max Hp") atLeast 1
    var currentHp = maxHp
        set(value) {
            field = Require.Stat(value, "Current Hp") inRange 0..maxHp
        }
    val defense = Require.Stat(defense, "Defense") atLeast 0


    override fun addToQueue() {
        turnsQueue.put(this)
        scheduledExecutor.shutdown()
    }
}
