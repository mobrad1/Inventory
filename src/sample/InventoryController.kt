package sample

import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.sql.*
import java.util.*



class InventoryController : Initializable{


    @FXML
    private lateinit var tvJunk : TableView<Junk>
    @FXML
    private lateinit var sn : TableColumn<Junk,Int>
    @FXML
    private lateinit var name : TableColumn<Junk,String>
    @FXML
    private lateinit var amount : TableColumn<Junk,Int>
    @FXML
    private lateinit var price : TableColumn<Junk,Int>
    @FXML
    private lateinit var category : TableColumn<Junk,String>
    @FXML
    private lateinit var customer : TableColumn<Junk,String>
    @FXML
    private lateinit var date :TableColumn<Junk,String>
    @FXML
    private lateinit var junkNameField : TextField
    @FXML
    private lateinit var junkAmountField : TextField
    @FXML
    private lateinit var junkPriceField : TextField
    @FXML
    private lateinit var junkCategoryField : ChoiceBox<String>
    @FXML
    private lateinit var junkCustomerField : TextField
    @FXML
    private lateinit var junkAddButton : Button
    @FXML
    private lateinit var textValidate : Label


    


    override fun initialize(location: URL?, resources: ResourceBundle?) {


        makeCategory()
        sn.cellValueFactory = PropertyValueFactory<Junk, Int>("sn")
        name.cellValueFactory = PropertyValueFactory<Junk,String>("name")
        amount.cellValueFactory = PropertyValueFactory<Junk,Int>("amount")
        price.cellValueFactory = PropertyValueFactory<Junk,Int>("price")
        category.cellValueFactory = PropertyValueFactory<Junk,String>("category")
        customer.cellValueFactory = PropertyValueFactory<Junk,String>("customer")
        date.cellValueFactory = PropertyValueFactory<Junk,String>("date")
        refresh()

    }
    @FXML
    private fun refresh(){
        val task = object : Task<ObservableList<Junk>>(){
            override fun call(): ObservableList<Junk> = fetchData()

            override fun succeeded() {
                tvJunk.items.clear()
                tvJunk.items.addAll(value)
            }
        }
        Thread(task).start()
    }
    fun fetchData(): ObservableList<Junk> {

        var inventoryList :ObservableList<Junk> = observableArrayList()

        //Connection to database
        connectDB()
        //end of connection to database

        //query the database
        val statement = connectDB().createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM `junks`")

        try {
            while (resultSet.next()) {

                inventoryList.add(Junk(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("amount"), resultSet.getInt("price"), resultSet.getString("category_id"), resultSet.getString("customer"), resultSet.getString("date")))

            }
        }catch (sqlException : SQLException){
            sqlException.printStackTrace()
        }
        return inventoryList
    }

    @FXML
    private fun connectDB() : Connection {
        val user = "root"
        var conn : Connection? = null
        val connectionProps = Properties()
        try{
        connectionProps.put("user",user)
         Class.forName("com.mysql.jdbc.Driver")
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/junk",connectionProps)

        //query the database
        }catch(sqlException :SQLException){
            sqlException.printStackTrace()
        }
        return conn!!
    }

    // Responsible for adding a new Junk
    @FXML
    fun addJunk(){
        connectDB()
        val statement = connectDB().createStatement()

        try {
            if(junkNameField.text!=null && junkAmountField.text != null && junkPriceField.text != null && junkCategoryField.value != null && junkCustomerField.text != null) {
                val resultSet = statement.executeUpdate("INSERT INTO `junks`" + "(`name`,`amount`,`price`,`category_id`,`customer`,`date`)" + " VALUES('${junkNameField.text}','${junkAmountField.text}','${junkPriceField.text}','${junkCategoryField.value}','${junkCustomerField.text}','december')")
                refresh()
                clearFields()
                textValidate.style = "-fx-text-fill : green"
                textValidate.text = "Junk added successfully"
            }else{
                textValidate.style = "-fx-text-fill : red"
                textValidate.text = "Please fill all details"
            }
        }catch (sqlException : SQLException){
            sqlException.printStackTrace()
        }
    }

    private fun makeCategory() :ChoiceBox<String>{
        val statement = connectDB().createStatement()
        val result = statement.executeQuery("SELECT * FROM `category`")
        var number = result.metaData
        var numberCount = number.columnCount
        while (result.next()) {
                junkCategoryField.items.add(result.getInt("id") - 1, result.getString("name"))
        }
        return junkCategoryField
   }

    private fun clearFields(){
        junkNameField.clear()
        junkAmountField.clear()
        junkPriceField.clear()
        junkCustomerField.clear()
        junkCategoryField.value = null

    }

    private fun validation(): Boolean {
        if (junkNameField.text!=null && junkAmountField.text != null && junkPriceField.text != null && junkCategoryField.value != null && junkCustomerField.text != null){
            textValidate.text = "Please fill all details"
        }
        return true
    }

}