package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.weapons.Knife
import cl.uchile.dcc.finalreality.model.weapons.Sword
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

private lateinit var thief1: Thief
private lateinit var thief2: Thief
private lateinit var knife: Knife
private lateinit var sword: Sword
private const val KNIFE_NAME = "testKnife"
private const val KNIFE_DAMAGE = 100
private const val KNIFE_WEIGHT = 30
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class ThiefTest : FunSpec({
    // equals method tests
    test("Two thieves with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) {name, maxHp, defense, currentHp ->
            assume(currentHp <= maxHp)
            thief1 = Thief(name, maxHp, defense, turnsQueue)
            thief2 = Thief(name, maxHp, defense, turnsQueue)
            knife = Knife(KNIFE_NAME, KNIFE_DAMAGE, KNIFE_WEIGHT)
            thief1.equip(knife)
            thief2.equip(knife)
            thief1.currentHp = currentHp
            thief2.currentHp = currentHp
            thief1 shouldNotBeSameInstanceAs thief2
            thief1 shouldBe thief2
        }
    }
    test("Two thieves with different parameters aren't equal") {
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
            thief1 = Thief(name1, maxHp1, defense1, turnsQueue)
            thief2 = Thief(name2, maxHp2, defense2, turnsQueue)
            knife = Knife(KNIFE_NAME, KNIFE_DAMAGE, KNIFE_WEIGHT)
            thief1.equip(knife)
            thief2.equip(knife)
            thief1.currentHp = currentHp1
            thief2.currentHp = currentHp2
            thief1 shouldNotBeSameInstanceAs thief2
            thief1 shouldNotBe thief2
        }
    }

    // hashCode method tests
    test("Two equal thieves should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { name, maxHp, defense, currentHp ->
            assume(currentHp <= maxHp)
            thief1 = Thief(name, maxHp, defense, turnsQueue)
            thief2 = Thief(name, maxHp, defense, turnsQueue)
            knife = Knife(KNIFE_NAME, KNIFE_DAMAGE, KNIFE_WEIGHT)
            thief1.equip(knife)
            thief2.equip(knife)
            thief1.currentHp = currentHp
            thief2.currentHp = currentHp
            thief1 shouldNotBeSameInstanceAs thief2
            thief1.shouldHaveSameHashCodeAs(thief2)
        }
    }
    test("Two different thieves shouldn't have the same hashCode") {
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
            thief1 = Thief(name1, maxHp1, defense1, turnsQueue)
            thief2 = Thief(name2, maxHp2, defense2, turnsQueue)
            knife = Knife(KNIFE_NAME, KNIFE_DAMAGE, KNIFE_WEIGHT)
            thief1.equip(knife)
            thief2.equip(knife)
            thief1.currentHp = currentHp1
            thief2.currentHp = currentHp2
            thief1 shouldNotBeSameInstanceAs thief2
            thief1.shouldNotHaveSameHashCodeAs(thief2)
        }
    }

    // toString method test
    test("The string representation of a thief should be correct") {
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
            thief1 = Thief(name1, maxHp, defense, turnsQueue)
            knife = Knife(name2, damage, weight)
            sword = Sword(name2, damage, weight)
            thief1.currentHp = currentHp
            thief1.equip(knife)
            "$thief1" shouldBe "Thief(name = '${thief1.name}', maxHp = ${thief1.maxHp}, currentHp = ${thief1.currentHp}, defense = ${thief1.defense}, equippedWeapon = ${thief1.equippedWeapon})"
            thief1.equip(sword)
            "$thief1" shouldBe "Thief(name = '${thief1.name}', maxHp = ${thief1.maxHp}, currentHp = ${thief1.currentHp}, defense = ${thief1.defense}, equippedWeapon = ${thief1.equippedWeapon})"
        }
    }

})
