package InterfaceGrafica

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView}
import package1.ImagesAlbum

class Controller {

  @FXML
  private var button1 : Button = _

  @FXML
  private var album: ListView[ImagesAlbum] = _



  def cria_Album(): Unit ={

    val lst: ObservableList[ImagesAlbum] = FXCollections.observableArrayList(new ImagesAlbum(List()))
    album.setItems(lst)
  }


}
