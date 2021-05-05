package InterfaceGrafica

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView, MenuButton, MenuItem, TextField, ToolBar}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.GridPane
import package1.Manipulation.generateBitMapFromImage
import package1.{BitMap, Manipulation}
import random.MyRandom
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.AnchorPane._

import java.io.FileInputStream
import java.io.File

class Controller {

  val path = System.getProperty("user.dir") + "\\Projeto_ppm\\ppm\\src\\imagens"

  @FXML
  private var carregarAlbum : Button = _
  @FXML
  private var carregarImagem : Button = _
  @FXML
  private var grid : Button = _
  @FXML
  private var salvar1 : Button = _
  @FXML
  private var salvar2 : Button = _
  @FXML
  private var salvar3 : Button = _
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
  private var salvarscale : Button = _
  @FXML
  private var left : Button = _
  @FXML
  private var right : Button = _
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
  private var input2 : TextField = _
  @FXML
  private var scaletext : TextField = _
  @FXML
  private var imagem : ImageView = _
  @FXML
  private var auxiliar : ImageView = new ImageView
  @FXML
  private var barra : ToolBar = _
  @FXML
  private var album: ListView[String] = _
  @FXML
  private var gridView: GridPane = _

  val anchor: AnchorPane = new AnchorPane(album)
  AnchorPane.setTopAnchor(album, 0.0)
  AnchorPane.setLeftAnchor(album, 0.0)
  /*AnchorPane.setTopAnchor(barra, 10.0)
  AnchorPane.setRightAnchor(barra, 10.0)*/
  anchor.getChildren().addAll(album)

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def carrega_Album(): Unit ={
    val lst: ObservableList[String] = FXCollections.observableArrayList()
    val list = getListOfFiles(path)
    def aux(i:Int,i2:Int):Unit= {
      if(i<i2){
        lst.addAll(list(i).getName)
        aux(i+1,i2)
      }
    }
    aux(0,list.length)
    album.setItems(lst)
    barra.setVisible(false)
    left.setVisible(false)
    right.setVisible(false)
    editar_Imagens.setVisible(true)
    grid.setVisible(true)
    imagem.setImage(null)
    album.setDisable(false)
  }

  def showImage:Unit = {
    gridView.setVisible(false)
    barra.setVisible(true)
    left.setVisible(true)
    right.setVisible(true)
    val str:String = album.getSelectionModel.getSelectedItem
    val f = new FileInputStream(path + "\\" + str)
    val img = new Image(f)
    imagem.setImage(img)
  }


  def mostrar(): Unit={
    editar_Imagens.show()
    album.setDisable(false)
    input.setVisible(false)
    input2.setVisible(false)
    salvar1.setVisible(false)
    salvar2.setVisible(false)
    salvar3.setVisible(false)
  }

  def salvarAdicionar():Unit={

    val str:String = input.getText
    input.clear()
    if(new File(str).exists()) {
      val str2: String = input2.getText
      val bit: BitMap = generateBitMapFromImage(str)
      bit.generateImageFromBitMap(path + "\\" + str2)
      input.setVisible(false)
      input2.setVisible(false)
      salvar2.setVisible(false)
    }else{
      input.setPromptText("Path mal introduzido")
    }
  }

  def adicionar(): Unit= {
    input.setVisible(true)
    input.setPromptText("Path da imagem")
    input2.setVisible(true)
    input2.setPromptText("Nome da imagem")
    salvar2.setVisible(true)
  }

  def salvarEliminar():Unit={
    val str:String = input.getText
    input.clear()
    val d = new File(path + "\\"+str)
    if (d.exists) {
      d.delete()
      input.setVisible(false)
      salvar1.setVisible(false)
      album.setDisable(false)
    } else {
      input.setPromptText("Imagem não existe")
      album.setDisable(false)
    }

  }

  def remover(): Unit= {
    album.setDisable(true)
    input.setVisible(true)
    input.setPromptText("Nome da imagem")
    salvar1.setVisible(true)
  }

  def salvarTrocar():Unit={

  }

  def trocar(): Unit= {
    input.setVisible(true)
    input.setPromptText("Nome da imagem")
    input2.setVisible(true)
    input2.setPromptText("Nome da imagem")
    salvar3.setVisible(true)
  }

