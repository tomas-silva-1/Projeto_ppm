package InterfaceGrafica

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView}
import javafx.scene.image.{Image, ImageView}
import package1.Manipulation.{Coords, Point, Section}
import package1.{ImagesAlbum, Manipulation}
import qtrees.{QLeaf, QNode, QTree}
import javafx.scene.layout.AnchorPane

import java.awt.Color

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
  private var imagem : ImageView = new ImageView()

  @FXML
  private var album: ListView[Album] = new ListView[Album]()

  val anchorpane = new AnchorPane()

  val l1: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 0): Point): Coords, Color.red): Section)
  val l2: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 0): Point): Coords, Color.blue): Section)
  val l3: QLeaf[Coords, Section] = QLeaf((((0, 1): Point, (0, 1): Point): Coords, Color.yellow): Section)
  val l4: QLeaf[Coords, Section] = QLeaf((((1, 1): Point, (1, 1): Point): Coords, Color.green): Section)
  val qt: QTree[Coords] = QNode(((0, 0), (1, 1)), l1, l2, l3, l4)
  val ex = Manipulation(qt)
  val bit = ex.makeBitMap()


  def carrega_Album: Unit ={
    val lst: ObservableList[Album] = FXCollections.observableArrayList(new Album("nome",bit))
    album.setItems(lst)
    import javafx.scene.layout.AnchorPane
    /*AnchorPane.setTopAnchor(album, 10.0)*/
  }

  def getImg(string: String) : Image={
    new Image(string)
  }

  def carregaImagem(): Unit={
    imagem.setImage(getImg("iscte.png"))
    imagem.setFitWidth(500)
    imagem.setFitHeight(100)
  }


}
