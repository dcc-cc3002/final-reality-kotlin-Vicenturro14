package cl.uchile.dcc.finalreality.model.weapons

import cl.uchile.dcc.finalreality.model.character.player.PlayerCharacter

/**
 * This interface represents a weapon from the game.
 * A weapon can be equipped to a character controlled by the player.
 * @property weight
 *   The weight of the weapon.
 * @author
 *   <a href="https://www.github.com/Vicenturro14">Vicenturro14</a>
 * @author
 *   Vicente Olivares
 */
interface Weapon {
    val weight: Int

    /**
     * Equip this weapon to an [Engineer][cl.uchile.dcc.finalreality.model.character.player.Engineer].
     * @param engineer
     *   The [PlayerCharacter] who is going to be equipped with this weapon.
     */
    fun equipToEngineer(engineer: PlayerCharacter)

    /**
     * Equip this weapon to a [Knight][cl.uchile.dcc.finalreality.model.character.player.Knight].
     * @param knight
     *   The [PlayerCharacter] who is going to be equipped with this weapon.
     */
    fun equipToKnight(knight: PlayerCharacter)

    /**
     * Equip this weapon to a [Thief][cl.uchile.dcc.finalreality.model.character.player.Thief].
     * @param thief
     *   The [PlayerCharacter] who is going to be equipped with this weapon.
     */
    fun equipToThief(thief: PlayerCharacter)

    /**
     * Equip this weapon to a [Black Mage][cl.uchile.dcc.finalreality.model.character.player.mages.BlackMage].
     * @param blackMage
     *   The [PlayerCharacter] who is going to be equipped with this weapon.
     */
    fun equipToBlackMage(blackMage: PlayerCharacter)

    /**
     * Equip this weapon to a [White Mage][cl.uchile.dcc.finalreality.model.character.player.mages.WhiteMage].
     * @param whiteMage
     *   The [PlayerCharacter] who is going to be equipped with this weapon.
     */
    fun equipToWhiteMage(whiteMage: PlayerCharacter)
}
