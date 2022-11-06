package cl.uchile.dcc.finalreality.model.character.player.mages

import cl.uchile.dcc.finalreality.exceptions.InvalidStatValueException
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowUnit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowUnit
import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInt
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue

private const val MAGE_NAME = "Josefina"
private const val MAGE_DEFENSE = 50
private const val MAGE_MAXHP = 200
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()
private lateinit var blackMage: BlackMage
private lateinit var whiteMage: WhiteMage

class AbstractMagicPlayerCharacterTest : FunSpec({

    test("The maxMp of a mage should be at least 0") {
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.negativeInt(-100000)
        ) { maxMp1, maxMp2 ->
            shouldNotThrow<InvalidStatValueException> {
                WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp1, MAGE_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp1, MAGE_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp2, MAGE_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp2, MAGE_DEFENSE, turnsQueue)
            }
        }
    }
    test("The currentMp of a mage should be at least 0"){
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.negativeInt(-100000)
        ) {maxMp, currentMp1, currentMp2 ->
            assume(currentMp1 <= maxMp)
            blackMage = BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                blackMage.currentMp = currentMp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                whiteMage.currentMp = currentMp1
            }
            shouldThrowUnit<InvalidStatValueException> {
                blackMage.currentMp = currentMp2
            }
            shouldThrowUnit<InvalidStatValueException> {
                whiteMage.currentMp = currentMp2
            }
        }
    }

    test("The currentMp of a mage should be at most maxMp") {
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(100000)
        ) {maxMp, currentMp1, currentMpAux ->
            assume(currentMp1 <= maxMp)
            blackMage = BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                blackMage.currentMp = currentMp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                whiteMage.currentMp = currentMp1
            }
            /* The value assigned to currentMp is currentMpAux + maxMp + 1 to ensure that
            the assignment is out of range. */
            val currentMp2 = maxMp + currentMpAux + 1
            shouldThrowUnit<InvalidStatValueException> {
                blackMage.currentMp = currentMp2
            }
            shouldThrowUnit<InvalidStatValueException> {
                whiteMage.currentMp = currentMp2
            }
        }
    }
})