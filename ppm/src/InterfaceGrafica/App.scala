package InterfaceGrafica

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage


class App extends Application{
  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Album")
    val fxmlLoader= new FXMLLoader(getClass.getResource("Controller.fxml"))
    val mainViewRoot : Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
object FixApp{
  def main(args: Array[String]): Unit = {
  Application.launch(classOf[App], args: _*)
  }
}
