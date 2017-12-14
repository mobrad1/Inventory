package sample
import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import javafx.collections.SetChangeListener
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.stage.Stage
import java.io.IOException
import java.net.URL
import java.sql.*
import java.text.DateFormat
import java.util.*
import java.util.Date


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

                inventoryList.add(Junk(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("amount"), resultSet.getInt("price"), resultSet.getInt("category_id"), resultSet.getString("customer"), resultSet.getString("date")))

            }
        }catch (sqlException : SQLException){
            sqlException.printStackTrace()
        }
        return inventoryList
    }

    @FXML
    fun connectDB() : Connection {
        val user = "root"
        var conn : Connection? = null
        val connectionProps : Properties = Properties()
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
    fun addJunk(){
        connectDB()
        val statement = connectDB().createStatement()
        val resultSet = statement.executeQuery("INSERT INTO `junks`" + "(name,amount,price,category_id,customer,date)" + "VALUES()")
    }

    private fun makeCategory() :ChoiceBox<String>{
//        junkCategoryField = ChoiceBox()
//        junkCategoryField.items = observableArrayList(Category(1,"bread",1000))
////        junkCategoryField.selectionModel.selectedIndexProperty().addListener(
////                ChangeListener((
////                        fun(ov :ObservableValue<Number>, value :Number, new_value : Number) {}) as (observable: ObservableValue<out Number>, oldValue: Number, newValue: Number) -> Unit))
//        return junkCategoryField
        junkCategoryField.items.add(0,"bread")
        return junkCategoryField
   }
}