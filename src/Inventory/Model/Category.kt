package Inventory.Model

/**
 * This class handles the category of junk
 * @param name is the name of the category
 * @param total is the total junk that falls into same category
 */
data class Category(
        var name:String,
        var total : Int = 0
)