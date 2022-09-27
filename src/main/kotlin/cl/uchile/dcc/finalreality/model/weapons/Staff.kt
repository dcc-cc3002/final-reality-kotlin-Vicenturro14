package cl.uchile.dcc.finalreality.model.weapons

class Staff(
    name: String,
    damage: Int,
    weight: Int,
    magicDamage: Int
) : AbstractMagicWeapon(name, damage, weight, magicDamage){
    override fun toString(): String {
        return super.toString()
    }
}