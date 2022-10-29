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

class KnifeTest : FunSpec({
    test("Two knives with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) {name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val knife1 = Knife(name1, damage1, weight1)
            val knife2 = Knife(name2, damage2, weight2)
            val knife3 = Knife(name1, damage1, weight1)
            knife1 shouldNotBeSameInstanceAs knife2
            knife1 shouldNotBe knife2
            knife1 shouldNotBeSameInstanceAs knife3
            knife1 shouldBe knife3
        }
    }
    test("Two equal knives should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val knife1 = Knife(name1, damage1, weight1)
            val knife2 = Knife(name2, damage2, weight2)
            val knife3 = Knife(name1, damage1, weight1)
            knife1 shouldNotBeSameInstanceAs knife2
            knife1.shouldNotHaveSameHashCodeAs(knife2)
            knife1 shouldNotBeSameInstanceAs knife3
            knife1.shouldHaveSameHashCodeAs(knife3)
        }
    }
    test("The string representation of a knife should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val knife1 = Knife(name, damage, weight)
            "$knife1" shouldBe "Knife(name = ${knife1.name}, damage = ${knife1.damage}, weight = ${knife1.weight})"
        }
    }
})
