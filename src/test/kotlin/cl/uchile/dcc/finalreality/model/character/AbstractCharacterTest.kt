package cl.uchile.dcc.finalreality.model.character

import cl.uchile.dcc.finalreality.exceptions.InvalidStatValueException
import cl.uchile.dcc.finalreality.model.character.player.Engineer
import cl.uchile.dcc.finalreality.model.character.player.Knight
import cl.uchile.dcc.finalreality.model.character.player.Thief
import cl.uchile.dcc.finalreality.model.character.player.mages.BlackMage
import cl.uchile.dcc.finalreality.model.character.player.mages.WhiteMage
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowUnit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowUnit
import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInt
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.nonPositiveInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue

lateinit var enemy: Enemy
lateinit var engineer: Engineer
lateinit var knight: Knight
lateinit var thief: Thief
lateinit var blackMage: BlackMage
lateinit var whiteMage: WhiteMage
private const val CHARACTER_NAME = "Constanza"
private const val CHARACTER_MAXHP = 200
private const val CHARACTER_DEFENSE = 100
private const val ENEMY_WEIGHT = 70
private const val MAGE_MAXMP = 100
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class AbstractCharacterTest : FunSpec({
    // maxHp parameter tests
    test("The maxHp of a character should be at least 1") {
        checkAll(
            Arb.positiveInt(100000)
        ) { maxHp ->
            shouldNotThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
        }
    }
    test("An exception should be thrown when the maxHp of a character is less than 1") {
        checkAll(
            Arb.nonPositiveInt(-100000)
        ) { maxHp ->
            shouldThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
        }
    }

    // currentHp property tests
    test("The currentHp of a character should be at least 0") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { maxHp, currentHp ->
            assume(currentHp <= maxHp)
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                engineer.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                knight.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                thief.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                blackMage.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                whiteMage.currentHp = currentHp
            }
        }
    }
    test("An exception should be thrown when the maxHp of a character is less than 0") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.negativeInt(-100000)
        ) { maxHp, currentHp ->
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)

            shouldThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                engineer.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                knight.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                thief.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                blackMage.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                whiteMage.currentHp = currentHp
            }
        }
    }
    test("The currentHp of a character should be at most maxHp") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { maxHp, currentHp1 ->
            assume(currentHp1 <= maxHp)
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                engineer.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                knight.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                thief.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                blackMage.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                whiteMage.currentHp = currentHp1
            }
        }
    }
    test("An exception should be thrown when the currentHp of a character is greater than maxHP") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { maxHp, currentHpAux ->
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            val currentHp = maxHp + currentHpAux + 1
            /* The value assigned to currentHp is currentHpAux + maxHp + 1 to ensure that
            the assignment is out of range. */
            shouldThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                engineer.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                knight.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                thief.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                blackMage.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                whiteMage.currentHp = currentHp
            }
        }
    }

    // defense parameter tests
    test("The defense of a character should be al least 0") {
        checkAll(
            Arb.nonNegativeInt(100000)
        ) { defense ->
            shouldNotThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, CHARACTER_MAXHP, defense, turnsQueue)
                Engineer(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                Knight(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                Thief(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                BlackMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense, turnsQueue)
                WhiteMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense, turnsQueue)
            }
        }
    }
    test("An exception should be thrown when the defense of a character is less than 0") {
        checkAll(
            Arb.negativeInt(-100000)
        ) { defense ->
            shouldThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, CHARACTER_MAXHP, defense, turnsQueue)
                Engineer(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                Knight(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                Thief(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                BlackMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense, turnsQueue)
                WhiteMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense, turnsQueue)
            }
        }
    }
})
