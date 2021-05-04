package InterfaceGrafica

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView, MenuButton, MenuItem, TextField}
import javafx.scene.image.{Image, ImageView}
import package1.Manipulation.{Coords, Point, Section}
import package1.{ImagesAlbum, Manipulation}
import qtrees.{QLeaf, QNode, QTree}
import javafx.scene.layout.AnchorPane
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue

import javax.imageio.ImageIO
import java.io.FileInputStream
//import javafx.scene.paint.Color

import java.awt.Color
import java.io.File

class Controller {

  @FXML
  private var criarAlbum : Button = _
  @FXML
  private var carregarImagem : Button = _
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
  private var Remover_Imagem : Button = _
  @FXML
  private var Adicionar_Imagem : Button = _
  @FXML
  private var Trocar_Imagens : Button = _
  @FXML
  private var editar_Imagens : MenuButton = _
  @FXML
  private var remover_Imagem : MenuItem = _
  @FXML
  private var adicionar_Imagem : MenuItem = _
  @FXML
  private var trocar_Imagens : MenuItem = _
  @FXML
  private var input : TextField = _
  @FXML
  private var imagem : ImageView = new ImageView()
  @FXML
  private var album: ListView[String] = new ListView[String]()

  //val anchorpane = new AnchorPane()

  /*val l1: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 0): Point): Coords, Color.red): Section)
  val l2: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 0): Point): Coords, Color.blue): Section)
  val l3: QLeaf[Coords, Section] = QLeaf((((0, 1): Point, (0, 1): Point): Coords, Color.yellow): Section)
  val l4: QLeaf[Coords, Section] = QLeaf((((1, 1): Point, (1, 1): Point): Coords, Color.green): Section)
  val qt: QTree[Coords] = QNode(((0, 0), (1, 1)), l1, l2, l3, l4)
  val ex = Manipulation(qt)
  val bit = ex.makeBitMap()*/

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def carrega_Album: Unit ={
    val lst: ObservableList[String] = FXCollections.observableArrayList()
    val list = getListOfFiles("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens")
    def aux(i:Int,i2:Int):Unit= {
      if(i<i2){
        lst.addAll(list(i).getName)
        aux(i+1,i2)
      }
    }
    aux(0,list.length)
    album.setItems(lst)
    //import javafx.scene.layout.AnchorPane
    /*AnchorPane.setTopAnchor(album, 10.0)*/
  }

  def showImage:Unit = {
    album.getSelectionModel.selectedItemProperty.addListener(new ChangeListener[String]() {
      override def changed(ov: ObservableValue[_ <: String], old_val: String, new_val: String): Unit = {
        val img = new Image("file:imagens\\"+old_val)
        print("Ola")
        imagem.setImage(img)
      }
    })
  }

  def mostrar(): Unit={
    editar_Imagens.show()
  }

  def remover(): Unit= {
    println("Ola")
  }

  def carrega_Imagem(): Unit={
    //val img : Image = Auxiliar.img()
    val f = new FileInputStream("iscte.png")
    /*val lst: ObservableList[Image] = FXCollections.observableArrayList()
    lst.addAll(img)*/
    val img = new Image(f)
    imagem.setImage(img)
    /*imagem.setFitWidth(500)
    imagem.setFitHeight(100)*/

  }


}
