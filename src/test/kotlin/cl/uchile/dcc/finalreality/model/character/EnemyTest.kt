package cl.uchile.dcc.finalreality.model.character

import cl.uchile.dcc.finalreality.exceptions.InvalidStatValueException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldHaveSameHashCodeAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.matchers.types.shouldNotHaveSameHashCodeAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.nonPositiveInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbitrary.string
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private lateinit var enemy1: Enemy
private lateinit var enemy2: Enemy
private lateinit var enemy3: Enemy
private const val ENEMY1_NAME = "Dylan"
private const val ENEMY2_NAME = "Javiera"
private const val ENEMY1_MAXHP = 100
private const val ENEMY2_MAXHP = 150
private const val ENEMY1_DEFENSE = 200
private const val ENEMY2_DEFENSE = 175
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class EnemyTest : FunSpec({
    beforeEach {
        turnsQueue.clear()
    }

    // equals method tests
    test("Two enemies with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(100000)
        ) { name, weight, maxHp, currentHp, defense ->
            assume(currentHp <= maxHp)
            enemy1 = Enemy(name, weight, maxHp, defense, turnsQueue)
            enemy2 = Enemy(name, weight, maxHp, defense, turnsQueue)
            enemy1.currentHp = currentHp
            enemy2.currentHp = currentHp
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1 shouldBe enemy2
        }
    }
    test("Two enemies with different parameters aren't equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, weight1, weight2, maxHp1, maxHp2, currentHp1, currentHp2, defense1, defense2 ->
            assume(name1 != name2 || weight1 != weight2 || maxHp1 != maxHp2 || currentHp1 != currentHp2 || defense1 != defense2)
            assume(currentHp1 <= maxHp1 && currentHp2 <= maxHp2)
            enemy1 = Enemy(name1, weight1, maxHp1, defense1, turnsQueue)
            enemy2 = Enemy(name2, weight2, maxHp2, defense2, turnsQueue)
            enemy1.currentHp = currentHp1
            enemy2.currentHp = currentHp2
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1 shouldNotBe enemy2
        }
    }

    // hashCode method tests
    test("Two equal enemies should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(100000)
        ) { name, weight, maxHp, currentHp, defense ->
            assume(currentHp <= maxHp)
            enemy1 = Enemy(name, weight, maxHp, defense, turnsQueue)
            enemy2 = Enemy(name, weight, maxHp, defense, turnsQueue)
            enemy1.currentHp = currentHp
            enemy2.currentHp = currentHp
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1.shouldHaveSameHashCodeAs(enemy2)
        }
    }
    test("Two different enemies shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(10000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name1, name2, weight1, weight2, maxHp1, maxHp2, currentHp1, currentHp2, defense1, defense2 ->
            assume(currentHp1 <= maxHp1 && currentHp2 <= maxHp2)
            assume(name1 != name2 || weight1 != weight2 || maxHp1 != maxHp2 || currentHp1 != currentHp2 || defense1 != defense2)
            enemy1 = Enemy(name1, weight1, maxHp1, defense1, turnsQueue)
            enemy2 = Enemy(name2, weight2, maxHp2, defense2, turnsQueue)
            enemy1.currentHp = currentHp1
            enemy2.currentHp = currentHp2
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1.shouldNotHaveSameHashCodeAs(enemy2)
        }
    }

    // toString method test
    test("The string representation of an enemy should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(10000),
            Arb.positiveInt(100000)
        ) { name, weight, maxHp, currentHp, defense ->
            assume(currentHp <= maxHp)
            enemy1 = Enemy(name, weight, maxHp, defense, turnsQueue)
            enemy1.currentHp = currentHp
            "$enemy1" shouldBe "Enemy(name = '${enemy1.name}', weight = ${enemy1.weight}, maxHp = ${enemy1.maxHp}, " +
                "currentHp = ${enemy1.currentHp}, defense = ${enemy1.defense})"
        }
    }

    // weight property tests
    test("The weight of an enemy should be at least 1") {
        checkAll(
            Arb.positiveInt(100000)
        ) { weight ->
            shouldNotThrow<InvalidStatValueException> {
                Enemy(ENEMY1_NAME, weight, ENEMY1_MAXHP, ENEMY1_DEFENSE, turnsQueue)
            }
        }
    }
    test("An exception should be thrown when the weight of an enemy is less than 1") {
        checkAll(
            Arb.nonPositiveInt(-100000)
        ) { weight ->
            shouldThrow<InvalidStatValueException> {
                Enemy(ENEMY2_NAME, weight, ENEMY2_MAXHP, ENEMY2_DEFENSE, turnsQueue)
            }
        }
    }

    // waitTurn method test
    test("An enemy should be able to be added to the turnsQueue") {
        val testWeight1 = 1
        val testWeight2 = 45
        val testWeight3 = 20
        enemy1 = Enemy(ENEMY1_NAME, testWeight1, ENEMY1_MAXHP, ENEMY1_DEFENSE, turnsQueue)
        enemy2 = Enemy(ENEMY2_NAME, testWeight2, ENEMY2_MAXHP, ENEMY2_DEFENSE, turnsQueue)
        enemy3 = Enemy(ENEMY1_NAME, testWeight3, ENEMY1_MAXHP, ENEMY1_DEFENSE, turnsQueue)
        val delay = 100 * (testWeight1 + testWeight2 + testWeight3)
        turnsQueue.isEmpty().shouldBeTrue()
        enemy1.waitTurn()
        enemy2.waitTurn()
        enemy3.waitTurn()
        withContext(Dispatchers.IO) {
            Thread.sleep(delay.toLong())
        }
        turnsQueue.contains(enemy1).shouldBeTrue()
        turnsQueue.contains(enemy2).shouldBeTrue()
        turnsQueue.contains(enemy3).shouldBeTrue()
    }
})
