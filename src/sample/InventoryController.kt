package sample
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
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

    


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        sn.cellValueFactory = PropertyValueFactory<Junk, Int>("sn")
        name.cellValueFactory = PropertyValueFactory<Junk,String>("name")
        amount.cellValueFactory = PropertyValueFactory<Junk,Int>("amount")
        price.cellValueFactory = PropertyValueFactory<Junk,Int>("price")
        category.cellValueFactory = PropertyValueFactory<Junk,String>("category")
        customer.cellValueFactory = PropertyValueFactory<Junk,String>("customer")
        date.cellValueFactory = PropertyValueFactory<Junk,String>("date")
        connectDB()
        refresh()

    }
    fun refresh(){
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
        var inventoryList :ObservableList<Junk> = FXCollections.observableArrayList(
                Junk(1,"Bradley",100,100,"bronze","Tunde","Tuesdady"),
                Junk(2,"Braey",100,150,"plastic","Tunde","wednesday")
        )
        return inventoryList
    }
    @FXML
    fun connectDB() : Connection {
        val user : String = "root"
        val password : String = ""
        var conn : Connection? = null
        val connectionProps : Properties = Properties()
        connectionProps.put("user",user)
    Class.forName("com.mysql.jdbc.Driver")
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer",connectionProps)

        println("Connected to datavase")
        return conn
    }
}