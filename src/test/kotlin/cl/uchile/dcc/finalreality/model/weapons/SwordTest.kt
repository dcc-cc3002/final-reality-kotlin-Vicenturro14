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
class SwordTest : FunSpec({
    // equals method tests
    test("Two swords with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val sword1 = Sword(name, damage, weight)
            val sword2 = Sword(name, damage, weight)
            sword1 shouldNotBeSameInstanceAs sword2
            sword1 shouldBe sword2
        }
    }
    test("Two swords with different parameters aren't equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val sword1 = Sword(name1, damage1, weight1)
            val sword2 = Sword(name2, damage2, weight2)
            sword1 shouldNotBeSameInstanceAs sword2
            sword1 shouldNotBe sword2
        }
    }

    // hashCode method tests
    test("Two equal swords should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val sword1 = Sword(name, damage, weight)
            val sword2 = Sword(name, damage, weight)
            sword1 shouldNotBeSameInstanceAs sword2
            sword1.shouldHaveSameHashCodeAs(sword2)
        }
    }
    test("Two different swords shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val sword1 = Sword(name1, damage1, weight1)
            val sword2 = Sword(name2, damage2, weight2)
            sword1 shouldNotBeSameInstanceAs sword2
            sword1.shouldNotHaveSameHashCodeAs(sword2)
        }
    }

    // toString method test
    test("The string representation of a sword should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val sword1 = Sword(name, damage, weight)
            "$sword1" shouldBe "Sword(name = '${sword1.name}', damage = ${sword1.damage}, weight = ${sword1.weight})"
        }
    }

    // equipTo... methods tests
    test("A sword can be equipped to a thief") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val sword1 = Sword(name1, damage, weight)
            val thief = Thief(name2, maxHp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                sword1.equipToThief(thief)
            }
            thief.equippedWeapon shouldBe sword1
        }
    }
    test("A sword can be equipped to a knight") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val sword1 = Sword(name1, damage, weight)
            val knight = Knight(name2, maxHp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                sword1.equipToKnight(knight)
            }
            knight.equippedWeapon shouldBe sword1
        }
    }
    test("An exception should be thrown when a sword is equipped to an engineer") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val sword1 = Sword(name1, damage, weight)
            val engineer = Engineer(name2, maxHp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                sword1.equipToEngineer(engineer)
            }
        }
    }
    test("An exception should be thrown when a sword is equipped to a black mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, maxMp, defense ->
            val sword1 = Sword(name1, damage, weight)
            val blackMage = BlackMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                sword1.equipToBlackMage(blackMage)
            }
        }
    }
    test("An exception should be thrown when a sword is equipped to a white mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, maxMp, defense ->
            val sword1 = Sword(name1, damage, weight)
            val whiteMage = WhiteMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                sword1.equipToWhiteMage(whiteMage)
            }
        }
    }
})
