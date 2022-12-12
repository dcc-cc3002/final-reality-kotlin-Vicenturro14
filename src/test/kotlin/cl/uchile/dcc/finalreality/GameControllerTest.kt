package cl.uchile.dcc.finalreality

import cl.uchile.dcc.finalreality.model.character.Enemy
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
import cl.uchile.dcc.finalreality.model.weapons.Knife
import cl.uchile.dcc.finalreality.model.weapons.Staff
import cl.uchile.dcc.finalreality.model.weapons.Sword
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.negativeInt
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbitrary.string
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.abs

private lateinit var gameController: GameController
private lateinit var axe: Axe
private lateinit var sword: Sword
private lateinit var bow: Bow
private lateinit var staff: Staff
private lateinit var knife: Knife
private lateinit var enemy: Enemy
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()
private const val STANDARD_PREFIX = "Standard"
private const val STANDARD_NUM_VALUE = 50
class GameControllerTest : FunSpec({
    beforeEach{
        gameController = GameController()
    }
    // Weapons creation tests
    test("The controller should be able to create an axe") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            axe = gameController.createAxe(name, damage, weight)
            axe shouldBe Axe(name, damage, weight)
        }
    }
    test("The controller should be able to create a bow") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            bow = gameController.createBow(name, damage, weight)
            bow shouldBe Bow(name, damage, weight)
        }
    }
    test("The controller should be able to create a knife") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            knife = gameController.createKnife(name, damage, weight)
            knife shouldBe Knife(name, damage, weight)
        }
    }
    test("The controller should be able to create a sword") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            sword = gameController.createSword(name, damage, weight)
            sword shouldBe Sword(name, damage, weight)
        }
    }
    test("The controller should be able to create a staff") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            staff = gameController.createStaff(name, damage, weight, magicDamage)
            staff shouldBe Staff(name, damage, weight, magicDamage)
        }
    }

    test("The controller should be able to create an enemy") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.positiveInt(100000)
        ) { name, weight, maxHp, defense, attack ->
            enemy = gameController.createEnemy(name, weight, maxHp, defense, attack)
            enemy shouldBe Enemy(name, weight, maxHp, defense, attack, turnsQueue)
        }
    }
    test("The absolute value of the stat should be used when the given stats are negative numbers") {
        checkAll(
            Arb.string(),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000)
        ) { name, weight, maxHp, defense, attack ->
            enemy = gameController.createEnemy(name, weight, maxHp, defense, attack)
            enemy shouldBe Enemy(name, abs(weight), abs(maxHp), abs(defense), abs(attack), turnsQueue)
        }
    }
    test("1 should be used when the given value of weight, maxHp or attack are 0") {
        checkAll(
            Arb.string(),
            Arb.nonNegativeInt(100000)
        ) { name, defense ->
            enemy = gameController.createEnemy(name, 0, 0, defense, 0)
            enemy shouldBe Enemy(name, 1, 1, defense, 1, turnsQueue)
        }
    }
})
