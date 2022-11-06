package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
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

private lateinit var engineer1: Engineer
private lateinit var engineer2: Engineer
private lateinit var engineer3: Engineer
private lateinit var axe: Axe
private lateinit var bow: Bow
private const val AXE_NAME = "testAxe"
private const val AXE_DAMAGE = 100
private const val AXE_WEIGHT = 30
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class EngineerTest : FunSpec({
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
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            engineer1.equip(axe)
            engineer2.equip(axe)
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
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            engineer1.equip(axe)
            engineer2.equip(axe)
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
            axe = Axe(name2, damage, weight)
            bow = Bow(name2, damage, weight)
            engineer1.currentHp = currentHp
            engineer1.equip(axe)
            "$engineer1" shouldBe "Engineer(name = '${engineer1.name}', maxHp = ${engineer1.maxHp}, currentHp = ${engineer1.currentHp}, defense = ${engineer1.defense}, equippedWeapon = ${engineer1.equippedWeapon})"
            engineer1.equip(bow)
            "$engineer1" shouldBe "Engineer(name = '${engineer1.name}', maxHp = ${engineer1.maxHp}, currentHp = ${engineer1.currentHp}, defense = ${engineer1.defense}, equippedWeapon = ${engineer1.equippedWeapon})"

        }
    }
})