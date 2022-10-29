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

class BowTest : FunSpec({
    test("Two bows with the same parameters are equal") {
        checkAll(
        Arb.string(),
        Arb.string(),
        Arb.positiveInt(100000),
        Arb.positiveInt(100000),
        Arb.positiveInt(100000),
        Arb.positiveInt(100000)
        ) {name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val bow1 = Bow(name1, damage1, weight1)
            val bow2 = Bow(name2, damage2, weight2)
            val bow3 = Bow(name1, damage1, weight1)
            bow1 shouldNotBeSameInstanceAs bow3
            bow1 shouldBe bow3
            bow1 shouldNotBeSameInstanceAs bow2
            bow1 shouldNotBe bow2
        }
    }

    test("Two equal bows should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val bow1 = Bow(name1, damage1, weight1)
            val bow2 = Bow(name2, damage2, weight2)
            val bow3 = Bow(name1, damage1, weight1)
            bow1 shouldNotBeSameInstanceAs bow2
            bow1.shouldNotHaveSameHashCodeAs(bow2)
            bow1 shouldNotBeSameInstanceAs bow3
            bow1.shouldHaveSameHashCodeAs(bow3)
        }
    }

    test("The string representation of an bow should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val bow1 = Bow(name, damage, weight)
            "$bow1" shouldBe "Bow(name = ${bow1.name}, damage = ${bow1.damage}, weight = ${bow1.weight})"
        }
    }
})
