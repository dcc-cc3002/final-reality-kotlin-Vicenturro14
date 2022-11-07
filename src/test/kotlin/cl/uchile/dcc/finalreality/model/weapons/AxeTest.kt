package cl.uchile.dcc.finalreality.model.weapons

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

class AxeTest : FunSpec({
    // equals method tests
    test("Two axes with the same parameters should be equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
        ) { name, damage, weight ->
            val axe1 = Axe(name, damage, weight)
            val axe2 = Axe(name, damage, weight)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1 shouldBe axe2
        }
    }
    test("Two axes with different parameters shouldn't be equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val axe1 = Axe(name1, damage1, weight1)
            val axe2 = Axe(name2, damage2, weight2)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1 shouldNotBe axe2
        }
    }

    // hashCode method tests
    test("Two equal axes should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val axe1 = Axe(name, damage, weight)
            val axe2 = Axe(name, damage, weight)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1.shouldHaveSameHashCodeAs(axe2)
        }
    }
    test("Two different axes shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val axe1 = Axe(name1, damage1, weight1)
            val axe2 = Axe(name2, damage2, weight2)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1.shouldNotHaveSameHashCodeAs(axe2)
        }
    }
    // toString method test
    test("The string representation of an axe should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val axe1 = Axe(name, damage, weight)
            "$axe1" shouldBe "Axe(name = '${axe1.name}', damage = ${axe1.damage}, weight = ${axe1.weight})"
        }
    }

    // equipTo
})
