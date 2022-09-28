package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.exceptions.Require
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import java.util.Objects
import java.util.concurrent.BlockingQueue

abstract class AbstractMagicPlayerCharacter(
    name: String,
    maxHp: Int,
    maxMp: Int,
    defense: Int,
    turnsQueue: BlockingQueue<GameCharacter>
) : AbstractPlayerCharacter(name, maxHp, defense, turnsQueue), MagicUser{
    val maxMp = Require.Stat(maxMp, "Max MP") atLeast 0
    var currentMp: Int = maxMp
        set(value) {
            field = Require.Stat(value, "Current MP") inRange 0..maxMp
        }

    override fun equals(other: Any?) = when {
        this === other                 -> true
        other !is WhiteMage            -> false
        hashCode() != other.hashCode() -> false
        name != other.name             -> false
        maxHp != other.maxHp           -> false
        maxMp != other.maxMp           -> false
        defense != other.defense       -> false
        else                           -> true
    }

    override fun hashCode() = Objects.hash(this::class, name, maxHp, maxMp, defense)
}