package cl.uchile.dcc.finalreality.model.character

import cl.uchile.dcc.finalreality.exceptions.InvalidStatValueException
import cl.uchile.dcc.finalreality.model.character.player.Engineer
import cl.uchile.dcc.finalreality.model.character.player.Knight
import cl.uchile.dcc.finalreality.model.character.player.Thief
import cl.uchile.dcc.finalreality.model.character.player.mages.BlackMage
import cl.uchile.dcc.finalreality.model.character.player.mages.WhiteMage
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
import cl.uchile.dcc.finalreality.model.weapons.Knife
import cl.uchile.dcc.finalreality.model.weapons.Staff
import cl.uchile.dcc.finalreality.model.weapons.Sword
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowUnit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowUnit
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInt
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.nonPositiveInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue

private lateinit var enemy: Enemy
private lateinit var engineer: Engineer
private lateinit var knight: Knight
private lateinit var thief: Thief
private lateinit var blackMage: BlackMage
private lateinit var whiteMage: WhiteMage
private lateinit var knife: Knife
private lateinit var sword: Sword
private lateinit var bow: Bow
private lateinit var staff: Staff
private lateinit var axe: Axe
private const val CHARACTER_NAME = "Constanza"
private const val CHARACTER_MAXHP = 200
private const val CHARACTER_DEFENSE = 100
private const val ENEMY_WEIGHT = 70
private const val ENEMY_ATTACK = 120
private const val MAGE_MAXMP = 100
private const val WEAPON_NAME = "TestWeapon"
private const val WEAPON_WEIGHT = 50
private const val WEAPON_DAMAGE = 100
private const val STAFF_MAGIC_DAMAGE = 30
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()

