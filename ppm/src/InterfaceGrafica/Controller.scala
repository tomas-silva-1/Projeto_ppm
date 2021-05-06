package InterfaceGrafica

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView, MenuButton, MenuItem, TextField, ToolBar}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.{AnchorPane, GridPane}
import package1.Manipulation.generateBitMapFromImage
import package1.{BitMap, Manipulation}
import random.MyRandom

import java.io.{File, FileInputStream}

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
    var list1 : List[String]= FileWriter.getImage.toList
    def aux(i:Int,i2:Int):Unit= {
      if(i<i2){
        lst.addAll(list1(i))
        aux(i+1,i2)
      }
    }
    aux(0,list1.length)
    album.setItems(lst)
    barra.setVisible(false)
    left.setVisible(false)
    right.setVisible(false)
    editar_Imagens.setVisible(true)
    grid.setVisible(true)
    imagem.setImage(null)
    album.setDisable(false)
    grid.setDisable(false)
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
    album.setDisable(true)
    grid.setDisable(true)
    barra.setVisible(false)
    left.setVisible(false)
    right.setVisible(false)
    input.setVisible(false)
    input2.setVisible(false)
    salvar1.setVisible(false)
    salvar2.setVisible(false)
    salvar3.setVisible(false)
  }

  def salvarAdicionar():Unit={
    val str:String = input.getText
    val str2: String = input2.getText
    input.clear()
    input2.clear()
    if(new File(str).exists()) {
      FileWriter.addImg(str2)
      val bit: BitMap = generateBitMapFromImage(str)
      bit.generateImageFromBitMap(path + "\\" + str2)
      input.setVisible(false)
      input2.setVisible(false)
      salvar2.setVisible(false)
      carrega_Album()
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
      FileWriter.removeImg(str)
      input.setVisible(false)
      salvar1.setVisible(false)
      album.setDisable(false)
      carrega_Album()
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
    val str:String = input.getText
    val str2:String = input2.getText
    input.clear()
    input2.clear()
    if(new File(path + "\\"+str).exists() && new File(path + "\\"+str2).exists()) {
      FileWriter.trocar(str,str2)
      input.setVisible(false)
      input2.setVisible(false)
      salvar3.setVisible(false)
      carrega_Album()
    }else{
      input.setPromptText("Path mal introduzido")
      input2.setPromptText("Path mal introduzido")
    }
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
    left.setVisible(false)
    right.setVisible(false)
    barra.setVisible(false)
    imagem.setImage(null)
    gridView.setGridLinesVisible(true)

    /*val anchor = new AnchorPane(grid, view)
    AnchorPane.setTopAnchor(view,1.0)
    AnchorPane.setLeftAnchor(view,1.0)
    AnchorPane.setRightAnchor(view,1.0)
    AnchorPane.setBottomAnchor(view,1.0)*/
    //anchor.getChildren.addAll(grid, view)


    /*def aux(i:Int,c:Int,l:Int):Unit= {
      if(i<album.getItems.size()) {
        var f = new FileInputStream(path + "\\" + album.getItems.get(i))
        var img = new Image(f)
        var view = new ImageView()
        //var grid2 = new GridPane()
        view.setFitWidth(gridView.getLayoutY)
        view.setFitHeight(gridView.getLayoutY)
        view.setImage(img)
        gridView.addRow(c,view)
        aux(i+1,c+1,l)
      }
    }
    aux(0,0,0)*/
    /*val c = 0
    val l = 0*/
    var k = 0
    for(c <- 0 to (album.getItems.size()/2).toInt - 1){
      for(l <- 0 to (album.getItems.size()/2).toInt - 1){
        var f = new FileInputStream(path + "\\" + album.getItems.get(k))
        var img = new Image(f)
        var view = new ImageView()
        view.setFitWidth(gridView.getLayoutY)
        view.setFitHeight(gridView.getLayoutY)
        view.setImage(img)
        gridView.add(view,l,c)
        k = k + 1
      }
    }
    if(album.getItems.size() % 2 != 0){
      var f = new FileInputStream(path + "\\" + album.getItems.get(album.getItems.size() - 1))
      var img = new Image(f)
      var view = new ImageView()
      view.setFitWidth(gridView.getLayoutY)
      view.setFitHeight(gridView.getLayoutY)
      view.setImage(img)
      gridView.addRow(album.getItems.size()/2,view)
    }


  }

}