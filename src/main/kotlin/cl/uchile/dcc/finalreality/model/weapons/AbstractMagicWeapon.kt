package cl.uchile.dcc.finalreality.model.weapons

abstract class AbstractMagicWeapon(
    name: String,
    damage: Int,
    weight: Int,
    val magicDamage: Int
) : AbstractWeapon(name, damage, weight){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AbstractMagicWeapon

        if (magicDamage != other.magicDamage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + magicDamage
        return result
    }

    override fun toString(): String {
        return super.toString()
    }
}