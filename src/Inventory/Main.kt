package Inventory

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {

    @Throws(Exception::class) override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("View/login.fxml"))
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
