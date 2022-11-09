Final Reality
=============

![http://creativecommons.org/licenses/by/4.0/](https://i.creativecommons.org/l/by/4.0/88x31.png)

This work is licensed under a
[Creative Commons Attribution 4.0 International License](http://creativecommons.org/licenses/by/4.0/)

Context
-------

This project's goal is to create a (simplified) clone of _Final Fantasy_'s combat, a game developed
by [_Square Enix_](https://www.square-enix.com)
Broadly speaking for the combat the player has a group of characters to control and a group of
enemies controlled by the computer.

---

*Información de Tarea 2 más abajo*
-

Tarea 1
===

Main.kt
-
- La funcion main muestra las clases por partes.
- Primero muestra las armas
- Luego muestra los personajes del jugador
- Después, muestra a los enemigos.
- Por ultimo, muestra el método waitTurn.
- Puse varios saltos de línea para hacerlo no tan molesto de leer.

Armas:
-
- Se crearon las interfaces Weapon y MagicWeapon.
- La interfaz MagicWeapon se agregó pensando en la posibilidad de que en un futuro se agregue otra 
arma mágica.
- La clase Weapon se cambió por la clase AbstractWeapon que implementa la interfaz Weapon.
- Se creó la clase AbstractMagicWeapon que extiende la clase AbstractWeapon e implementa la interfaz
MagicWeapon.
- La clase AbstractMagicWeapon agrega el atributo de magicDamage.
- Se creó una clase por cada tipo de arma del juego.
- Las clases Axe, Knife, Sword y Bow extienden de AbstractWeapon.
- La clase Staff extiende de AbstractMagicWeapon.

Characters:
-
- Se creó la interfaz MagicUser, pensando que para una siguiente tarea se agreguen los hechizos, 
y la función de lanzar un hechizo estaría en esta interfaz.
- Se creó la clase AbstractMagicPlayerCharacter que extiende a AbstractPlayerCharacter.
- Las clases Thief, Knight y Engineer extienden a AbstractPlayerCharacter.
- Las clases BlackMage y WhiteMage extienden a AbstractMagicPlayerCharacter.
- El override del método waitTurn en AbstractCharacter fue eliminado y fue reemplazado por un 
override del método en la clase Enemy y otro override en la clase AbstractPlayerCharacter.

---

Tarea 2
===

Tests:
-
- Se creó la carpeta test que contiene los tests de las clases del proyecto, está ubicada al mismo 
nivel que la carpeta main.
- Los archivos de las clases de testeo están organizadas de la misma forma que el proyecto.
- KtlintCheck indica que los imports no están ordenados lexicográficamente en los archivos EnemyTest.kt
y AbtractPlayerCharacterTest.kt, pero sí lo están. Yo supongo qeu se debe a los últimos dos imports, 
que fueron añadidos porque intelliJ arrojaba un warning si no agregaba el WithContext para usar el método sleep.

Restricción de armas:
-
- En la interfaz Weapon se agregaron los siguientes métodos:
  - equipToEngineer
  - equipToKnight
  - equipToThief
  - equipToBlackMage
  - equipToWhiteMage 
- Estos métodos equipan al arma que llamó al método a un personaje de la clase mencionada en el 
nombre de la función.
- El override del método equip en AbstractPlayerCharacter fue eliminado y reemplazado por un override
del método en cada una de las subclases no abstractas de AbstractPlayerCharacter, esto con el objetivo
de usar double dispatch llamando a alguno de los métodos añadidos a la interfaz Weapon para incorporar 
la restricción de armas.
- Se creó la excepción UnableToEquip, que será arrojada cuando un personaje intente equiparse un 
arma no permitida para su clase de personaje.

