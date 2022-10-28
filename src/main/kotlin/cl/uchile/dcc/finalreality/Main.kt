package cl.uchile.dcc.finalreality

import cl.uchile.dcc.finalreality.model.character.Enemy
import cl.uchile.dcc.finalreality.model.character.GameCharacter
import cl.uchile.dcc.finalreality.model.character.player.BlackMage
import cl.uchile.dcc.finalreality.model.character.player.Engineer
import cl.uchile.dcc.finalreality.model.character.player.Knight
import cl.uchile.dcc.finalreality.model.character.player.Thief
import cl.uchile.dcc.finalreality.model.character.player.WhiteMage
import cl.uchile.dcc.finalreality.model.weapons.Axe
import cl.uchile.dcc.finalreality.model.weapons.Bow
import cl.uchile.dcc.finalreality.model.weapons.Knife
import cl.uchile.dcc.finalreality.model.weapons.Staff
import cl.uchile.dcc.finalreality.model.weapons.Sword
import java.util.concurrent.LinkedBlockingQueue

fun main() {

    println("\nWelcome to FinalReality!\n")

    println("--------------------------------Weapons tests--------------------------------")
    println("There are 5 different weapons in FinalReality: the axe, the bow, the knife, the staff and the sword.")
    println("Every weapon has a name, damage points and weight.\n")

    println("The axe.")
    val axe1 = Axe("testAxe", 1, 10)
    val axe2 = Axe("testAxe2", 5, 20)
    val axe3 = Axe("testAxe", 1, 10)
    println("Here is an example of an axe: $axe1")
    println("axe1 weighs 10 units, so axe1.weight returns ${axe1.weight}.")
    println("axe1 is the same as axe3, so axe1.equals(axe3) returns ${axe1 == axe3} and axe3.equals(axe1) returns ${axe3 == axe1}.")
    println("axe1 is not the same as axe2, so axe1.equals(axe2) returns ${axe1 == axe2} and axe2.equals(axe1) returns ${axe2 == axe1}.\n")

    println("The bow.")
    val bow1 = Bow("testBow", 2, 20)
    val bow2 = Bow("testBow2", 5, 22)
    val bow3 = Bow("testBow", 2, 20)
    println("Here is an example of a bow: $bow1")
    println("bow1 weighs 20 units, so bow1.weight returns ${bow1.weight}.")
    println("bow1 is the same as bow3, so bow1.equals(bow3) returns ${bow1 == bow3} and bow3.equals(bow1) returns ${bow3 == bow1}.")
    println("bow1 is not the same as bow2, so bow1.equals(bow2) returns ${bow1 == bow2} and bow2.equals(bow1) returns ${bow2 == bow1}.\n")

    println("The knife.")
    val knife1 = Knife("testKnife", 3, 30)
    val knife2 = Knife("testKnife2", 53, 11)
    val knife3 = Knife("testKnife", 3, 30)
    println("Here is an example of a knife: $knife1")
    println("knife1 weighs 30 units, so knife1.weight returns ${knife1.weight}.")
    println("knife1 is the same as knife3, so knife1.equals(knife3) returns ${knife1 == knife3} and knife3.equals(knife1) returns ${knife3 == knife1}.")
    println("knife1 is not the same as knife2, so knife1.equals(knife2) returns ${knife1 == knife2} and knife2.equals(knife1) returns ${knife2 == knife1}.\n")

    println("The staff.")
    val staff1 = Staff("testStaff", 4, 40, 3)
    val staff2 = Staff("testStaff2", 4, 40, 1)
    val staff3 = Staff("testStaff", 4, 40, 3)
    println("Here is an example of a staff: $staff1")
    println("The staff is a magical weapon, thus it has normal damage points and magic damage points. For example, staff1 has ${staff1.magicDamage} magic damage points.")
    println("staff1 weighs 40 units, so staff1.weight returns ${staff1.weight}.")
    println("staff1 is the same as staff3, so staff1.equals(staff3) returns ${staff1 == staff3} and staff3.equals(staff1) returns ${staff3 == staff1}.")
    println("staff1 is not the same as staff2, so staff1.equals(staff2) returns ${staff1 == staff2} and staff2.equals(staff1) returns ${staff2 == staff1}.\n")

    println("The sword.")
    val sword1 = Sword("testSword", 5, 40)
    val sword2 = Sword("testSword2", 42, 53)
    val sword3 = Sword("testSword", 5, 40)
    println("Here is an example of a sword: $sword1")
    println("sword1 weighs 40 units, so sword1.weight returns ${sword1.weight}.")
    println("sword1 is the same as sword3, so sword1.equals(sword3) returns ${sword1 == sword3} and sword3.equals(sword1) returns ${sword3 == sword1}.")
    println("sword1 is not the same as sword2, so sword1.equals(sword2) returns ${sword1 == sword2} and sword2.equals(sword1) returns ${sword2 == sword1}.\n\n")

    println("--------------------------------Player-Characters tests--------------------------------")
    println("In FinalReality, every player-character has a name, a maximum of HP, current HP, defense points, a class and can be equipped with a weapon.")
    println("There are 5 classes: the engineer, the knight, the thief, the white mage and the black mage.\n")
    val queue = LinkedBlockingQueue<GameCharacter>()

    println("The engineer.")
    val eng1 = Engineer("testEngineer", 30, 20, queue)
    val eng2 = Engineer("testEngineer2", 33, 10, queue)
    val eng3 = Engineer("testEngineer", 30, 20, queue)
    eng1.equip(bow1)
    eng2.equip(bow2)
    eng3.equip(bow3)
    println("Here is an example of an engineer equipped with a bow: $eng1")
    println("eng1 is the same as eng3, so eng1.equals(eng3) returns ${eng1 == eng3} and eng3.equals(eng1) returns ${eng3 == eng1}.")
    println("eng1 is not the same as eng2, so eng1.equals(eng2) returns ${eng1 == eng2} and eng2.equals(eng1) returns ${eng2 == eng1}.")
    println("With the equip method, the equipped weapon of an engineer can be changed.")
    eng1.equip(sword1)
    println("eng1 has been equipped with a sword: $eng1\n")

    println("The knight.")
    val knight1 = Knight("testKnight", 20, 20, queue)
    val knight2 = Knight("testKnight2", 25, 21, queue)
    val knight3 = Knight("testKnight", 20, 20, queue)
    knight1.equip(sword1)
    knight2.equip(sword2)
    knight3.equip(sword3)
    println("Here is an example of a knight equipped with a sword: $knight1")
    println("knight1 is the same as knight3, so knight1.equals(knight3) returns ${knight1 == knight3} and knight3.equals(knight1) returns ${knight3 == knight1}.")
    println("knight1 is not the same as knight2, so knight1.equals(knight2) returns ${knight1 == knight2} and knight2.equals(knight1) returns ${knight2 == knight1}.")
    println("With the equip method, the equipped weapon of a knight can be changed.")
    knight1.equip(axe1)
    println("knight1 has been equipped with an axe: $knight1\n")

    println("The thief.")
    val thief1 = Thief("testThief", 40, 30, queue)
    val thief2 = Thief("testThief", 44, 10, queue)
    val thief3 = Thief("testThief", 40, 30, queue)
    thief1.equip(knife1)
    thief2.equip(knife2)
    thief3.equip(knife3)
    println("Here is an example of a thief equipped with a sword: $thief1")
    println("thief1 is the same as thief3, so thief1.equals(thief3) returns ${thief1 == thief3} and thief3.equals(thief1) returns ${thief3 == thief1}.")
    println("thief1 is not the same as thief2, so thief1.equals(thief2) returns ${thief1 == thief2} and thief2.equals(thief1) returns ${thief2 == thief1}.")
    println("With the equip method, the equipped weapon of a thief can be changed.")
    thief1.equip(axe1)
    println("thief1 has been equipped with an axe: $thief1\n")

    println("The white mage and the black mage.")
    val bMage1 = BlackMage("testBMage", 20, 20, 20, queue)
    val bMage2 = BlackMage("testBMage2", 24, 22, 25, queue)
    val bMage3 = BlackMage("testBMage", 20, 20, 20, queue)
    val wMage1 = WhiteMage("testWMage", 26, 26, 26, queue)
    val wMage2 = WhiteMage("testWMage2", 24, 22, 25, queue)
    val wMage3 = WhiteMage("testWMage", 26, 26, 26, queue)
    bMage1.equip(staff1)
    bMage2.equip(staff2)
    bMage3.equip(staff3)
    wMage1.equip(staff1)
    wMage2.equip(staff2)
    wMage3.equip(staff3)
    println("Mages are magical characters, thus they have a maximum of mana points(MP) and current MP.")
    println("For example bMage1 has a maximum of ${bMage1.maxMp} MP and currently has ${bMage1.currentMp} MP.")
    println("Here is an example of a white mage and a black mage, both equipped with a staff:\n $bMage1\n $wMage1")
    println("bMage1 is the same as bMage3, so bMage1.equals(bMage3) returns ${bMage1 == bMage3} and bMage3.equals(bMage1) returns ${bMage3 == bMage1}.")
    println("bMage1 is not the same as bMage2, so bMage1.equals(bMage2) returns ${bMage1 == bMage2} and bMage2.equals(bMage1) returns ${bMage2 == bMage1}.")
    println("wMage1 is the same as wMage3, so wMage1.equals(wMage3) returns ${wMage1 == wMage3} and wMage3.equals(wMage1) returns ${wMage3 == wMage1}.")
    println("wMage1 is not the same as wMage2, so wMage1.equals(wMage2) returns ${wMage1 == wMage2} and wMage2.equals(wMage1) returns ${wMage2 == wMage1}.")
    println("With the equip method, mages can change their weapons.")
    bMage1.equip(knife1)
    wMage1.equip(sword1)
    println("bMage1 has been equipped with a knife and wMage has been equipped with a sword: $bMage1\n $wMage1\n\n")

    println("--------------------------------Enemies tests--------------------------------")
    println("There is another type of character: the enemy.")
    println("Every enemy has a name, a weight a maximum of HP, current HP and defense points.")
    val enemy1 = Enemy("testEnemy", 40, 40, 30, 49, queue)
    val enemy2 = Enemy("testEnemy2", 52, 52, 32, 29, queue)
    val enemy3 = Enemy("testEnemy", 40, 40, 30, 49, queue)
    println("Here is an example of a enemy: $enemy1")
    println("enemy1 is the same as enemy3, so enemy1.equals(enemy3) returns ${enemy1 == enemy3} and enemy3.equals(enemy1) returns ${enemy3 == enemy1}.")
    println("enemy1 is not the same as enemy2, so enemy1.equals(enemy2) returns ${enemy1 == enemy2} and enemy2.equals(enemy1) returns ${enemy2 == enemy1}.\n\n")

    println("--------------------------------Queue tests--------------------------------")
    println("In the game, the characters are added to a queue to wait for their turn to attack.")
    println("Every character can wait for their turn with the waitTurn method.")
    println("Lets add two characters to the queue, so they can wait for their turn.")
    bMage1.waitTurn()
    enemy1.waitTurn()
    println("Now, we should wait 6 seconds to ensure that all characters have finished waiting and then show themselves.\n")
    Thread.sleep(6000)
    while (!queue.isEmpty()) {
        // Pops and prints the names of the characters of the queue to illustrate the turns
        // order
        println(queue.poll())
    }
}
