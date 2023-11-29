package cl.uchile.dcc.finalreality.exceptions

/**
 * This exception is used to indicate that it's not possible to equip an item.
 *
 * @constructor Creates a new `UnableToEquipException` with a `description` of the error.
 *
 * @author <a href="https://github.com/Vicenturro14">Vicenturro14</a>
 * @author Vicente Olivares
 */
class UnableToEquipException(description: String) :
    Exception(description)
