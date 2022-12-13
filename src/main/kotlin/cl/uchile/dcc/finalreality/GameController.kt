package cl.uchile.dcc.finalreality

import cl.uchile.dcc.finalreality.exceptions.InvalidStatValueException
import cl.uchile.dcc.finalreality.exceptions.Require
import cl.uchile.dcc.finalreality.exceptions.UnableToEquipException
import cl.uchile.dcc.finalreality.model.character.Enemy
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.character.player.Engineer
import cl.uchile.dcc.finalreality.model.character.player.Knight
import cl.uchile.dcc.finalreality.model.character.player.PlayerCharacter
import cl.uchile.dcc.finalreality.model.character.player.Thief
import cl.uchile.dcc.finalreality.model.character.player.mages.BlackMage
import cl.uchile.dcc.finalreality.model.character.player.mages.WhiteMage
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
import cl.uchile.dcc.finalreality.model.weapons.Knife
import cl.uchile.dcc.finalreality.model.weapons.Staff
import cl.uchile.dcc.finalreality.model.weapons.Sword
import cl.uchile.dcc.finalreality.model.weapons.Weapon
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.abs

/**
 * Turns the given value into a positive number. If the value is 0, returns 1.
 * In other cases returns the absolut value of the value.
 *
 * @param value
 *   Value turned into a valid value for stats, it can be a valid or invalid value for stats.
 */
fun toPositiveNum(value: Int): Int{
    return if (value == 0) 1 else abs(value)
}

// Standard Values and Objects
private const val STANDARD_PREFIX = "Standard"
private const val STANDARD_NUM_VALUE = 50
private val standardKnife = Knife("$STANDARD_PREFIX knife", STANDARD_NUM_VALUE, STANDARD_NUM_VALUE)
private val standardBow = Bow("$STANDARD_PREFIX bow", STANDARD_NUM_VALUE, STANDARD_NUM_VALUE)
private val standardSword = Sword("$STANDARD_PREFIX sword", STANDARD_NUM_VALUE, STANDARD_NUM_VALUE)
private val standardStaff = Staff("$STANDARD_PREFIX staff", STANDARD_NUM_VALUE, STANDARD_NUM_VALUE, STANDARD_NUM_VALUE)

private const val PLAYER_CHARACTER_NUM = 4
private const val ENEMIES_MAX = 8

/**
 * Class for controlling the game.
 *
 *
 */
class GameController {

    private val turnsQueue = LinkedBlockingQueue<GameCharacter>()
    private val playerCharacters = mutableListOf<GameCharacter>()
    private val enemyCharacters = mutableListOf<GameCharacter>()
    var activeEnemiesCounter: Int = 0
        private set(value) {
            field = Require.Stat(value, "Active Enemies Counter") inRange 0 .. ENEMIES_MAX
        }
    var activePlayerCharactersCounter: Int = 0
        private set(value) {
            field = Require.Stat(value, "Active PlayerCharacters Counter") inRange 0 .. PLAYER_CHARACTER_NUM
        }

    /**
     * Creates an [Axe] with the given stats.
     */
    fun createAxe(name: String, damage: Int, weight: Int) = Axe(name, damage, weight)

    /**
     * Creates an [Sword] with the given stats.
     */
    fun createSword(name: String, damage: Int, weight: Int) = Sword(name, damage, weight)

    /**
     * Creates an [Staff] with the given stats.
     */
    fun createStaff(name: String, damage: Int, weight: Int, magicDamage: Int) = Staff(name, damage, weight, magicDamage)

    /**
     * Creates an [Bow] with the given stats.
     */
    fun createBow(name: String, damage: Int, weight: Int) = Bow(name, damage, weight)

    /**
     * Creates an [Knife] with the given stats.
     */
    fun createKnife(name: String, damage: Int, weight: Int) = Knife(name, damage, weight)

    /**
     * Creates an enemy with the given stats.
     * If the weight, maxHp, or attack value are negative numbers,
     * their absolut value will be used. If they are 0, 1 will be used.
     * If the defense is a negative value, its absolut value will be used.
     *
     * @param name
     *   The name of the created enemy.
     * @param weight
     *   The weight of the created enemy.
     * @param maxHp
     *   The maximum of health points of the created enemy.
     * @param defense
     *   The defense of the created enemy.
     * @param attack
     *   The attack of the created enemy.
     */
    fun createEnemy(name: String, weight: Int, maxHp: Int, defense: Int, attack: Int) : Enemy {
        return try {
            Enemy(name, weight, maxHp, defense, attack, turnsQueue)
        } catch (e: InvalidStatValueException) {
            Enemy(name, toPositiveNum(weight), toPositiveNum(maxHp), abs(defense), toPositiveNum(attack), turnsQueue)
        }
    }

    /**
     * Creates an engineer with the given stats and weapon.
     * If the maxHp is a negative number, its absolut value will be used. If it's 0, 1 will be used.
     * If the defense is a negative number, its absolut value will be used.
     * If the weapon can't be equipped, a standard bow will be equipped.
     *
     * @param name
     *   The name of the created engineer.
     * @param maxHp
     *   The maximum of health points of the created engineer.
     * @param defense
     *   The defense of the created engineer.
     * @param weapon
     *   The weapon of the created engineer.
     */
    fun createEngineer(name: String, maxHp: Int, defense: Int, weapon: Weapon) : Engineer {
        val character = try {
            Engineer(name, maxHp, defense, turnsQueue)
        } catch (e1: InvalidStatValueException) {
            Engineer(name, toPositiveNum(maxHp), abs(defense), turnsQueue)
        }
        try {
            character.equip(weapon)
        } catch (e2: UnableToEquipException) {
            character.equip(standardBow)
        }
        return character
    }

