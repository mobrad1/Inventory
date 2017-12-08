package sample


import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField

class LoginController {
    @FXML
    lateinit var username : TextField

    @FXML
    lateinit var password : PasswordField

    @FXML
    lateinit var error : Label

     fun login(){
         validation()
     }
    private fun validation(){
        if (username.text == "bradley" && password.text == "Mojanity@1" ) println("You can Enter Please") else error.text = "Username or password incorrect"
    }
}