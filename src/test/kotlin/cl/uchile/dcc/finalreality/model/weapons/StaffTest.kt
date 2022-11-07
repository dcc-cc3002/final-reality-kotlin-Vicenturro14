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

class StaffTest : FunSpec({
    // equals method tests
    test("Two staffs with the same parameters are equal") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            val staff1 = Staff(name, damage, weight, magicDamage)
            val staff2 = Staff(name, damage, weight, magicDamage)
            staff1 shouldNotBeSameInstanceAs staff2
            staff1 shouldBe staff2
        }
    }
    test("Two staffs with different parameters aren't equal") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2, magicDamage1, magicDamage2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2 || magicDamage1 != magicDamage2)
            val staff1 = Staff(name1, damage1, weight1, magicDamage1)
            val staff2 = Staff(name2, damage2, weight2, magicDamage2)
            staff1 shouldNotBeSameInstanceAs staff2
            staff1 shouldNotBe staff2
        }
    }

    // hashCode method tests
    test("Two equal staffs should have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            val staff1 = Staff(name, damage, weight, magicDamage)
            val staff2 = Staff(name, damage, weight, magicDamage)
            staff1 shouldNotBeSameInstanceAs staff2
            staff1.shouldHaveSameHashCodeAs(staff2)
        }
    }
    test("Two different staffs shouldn't have the same hashCode") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name1, name2, damage1, damage2, weight1, weight2, magicDamage1, magicDamage2 ->
            assume(name1 != name2 || damage1 != damage2 || weight1 != weight2 || magicDamage1 != magicDamage2)
            val staff1 = Staff(name1, damage1, weight1, magicDamage1)
            val staff2 = Staff(name2, damage2, weight2, magicDamage2)
            staff1 shouldNotBeSameInstanceAs staff2
            staff1.shouldNotHaveSameHashCodeAs(staff2)
        }
    }

    // toString method test
    test("The string representation of a staff should be correct") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            val staff1 = Staff(name, damage, weight, magicDamage)
            "$staff1" shouldBe "Staff(name = '${staff1.name}', damage = ${staff1.damage}, weight = ${staff1.weight}, magicDamage = ${staff1.magicDamage})"
        }
    }
})
