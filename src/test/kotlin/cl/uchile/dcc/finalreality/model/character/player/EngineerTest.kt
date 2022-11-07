package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.exceptions.UnableToEquipException
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
import cl.uchile.dcc.finalreality.model.weapons.Knife
import cl.uchile.dcc.finalreality.model.weapons.Staff
import cl.uchile.dcc.finalreality.model.weapons.Sword
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

private lateinit var engineer1: Engineer
private lateinit var engineer2: Engineer
private lateinit var axe: Axe
private lateinit var sword: Sword
private lateinit var bow: Bow
private lateinit var knife: Knife
private lateinit var staff: Staff
private const val ENGINEER_NAME = "Katerin"
private const val ENGINEER_MAXHP = 50
private const val ENGINEER_DEFENSE = 150
private const val AXE_NAME = "testAxe"
private const val AXE_DAMAGE = 100
private const val AXE_WEIGHT = 30
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class EngineerTest : FunSpec({
    // equals method tests
    test("Two engineers with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { name, maxHp, defense, currentHp ->
            assume(maxHp >= currentHp)
            engineer1 = Engineer(name, maxHp, defense, turnsQueue)
            engineer2 = Engineer(name, maxHp, defense, turnsQueue)
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            engineer1.equip(axe)
            engineer2.equip(axe)
            engineer1.currentHp = currentHp
            engineer2.currentHp = currentHp
            engineer1 shouldNotBeSameInstanceAs engineer2
            engineer1 shouldBe engineer2
        }
    }
    test("Two engineers with different parameters aren't equal") {
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
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            engineer1.equip(axe)
            engineer2.equip(axe)
            engineer1.currentHp = currentHp1
            engineer2.currentHp = currentHp2
            engineer1 shouldNotBeSameInstanceAs engineer2
            engineer1 shouldNotBe engineer2
        }
    }

    // hashCode method tests
    test("Two equal engineers should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { name, maxHp, defense, currentHp ->
            assume(maxHp >= currentHp)
            engineer1 = Engineer(name, maxHp, defense, turnsQueue)
            engineer2 = Engineer(name, maxHp, defense, turnsQueue)
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            engineer1.equip(axe)
            engineer2.equip(axe)
            engineer1.currentHp = currentHp
            engineer2.currentHp = currentHp
            engineer1 shouldNotBeSameInstanceAs engineer2
            engineer1.shouldHaveSameHashCodeAs(engineer2)
        }
    }
    test("Two different engineers shouldn't have the same hashCode") {
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
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            engineer1.equip(axe)
            engineer2.equip(axe)
            engineer1.currentHp = currentHp1
            engineer2.currentHp = currentHp2
            engineer1 shouldNotBeSameInstanceAs engineer2
            engineer1.shouldNotHaveSameHashCodeAs(engineer2)
        }
    }

    // toString method test
    test("The string representation of an engineer should be correct") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { name1, name2, maxHp, defense, damage, weight, currentHp ->
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

    // Weapon equip tests
    test("An engineer can be equipped with an axe") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            engineer1 = Engineer(ENGINEER_NAME, ENGINEER_MAXHP, ENGINEER_DEFENSE, turnsQueue)
            axe = Axe(name, damage, weight)
            shouldNotThrow<UnableToEquipException> {
                engineer1.equip(axe)
            }
        }
    }
    test("An engineer can be equipped with a bow") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            engineer1 = Engineer(ENGINEER_NAME, ENGINEER_MAXHP, ENGINEER_DEFENSE, turnsQueue)
            bow = Bow(name, damage, weight)
            shouldNotThrow<UnableToEquipException> {
                engineer1.equip(bow)
            }
        }
    }
    test("An exception should be thrown when a staff is equipped to a engineer") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            engineer1 = Engineer(ENGINEER_NAME, ENGINEER_MAXHP, ENGINEER_DEFENSE, turnsQueue)
            staff = Staff(name, damage, weight, magicDamage)
            shouldThrow<UnableToEquipException> {
                engineer1.equip(staff)
            }
        }
    }
    test("An exception should be thrown when a sword is equipped to a engineer") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            engineer1 = Engineer(ENGINEER_NAME, ENGINEER_MAXHP, ENGINEER_DEFENSE, turnsQueue)
            sword = Sword(name, damage, weight)
            shouldThrow<UnableToEquipException> {
                engineer1.equip(sword)
            }
        }
    }
    test("An exception should be thrown when a knife is equipped to a engineer") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            engineer1 = Engineer(ENGINEER_NAME, ENGINEER_MAXHP, ENGINEER_DEFENSE, turnsQueue)
            knife = Knife(name, damage, weight)
            shouldThrow<UnableToEquipException> {
                engineer1.equip(knife)
            }
        }
    }
})
