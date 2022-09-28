package cl.uchile.dcc.finalreality.model.weapons

import java.util.Objects

class Staff(
    name: String,
    damage: Int,
    weight: Int,
    magicDamage: Int
) : AbstractMagicWeapon(name, damage, weight, magicDamage){
    override fun equals(other: Any?) = when {
        this === other                        -> true
        other !is Staff                       -> false
        hashCode() != other.hashCode()        -> false
        this.name != other.name               -> false
        this.damage != other.damage           -> false
        this.weight != other.weight           -> false
        this.magicDamage != other.magicDamage -> false
        else                                  -> true
    }

    override fun hashCode(): Int = Objects.hash(this::class, name, damage, weight, magicDamage)

    override fun toString() = "Staff(" +
        "name = $name," +
        "damage = $damage," +
        "weight = $weight, " +
        "magicDamage = $magicDamage" +
        ")"
}