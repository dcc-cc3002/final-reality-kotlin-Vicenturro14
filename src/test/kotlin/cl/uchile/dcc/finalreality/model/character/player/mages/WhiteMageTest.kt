package cl.uchile.dcc.finalreality.model.character.player.mages

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.weapons.Staff
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

private lateinit var whiteMage1: WhiteMage
private lateinit var whiteMage2: WhiteMage
private lateinit var staff: Staff
private const val STAFF_NAME = "testStaff"
private const val STAFF_DAMAGE = 10
private const val STAFF_WEIGHT = 15
private const val STAFF_MAGIC_DAMAGE = 50
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class WhiteMageTest : FunSpec({
    // equals method tests
    test("Two white mages with the same parameters should be equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(100000)
            ) {name, maxHp, maxMp, currentHp, currentMp, defense ->
            assume(currentHp <= maxHp && currentMp <= maxMp)
            whiteMage1 = WhiteMage(name, maxHp, maxMp, defense, turnsQueue)
            whiteMage2 = WhiteMage(name, maxHp, maxMp, defense, turnsQueue)
            whiteMage1.currentHp = currentHp
            whiteMage2.currentHp = currentHp
            whiteMage1.currentMp = currentMp
            whiteMage2.currentMp = currentMp
            staff = Staff(STAFF_NAME, STAFF_DAMAGE, STAFF_WEIGHT, STAFF_MAGIC_DAMAGE)
            whiteMage1.equip(staff)
            whiteMage2.equip(staff)
            whiteMage1 shouldNotBeSameInstanceAs whiteMage2
            whiteMage1 shouldBe whiteMage2
        }
    }
    test("Two white mages with different parameters shouldn't be equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
            ) {name1, name2, maxHp1, maxHp2, maxMp1, maxMp2, currentHp1, currentHp2, currentMp1, currentMp2, defense1, defense2 ->
            assume(name1 != name2 || maxHp1 != maxHp2 || maxMp1 != maxMp2 || currentHp1 != currentHp2 || currentMp1 != currentMp2|| defense1 != defense2)
            assume(currentHp1 <= maxHp1 && currentHp2 <= maxHp2 && currentMp1 <= maxMp1 && currentMp2 <= maxMp2)
            whiteMage1 = WhiteMage(name1, maxHp1, maxMp1, defense1, turnsQueue)
            whiteMage2 = WhiteMage(name2, maxHp2, maxMp2, defense2, turnsQueue)
            whiteMage1.currentHp = currentHp1
            whiteMage2.currentHp = currentHp2
            whiteMage1.currentMp = currentMp1
            whiteMage2.currentMp = currentMp2
            staff = Staff(STAFF_NAME, STAFF_DAMAGE, STAFF_WEIGHT, STAFF_MAGIC_DAMAGE)
            whiteMage1.equip(staff)
            whiteMage2.equip(staff)
            whiteMage1 shouldNotBeSameInstanceAs whiteMage2
            whiteMage1 shouldNotBe whiteMage2
        }
    }

    // hashCode method tests
    test("Two equal white mages should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(100000)
        ) {name, maxHp, maxMp, currentHp, currentMp, defense ->
            assume(currentHp <= maxHp && currentMp <= maxMp)
            whiteMage1 = WhiteMage(name, maxHp, maxMp, defense, turnsQueue)
            whiteMage2 = WhiteMage(name, maxHp, maxMp, defense, turnsQueue)
            whiteMage1.currentHp = currentHp
            whiteMage2.currentHp = currentHp
            whiteMage1.currentMp = currentMp
            whiteMage2.currentMp = currentMp
            staff = Staff(STAFF_NAME, STAFF_DAMAGE, STAFF_WEIGHT, STAFF_MAGIC_DAMAGE)
            whiteMage1.equip(staff)
            whiteMage2.equip(staff)
            whiteMage1 shouldNotBeSameInstanceAs whiteMage2
            whiteMage1.shouldHaveSameHashCodeAs(whiteMage2)
        }
    }
    test("Two different white mages shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(1000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) {name1, name2, maxHp1, maxHp2, maxMp1, maxMp2, currentHp1, currentHp2, currentMp1, currentMp2, defense1, defense2 ->
            assume(name1 != name2 || maxHp1 != maxHp2 || maxMp1 != maxMp2 || currentHp1 != currentHp2 || currentMp1 != currentMp2|| defense1 != defense2)
            assume(currentHp1 <= maxHp1 && currentHp2 <= maxHp2 && currentMp1 <= maxMp1 && currentMp2 <= maxMp2)
            whiteMage1 = WhiteMage(name1, maxHp1, maxMp1, defense1, turnsQueue)
            whiteMage2 = WhiteMage(name2, maxHp2, maxMp2, defense2, turnsQueue)
            whiteMage1.currentHp = currentHp1
            whiteMage2.currentHp = currentHp2
            whiteMage1.currentMp = currentMp1
            whiteMage2.currentMp = currentMp2
            staff = Staff(STAFF_NAME, STAFF_DAMAGE, STAFF_WEIGHT, STAFF_MAGIC_DAMAGE)
            whiteMage1.equip(staff)
            whiteMage2.equip(staff)
            whiteMage1 shouldNotBeSameInstanceAs whiteMage2
            whiteMage1.shouldNotHaveSameHashCodeAs(whiteMage2)
        }
    }

    // toString method test
    test("The string representation of a white mage should be correct") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) {name1, name2, weight, maxHp, maxMp, currentHp, currentMp, defense, damage, magicDamage ->
            assume(currentHp <= maxHp && currentMp <= maxMp)
            whiteMage1 = WhiteMage(name1, maxHp, maxMp, defense, turnsQueue)
            whiteMage1.currentHp = currentHp
            whiteMage1.currentMp = currentMp
            staff = Staff(name2, damage, weight, magicDamage)
            whiteMage1.equip(staff)
            "$whiteMage1" shouldBe "WhiteMage(name = '${whiteMage1.name}', " +
                "maxHp = ${whiteMage1.maxHp}, maxMp = ${whiteMage1.maxMp}, " +
                "currentHp = ${whiteMage1.currentHp}, currentMp = ${whiteMage1.currentMp}, " +
                "defense = ${whiteMage1.defense}, equippedWeapon = ${whiteMage1.equippedWeapon})"

        }
    }
})
