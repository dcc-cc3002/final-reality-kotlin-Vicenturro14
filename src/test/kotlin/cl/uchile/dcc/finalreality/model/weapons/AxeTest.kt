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
    test("Two axes with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) {name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val axe1 = Axe(name1, damage1, weight1)
            val axe2 = Axe(name2, damage2, weight2)
            val axe3 = Axe(name1, damage1, weight1)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1 shouldNotBe axe2
            axe1 shouldNotBeSameInstanceAs axe3
            axe1 shouldBe axe3
        }
    }

    test("Two equal axes should have the same hashCode") {
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
            val axe3 = Axe(name1, damage1, weight1)
            axe1 shouldNotBeSameInstanceAs axe2
            axe1.shouldNotHaveSameHashCodeAs(axe2)
            axe1 shouldNotBeSameInstanceAs axe3
            axe1.shouldHaveSameHashCodeAs(axe3)
        }
    }

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
})
