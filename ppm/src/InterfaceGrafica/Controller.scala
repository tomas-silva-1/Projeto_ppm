package InterfaceGrafica

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView}
import package1.ImagesAlbum

class Controller {

  @FXML
  private var criarAlbum : Button = _
  @FXML
  private var botao2 : Button = _
  @FXML
  private var botao3 : Button = _
  @FXML
  private var rotateR : Button = _
  @FXML
  private var rotateL : Button = _
  @FXML
  private var mirrorV : Button = _
  @FXML
  private var mirrorH : Button = _
  @FXML
  private var sepia : Button = _
  @FXML
  private var contrast : Button = _
  @FXML
  private var noise : Button = _
  @FXML
  private var scale : Button = _

  @FXML
  private var album: ListView[ImagesAlbum] = _



  def cria_Album(): Unit ={

    val lst: ObservableList[ImagesAlbum] = FXCollections.observableArrayList(new ImagesAlbum(List()))
    album.setItems(lst)
  }


}
