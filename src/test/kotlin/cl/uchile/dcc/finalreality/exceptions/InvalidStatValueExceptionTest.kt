package cl.uchile.dcc.finalreality.exceptions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class InvalidStatValueExceptionTest : FunSpec({
    val prefix = "The required condition is not met. "
    test("An invalid stat exception can be thrown with a description") {
        checkAll(
            Arb.string()
        ) {description ->
            shouldThrow<InvalidStatValueException> {
                throw InvalidStatValueException(description)
            }.message shouldBe prefix + description
        }
    }
})
