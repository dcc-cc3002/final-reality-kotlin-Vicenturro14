package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import java.util.concurrent.LinkedBlockingQueue

private const val MAGE_NAME = "Josefina"
private const val MAGE_DEFENSE = 50
private const val MAGE_MAXHP = 200
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()
