package cl.uchile.dcc.finalreality.model.character

import cl.uchile.dcc.finalreality.exceptions.InvalidStatValueException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowUnit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowUnit
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldHaveSameHashCodeAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.matchers.types.shouldNotHaveSameHashCodeAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInt
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
private const val ENEMY1_WEIGHT = 70
private const val ENEMY2_WEIGHT = 1
private const val ENEMY1_MAXHP = 100
private const val ENEMY2_MAXHP = 150
private const val ENEMY1_DEFENSE = 200
private const val ENEMY2_DEFENSE = 175
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class EnemyTest : FunSpec({

    beforeEach {
        turnsQueue.clear()
    }

    test("Two enemies with the same parameters are equal") {
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
            enemy3 = Enemy(name1, weight1, maxHp1, defense1, turnsQueue)
            enemy1.currentHp = currentHp1
            enemy2.currentHp = currentHp2
            enemy3.currentHp = currentHp1
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1 shouldNotBe enemy2
            enemy1 shouldNotBeSameInstanceAs enemy3
            enemy1 shouldBe enemy3
        }
    }

    test("Two equal enemies should have the same hashCode") {
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
            enemy3 = Enemy(name1, weight1, maxHp1, defense1, turnsQueue)
            enemy1.currentHp = currentHp1
            enemy2.currentHp = currentHp2
            enemy3.currentHp = currentHp1
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1.shouldNotHaveSameHashCodeAs(enemy2)
            enemy1 shouldNotBeSameInstanceAs enemy3
            enemy1.shouldHaveSameHashCodeAs(enemy3)
        }
    }

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

    test("The weight of an enemy should be at least 1") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonPositiveInt(-100000)
        ) { weight1, weight2 ->
            shouldNotThrow<InvalidStatValueException> {
                Enemy(ENEMY1_NAME, weight1, ENEMY1_MAXHP, ENEMY1_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Enemy(ENEMY2_NAME, weight2, ENEMY2_MAXHP, ENEMY2_DEFENSE, turnsQueue)
            }
        }
    }

    test("An enemy should be able to be added to the turnsQueue") {
        enemy1 = Enemy(ENEMY1_NAME, ENEMY1_WEIGHT, ENEMY1_MAXHP, ENEMY1_DEFENSE, turnsQueue)
        enemy2 = Enemy(ENEMY2_NAME, ENEMY2_WEIGHT, ENEMY2_MAXHP, ENEMY2_DEFENSE, turnsQueue)
        val delay = 100 * (ENEMY1_WEIGHT + ENEMY2_WEIGHT)
        turnsQueue.isEmpty().shouldBeTrue()
        enemy1.waitTurn()
        enemy2.waitTurn()
        withContext(Dispatchers.IO) {
            Thread.sleep(delay.toLong())
        }
        turnsQueue.poll() shouldBe enemy2
        turnsQueue.poll() shouldBe enemy1
    }


})
