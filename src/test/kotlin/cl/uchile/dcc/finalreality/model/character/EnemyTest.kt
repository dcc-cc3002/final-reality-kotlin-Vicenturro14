package cl.uchile.dcc.finalreality.model.character

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldHaveSameHashCodeAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.matchers.types.shouldNotHaveSameHashCodeAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbitrary.string
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class EnemyTest : FunSpec({
    lateinit var turnsQueue: BlockingQueue<GameCharacter>

    beforeEach {
        turnsQueue = LinkedBlockingQueue()
    }

    test("Two enemies with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, weight, maxHp, currentHp, defense, attack ->
            assume(currentHp <= maxHp)
            val enemy1 = Enemy(name, weight, maxHp, defense, attack, turnsQueue)
            val enemy2 = Enemy(name, weight, maxHp, defense, attack, turnsQueue)
            enemy1.currentHp = currentHp
            enemy2.currentHp = currentHp
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1 shouldBe enemy2
        }
    }
    test("Two enemies with different parameters shouldn't be equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, weight1, weight2, maxHp1, maxHp2, currentHp1, currentHp2, defense1, defense2, attack1, attack2 ->
            assume(name1 != name2 || weight1 != weight2 || maxHp1 != maxHp2 || currentHp1 != currentHp2 || defense1 != defense2 || attack1 != attack2)
            assume(currentHp1 <= maxHp1 && currentHp2 <= maxHp2)
            val enemy1 = Enemy(name1, weight1, maxHp1, defense1, attack1, turnsQueue)
            val enemy2 = Enemy(name2, weight2, maxHp2, defense2, attack2, turnsQueue)
            enemy1.currentHp = currentHp1
            enemy2.currentHp = currentHp2
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1 shouldNotBe enemy2
        }
    }
    test("Two equal enemies should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
            ) {name, weight, maxHp, currentHp, defense, attack ->
            assume(currentHp <= maxHp)
            val enemy1 = Enemy(name, weight, maxHp, defense, attack, turnsQueue)
            val enemy2 = Enemy(name, weight, maxHp, defense, attack, turnsQueue)
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
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
        ) { name1, name2, weight1, weight2, maxHp1, maxHp2, defense1, defense2, attack1, attack2 ->
            val enemy1 = Enemy(name1, weight1, maxHp1, defense1, attack1, turnsQueue)
            val enemy2 = Enemy(name2, weight2, maxHp2, defense2, attack2, turnsQueue)
            enemy1 shouldNotBeSameInstanceAs enemy2
            enemy1.shouldNotHaveSameHashCodeAs(enemy2)
        }
    }

    test("The string representation of a knife should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, weight, maxHp, defense, attack ->
            val enemy = Enemy(name, weight, maxHp, defense, attack, turnsQueue)
            "$enemy" shouldBe "Enemy(${enemy.name}, ${enemy.weight}, ${enemy.maxHp}"
        }

    }

    test("waitTurn") { }
    test("weight") {
    }
})