class AbstractCharacterTest : FunSpec({
    // maxHp parameter tests
    test("The maxHp of a character should be at least 1") {
        checkAll(
            Arb.positiveInt(100000)
        ) { maxHp ->
            shouldNotThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, ENEMY_ATTACK, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldNotThrow<InvalidStatValueException> {
                WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
        }
    }
    test("An exception should be thrown when the maxHp of a character is less than 1") {
        checkAll(
            Arb.nonPositiveInt(-100000)
        ) { maxHp ->
            shouldThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, ENEMY_ATTACK, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
            shouldThrow<InvalidStatValueException> {
                WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            }
        }
    }

    // currentHp property tests
    test("The currentHp of a character should be at least 0") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { maxHp, currentHp ->
            assume(currentHp <= maxHp)
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, ENEMY_ATTACK, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                engineer.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                knight.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                thief.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                blackMage.currentHp = currentHp
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                whiteMage.currentHp = currentHp
            }
        }
    }
    test("An exception should be thrown when the maxHp of a character is less than 0") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.negativeInt(-100000)
        ) { maxHp, currentHp ->
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, ENEMY_ATTACK, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)

            shouldThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                engineer.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                knight.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                thief.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                blackMage.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                whiteMage.currentHp = currentHp
            }
        }
    }
    test("The currentHp of a character should be at most maxHp") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(10000)
        ) { maxHp, currentHp1 ->
            assume(currentHp1 <= maxHp)
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, ENEMY_ATTACK, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            shouldNotThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                engineer.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                knight.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                thief.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                blackMage.currentHp = currentHp1
            }
            shouldNotThrowUnit<InvalidStatValueException> {
                whiteMage.currentHp = currentHp1
            }
        }
    }
    test("An exception should be thrown when the currentHp of a character is greater than maxHP") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { maxHp, currentHpAux ->
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, CHARACTER_DEFENSE, ENEMY_ATTACK, turnsQueue)
            engineer = Engineer(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            knight = Knight(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            thief = Thief(CHARACTER_NAME, maxHp, CHARACTER_DEFENSE, turnsQueue)
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, CHARACTER_DEFENSE, turnsQueue)
            val currentHp = maxHp + currentHpAux + 1
            /* The value assigned to currentHp is currentHpAux + maxHp + 1 to ensure that
            the assignment is out of range. */
            shouldThrowUnit<InvalidStatValueException> {
                enemy.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                engineer.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                knight.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                thief.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                blackMage.currentHp = currentHp
            }
            shouldThrowUnit<InvalidStatValueException> {
                whiteMage.currentHp = currentHp
            }
        }
    }

    // defense parameter tests
    test("The defense of a character should be al least 0") {
        checkAll(
            Arb.nonNegativeInt(100000)
        ) { defense ->
            shouldNotThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, CHARACTER_MAXHP, defense, ENEMY_ATTACK, turnsQueue)
                Engineer(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                Knight(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                Thief(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                BlackMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense, turnsQueue)
                WhiteMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense, turnsQueue)
            }
        }
    }
    test("An exception should be thrown when the defense of a character is less than 0") {
        checkAll(
            Arb.negativeInt(-100000)
        ) { defense ->
            shouldThrow<InvalidStatValueException> {
                Enemy(CHARACTER_NAME, ENEMY_WEIGHT, CHARACTER_MAXHP, defense, ENEMY_ATTACK, turnsQueue)
                Engineer(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                Knight(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                Thief(CHARACTER_NAME, CHARACTER_MAXHP, defense, turnsQueue)
                BlackMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense, turnsQueue)
                WhiteMage(CHARACTER_NAME, CHARACTER_MAXHP, MAGE_MAXMP, defense, turnsQueue)
            }
        }
    }

    // receiveAttack method tests
    test("A character should be able to receive an attack") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.positiveInt(10000),
            Arb.positiveInt(10000)
        ) { maxHp, damage, defense ->
            assume(defense / 3 <= damage)
            assume((damage - defense / 3) / 10 <= maxHp)
            val damageReceived = maxHp - (damage - defense / 3) / 10
            knife = Knife(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT)
            sword = Sword(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT)
            bow = Bow(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT)
            axe = Axe(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT)
            staff = Staff(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT, STAFF_MAGIC_DAMAGE)
            // Enemy
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, defense, ENEMY_ATTACK, turnsQueue)
            enemy.receiveAttack(damage)
            enemy.currentHp shouldBe damageReceived
            // Engineer
            engineer = Engineer(CHARACTER_NAME, maxHp, defense, turnsQueue)
            engineer.equip(bow)
            engineer.receiveAttack(damage)
            engineer.currentHp shouldBe damageReceived
            // Knight
            knight = Knight(CHARACTER_NAME, maxHp, defense, turnsQueue)
            knight.equip(axe)
            knight.receiveAttack(damage)
            knight.currentHp shouldBe damageReceived
            // Thief
            thief = Thief(CHARACTER_NAME, maxHp, defense, turnsQueue)
            thief.equip(sword)
            thief.receiveAttack(damage)
            thief.currentHp shouldBe damageReceived
            // Black Mage
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, defense, turnsQueue)
            blackMage.equip(knife)
            blackMage.receiveAttack(damage)
            blackMage.currentHp shouldBe damageReceived
            // White Mage
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, defense, turnsQueue)
            whiteMage.equip(staff)
            whiteMage.receiveAttack(damage)
            whiteMage.currentHp shouldBe damageReceived
        }
    }
    test("An exception should be thrown when the total damage received is greater than the currentHp") {
        checkAll(
            Arb.positiveInt(1000),
            Arb.positiveInt(100000),
            Arb.positiveInt(10000)
        ) { maxHp, damage, defense ->
            assume(defense / 3 <= damage)
            assume((damage - defense / 3) / 10 > maxHp)
            knife = Knife(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT)
            sword = Sword(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT)
            bow = Bow(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT)
            axe = Axe(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT)
            staff = Staff(WEAPON_NAME, WEAPON_DAMAGE, WEAPON_WEIGHT, STAFF_MAGIC_DAMAGE)
            // Enemy
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, defense, ENEMY_ATTACK, turnsQueue)
            shouldThrow<InvalidStatValueException> {
                enemy.receiveAttack(damage)
            }

            // Engineer
            engineer = Engineer(CHARACTER_NAME, maxHp, defense, turnsQueue)
            engineer.equip(bow)
            shouldThrow<InvalidStatValueException> {
                engineer.receiveAttack(damage)
            }

            // Knight
            knight = Knight(CHARACTER_NAME, maxHp, defense, turnsQueue)
            knight.equip(axe)
            shouldThrow<InvalidStatValueException> {
                knight.receiveAttack(damage)
            }

            // Thief
            thief = Thief(CHARACTER_NAME, maxHp, defense, turnsQueue)
            thief.equip(sword)
            shouldThrow<InvalidStatValueException> {
                thief.receiveAttack(damage)
            }

            // Black Mage
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, defense, turnsQueue)
            blackMage.equip(knife)
            shouldThrow<InvalidStatValueException> {
                blackMage.receiveAttack(damage)
            }

            // White Mage
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, defense, turnsQueue)
            whiteMage.equip(staff)
            shouldThrow<InvalidStatValueException> {
            whiteMage.receiveAttack(damage)
            }
        }
    }

    test("A character should be able to attack another character") {
        checkAll(
            Arb.positiveInt(100000),
            Arb.positiveInt(10000),
            Arb.positiveInt(10000)
        ) { maxHp, damage, defense ->
            assume(defense / 3 <= damage)
            assume((damage - defense / 3) / 10 <= maxHp)
            val damageReceived = maxHp - (damage - defense / 3) / 10
            val target1 = Thief(CHARACTER_NAME, maxHp, defense, turnsQueue)
            val target2 = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, defense, ENEMY_ATTACK, turnsQueue)
            knife = Knife(WEAPON_NAME, damage, WEAPON_WEIGHT)
            sword = Sword(WEAPON_NAME, damage, WEAPON_WEIGHT)
            bow = Bow(WEAPON_NAME, damage, WEAPON_WEIGHT)
            axe = Axe(WEAPON_NAME, damage, WEAPON_WEIGHT)
            staff = Staff(WEAPON_NAME, damage, WEAPON_WEIGHT, STAFF_MAGIC_DAMAGE)
            target1.equip(knife)
            // Enemy
            enemy = Enemy(CHARACTER_NAME, ENEMY_WEIGHT, maxHp, defense, damage, turnsQueue)
            enemy.attackCharacter(target2)
            target2.currentHp shouldBe damageReceived
            // Engineer
            engineer = Engineer(CHARACTER_NAME, maxHp, defense, turnsQueue)
            engineer.equip(bow)
            engineer.attackCharacter(target1)
            target1.currentHp shouldBe damageReceived
            target1.currentHp = maxHp
            // Knight
            knight = Knight(CHARACTER_NAME, maxHp, defense, turnsQueue)
            knight.equip(axe)
            knight.attackCharacter(target1)
            target1.currentHp shouldBe damageReceived
            target1.currentHp = maxHp
            // Thief
            thief = Thief(CHARACTER_NAME, maxHp, defense, turnsQueue)
            thief.equip(sword)
            thief.attackCharacter(target1)
            target1.currentHp shouldBe damageReceived
            target1.currentHp = maxHp
            // Black Mage
            blackMage = BlackMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, defense, turnsQueue)
            blackMage.equip(knife)
            blackMage.attackCharacter(target1)
            target1.currentHp shouldBe damageReceived
            target1.currentHp = maxHp
            // White Mage
            whiteMage = WhiteMage(CHARACTER_NAME, maxHp, MAGE_MAXMP, defense, turnsQueue)
            whiteMage.equip(staff)
            whiteMage.attackCharacter(target1)
            target1.currentHp shouldBe damageReceived
        }
    }
})
