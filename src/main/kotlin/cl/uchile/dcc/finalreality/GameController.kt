package cl.uchile.dcc.finalreality

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import java.util.concurrent.LinkedBlockingQueue

class GameController {
    private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

    fun attack(attacker: GameCharacter, target: GameCharacter) {

    }
}