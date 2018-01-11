package Inventory.Model

/**
 * This class holds the data for
 * the total of each category
 * @param category is the category
 * @param kilo is the total kilogram for a single category
 * @param price is the total price for a single category
 */
data class Total(
        var category : String,
        var kilo : Int,
        var price : String
)