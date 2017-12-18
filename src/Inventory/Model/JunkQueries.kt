package Inventory.Model

import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import Inventory.Model.Junk
import java.sql.*

class JunkQueries {
    private val URL : String = "jdbc:mysql://localhost:3306/junk"
    private val USERNAME : String = "root"
    private val PASSWORD : String? = null
    private var conn : Connection? = null
    private var selectAllJunk : PreparedStatement? = null
    private var addJunk : PreparedStatement? = null

    fun JunkQueries(){
        try{
            Class.forName("com.mysql.jdbc.Driver")
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD)
            selectAllJunk = conn?.prepareStatement("SELECT * FROM `junks`")
            addJunk = conn?.prepareStatement("INSERT INTO junks" + "(name,amount,price,category,customer,date)" + "VALUES(?,?,?,?,?,?)")


        }catch (sqlException : SQLException){
            sqlException.printStackTrace()
            System.exit(1)

        }

    }
    fun fetchData(): ObservableList<Junk>? {
        var results : ObservableList<Junk>? = null
        var resultSet : ResultSet? = null

        try {
            resultSet = selectAllJunk?.executeQuery()
            results = observableArrayList(Junk(1, "bronze", 100, 1000, "Bread", "Bradley", "November"))
            val metadata : ResultSetMetaData = resultSet!!.metaData
            val number = metadata.columnCount


        }catch (sqlException: SQLException){
            sqlException.printStackTrace()
        }
        finally {
            try {
                resultSet?.close()
            }catch (sqlException: SQLException){
                sqlException.printStackTrace()
            }
        }
        return results
    }


}