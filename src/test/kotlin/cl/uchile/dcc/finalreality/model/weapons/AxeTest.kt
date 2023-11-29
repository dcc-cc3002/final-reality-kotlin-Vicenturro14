package cl.uchile.dcc.finalreality.model.weapons

import cl.uchile.dcc.finalreality.exceptions.UnableToEquipException
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.character.player.Engineer
import cl.uchile.dcc.finalreality.model.character.player.Knight
import cl.uchile.dcc.finalreality.model.character.player.Thief
import cl.uchile.dcc.finalreality.model.character.player.mages.BlackMage
import cl.uchile.dcc.finalreality.model.character.player.mages.WhiteMage
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldHaveSameHashCodeAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.matchers.types.shouldNotHaveSameHashCodeAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbitrary.string
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue

private val turnsQueue = LinkedBlockingQueue<GameCharacter>()
class AxeTest : FunSpec({
    // equals method tests
    test("Two axes with the same parameters should be equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
        ) { name, damage, weight ->
            val axe1 = Axe(name, damage, weight)
            val axe2 = Axe(name, damage, weight)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1 shouldBe axe2
        }
    }
    test("Two axes with different parameters shouldn't be equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val axe1 = Axe(name1, damage1, weight1)
            val axe2 = Axe(name2, damage2, weight2)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1 shouldNotBe axe2
        }
    }

    // hashCode method tests
    test("Two equal axes should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val axe1 = Axe(name, damage, weight)
            val axe2 = Axe(name, damage, weight)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1.shouldHaveSameHashCodeAs(axe2)
        }
    }
    test("Two different axes shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val axe1 = Axe(name1, damage1, weight1)
            val axe2 = Axe(name2, damage2, weight2)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1.shouldNotHaveSameHashCodeAs(axe2)
        }
    }

    // toString method test
    test("The string representation of an axe should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val axe1 = Axe(name, damage, weight)
            "$axe1" shouldBe "Axe(name = '${axe1.name}', damage = ${axe1.damage}, weight = ${axe1.weight})"
        }
    }

    // equipTo... methods tests
    test("An axe can be equipped to an engineer") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val axe1 = Axe(name1, damage, weight)
            val engineer = Engineer(name2, maxHp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                axe1.equipToEngineer(engineer)
            }
            engineer.equippedWeapon shouldBe axe1
        }
    }
    test("An axe can be equipped to a knight") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val axe1 = Axe(name1, damage, weight)
            val knight = Knight(name2, maxHp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                axe1.equipToKnight(knight)
            }
            knight.equippedWeapon shouldBe axe1
        }
    }
    test("An exception should be thrown when an axe is equipped to a thief") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val axe1 = Axe(name1, damage, weight)
            val thief = Thief(name2, maxHp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                axe1.equipToThief(thief)
            }
        }
    }
    test("An exception should be thrown when an axe is equipped to a black mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, maxMp, defense ->
            val axe1 = Axe(name1, damage, weight)
            val blackMage = BlackMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                axe1.equipToBlackMage(blackMage)
            }
        }
    }
    test("An exception should be thrown when an axe is equipped to a white mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, maxMp, defense ->
            val axe1 = Axe(name1, damage, weight)
            val whiteMage = WhiteMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                axe1.equipToWhiteMage(whiteMage)
            }
        }
    }
})
