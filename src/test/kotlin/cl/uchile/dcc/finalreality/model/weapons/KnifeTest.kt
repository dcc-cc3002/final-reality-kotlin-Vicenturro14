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

class KnifeTest : FunSpec({
    // equals method tests
    test("Two knives with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val knife1 = Knife(name, damage, weight)
            val knife2 = Knife(name, damage, weight)
            knife1 shouldNotBeSameInstanceAs knife2
            knife1 shouldBe knife2
        }
    }
    test("Two knives with different parameters aren't equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val knife1 = Knife(name1, damage1, weight1)
            val knife2 = Knife(name2, damage2, weight2)
            knife1 shouldNotBeSameInstanceAs knife2
            knife1 shouldNotBe knife2
        }
    }

    // hashCode method tests
    test("Two equal knives should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val knife1 = Knife(name, damage, weight)
            val knife2 = Knife(name, damage, weight)
            knife1 shouldNotBeSameInstanceAs knife2
            knife1.shouldHaveSameHashCodeAs(knife2)
        }
    }
    test("Two different knives shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val knife1 = Knife(name1, damage1, weight1)
            val knife2 = Knife(name2, damage2, weight2)
            knife1 shouldNotBeSameInstanceAs knife2
            knife1.shouldNotHaveSameHashCodeAs(knife2)
        }
    }

    // toString method test
    test("The string representation of a knife should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val knife1 = Knife(name, damage, weight)
            "$knife1" shouldBe "Knife(name = '${knife1.name}', damage = ${knife1.damage}, weight = ${knife1.weight})"
        }
    }

    // equipTo... methods tests
    test("A knife can be equipped to a thief") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val knife1 = Knife(name1, damage, weight)
            val thief = Thief(name2, maxHp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                knife1.equipToThief(thief)
            }
            thief.equippedWeapon shouldBe knife1
        }
    }
    test("A knife can be equipped to a knight") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val knife1 = Knife(name1, damage, weight)
            val knight = Knight(name2, maxHp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                knife1.equipToKnight(knight)
            }
            knight.equippedWeapon shouldBe knife1
        }
    }
    test("A knife can be equipped to a black mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, maxMp, defense ->
            val knife1 = Knife(name1, damage, weight)
            val blackMage = BlackMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                knife1.equipToBlackMage(blackMage)
            }
            blackMage.equippedWeapon shouldBe knife1
        }
    }
    test("An exception should be thrown when a knife is equipped to a white mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, maxMp, defense ->
            val knife1 = Knife(name1, damage, weight)
            val whiteMage = WhiteMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                knife1.equipToWhiteMage(whiteMage)
            }
        }
    }
    test("An exception should be thrown when a knife is equipped to an engineer") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val knife1 = Knife(name1, damage, weight)
            val engineer = Engineer(name2, maxHp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                knife1.equipToEngineer(engineer)
            }
        }
    }
})
