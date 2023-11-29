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
class BowTest : FunSpec({
    // equals method tests
    test("Two bows with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val bow1 = Bow(name, damage, weight)
            val bow2 = Bow(name, damage, weight)
            bow1 shouldNotBeSameInstanceAs bow2
            bow1 shouldBe bow2
        }
    }
    test("Two bows with the different parameters aren't equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val bow1 = Bow(name1, damage1, weight1)
            val bow2 = Bow(name2, damage2, weight2)
            bow1 shouldNotBeSameInstanceAs bow2
            bow1 shouldNotBe bow2
        }
    }

    // hashCode method tests
    test("Two equal bows should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val bow1 = Bow(name, damage, weight)
            val bow2 = Bow(name, damage, weight)
            bow1 shouldNotBeSameInstanceAs bow2
            bow1.shouldHaveSameHashCodeAs(bow2)
        }
    }
    test("Two different bows shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val bow1 = Bow(name1, damage1, weight1)
            val bow2 = Bow(name2, damage2, weight2)
            bow1 shouldNotBeSameInstanceAs bow2
            bow1.shouldNotHaveSameHashCodeAs(bow2)
        }
    }

    // toString method test
    test("The string representation of an bow should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val bow1 = Bow(name, damage, weight)
            "$bow1" shouldBe "Bow(name = '${bow1.name}', damage = ${bow1.damage}, weight = ${bow1.weight})"
        }
    }

    // equipTo... methods tests
    test("A bow can be equipped to an engineer") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val bow1 = Bow(name1, damage, weight)
            val engineer = Engineer(name2, maxHp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                bow1.equipToEngineer(engineer)
            }
            engineer.equippedWeapon shouldBe bow1
        }
    }
    test("A bow can be equipped to a thief") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val bow1 = Bow(name1, damage, weight)
            val thief = Thief(name2, maxHp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                bow1.equipToThief(thief)
            }
            thief.equippedWeapon shouldBe bow1
        }
    }
    test("An exception should be thrown when a bow is equipped to a knight") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, defense ->
            val bow1 = Bow(name1, damage, weight)
            val knight = Knight(name2, maxHp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                bow1.equipToKnight(knight)
            }
        }
    }
    test("An exception should be thrown when a bow is equipped to a black mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, maxMp, defense ->
            val bow1 = Bow(name1, damage, weight)
            val blackMage = BlackMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                bow1.equipToBlackMage(blackMage)
            }
        }
    }
    test("An exception should be thrown when a bow is equipped to a white mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, maxHp, maxMp, defense ->
            val bow1 = Bow(name1, damage, weight)
            val whiteMage = WhiteMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                bow1.equipToWhiteMage(whiteMage)
            }
        }
    }
})
