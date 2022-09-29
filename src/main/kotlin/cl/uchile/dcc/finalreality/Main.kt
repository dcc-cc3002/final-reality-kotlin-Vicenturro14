package cl.uchile.dcc.finalreality

import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.character.player.Engineer
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
import cl.uchile.dcc.finalreality.model.weapons.Knife
import cl.uchile.dcc.finalreality.model.weapons.Staff
import cl.uchile.dcc.finalreality.model.weapons.Sword
import java.util.concurrent.LinkedBlockingQueue

fun main(){
    val queue = LinkedBlockingQueue<GameCharacter>()

    println("Welcome to FinalReality!\n")

    println("-------------------Weapons tests-------------------")
    println("There are 5 different weapons in FinalReality: the axe, the bow, the knife, the staff and the sword.")
    println("Every weapon has a name, damage points and weight.\n")

    println("The axe.")
    val axe1 = Axe("testAxe", 1, 10)
    val axe2 = Axe("testAxe2", 5, 20)
    val axe3 = Axe("testAxe", 1, 10)
    println("Here is an example of an axe: $axe1")
    println("axe1 weighs 10 units, so axe1.giveWeight() returns ${axe1.giveWeight()}.")
    println("axe1 is the same as axe3, so axe1.equals(axe3) returns ${axe1==axe3} and axe3.equals(axe1) returns ${axe3==axe1}.")
    println("axe1 is not the same as axe2, so axe1.equals(axe2) returns ${axe1==axe2} and axe2.equals(axe1) returns ${axe2==axe1}.\n")

    println("The bow.")
    val bow1 = Bow("testBow", 2, 20)
    val bow2 = Bow("testBow2", 5, 22)
    val bow3 = Bow("testBow", 2, 20)
    println("Here is an example of a bow: $bow1")
    println("bow1 weighs 20 units, so bow1.giveWeight() returns ${bow1.giveWeight()}.")
    println("bow1 is the same as bow3, so bow1.equals(bow3) returns ${bow1==bow3} and bow3.equals(bow1) returns ${bow3==bow1}.")
    println("bow1 is not the same as bow2, so bow1.equals(bow2) returns ${bow1==bow2} and bow2.equals(bow1) returns ${bow2==bow1}.\n")

    println("The knife.")
    val knife1 = Knife("testKnife", 3, 30)
    val knife2 = Knife("testKnife2", 53, 11)
    val knife3 = Knife("testKnife", 3, 30)
    println("Here is an example of a knife: $knife1")
    println("knife1 weighs 30 units, so knife1.giveWeight() returns ${knife1.giveWeight()}.")
    println("knife1 is the same as knife3, so knife1.equals(knife3) returns ${knife1==knife3} and knife3.equals(knife1) returns ${knife3==knife1}.")
    println("knife1 is not the same as knife2, so knife1.equals(knife2) returns ${knife1==knife2} and knife2.equals(knife1) returns ${knife2==knife1}.\n")

    println("The staff.")
    val staff1 = Staff("testStaff", 4,40, 3)
    val staff2 = Staff("testStaff2", 4,40, 1)
    val staff3 = Staff("testStaff", 4,40, 3)
    println("Here is an example of a staff: $staff1")
    println("The staff is a magical weapon, thus it has normal damage points and magic damage points. For example, staff1 has ${staff1.magicDamage} magic damage points.")
    println("staff1 weighs 40 units, so staff1.giveWeight() returns ${staff1.giveWeight()}.")
    println("staff1 is the same as staff3, so staff1.equals(staff3) returns ${staff1==staff3} and staff3.equals(staff1) returns ${staff3==staff1}.")
    println("staff1 is not the same as staff2, so staff1.equals(staff2) returns ${staff1==staff2} and staff2.equals(staff1) returns ${staff2==staff1}.\n")

    println("The sword.")
    val sword1 = Sword("testSword", 5, 50)
    val sword2 = Sword("testSword2", 42, 53)
    val sword3 = Sword("testSword", 5, 50)
    println("Here is an example of a sword: $sword1\n")
    println("sword1 weighs 50 units, so sword1.giveWeight() returns ${sword1.giveWeight()}.")
    println("sword1 is the same as sword3, so sword1.equals(sword3) returns ${sword1==sword3} and sword3.equals(sword1) returns ${sword3==sword1}.")
    println("sword1 is not the same as sword2, so sword1.equals(sword2) returns ${sword1==sword2} and sword2.equals(sword1) returns ${sword2==sword1}.\n\n")


    println("-------------------Player-Characters tests-------------------")
    println("In FinalReality, every player-character has a name, a maximum of HP, defense points, a class and can be equipped with a weapon.")
    println("There are 5 classes: the engineer, the knight, the thief, the white mage and the black mage.\n")

    println("The engineer.")
    val eng1 = Engineer("testEngineer", 30, 20, queue)
    val eng2 = Engineer("testEngineer2", 33, 10, queue)
    val eng3 = Engineer("testEngineer", 30, 20, queue)

    println("Here is an example of an engineer: $eng1")
    println(""

    println("The knight.")

    println("The thief.")

    println("The white mage.")

    println("The black mage.")

}