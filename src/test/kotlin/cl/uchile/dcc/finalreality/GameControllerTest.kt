package cl.uchile.dcc.finalreality

import cl.uchile.dcc.finalreality.model.character.Enemy
import cl.uchile.dcc.finalreality.model.character.GameCharacter
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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInt
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.abs

private lateinit var gameController: GameController
private lateinit var axe: Axe
private lateinit var sword: Sword
private lateinit var bow: Bow
private lateinit var staff: Staff
private lateinit var knife: Knife
private lateinit var enemy: Enemy
private lateinit var engineer: Engineer
private lateinit var thief: Thief
private lateinit var knight: Knight
private lateinit var blackMage: BlackMage
private lateinit var whiteMage: WhiteMage
private val turnsQueue = LinkedBlockingQueue<GameCharacter>()
private const val STANDARD_PREFIX = "Standard"
private const val STANDARD_NUM_VALUE = 50
private val standardBow = Bow("$STANDARD_PREFIX bow", STANDARD_NUM_VALUE, STANDARD_NUM_VALUE)
private val standardKnife = Knife("$STANDARD_PREFIX knife", STANDARD_NUM_VALUE, STANDARD_NUM_VALUE)
private val standardStaff = Staff("$STANDARD_PREFIX staff", STANDARD_NUM_VALUE, STANDARD_NUM_VALUE, STANDARD_NUM_VALUE)
private val standardSword = Sword("$STANDARD_PREFIX sword", STANDARD_NUM_VALUE, STANDARD_NUM_VALUE)
class GameControllerTest : FunSpec({
    beforeEach{
        gameController = GameController()
    }
    // Weapons creation tests
    test("The controller should be able to create an axe") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            axe = gameController.createAxe(name, damage, weight)
            axe shouldBe Axe(name, damage, weight)
        }
    }
    test("The controller should be able to create a bow") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            bow = gameController.createBow(name, damage, weight)
            bow shouldBe Bow(name, damage, weight)
        }
    }
    test("The controller should be able to create a knife") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            knife = gameController.createKnife(name, damage, weight)
            knife shouldBe Knife(name, damage, weight)
        }
    }
    test("The controller should be able to create a sword") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight ->
            sword = gameController.createSword(name, damage, weight)
            sword shouldBe Sword(name, damage, weight)
        }
    }
    test("The controller should be able to create a staff") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { name, damage, weight, magicDamage ->
            staff = gameController.createStaff(name, damage, weight, magicDamage)
            staff shouldBe Staff(name, damage, weight, magicDamage)
        }
    }

    // Enemy creation tests
    test("The controller should be able to create an enemy") {
        checkAll(
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.positiveInt(100000)
        ) { name, weight, maxHp, defense, attack ->
            enemy = gameController.createEnemy(name, weight, maxHp, defense, attack)
            enemy shouldBe Enemy(name, weight, maxHp, defense, attack, turnsQueue)
        }
    }
    test("The controller should use the absolute value of a stat in the enemy when the given stat is a negative number") {
        checkAll(
            Arb.string(),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000)
        ) { name, weight, maxHp, defense, attack ->
            enemy = gameController.createEnemy(name, weight, maxHp, defense, attack)
            enemy shouldBe Enemy(name, abs(weight), abs(maxHp), abs(defense), abs(attack), turnsQueue)
        }
    }
    test("The controller should use 1 as weight, maxHp or attack of the enemy when the given values are 0") {
        checkAll(
            Arb.string(),
            Arb.nonNegativeInt(100000)
        ) { name, defense ->
            enemy = gameController.createEnemy(name, 0, 0, defense, 0)
            enemy shouldBe Enemy(name, 1, 1, defense, 1, turnsQueue)
        }
    }

    // Engineer creation tests
    test("The controller should be able to create an engineer") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { charName, weaponName, maxHp, damage, weight, defense ->
            bow = Bow(weaponName, damage, weight)
            axe = Axe(weaponName, damage, weight)

            engineer = gameController.createEngineer(charName, maxHp, defense, bow)
            engineer shouldBe Engineer(charName, maxHp, defense, turnsQueue)
            engineer.equippedWeapon shouldBe bow

            engineer = gameController.createEngineer(charName, maxHp, defense, axe)
            engineer shouldBe Engineer(charName, maxHp, defense, turnsQueue)
            engineer.equippedWeapon shouldBe axe
        }
    }
    test("The controller should use the absolute value of a stat in the engineer when the given stat is a negative number") {
        checkAll(
            Arb.string(),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000)
        ) { name, maxHp, defense ->
            engineer = gameController.createEngineer(name, maxHp, defense, standardBow)
            engineer shouldBe Engineer(name, abs(maxHp), abs(defense), turnsQueue)
        }
    }
    test("The controller should use 1 as maxHp of the engineer when the given maxHp is 0") {
        checkAll(
            Arb.string(),
            Arb.nonNegativeInt(100000)
        ) { name, defense ->
            engineer = gameController.createEngineer(name, 0, defense, standardBow)
            engineer shouldBe Engineer(name, 1,defense, turnsQueue)
        }
    }
    test("A standard bow should be equipped to the engineer when the given weapon is unable to be equipped to an engineer.") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.nonNegativeInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { charName, weaponName, defense, maxHp, damage, weight, magicDamage ->
            sword = Sword(weaponName, damage, weight)
            knife = Knife(weaponName, damage, weight)
            staff = Staff(weaponName, damage, weight, magicDamage)

            engineer = gameController.createEngineer(charName, maxHp, defense, sword)
            engineer.equippedWeapon shouldBe standardBow

            engineer = gameController.createEngineer(charName, maxHp, defense, knife)
            engineer.equippedWeapon shouldBe standardBow

            engineer = gameController.createEngineer(charName, maxHp, defense, staff)
            engineer.equippedWeapon shouldBe standardBow
        }
    }
    // Thief creation tests
    test("The controller should be able to create an thief") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { charName, weaponName, maxHp, damage, weight, defense ->
            bow = Bow(weaponName, damage, weight)
            sword = Sword(weaponName, damage, weight)
            knife = Knife(weaponName, damage, weight)

            thief = gameController.createThief(charName, maxHp, defense, bow)
            thief shouldBe Thief(charName, maxHp, defense, turnsQueue)
            thief.equippedWeapon shouldBe bow

            thief = gameController.createThief(charName, maxHp, defense, sword)
            thief shouldBe Thief(charName, maxHp, defense, turnsQueue)
            thief.equippedWeapon shouldBe sword

            thief = gameController.createThief(charName, maxHp, defense, knife)
            thief shouldBe Thief(charName, maxHp, defense, turnsQueue)
            thief.equippedWeapon shouldBe knife
        }
    }
    test("The controller should use the absolute value of a stat in the thief when the given stat is a negative number") {
        checkAll(
            Arb.string(),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000)
        ) { name, maxHp, defense ->
            thief = gameController.createThief(name, maxHp, defense, standardKnife)
            thief shouldBe Thief(name, abs(maxHp), abs(defense), turnsQueue)
        }
    }
    test("The controller should use 1 as maxHp of the thief when the given maxHp is 0") {
        checkAll(
            Arb.string(),
            Arb.nonNegativeInt(100000)
        ) { name, defense ->
            thief = gameController.createThief(name, 0, defense, standardKnife)
            thief shouldBe Thief(name, 1,defense, turnsQueue)
        }
    }
    test("A standard knife should be equipped to the thief when the given weapon is unable to be equipped to an thief.") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.nonNegativeInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { charName, weaponName, defense, maxHp, damage, weight, magicDamage ->
            axe = Axe(weaponName, damage, weight)
            staff = Staff(weaponName, damage, weight, magicDamage)

            thief = gameController.createThief(charName, maxHp, defense, axe)
            thief.equippedWeapon shouldBe standardKnife

            thief = gameController.createThief(charName, maxHp, defense, staff)
            thief.equippedWeapon shouldBe standardKnife
        }
    }    
    // Knight creation tests
    test("The controller should be able to create an knight") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000)
        ) { charName, weaponName, maxHp, damage, weight, defense ->
            axe = Axe(weaponName, damage, weight)
            sword = Sword(weaponName, damage, weight)
            knife = Knife(weaponName, damage, weight)

            knight = gameController.createKnight(charName, maxHp, defense, axe)
            knight shouldBe Knight(charName, maxHp, defense, turnsQueue)
            knight.equippedWeapon shouldBe axe

            knight = gameController.createKnight(charName, maxHp, defense, sword)
            knight shouldBe Knight(charName, maxHp, defense, turnsQueue)
            knight.equippedWeapon shouldBe sword

            knight = gameController.createKnight(charName, maxHp, defense, knife)
            knight shouldBe Knight(charName, maxHp, defense, turnsQueue)
            knight.equippedWeapon shouldBe knife
        }
    }
    test("The controller should use the absolute value of a stat in the knight when the given stat is a negative number") {
        checkAll(
            Arb.string(),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000)
        ) { name, maxHp, defense ->
            knight = gameController.createKnight(name, maxHp, defense, standardSword)
            knight shouldBe Knight(name, abs(maxHp), abs(defense), turnsQueue)
        }
    }
    test("The controller should use 1 as maxHp of the knight when the given maxHp is 0") {
        checkAll(
            Arb.string(),
            Arb.nonNegativeInt(100000)
        ) { name, defense ->
            knight = gameController.createKnight(name, 0, defense, standardSword)
            knight shouldBe Knight(name, 1,defense, turnsQueue)
        }
    }
    test("A standard sword should be equipped to the knight when the given weapon is unable to be equipped to an knight.") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.nonNegativeInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { charName, weaponName, defense, maxHp, damage, weight, magicDamage ->
            bow = Bow(weaponName, damage, weight)
            staff = Staff(weaponName, damage, weight, magicDamage)

            knight = gameController.createKnight(charName, maxHp, defense, bow)
            knight.equippedWeapon shouldBe standardSword

            knight = gameController.createKnight(charName, maxHp, defense, staff)
            knight.equippedWeapon shouldBe standardSword
        }
    }
                                       
    // BlackMage creation tests
    test("The controller should be able to create an black mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { charName, weaponName, maxHp, damage, magicDamage, weight, defense, maxMp ->
            staff = Staff(weaponName, damage, weight, magicDamage)
            knife = Knife(weaponName, damage, weight)

            blackMage = gameController.createBlackMage(charName, maxHp, maxMp, defense, staff)
            blackMage shouldBe BlackMage(charName, maxHp, maxMp, defense, turnsQueue)
            blackMage.equippedWeapon shouldBe staff
            
            blackMage = gameController.createBlackMage(charName, maxHp, maxMp, defense, knife)
            blackMage shouldBe BlackMage(charName, maxHp, maxMp, defense, turnsQueue)
            blackMage.equippedWeapon shouldBe knife
        }
    }
    test("The controller should use the absolute value of a stat in the blackMage when the given stat is a negative number") {
        checkAll(
            Arb.string(),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000)
        ) { name, maxHp, maxMp, defense ->
            blackMage = gameController.createBlackMage(name, maxHp, maxMp, defense, standardStaff)
            blackMage shouldBe BlackMage(name, abs(maxHp), abs(maxMp), abs(defense), turnsQueue)
        }
    }
    test("The controller should use 1 as maxHp of the blackMage when the given maxHp is 0") {
        checkAll(
            Arb.string(),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name, maxMp, defense ->
            blackMage = gameController.createBlackMage(name, 0, maxMp, defense, standardStaff)
            blackMage shouldBe BlackMage(name, 1, maxMp,defense, turnsQueue)
        }
    }
    test("A standard staff should be equipped to the blackMage when the given weapon is unable to be equipped to an blackMage.") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { charName, weaponName, defense, maxMp, maxHp, damage, weight ->
            bow = Bow(weaponName, damage, weight)
            axe = Axe(weaponName, damage, weight)
            sword = Sword(weaponName, damage, weight)

            blackMage = gameController.createBlackMage(charName, maxHp, maxMp, defense, bow)
            blackMage.equippedWeapon shouldBe standardStaff

            blackMage = gameController.createBlackMage(charName, maxHp, maxMp, defense, sword)
            blackMage.equippedWeapon shouldBe standardStaff
            
            blackMage = gameController.createBlackMage(charName, maxHp, maxMp, defense, axe)
            blackMage.equippedWeapon shouldBe standardStaff
        }
    }
                                       
    // WhiteMage creation tests
    test("The controller should be able to create an white mage") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { charName, weaponName, maxHp, damage, magicDamage, weight, defense, maxMp ->
            staff = Staff(weaponName, damage, weight, magicDamage)

            whiteMage = gameController.createWhiteMage(charName, maxHp, maxMp, defense, staff)
            whiteMage shouldBe WhiteMage(charName, maxHp, maxMp, defense, turnsQueue)
            whiteMage.equippedWeapon shouldBe staff
        }
    }
    test("The controller should use the absolute value of a stat in the whiteMage when the given stat is a negative number") {
        checkAll(
            Arb.string(),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000),
            Arb.negativeInt(-100000)
        ) { name, maxHp, maxMp, defense ->
            whiteMage = gameController.createWhiteMage(name, maxHp, maxMp, defense, standardStaff)
            whiteMage shouldBe WhiteMage(name, abs(maxHp), abs(maxMp), abs(defense), turnsQueue)
        }
    }
    test("The controller should use 1 as maxHp of the whiteMage when the given maxHp is 0") {
        checkAll(
            Arb.string(),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { name, maxMp, defense ->
            whiteMage = gameController.createWhiteMage(name, 0, maxMp, defense, standardStaff)
            whiteMage shouldBe WhiteMage(name, 1, maxMp,defense, turnsQueue)
        }
    }
    test("A standard staff should be equipped to the whiteMage when the given weapon is unable to be equipped to an whiteMage.") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000)
        ) { charName, weaponName, defense, maxMp, maxHp, damage, weight ->
            bow = Bow(weaponName, damage, weight)
            axe = Axe(weaponName, damage, weight)
            sword = Sword(weaponName, damage, weight)
            knife = Knife(weaponName, damage, weight)

            whiteMage = gameController.createWhiteMage(charName, maxHp, maxMp, defense, bow)
            whiteMage.equippedWeapon shouldBe standardStaff

            whiteMage = gameController.createWhiteMage(charName, maxHp, maxMp, defense, sword)
            whiteMage.equippedWeapon shouldBe standardStaff

            whiteMage = gameController.createWhiteMage(charName, maxHp, maxMp, defense, axe)
            whiteMage.equippedWeapon shouldBe standardStaff

            whiteMage = gameController.createWhiteMage(charName, maxHp, maxMp, defense, knife)
            whiteMage.equippedWeapon shouldBe standardStaff
        }
    }

    // changeWeapon method tests
    test("The controller should be able to change the weapon from a player character") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { charName, weaponName, maxHp, damage, magicDamage, weight, defense, maxMp ->
            bow = Bow(weaponName, damage, weight)
            axe = Axe(weaponName, damage, weight)
            sword = Sword(weaponName, damage, weight)
            knife = Knife(weaponName, damage, weight)
            staff = Staff(weaponName, damage, weight, magicDamage)

            engineer = gameController.createEngineer(charName, maxHp, defense, standardBow)
            gameController.changeWeapon(engineer, axe)
            engineer.equippedWeapon shouldBe axe
            gameController.changeWeapon(engineer, bow)
            engineer.equippedWeapon shouldBe bow

            knight = gameController.createKnight(charName, maxHp, defense, standardSword)
            gameController.changeWeapon(knight, axe)
            knight.equippedWeapon shouldBe axe
            gameController.changeWeapon(knight, sword)
            knight.equippedWeapon shouldBe sword
            gameController.changeWeapon(knight, knife)
            knight.equippedWeapon shouldBe knife

            thief = gameController.createThief(charName, maxHp, defense, standardKnife)
            gameController.changeWeapon(thief, bow)
            thief.equippedWeapon shouldBe bow
            gameController.changeWeapon(thief, sword)
            thief.equippedWeapon shouldBe sword
            gameController.changeWeapon(thief, knife)
            thief.equippedWeapon shouldBe knife

            blackMage = gameController.createBlackMage(charName, maxHp, maxMp, defense, standardStaff)
            gameController.changeWeapon(blackMage, knife)
            blackMage.equippedWeapon shouldBe knife
            gameController.changeWeapon(blackMage, staff)
            blackMage.equippedWeapon shouldBe staff

            whiteMage = gameController.createWhiteMage(charName, maxHp, maxMp, defense, standardStaff)
            gameController.changeWeapon(whiteMage, staff)
            whiteMage.equippedWeapon shouldBe staff
        }
    }
    test("The controller shouldn't change the weapon from a player character when the new weapon can't be equipped to the character") {
        checkAll(
            Arb.string(),
            Arb.string(),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.positiveInt(100000),
            Arb.nonNegativeInt(100000),
            Arb.nonNegativeInt(100000)
        ) { charName, weaponName, maxHp, damage, magicDamage, weight, defense, maxMp ->
            bow = Bow(weaponName, damage, weight)
            axe = Axe(weaponName, damage, weight)
            sword = Sword(weaponName, damage, weight)
            knife = Knife(weaponName, damage, weight)
            staff = Staff(weaponName, damage, weight, magicDamage)

            engineer = gameController.createEngineer(charName, maxHp, defense, standardBow)
            gameController.changeWeapon(engineer, sword)
            engineer.equippedWeapon shouldBe standardBow
            engineer.equippedWeapon shouldNotBe sword
            gameController.changeWeapon(engineer, knife)
            engineer.equippedWeapon shouldBe standardBow
            engineer.equippedWeapon shouldNotBe knife
            gameController.changeWeapon(engineer, staff)
            engineer.equippedWeapon shouldBe standardBow
            engineer.equippedWeapon shouldNotBe staff


            knight = gameController.createKnight(charName, maxHp, defense, standardSword)
            gameController.changeWeapon(knight, staff)
            knight.equippedWeapon shouldBe standardSword
            knight.equippedWeapon shouldNotBe staff
            gameController.changeWeapon(knight, bow)
            knight.equippedWeapon shouldBe standardSword
            knight.equippedWeapon shouldNotBe bow

            thief = gameController.createThief(charName, maxHp, defense, standardKnife)
            gameController.changeWeapon(thief, staff)
            thief.equippedWeapon shouldBe standardKnife
            thief.equippedWeapon shouldNotBe staff
            gameController.changeWeapon(thief, axe)
            thief.equippedWeapon shouldBe standardKnife
            thief.equippedWeapon shouldNotBe axe

            blackMage = gameController.createBlackMage(charName, maxHp, maxMp, defense, standardStaff)
            gameController.changeWeapon(blackMage, sword)
            blackMage.equippedWeapon shouldBe standardStaff
            blackMage.equippedWeapon shouldNotBe sword
            gameController.changeWeapon(blackMage, axe)
            blackMage.equippedWeapon shouldBe standardStaff
            blackMage.equippedWeapon shouldNotBe axe
            gameController.changeWeapon(blackMage, bow)
            blackMage.equippedWeapon shouldBe standardStaff
            blackMage.equippedWeapon shouldNotBe bow

            whiteMage = gameController.createWhiteMage(charName, maxHp, maxMp, defense, standardStaff)
            gameController.changeWeapon(engineer, sword)
            whiteMage.equippedWeapon shouldBe standardStaff
            whiteMage.equippedWeapon shouldNotBe sword
            gameController.changeWeapon(engineer, knife)
            whiteMage.equippedWeapon shouldBe standardStaff
            whiteMage.equippedWeapon shouldNotBe knife
            gameController.changeWeapon(engineer, bow)
            whiteMage.equippedWeapon shouldBe standardStaff
            whiteMage.equippedWeapon shouldNotBe bow
            gameController.changeWeapon(engineer, knife)
            whiteMage.equippedWeapon shouldBe standardStaff
            whiteMage.equippedWeapon shouldNotBe knife
        }
    }
})
