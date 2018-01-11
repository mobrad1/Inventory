package Inventory.Model

/**
 * @param sn is the id of the junk
 * @param name is the name of the junk
 * @param amount is the amount of scrap in kilo
 * @param price is the price of the scrap in naira
 * @param category is the category of the scrap
 * @param customer is the name of the customer that sold
 * @param date is the date when the scrap was bought
 */
data class Junk(
        val sn : Int,
        val name : String,
        val amount : Int,
        val price : Int,
        val category: String,
        val customer : String,
        val date: String
)


