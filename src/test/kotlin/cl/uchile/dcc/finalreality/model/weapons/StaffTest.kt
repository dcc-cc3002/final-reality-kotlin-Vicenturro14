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
class StaffTest : FunSpec({
    // equals method tests
    test("Two staffs with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            val staff1 = Staff(name, damage, weight, magicDamage)
            val staff2 = Staff(name, damage, weight, magicDamage)
            staff1 shouldNotBeSameInstanceAs staff2
            staff1 shouldBe staff2
        }
    }
    test("Two staffs with different parameters aren't equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2, magicDamage1, magicDamage2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2 || magicDamage1 != magicDamage2)
            val staff1 = Staff(name1, damage1, weight1, magicDamage1)
            val staff2 = Staff(name2, damage2, weight2, magicDamage2)
            staff1 shouldNotBeSameInstanceAs staff2
            staff1 shouldNotBe staff2
        }
    }

    // hashCode method tests
    test("Two equal staffs should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            val staff1 = Staff(name, damage, weight, magicDamage)
            val staff2 = Staff(name, damage, weight, magicDamage)
            staff1 shouldNotBeSameInstanceAs staff2
            staff1.shouldHaveSameHashCodeAs(staff2)
        }
    }
    test("Two different staffs shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2, magicDamage1, magicDamage2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2 || magicDamage1 != magicDamage2)
            val staff1 = Staff(name1, damage1, weight1, magicDamage1)
            val staff2 = Staff(name2, damage2, weight2, magicDamage2)
            staff1 shouldNotBeSameInstanceAs staff2
            staff1.shouldNotHaveSameHashCodeAs(staff2)
        }
    }

    // toString method test
    test("The string representation of a staff should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            val staff1 = Staff(name, damage, weight, magicDamage)
            "$staff1" shouldBe "Staff(name = '${staff1.name}', damage = ${staff1.damage}, weight = ${staff1.weight}, magicDamage = ${staff1.magicDamage})"
        }
    }

    // equipTo... methods tests
    test("A staff can be equipped to a black mage.") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, magicDamage, maxHp, maxMp, defense ->
            val staff1 = Staff(name1, damage, weight, magicDamage)
            val blackMage = BlackMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                staff1.equipToBlackMage(blackMage)
            }
            blackMage.equippedWeapon shouldBe staff1
        }
    }
    test("A staff can be equipped to a white mage.") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, magicDamage, maxHp, maxMp, defense ->
            val staff1 = Staff(name1, damage, weight, magicDamage)
            val whiteMage = WhiteMage(name2, maxHp, maxMp, defense, turnsQueue)
            shouldNotThrow<UnableToEquipException> {
                staff1.equipToWhiteMage(whiteMage)
            }
            whiteMage.equippedWeapon shouldBe staff1
        }
    }
    test("An exception should be thrown when a staff is equipped to a thief") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, magicDamage, maxHp, defense ->
            val staff1 = Staff(name1, damage, weight, magicDamage)
            val thief = Thief(name2, maxHp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                staff1.equipToThief(thief)
            }
        }
    }
    test("An exception should be thrown when a staff is equipped to a knight") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, magicDamage, maxHp, defense ->
            val staff1 = Staff(name1, damage, weight, magicDamage)
            val knight = Knight(name2, maxHp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                staff1.equipToKnight(knight)
            }
        }
    }
    test("An exception should be thrown when a staff is equipped to an engineer") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, damage, weight, magicDamage, maxHp, defense ->
            val staff1 = Staff(name1, damage, weight, magicDamage)
            val engineer = Engineer(name2, maxHp, defense, turnsQueue)
            shouldThrow<UnableToEquipException> {
                staff1.equipToEngineer(engineer)
            }
        }
    }
})