  def rodarR():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage(path + "\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).rotateR()
    Manipulation.makeBitMap(qt).generateImageFromBitMap(path + "\\"+str)
    val f = new FileInputStream(path + "\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def rodarE():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage(path + "\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).rotateL()
    Manipulation.makeBitMap(qt).generateImageFromBitMap(path + "\\"+str)
    val f = new FileInputStream(path + "\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def espelhoV():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage(path + "\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mirrorV()
    Manipulation.makeBitMap(qt).generateImageFromBitMap(path + "\\"+str)
    val f = new FileInputStream(path + "\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def espelhoH():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage(path + "\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mirrorH()
    Manipulation.makeBitMap(qt).generateImageFromBitMap(path + "\\"+str)
    val f = new FileInputStream(path + "\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def noisee():Unit={
    val r = MyRandom(3)
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage(path + "\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mapColourEffectNoise(r)
    Manipulation.makeBitMap(qt).generateImageFromBitMap(path + "\\"+str)
    val f = new FileInputStream(path + "\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def contraste():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage(path + "\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mapColourEffectContrast()
    Manipulation.makeBitMap(qt).generateImageFromBitMap(path + "\\"+str)
    val f = new FileInputStream(path + "\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def sepiaa():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage(path + "\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mapColourEffectSepia()
    Manipulation.makeBitMap(qt).generateImageFromBitMap(path + "\\"+str)
    val f = new FileInputStream(path + "\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def resize():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val d : Double = scaletext.getText.toDouble
    val bit: BitMap = generateBitMapFromImage(path + "\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).scale(d)
    Manipulation.makeBitMap(qt).generateImageFromBitMap(path + "\\"+str)
    val f = new FileInputStream(path + "\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
    scaletext.setVisible(false)
    salvarscale.setVisible(false)
    rotateR.setVisible(true)
    rotateL.setVisible(true)
    mirrorH.setVisible(true)
    mirrorV.setVisible(true)
    noise.setVisible(true)
    contrast.setVisible(true)
    sepia.setVisible(true)
  }

  def opentextscale():Unit={
    rotateR.setVisible(false)
    rotateL.setVisible(false)
    mirrorH.setVisible(false)
    mirrorV.setVisible(false)
    noise.setVisible(false)
    contrast.setVisible(false)
    sepia.setVisible(false)
    scaletext.setVisible(true)
    scaletext.setText("")
    salvarscale.setVisible(true)
  }

  def doLeft():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val index = album.getItems.indexOf(str)
    if(index - 1 >= 0) {
      val str2:String = album.getItems.get(index - 1)
      album.getSelectionModel.select(index - 1)
      val f = new FileInputStream(path + "\\" + str2)
      val img = new Image(f)
      imagem.setImage(img)
    }
  }

  def doRight():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val index = album.getItems.indexOf(str)
    if(index + 1 < album.getItems.size()) {
      val str2:String = album.getItems.get(index + 1)
      album.getSelectionModel.select(index + 1)
      val f = new FileInputStream(path + "\\" + str2)
      val img = new Image(f)
      imagem.setImage(img)
    }
  }

  def mostrarGrid():Unit={
    gridView.setVisible(true)
    gridView.setGridLinesVisible(true)
    left.setVisible(false)
    right.setVisible(false)
    barra.setVisible(false)
    imagem.setImage(null)
    def aux(i:Int,c:Int,l:Int):Unit= {
      if(i<album.getItems.size()) {
        val anchorpane = new AnchorPane
        var f = new FileInputStream(path + "\\" + album.getItems.get(i))
        var img = new Image(f)
        var view = new ImageView()
        view.setImage(img)
        gridView.add(view,c,l)
        setTopAnchor(view, 10.0)
        aux(i+1,c+1,l)
      }
    }
    aux(0,0,0)

    /*val f = new FileInputStream(path + "\\"  + album.getItems.get(0))
    val img = new Image(f)
    auxiliar.setImage(img)
    gridView.add(auxiliar,0,0)

    val f1 = new FileInputStream(path + "\\"  + album.getItems.get(1))
    val img1 = new Image(f1)
    val i = new ImageView()
    i.setImage(img1)
    gridView.add(i,0,1)

    val f2 = new FileInputStream(path + "\\"  + album.getItems.get(2))
    val img2 = new Image(f2)
    val i2 = new ImageView()
    i2.setImage(img2)
    gridView.add(i2,1,0)

    val f3 = new FileInputStream(path + "\\"  + album.getItems.get(3))
    val img3 = new Image(f3)
    val i3 = new ImageView()
    i3.setImage(img3)
    gridView.add(i3,1,1)*/

  }




}