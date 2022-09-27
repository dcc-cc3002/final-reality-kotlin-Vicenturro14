package cl.uchile.dcc.finalreality.model.weapons

abstract class AbstractMagicWeapon(
    name: String,
    damage: Int,
    weight: Int,
    val magicDamage: Int
) : AbstractWeapon(name, damage, weight){
}