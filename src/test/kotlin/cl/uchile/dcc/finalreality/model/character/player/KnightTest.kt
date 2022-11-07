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

private lateinit var knight1: Knight
private lateinit var knight2: Knight
private lateinit var axe: Axe
private lateinit var sword: Sword
private lateinit var bow: Bow
private lateinit var knife: Knife
private lateinit var staff: Staff
private const val AXE_NAME = "testAxe"
private const val AXE_DAMAGE = 100
private const val AXE_WEIGHT = 30
private const val KNIGHT_NAME = "Martin"
private const val KNIGHT_MAXHP = 100
private const val KNIGHT_DEFENSE = 200
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class KnightTest : FunSpec({
    // equals method tests
    test("Two knights with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { name, maxHp, defense, currentHp ->
            assume(maxHp >= currentHp)
            knight1 = Knight(name, maxHp, defense, turnsQueue)
            knight2 = Knight(name, maxHp, defense, turnsQueue)
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            knight1.equip(axe)
            knight2.equip(axe)
            knight1.currentHp = currentHp
            knight2.currentHp = currentHp
            knight1 shouldNotBeSameInstanceAs knight2
            knight1 shouldBe knight2
        }
    }
    test("Two knights with different parameters aren't equal") {
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
            knight1 = Knight(name1, maxHp1, defense1, turnsQueue)
            knight2 = Knight(name2, maxHp2, defense2, turnsQueue)
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            knight1.equip(axe)
            knight2.equip(axe)
            knight1.currentHp = currentHp1
            knight2.currentHp = currentHp2
            knight1 shouldNotBeSameInstanceAs knight2
            knight1 shouldNotBe knight2
        }
    }

    // hashCode method tests
    test("Two equal knights should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { name, maxHp, defense, currentHp ->
            assume(maxHp >= currentHp)
            knight1 = Knight(name, maxHp, defense, turnsQueue)
            knight2 = Knight(name, maxHp, defense, turnsQueue)
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            knight1.equip(axe)
            knight2.equip(axe)
            knight1.currentHp = currentHp
            knight2.currentHp = currentHp
            knight1 shouldNotBeSameInstanceAs knight2
            knight1.shouldHaveSameHashCodeAs(knight2)
        }
    }
    test("Two different knights shouldn't have the same hashCode") {
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
            knight1 = Knight(name1, maxHp1, defense1, turnsQueue)
            knight2 = Knight(name2, maxHp2, defense2, turnsQueue)
            axe = Axe(AXE_NAME, AXE_DAMAGE, AXE_WEIGHT)
            knight1.equip(axe)
            knight2.equip(axe)
            knight1.currentHp = currentHp1
            knight2.currentHp = currentHp2
            knight1 shouldNotBeSameInstanceAs knight2
            knight1.shouldNotHaveSameHashCodeAs(knight2)
        }
    }

    // toString method test
    test("The string representation of a knight should be correct") {
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
            knight1 = Knight(name1, maxHp, defense, turnsQueue)
            axe = Axe(name2, damage, weight)
            sword = Sword(name2, damage, weight)
            knight1.currentHp = currentHp
            knight1.equip(axe)
            "$knight1" shouldBe "Knight(name = '${knight1.name}', maxHp = ${knight1.maxHp}, currentHp = ${knight1.currentHp}, defense = ${knight1.defense}, equippedWeapon = ${knight1.equippedWeapon})"
            knight1.equip(sword)
            "$knight1" shouldBe "Knight(name = '${knight1.name}', maxHp = ${knight1.maxHp}, currentHp = ${knight1.currentHp}, defense = ${knight1.defense}, equippedWeapon = ${knight1.equippedWeapon})"
        }
    }

    // Weapons equip tests
    test("A knight can be equipped with a sword") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            knight1 = Knight(KNIGHT_NAME, KNIGHT_MAXHP, KNIGHT_DEFENSE, turnsQueue)
            sword = Sword(name, damage, weight)
            shouldNotThrow<UnableToEquipException> {
                knight1.equip(sword)
            }
        }
    }
    test("A knight can be equipped with an axe") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            knight1 = Knight(KNIGHT_NAME, KNIGHT_MAXHP, KNIGHT_DEFENSE, turnsQueue)
            axe = Axe(name, damage, weight)
            shouldNotThrow<UnableToEquipException> {
                knight1.equip(axe)
            }
        }
    }
    test("A knight can be equipped with a knife") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            knight1 = Knight(KNIGHT_NAME, KNIGHT_MAXHP, KNIGHT_DEFENSE, turnsQueue)
            knife = Knife(name, damage, weight)
            shouldNotThrow<UnableToEquipException> {
                knight1.equip(knife)
            }
        }
    }
    test("An exception should be thrown when a staff is equipped to a knight") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            knight1 = Knight(KNIGHT_NAME, KNIGHT_MAXHP, KNIGHT_DEFENSE, turnsQueue)
            staff = Staff(name, damage, weight, magicDamage)
            shouldThrow<UnableToEquipException> {
                knight1.equip(staff)
            }
        }
    }
    test("An exception should be thrown when a bow is equipped to a knight") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            knight1 = Knight(KNIGHT_NAME, KNIGHT_MAXHP, KNIGHT_DEFENSE, turnsQueue)
            bow = Bow(name, damage, weight)
            shouldThrow<UnableToEquipException> {
                knight1.equip(bow)
            }
        }
    }
})
