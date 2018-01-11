package Inventory.Controller

import Inventory.Model.Category
import Inventory.Model.Total
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.chart.PieChart
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.BorderPane
import java.net.URL
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.text.NumberFormat
import java.util.*

class StatisticController : Initializable {
    @FXML lateinit var totalJunk : TableView<Total>
    @FXML
    private lateinit var junkStat : PieChart
    @FXML
    private lateinit var rootPane : BorderPane
    @FXML
    private  var category : TableColumn<Total,String>? = null
    @FXML
    private  var kilo : TableColumn<Total,Int>? = null
    @FXML
    private  var price : TableColumn<Total,Int>? = null

    // Initializes the whole data into the table
    override fun initialize(location: URL?, resources: ResourceBundle?) {
           refresh()
           category?.cellValueFactory = PropertyValueFactory<Total,String>("Category")
           kilo?.cellValueFactory = PropertyValueFactory<Total,Int>("Kilo")
           price?.cellValueFactory = PropertyValueFactory<Total,Int>("Price")
    }

    //Open the table view
    @FXML
    private fun openTable(event: ActionEvent){
        var pane : BorderPane = FXMLLoader.load(javaClass.getResource("../View/MainView.fxml"))
        rootPane.children.setAll(pane)
    }
    //returns a connection to the database
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

    // creates the Pie chart
    private fun getChart() {
        var pieData: ObservableList<PieChart.Data>? = FXCollections.observableArrayList()

        var categoryList: ObservableList<Category> = FXCollections.observableArrayList()
        val categorySet = connectDB().createStatement().executeQuery("SELECT * FROM `category`")
        while (categorySet.next()) {
            categoryList.add(Category(categorySet.getString("name"), 0))
        }
        for (cat in categoryList) {
            val resultSet = connectDB().createStatement().executeQuery("SELECT COUNT(*) FROM `junks` WHERE `category_id` = '${cat.name}'")

            while (resultSet.next()) {
                pieData?.add(
                        PieChart.Data(cat.name,resultSet.getInt("COUNT(*)").toDouble())
                )
            }
        }

        junkStat.data = pieData
    }

    // gets the total price of available category
    private fun getTotalPrice(catName :String): String {
        var totalPrice = 0
            val resultSet = connectDB().createStatement().executeQuery("SELECT * FROM `junks` WHERE `category_id` = '$catName'")
            while (resultSet.next()) {
                if(catName == resultSet.getString("category_id")){
                    totalPrice += resultSet.getInt("price")
                }
        }

        var formatter : NumberFormat = NumberFormat.getCurrencyInstance()
        return formatter.format(totalPrice)
    }

    //gets the total amount of available category in kilos
    private fun getTotalAmount(catName: String) : Int{
        var totalAmount = 0
        val resultSet = connectDB().createStatement().executeQuery("SELECT * FROM `junks` WHERE `category_id` = '$catName'")
        while (resultSet.next()){
            if(catName == resultSet.getString("category_id")){
                totalAmount += resultSet.getInt("amount")
            }
        }
        return totalAmount
    }

    //gets the data for the table
    fun getTableData(): ObservableList<Total> {
        var totalList : ObservableList<Total> = FXCollections.observableArrayList()
        val resultSet = connectDB().createStatement().executeQuery("SELECT * FROM `category`")
        while (resultSet.next()){
            totalList.add(Total(resultSet.getString("name"),getTotalAmount(resultSet.getString("name")),getTotalPrice(resultSet.getString("name"))))
        }

        return totalList
    }
    //creates a thread that calls the data
    private fun refresh(){
        val task = object : Task<ObservableList<Total>>(){
            override fun call(): ObservableList<Total> = getTableData()

            override fun succeeded() {
                totalJunk.items.clear()
                totalJunk.items.addAll(value)
            }
        }
        Thread(task).start()
        getChart()
    }
 }
