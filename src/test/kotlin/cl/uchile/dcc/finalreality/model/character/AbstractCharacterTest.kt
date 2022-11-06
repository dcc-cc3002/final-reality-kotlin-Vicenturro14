package cl.uchile.dcc.finalreality.model.character

import cl.uchile.dcc.finalreality.exceptions.InvalidStatValueException
import cl.uchile.dcc.finalreality.model.character.player.BlackMage
import cl.uchile.dcc.finalreality.model.character.player.Engineer
import cl.uchile.dcc.finalreality.model.character.player.Knight
import cl.uchile.dcc.finalreality.model.character.player.Thief
import cl.uchile.dcc.finalreality.model.character.player.WhiteMage
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

    test("The maxHp of a character should be at least 1") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonPositiveInt(-100000)
        ) {maxHp1, maxHp2 ->
            shouldNotThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp1, CHARACTER_DEFENSE, turnsQueue)
                Engineer(CHARACTER_NAME, maxHp1, CHARACTER_DEFENSE, turnsQueue)
                Knight(CHARACTER_NAME, maxHp1, CHARACTER_DEFENSE, turnsQueue)
                Thief(CHARACTER_NAME, maxHp1, CHARACTER_DEFENSE, turnsQueue)
                BlackMage(CHARACTER_NAME, maxHp1, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
                WhiteMage(CHARACTER_NAME, maxHp1, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp2, CHARACTER_DEFENSE, turnsQueue)
                Engineer(CHARACTER_NAME, maxHp2, CHARACTER_DEFENSE, turnsQueue)
                Knight(CHARACTER_NAME, maxHp2, CHARACTER_DEFENSE, turnsQueue)
                Thief(CHARACTER_NAME, maxHp2, CHARACTER_DEFENSE, turnsQueue)
                BlackMage(CHARACTER_NAME, maxHp2, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
                WhiteMage(CHARACTER_NAME, maxHp2, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
        }
    }
    test("The currentHp of a character should be at least 0") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.negativeInt(-100000)
        ) {maxHp, currentHp1, currentHp2 ->
            assume(currentHp1 <= maxHp)
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp1
                engineer.currentHp = currentHp1
                knight.currentHp = currentHp1
                thief.currentHp = currentHp1
                blackMage.currentHp = currentHp1
                whiteMage.currentHp = currentHp1
            }
            shouldThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp2
                engineer.currentHp = currentHp2
                knight.currentHp = currentHp2
                thief.currentHp = currentHp2
                blackMage.currentHp = currentHp2
                whiteMage.currentHp = currentHp2
            }
        }
    }
    test("The currentHp of a character chould be at most maxHp") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(100000)
        ) {maxHp, currentHp1, currentHpAux ->
            assume(currentHp1 <= maxHp)
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp1
                engineer.currentHp = currentHp1
                knight.currentHp = currentHp1
                thief.currentHp = currentHp1
                blackMage.currentHp = currentHp1
                whiteMage.currentHp = currentHp1
            }
            val currentHp2 = maxHp + currentHpAux + 1
            /* The value assigned to currentHp is currentHpAux + maxHp + 1 to ensure that
            the assignment is out of range. */
            shouldThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp2
                engineer.currentHp = currentHp2
                knight.currentHp = currentHp2
                thief.currentHp = currentHp2
                blackMage.currentHp = currentHp2
                whiteMage.currentHp = currentHp2
            }
        }
    }
    test("The defense of a character should be al least 0") {
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.negativeInt(-100000)
        ) {defense1, defense2 ->
            shouldNotThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, CHARACTER_MAXHP, defense1, turnsQueue)
                Engineer(CHARACTER_NAME, CHARACTER_MAXHP, defense1, turnsQueue)
                Knight(CHARACTER_NAME, CHARACTER_MAXHP, defense1, turnsQueue)
                Thief(CHARACTER_NAME, CHARACTER_MAXHP, defense1, turnsQueue)
                BlackMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense1, turnsQueue)
                WhiteMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense1, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, CHARACTER_MAXHP, defense2, turnsQueue)
                Engineer(CHARACTER_NAME, CHARACTER_MAXHP, defense2, turnsQueue)
                Knight(CHARACTER_NAME, CHARACTER_MAXHP, defense2, turnsQueue)
                Thief(CHARACTER_NAME, CHARACTER_MAXHP, defense2, turnsQueue)
                BlackMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense2, turnsQueue)
                WhiteMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense2, turnsQueue)
            }
        }
    }
})
