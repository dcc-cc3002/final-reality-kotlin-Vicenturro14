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
    // maxMp parameter tests
    test("The maxMp of a mage should be at least 0") {
        checkAll(
            Arb.nonNegativeInt(100000)
        ) { maxMp ->
            shouldNotThrow<InvalidStatValueException> {
                WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            }
        }
    }
    test("An exception should be thrown when the maxMp of a mage is less than 0") {
        checkAll(
            Arb.negativeInt(-100000)
        ) { maxMp ->
            shouldThrow<InvalidStatValueException> {
                WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            }
        }
    }

    // currentMp property tests
    test("The currentMp of a mage should be at least 0") {
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(10000)
        ) { maxMp, currentMp ->
            assume(currentMp <= maxMp)
            blackMage = BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                blackMage.currentMp = currentMp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                whiteMage.currentMp = currentMp
            }
        }
    }
    test("An exception should be thrown when the currentMp of a mage is less than 0") {
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.negativeInt(-100000)
        ) { maxMp, currentMp ->
            blackMage = BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            shouldThrowUnit<InvalidStatValueException> {
                blackMage.currentMp = currentMp
            }
            shouldThrowUnit<InvalidStatValueException> {
                whiteMage.currentMp = currentMp
            }
        }
    }
    test("The currentMp of a mage should be at most maxMp") {
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(10000)
        ) { maxMp, currentMp ->
            assume(currentMp <= maxMp)
            blackMage = BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                blackMage.currentMp = currentMp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                whiteMage.currentMp = currentMp
            }
        }
    }
    test("An exception should be thrown when the currentMp of a mage is greater than maxMp") {
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { maxMp, currentMpAux ->
            blackMage = BlackMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(MAGE_NAME, MAGE_MAXHP, maxMp, MAGE_DEFENSE, turnsQueue)
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