    /**
     * Creates a thief with the given stats and weapon.
     * If the maxHp is a negative number, its absolut value will be used. If it's 0, 1 will be used.
     * If the defense is a negative number, its absolut value will be used.
     * If the weapon can't be equipped, a standard knife will be equipped.
     *
     * @param name
     *   The name of the created thief.
     * @param maxHp
     *   The maximum of health points of the created thief.
     * @param defense
     *   The defense of the created thief.
     * @param weapon
     *   The weapon of the created thief.
     */
    fun createThief(name: String, maxHp: Int, defense: Int, weapon: Weapon) : Thief {
        val character = try {
            Thief(name, maxHp, defense, turnsQueue)
        } catch (e1: InvalidStatValueException) {
            Thief(name, toPositiveNum(maxHp), abs(defense), turnsQueue)
        }
        try {
            character.equip(weapon)
        } catch (e2: UnableToEquipException) {
            character.equip(standardKnife)
        }
        return character
    }

    /**
     * Creates a knight.
     * If the maxHp is a negative number, its absolut value will be used. If it's 0, 1 will be used.
     * If the defense is a negative number, its absolut value will be used.
     * If the weapon can't be equipped, a standard sword will be equipped to the character.
     *
     * @param name
     *   The name of the created knight.
     * @param maxHp
     *   The maximum of health points of the created knight.
     * @param defense
     *   The defense of the created knight.
     * @param weapon
     *   The weapon of the created knight.
     */
    fun createKnight(name: String, maxHp: Int, defense: Int, weapon: Weapon) : Knight {
        val character = try {
            Knight(name, maxHp, defense, turnsQueue)
        } catch (e1: InvalidStatValueException) {
            Knight(name, toPositiveNum(maxHp), abs(defense), turnsQueue)
        }
        try {
            character.equip(weapon)
        } catch (e2: UnableToEquipException) {
            character.equip(standardSword)
        }
        return character
    }

    /**
     * Creates a black mage.
     * If the maxHp is a negative number, its absolut value will be used. If it's 0, 1 will be used.
     * If the defense or maxMp is a negative number, its absolut value will be used.
     * If the weapon can't be equipped, a standard staff will be equipped to the character.
     *
     * @param name
     *   The name of the created black mage.
     * @param maxHp
     *   The maximum of health points of the created black mage.
     * @param maxMp
     *   The maximum of mana points of the created black mage.
     * @param defense
     *   The defense of the created black mage.
     * @param weapon
     *   The weapon of the created black mage.
     */
    fun createBlackMage(name: String, maxHp: Int, maxMp: Int, defense: Int, weapon: Weapon) : BlackMage {
        val character = try {
            BlackMage(name, maxHp, maxMp, defense, turnsQueue)
        } catch (e1: InvalidStatValueException) {
            BlackMage(name, toPositiveNum(maxHp), abs(maxMp), abs(defense), turnsQueue)
        }
        try {
            character.equip(weapon)
        } catch (e2: UnableToEquipException) {
            character.equip(standardStaff)
        }
        return character
    }

    /**
     * Creates a white mage.
     * If the maxHp is a negative number, its absolut value will be used. If it's 0, 1 will be used.
     * If the defense or maxMp is a negative number, its absolut value will be used.
     * If the weapon can't be equipped, a standard staff will be equipped to the character.
     *
     * @param name
     *   The name of the created white mage.
     * @param maxHp
     *   The maximum of health points of the created white mage.
     * @param maxMp
     *   The maximum of mana points of the created white mage.
     * @param defense
     *   The defense of the created white mage.
     * @param weapon
     *   The weapon of the created white mage.
     */
    fun createWhiteMage(name: String, maxHp: Int, maxMp: Int, defense: Int, weapon: Weapon) : WhiteMage {
        val character = try {
            WhiteMage(name, maxHp, maxMp, defense, turnsQueue)
        } catch (e1: InvalidStatValueException) {
            WhiteMage(name, toPositiveNum(maxHp), abs(maxMp), abs(defense), turnsQueue)
        }
        try {
            character.equip(weapon)
        } catch (e2: UnableToEquipException) {
            character.equip(standardStaff)
        }
        return character
    }

    /**
     * Changes the equipped weapon to a player character.
     */
    fun changeWeapon(character: PlayerCharacter, weapon: Weapon) {
        try {
            character.equip(weapon)
        } catch (e: UnableToEquipException) {
            return
        }
    }

    /**
     * Starts the game by adding the enemies from enemyCharacters and the characters from playerCharacters to the turns queue.
     * If the number of active characters in playerCharacters isn't the same as PLAYER_CHARACTER_NUM, the game doesn't start.
     */
    fun startGame() {
        if (activePlayerCharactersCounter == PLAYER_CHARACTER_NUM) {
            for (enemy in enemyCharacters) {
                enemy.waitTurn()
            }
            for (playerCharacter in playerCharacters) {
                playerCharacter.waitTurn()
            }
        }
    }

    /**
     * Instructs a character to attack another character.
     *
     * @param attacker
     *   [GameCharacter] that is going to attack.
     * @param target
     *   [GameCharacter] that is going to receive the attack.
     */
    fun attack(attacker: GameCharacter, target: GameCharacter) {
            attacker.attackCharacter(target)
    }
    fun update(character: GameCharacter) {
        turnsQueue.remove(character)
    }
}
