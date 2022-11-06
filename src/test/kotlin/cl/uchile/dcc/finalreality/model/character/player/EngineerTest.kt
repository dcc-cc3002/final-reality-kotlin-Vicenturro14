package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.exceptions.InvalidStatValueException
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowUnit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowUnit
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldHaveSameHashCodeAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.matchers.types.shouldNotHaveSameHashCodeAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInt
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.nonPositiveInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbitrary.string
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue

lateinit var engineer1: Engineer
lateinit var engineer2: Engineer
lateinit var engineer3: Engineer
private const val ENG1_NAME = "Arturo"
private const val ENG2_NAME = "Juan Pablo"
private const val ENG1_DEFENSE = 75
private const val ENG2_DEFENSE = 150
private const val ENG1_MAXHP = 200
private const val ENG2_MAXHP = 100

private val axe = Axe("testAxe", 100, 30)
private val bow = Bow("testBow", 75, 20)
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class EngineerTest : FunSpec({
    beforeEach{
        turnsQueue.clear()
    }

    test("Two engineers with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(10000)
        ) {name1, name2, maxHp1, maxHp2, defense1, defense2, currentHp1, currentHp2 ->
            assume(maxHp1 >= currentHp1 && maxHp2 >= currentHp2)
            engineer1 = Engineer(name1, maxHp1, defense1, turnsQueue)
            engineer2 = Engineer(name2, maxHp2, defense2, turnsQueue)
            engineer3 = Engineer(name1, maxHp1, defense1, turnsQueue)
            engineer1.equip(axe)
            engineer2.equip(bow)
            engineer3.equip(axe)
            engineer1.currentHp = currentHp1
            engineer2.currentHp = currentHp2
            engineer3.currentHp = currentHp1
            engineer1 shouldNotBeSameInstanceAs engineer2
            engineer1 shouldNotBe engineer2
            engineer1 shouldNotBeSameInstanceAs engineer3
            engineer1 shouldBe engineer3
        }
    }
    test("Two equal engineers should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(10000)
        ) { name1, name2, maxHp1, maxHp2, defense1, defense2, currentHp1, currentHp2 ->
            assume(maxHp1 >= currentHp1 && maxHp2 >= currentHp2)
            engineer1 = Engineer(name1, maxHp1, defense1, turnsQueue)
            engineer2 = Engineer(name2, maxHp2, defense2, turnsQueue)
            engineer3 = Engineer(name1, maxHp1, defense1, turnsQueue)
            engineer1.equip(axe)
            engineer2.equip(bow)
            engineer3.equip(axe)
            engineer1.currentHp = currentHp1
            engineer2.currentHp = currentHp2
            engineer3.currentHp = currentHp1
            engineer1 shouldNotBeSameInstanceAs engineer2
            engineer1.shouldNotHaveSameHashCodeAs(engineer2)
            engineer1 shouldNotBeSameInstanceAs engineer3
            engineer1.shouldHaveSameHashCodeAs(engineer3)
        }
    }
    test("The string representation of an engineer should be correct") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) {name1, name2, maxHp, defense, damage, weight, currentHp ->
            assume(currentHp <= maxHp)
            engineer1 = Engineer(name1, maxHp, defense, turnsQueue)
            val testAxe = Axe(name2, damage, weight)
            val testBow = Bow(name2, damage, weight)
            engineer1.currentHp = currentHp
            engineer1.equip(testAxe)
            "$engineer1" shouldBe "Engineer(name = '${engineer1.name}', maxHp = ${engineer1.maxHp}, currentHp = ${engineer1.currentHp}, defense = ${engineer1.defense}, equippedWeapon = ${engineer1.equippedWeapon})"
            engineer1.equip(testBow)
            "$engineer1" shouldBe "Engineer(name = '${engineer1.name}', maxHp = ${engineer1.maxHp}, currentHp = ${engineer1.currentHp}, defense = ${engineer1.defense}, equippedWeapon = ${engineer1.equippedWeapon})"

        }
    }
    test("The maxHp of an engineer should be at least 1") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonPositiveInt(-100000)
        ) {maxHp1, maxHp2 ->
            shouldNotThrow<InvalidStatValueException> {
                Engineer(ENG1_NAME, maxHp1, ENG1_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Engineer(ENG2_NAME, maxHp2, ENG2_DEFENSE, turnsQueue)

            }
        }
    }
        test("The currentHp of an engineer should be at least 0") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.negativeInt(-100000)
        ) {maxHp1, maxHp2, currentHp1, currentHp2 ->
            assume(currentHp1 <= maxHp1)
            engineer1 = Engineer(ENG1_NAME, maxHp1, ENG1_DEFENSE, turnsQueue)
            engineer2 = Engineer(ENG2_NAME, maxHp2, ENG2_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                engineer1.currentHp = currentHp1
            }
            shouldThrowUnit<InvalidStatValueException> {
                engineer2.currentHp = currentHp2
            }
        }
    }

    test("The currentHp of an engineer should be at most maxHp") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(1000000)
        ) {maxHp1, maxHp2, currentHp1, currentHp2 ->
            assume(currentHp1 <= maxHp1)
            engineer1 = Engineer(ENG1_NAME, maxHp1, ENG1_DEFENSE, turnsQueue)
            engineer2 = Engineer(ENG2_NAME, maxHp2, ENG2_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                engineer1.currentHp = currentHp1
            }
            shouldThrowUnit<InvalidStatValueException> {
                // The value assigned to engineer2.currentHp is currentHp2 + maxHp2 + 1 to ensure that assignment is out of range.
                engineer2.currentHp = currentHp2 + maxHp2 + 1
            }
        }
    }

    test("The defense of an engineer should be at least 0") {
        checkAll(
            Arb.nonNegativeInt(100000),
            Arb.negativeInt(-100000)
        ) {defense1, defense2 ->
            shouldNotThrow<InvalidStatValueException> {
                Engineer(ENG1_NAME, ENG1_MAXHP, defense1, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Engineer(ENG2_NAME, ENG2_MAXHP, defense2, turnsQueue)
            }
        }
    }
})