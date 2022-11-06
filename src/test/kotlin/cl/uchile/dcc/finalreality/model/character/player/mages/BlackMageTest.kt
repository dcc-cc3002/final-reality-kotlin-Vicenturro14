package cl.uchile.dcc.finalreality.model.character.player.mages

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.character.knight
import cl.uchile.dcc.finalreality.model.weapons.Knife
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

private lateinit var blackMage1: BlackMage
private lateinit var blackMage2: BlackMage
private lateinit var blackMage3: BlackMage
private lateinit var staff: Staff
private lateinit var knife: Knife
private const val STAFF_NAME = "testStaff"
private const val STAFF_DAMAGE = 10
private const val STAFF_WEIGHT = 15
private const val STAFF_MAGIC_DAMAGE = 50
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class BlackMageTest : FunSpec({

    test("Two black mages with the same parameters should be equal") {
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
            blackMage1 = BlackMage(name1, maxHp1, maxMp1, defense1, turnsQueue)
            blackMage2 = BlackMage(name2, maxHp2, maxMp2, defense2, turnsQueue)
            blackMage3 = BlackMage(name1, maxHp1, maxMp1, defense1, turnsQueue)
            blackMage1.currentHp = currentHp1
            blackMage2.currentHp = currentHp2
            blackMage3.currentHp = currentHp1
            blackMage1.currentMp = currentMp1
            blackMage2.currentMp = currentMp2
            blackMage3.currentMp = currentMp1
            staff = Staff(STAFF_NAME, STAFF_DAMAGE, STAFF_WEIGHT, STAFF_MAGIC_DAMAGE)
            blackMage1.equip(staff)
            blackMage2.equip(staff)
            blackMage3.equip(staff)
            blackMage1 shouldNotBeSameInstanceAs blackMage2
            blackMage1 shouldNotBe blackMage2
            blackMage1 shouldNotBeSameInstanceAs blackMage3
            blackMage1 shouldBe blackMage3
        }
    }

    test("Two equal black mages should have the same hashCode") {
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
            blackMage1 = BlackMage(name1, maxHp1, maxMp1, defense1, turnsQueue)
            blackMage2 = BlackMage(name2, maxHp2, maxMp2, defense2, turnsQueue)
            blackMage3 = BlackMage(name1, maxHp1, maxMp1, defense1, turnsQueue)
            blackMage1.currentHp = currentHp1
            blackMage2.currentHp = currentHp2
            blackMage3.currentHp = currentHp1
            blackMage1.currentMp = currentMp1
            blackMage2.currentMp = currentMp2
            blackMage3.currentMp = currentMp1
            staff = Staff(STAFF_NAME, STAFF_DAMAGE, STAFF_WEIGHT, STAFF_MAGIC_DAMAGE)
            blackMage1.equip(staff)
            blackMage2.equip(staff)
            blackMage3.equip(staff)
            blackMage1 shouldNotBeSameInstanceAs blackMage2
            blackMage1.shouldNotHaveSameHashCodeAs(blackMage2)
            blackMage1 shouldNotBeSameInstanceAs blackMage3
            blackMage1.shouldHaveSameHashCodeAs(blackMage3)
        }
    }

    test("The string representation of a black mage should be correct") {
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
            blackMage1 = BlackMage(name1, maxHp, maxMp, defense, turnsQueue)
            blackMage1.currentHp = currentHp
            blackMage1.currentMp = currentMp
            staff = Staff(name2, damage, weight, magicDamage)
            knife = Knife(name2, damage, weight)
            blackMage1.equip(staff)
            "$blackMage1" shouldBe "BlackMage(name = '${blackMage1.name}', " +
                "maxHp = ${blackMage1.maxHp}, maxMp = ${blackMage1.maxMp}, " +
                "currentHp = ${blackMage1.currentHp}, currentMp = ${blackMage1.currentMp}, " +
                "defense = ${blackMage1.defense}, equippedWeapon = ${blackMage1.equippedWeapon})"
            blackMage1.equip(knife)
            "$blackMage1" shouldBe "BlackMage(name = '${blackMage1.name}', " +
                "maxHp = ${blackMage1.maxHp}, maxMp = ${blackMage1.maxMp}, " +
                "currentHp = ${blackMage1.currentHp}, currentMp = ${blackMage1.currentMp}, " +
                "defense = ${blackMage1.defense}, equippedWeapon = ${blackMage1.equippedWeapon})"
        }
    }
})
