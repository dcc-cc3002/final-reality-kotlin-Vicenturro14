package cl.uchile.dcc.finalreality.exceptions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class UnableToEquipExceptionTest : FunSpec({
    val prefix = "Impossible to equip item. "
    test("An unable to equip exception can be thrown with a description.") {
        checkAll(
            Arb.string()
        ) { description ->
            shouldThrow<UnableToEquipException> {
                throw UnableToEquipException(description)
            }.message shouldBe prefix + description
        }
    }
})
