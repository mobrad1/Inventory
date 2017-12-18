/** @author
 * Bradley Yarrow
 *
 * **/
package Inventory.Controller

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.stage.Stage
import Inventory.Model.JunkQueries
import java.io.IOException




class LoginController  {
    @FXML private lateinit var username: TextField

    @FXML private lateinit var password: PasswordField

    @FXML private lateinit var error: Label

    @FXML
    @Throws(IOException::class) private fun handleButtonAction(event: ActionEvent){
        validation(event)
    }
    @FXML
    @Throws(IOException::class) private fun validation(event: ActionEvent) = if (username.text == "bradley" && password.text == "Mojanity@1" ) {

        val home_page = FXMLLoader.load<Parent>(javaClass.getResource("../View/MainView.fxml"))

        val c : JunkQueries? = null
        val home_page_scene = Scene(home_page, 766.0, 423.0)
        val app_stage = (event.source as Node).scene.window as Stage
        app_stage.scene = home_page_scene
        app_stage.title = "Inventory"
        app_stage.onShown = EventHandler { c?.fetchData() }
        app_stage.show()

    }
    else  error.text = "Username or password incorrect"


}