package sample

import javafx.application.Application
import javafx.application.Application.*
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.stage.Stage

import javax.swing.text.TabableView

class Main : Application() {

    @Throws(Exception::class) override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("login.fxml"))
        primaryStage.title = "Login"
        primaryStage.scene = Scene(root, 386.0, 284.0)
        primaryStage.show()

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}
