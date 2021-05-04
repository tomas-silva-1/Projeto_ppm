package InterfaceGrafica

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView, MenuButton, MenuItem, TextField, ToolBar}
import javafx.scene.image.{Image, ImageView}
import package1.Manipulation.{Coords, Point, Section, generateBitMapFromImage}
import package1.{BitMap, ImagesAlbum, Manipulation}
import qtrees.{QLeaf, QNode, QTree}
import javafx.scene.layout.AnchorPane
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue


import javax.imageio.ImageIO
import java.io.FileInputStream
import java.nio.file.{Files, Paths, StandardCopyOption}
//import javafx.scene.paint.Color

import java.awt.Color
import java.io.File

class Controller {

  @FXML
  private var carregarAlbum : Button = _
  @FXML
  private var carregarImagem : Button = _
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
  private var barra : ToolBar = _
  @FXML
  private var album: ListView[String] = _

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
    val list = getListOfFiles("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens")
    def aux(i:Int,i2:Int):Unit= {
      if(i<i2){
        lst.addAll(list(i).getName)
        aux(i+1,i2)
      }
    }
    aux(0,list.length)
    album.setItems(lst)
    barra.setVisible(false)
    imagem.setImage(null)
  }

  def showImage:Unit = {
    barra.setVisible(true)
    val str:String = album.getSelectionModel.getSelectedItem
    val f = new FileInputStream("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\" + str)
    val img = new Image(f)
    imagem.setImage(img)
  }


  def mostrar(): Unit={
    editar_Imagens.show()
    input.setVisible(false)
    input2.setVisible(false)
    salvar1.setVisible(false)
    salvar2.setVisible(false)
  }

  def salvarAdicionar():Unit={

    val str:String = input.getText
    if(new File(str).exists()) {
      val str2: String = input2.getText
      val bit: BitMap = generateBitMapFromImage(str)
      bit.generateImageFromBitMap("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\" + str2)
      input.setVisible(false)
      input2.setVisible(false)
      salvar2.setVisible(false)
    }else{
      input.setText("Path mal introduzido")
    }
  }

  def adicionar(): Unit= {
    input.setVisible(true)
    input.setText("Path da imagem")
    input2.setVisible(true)
    input2.setText("Nome da imagem")
    salvar2.setVisible(true)
  }

  def salvarEliminar():Unit={
    val str:String = input.getText
    val d = new File("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    if (d.exists) {
      d.delete()
      input.setVisible(false)
      salvar1.setVisible(false)
    } else {
      input.setText("Imagem não existe")
    }

  }

  def remover(): Unit= {
    input.setVisible(true)
    input.setText("Nome da imagem")
    salvar1.setVisible(true)
  }

  def trocar(): Unit= {
    println("Trocado")
  }

  def rodarR():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).rotateR()
    Manipulation.makeBitMap(qt).generateImageFromBitMap("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val f = new FileInputStream("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def rodarE():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).rotateL()
    Manipulation.makeBitMap(qt).generateImageFromBitMap("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val f = new FileInputStream("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def espelhoV():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mirrorV()
    Manipulation.makeBitMap(qt).generateImageFromBitMap("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val f = new FileInputStream("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def espelhoH():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mirrorH()
    Manipulation.makeBitMap(qt).generateImageFromBitMap("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val f = new FileInputStream("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  /*def noisee():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mirrorH()
    Manipulation.makeBitMap(qt).generateImageFromBitMap("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val f = new FileInputStream("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }*/

  def contraste():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mapColourEffectContrast()
    Manipulation.makeBitMap(qt).generateImageFromBitMap("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val f = new FileInputStream("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def sepiaa():Unit={
    val str:String = album.getSelectionModel.getSelectedItem
    val bit: BitMap = generateBitMapFromImage("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val qt= Manipulation(Manipulation.makeQTree(bit)).mapColourEffectSepia()
    Manipulation.makeBitMap(qt).generateImageFromBitMap("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val f = new FileInputStream("C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+str)
    val img = new Image(f)
    imagem.setImage(img)
  }

  def resize():Unit={
  }

  def opentextscale():Unit={
    scaletext.setVisible(true)
    scaletext.setText("Factor de escala")
  }


}