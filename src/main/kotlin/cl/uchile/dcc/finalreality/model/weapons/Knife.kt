package cl.uchile.dcc.finalreality.model.weapons

import java.util.Objects

class Knife(
    name: String,
    damage: Int,
    weight: Int
) : AbstractWeapon(name, damage, weight){

    override fun equals(other: Any?) = when {
        this === other                 -> true
        other !is Knife                -> false
        hashCode() != other.hashCode() -> false
        this.name != other.name        -> false
        this.damage != other.damage    -> false
        this.weight != other.weight    -> false
        else                           -> true
    }

    override fun hashCode(): Int = Objects.hash(this::class, name, damage, weight)

    override fun toString() = "Knife(" +
        "name = $name," +
        "damage = $damage," +
        "weight = $weight" +
        ")"
}