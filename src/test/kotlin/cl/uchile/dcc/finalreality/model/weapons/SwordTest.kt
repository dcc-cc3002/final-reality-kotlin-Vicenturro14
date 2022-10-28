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

class SwordTest : FunSpec({
    test("Two swords with the same parameters are equal") {
        checkAll(
        Arb.string(),
        Arb.positiveInt(100000),
        Arb.positiveInt(100000)
    ) {name, damage, weight ->
        val sword1 = Sword(name, damage, weight)
        val sword2 = Sword(name, damage, weight)
        sword1 shouldNotBeSameInstanceAs sword2
        sword2 shouldBe sword2
    }
}
    test("Two swords with different parameters shouldn't be equal") {
        checkAll(
        Arb.string(),
        Arb.string(),
        Arb.positiveInt(100000),
        Arb.positiveInt(100000),
        Arb.positiveInt(100000),
        Arb.positiveInt(100000)
        ) {name1, name2, damage1, damage2, weight1, weight2 ->
        assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
        val sword1 = Sword(name1, damage1, weight1)
        val sword2 = Sword(name2, damage2, weight2)
        sword1 shouldNotBeSameInstanceAs sword2
        sword1 shouldNotBe sword2
        }
    }
    test("Two equal swords should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val sword1 = Sword(name, damage, weight)
            val sword2 = Sword(name, damage, weight)
            sword1.shouldHaveSameHashCodeAs(sword2)
        }
    }
    test("Two different swords shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2)
            val sword1 = Sword(name1, damage1, weight1)
            val sword2 = Sword(name2, damage2, weight2)
            sword1.shouldNotHaveSameHashCodeAs(sword2)
        }
    }
    test("The string representation of a sword should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            val sword1 = Sword(name, damage, weight)
            "$sword1" shouldBe "Sword(${sword1.name}, ${sword1.damage}, ${sword1.weight})"
        }
    }
})
