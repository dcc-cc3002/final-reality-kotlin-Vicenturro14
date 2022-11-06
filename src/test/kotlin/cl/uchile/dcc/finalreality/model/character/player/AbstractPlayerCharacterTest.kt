package cl.uchile.dcc.finalreality.model.character.player

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.character.player.mages.BlackMage
import cl.uchile.dcc.finalreality.model.character.player.mages.WhiteMage
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
import cl.uchile.dcc.finalreality.model.weapons.Knife
import cl.uchile.dcc.finalreality.model.weapons.Staff
import cl.uchile.dcc.finalreality.model.weapons.Sword
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private lateinit var engineer: Engineer
private lateinit var knight: Knight
private lateinit var thief: Thief
private lateinit var blackMage: BlackMage
private lateinit var whiteMage: WhiteMage
private lateinit var testSword: Sword
private lateinit var testBow: Bow
private lateinit var testKnife: Knife
private lateinit var testStaff: Staff
private lateinit var testAxe: Axe
private const val PLAYER_CHARACTER_NAME = "Lucas"
private const val PLAYER_CHARACTER_MAXHP = 200
private const val PLAYER_CHARACTER_DEFENSE = 100
private const val MAGE_MAXMP = 100
private const val WEAPON_NAME = "testWeapon"
private const val WEAPON_DAMAGE = 50
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class AbstractPlayerCharacterTest : FunSpec({
    beforeEach {
        turnsQueue.clear()
        engineer = Engineer(PLAYER_CHARACTER_NAME, PLAYER_CHARACTER_MAXHP, PLAYER_CHARACTER_DEFENSE, turnsQueue)
        knight = Knight(PLAYER_CHARACTER_NAME, PLAYER_CHARACTER_MAXHP, PLAYER_CHARACTER_DEFENSE, turnsQueue)
        thief = Thief(PLAYER_CHARACTER_NAME, PLAYER_CHARACTER_MAXHP, PLAYER_CHARACTER_DEFENSE, turnsQueue)
        blackMage = BlackMage(PLAYER_CHARACTER_NAME, PLAYER_CHARACTER_MAXHP, MAGE_MAXMP, PLAYER_CHARACTER_DEFENSE, turnsQueue)
        whiteMage = WhiteMage(PLAYER_CHARACTER_NAME, PLAYER_CHARACTER_MAXHP, MAGE_MAXMP, PLAYER_CHARACTER_DEFENSE, turnsQueue)
    }
    test("A PlayerCharacter should be able to be equipped with a weapon") {
        checkAll(
            Arb.string(),
            Arb.nonNegativeInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) {name, damage, weight, magicDamage ->
            testAxe = Axe(name, damage, weight)
            engineer.equip(testAxe)
            engineer.equippedWeapon shouldBe testAxe

            testSword = Sword(name, damage, weight)
            knight.equip(testSword)
            knight.equippedWeapon shouldBe testSword

            testBow = Bow(name, damage, weight)
            thief.equip(testBow)
            thief.equippedWeapon shouldBe testBow

            testKnife = Knife(name, damage, weight)
            blackMage.equip(testKnife)
            blackMage.equippedWeapon shouldBe testKnife

            testStaff = Staff(name, damage, weight, magicDamage)
            whiteMage.equip(testStaff)
            whiteMage.equippedWeapon shouldBe testStaff
        }
    }

    test("A PlayerCharacter should be able to be added to the turnsQueue") {
        val testWeight1 = 1
        val testWeight2 = 8
        val testWeight3 = 14
        val testWeight4 = 29
        val testWeight5 = 31
        val delay = 100 * (testWeight1 + testWeight2 + testWeight3 + testWeight4 + testWeight5)
        testAxe = Axe(WEAPON_NAME, WEAPON_DAMAGE, testWeight1)
        testSword = Sword(WEAPON_NAME, WEAPON_DAMAGE, testWeight2)
        testBow = Bow(WEAPON_NAME, WEAPON_DAMAGE, testWeight3)
        testKnife = Knife(WEAPON_NAME, WEAPON_DAMAGE, testWeight4)
        testStaff = Staff(WEAPON_NAME, WEAPON_DAMAGE, testWeight5, WEAPON_DAMAGE)
        engineer.equip(testAxe)
        knight.equip(testSword)
        thief.equip(testBow)
        blackMage.equip(testKnife)
        whiteMage.equip(testStaff)
        engineer.waitTurn()
        knight.waitTurn()
        thief.waitTurn()
        blackMage.waitTurn()
        whiteMage.waitTurn()
        withContext(Dispatchers.IO) {
            Thread.sleep(delay.toLong())
        }
        turnsQueue.contains(engineer).shouldBeTrue()
        turnsQueue.contains(thief).shouldBeTrue()
        turnsQueue.contains(knight).shouldBeTrue()
        turnsQueue.contains(blackMage).shouldBeTrue()
        turnsQueue.contains(whiteMage).shouldBeTrue()
    }
})
