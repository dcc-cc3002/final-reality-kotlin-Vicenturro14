package cl.uchile.dcc.finalreality.exceptions

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.PropTestConfig
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbitrary.string
import io.kotest.property.assume
import io.kotest.property.checkAll

class RequireTest : FunSpec({

    test("Require.Stat(...).atLeast should throw an exception when the stat value is less than the 'least'") {
        checkAll(
            PropTestConfig(maxDiscardPercentage = 55),
            Arb.string(),
            Arb.int(),
            Arb.int()
        ) { statName, statValue, least ->
            assume(statValue < least)
            shouldThrow<InvalidStatValueException> {
                Require.Stat(statValue, statName) atLeast least
            }
        }
    }
    test("Require.Stat(...).atLeast shouldn't throw an exception when the stat value is greater than the 'least'") {
        checkAll(
            PropTestConfig(maxDiscardPercentage = 55),
            Arb.string(),
            Arb.int(),
            Arb.int()
        ) { statName, statValue, least ->
            assume(statValue > least)
            shouldNotThrow<InvalidStatValueException> {
                Require.Stat(statValue, statName) atLeast least
            }
        }
    }
    test("Require.Stat(...).atLeast shouldn't throw an exception when the stat value and the 'least' are the same") {
        checkAll(
            Arb.string(),
            Arb.int()
        ) { statName, statValue ->
            shouldNotThrow<InvalidStatValueException> {
                Require.Stat(statValue, statName) atLeast statValue
            }
        }
    }

    test("Require.Stat(...).inRange should throw an exception when the stat value is less than the start value of the 'range'") {
        checkAll(
            PropTestConfig(maxDiscardPercentage = 55),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.int(-50000, 50000),
            Arb.int(-50000, 50000)
        ) { statName, auxValue, rangeStart, rangeEnd ->
            assume(rangeStart <= rangeEnd)
            // statValue is rangeStart - auxValue, where auxValue > 0, to ensure that statValue < rangeStart.
            val statValue = rangeStart - auxValue
            shouldThrow<InvalidStatValueException> {
                Require.Stat(statValue, statName) inRange rangeStart..rangeEnd
            }
        }
    }

    test("Require.Stat(...).inRange should throw an exception when the stat value is greater than the end value of the 'range'") {
        checkAll(
            PropTestConfig(maxDiscardPercentage = 55),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.int(-50000, 50000),
            Arb.int(-50000, 50000)
        ) { statName, auxValue, rangeStart, rangeEnd ->
            assume(rangeStart <= rangeEnd)
            // statValue is rangeEnd + auxValue, where auxValue > 0, to ensure that statValue > rangeEnd.
            val statValue = rangeEnd + auxValue
            shouldThrow<InvalidStatValueException> {
                Require.Stat(statValue, statName) inRange rangeStart..rangeEnd
            }
        }
    }
    test("Require.Stat(...).inRange shouldn't throw an exception when the stat value is in the 'range'") {
        checkAll(
            PropTestConfig(maxDiscardPercentage = 55),
            Arb.string(),
            Arb.int(-50000, 50000),
            Arb.int(-50000, 50000)
        ) { statName, rangeStart, rangeEnd ->
            assume(rangeStart <= rangeEnd)
            val statValue = (rangeStart..rangeEnd).random()
            shouldNotThrow<InvalidStatValueException> {
                Require.Stat(statValue, statName) inRange rangeStart..rangeEnd
            }
        }
    }
})
