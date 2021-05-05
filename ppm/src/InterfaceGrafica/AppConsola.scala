package InterfaceGrafica

import package1.BitMap.generateImageFromBitMap
import package1.{BitMap, Manipulation}
import package1.Manipulation.{Coords, contrast, generateBitMapFromImage, makeBitMap, makeQTree, mapColourEffect, mirrorH, mirrorV, rotateL, rotateR, scale, sepia}
import qtrees.QTree

import scala.io.StdIn.{readDouble, readInt, readLine}

case class AppConsola() {
}
object AppConsola{

  def iniciar(): String={
    println("Insira o nome da imagem que deseja alterar:")
    "C:\\Users\\rodri\\Desktop\\Iscte\\Ppm\\Ppm_Projeto\\Projeto_ppm\\ppm\\src\\imagens\\"+readLine()

  }
  def loadImage(string: String): QTree[Coords] = {
    val bitMap: BitMap = generateBitMapFromImage(string)
    makeQTree(bitMap)
  }

  def editImage(qTree: QTree[Coords], f: QTree[Coords]=>QTree[Coords]) : QTree[Coords]={
    f(qTree)
  }
  def saveImage(QTree: QTree[Coords],string: String): Unit ={
    generateImageFromBitMap(makeBitMap(QTree),string)
  }


  def getScaleFactor() : Double = {
    println("Insira o fator de scale")
    readDouble()
  }

  def chooseFunction(string: String): Unit = {
    println("1- Scale image")
    println("2- Mirror image vertically")
    println("3- Mirror image horizontally")
    println("4- Rotate image Left")
    println("5- Rotate image Right")
    println("6- Apply contrast to the image")
    println("7- Apply sepia to the image")
    println("8- Apply noise to the image")
    println("0- exit")
    val n = readInt()
    n match {
      case 1 =>{saveImage(scale(getScaleFactor(),loadImage(string)),string)
        println("Alteraões efetuadas á imagem")
        chooseFunction(string)}
      case 2=>{saveImage(editImage(loadImage(string),mirrorV),string)
        println("Alteraões efetuadas á imagem")
      chooseFunction(string)}
      case 3=>{saveImage(editImage(loadImage(string),mirrorH),string)
        println("Alteraões efetuadas á imagem")
        chooseFunction(string)}
      case 4=>{saveImage(editImage(loadImage(string),rotateL),string)
        println("Alteraões efetuadas á imagem")
        chooseFunction(string)}
      case 5=>{saveImage(editImage(loadImage(string),rotateR),string)
        println("Alteraões efetuadas á imagem")
        chooseFunction(string)}
      case 6=>{saveImage(mapColourEffect(contrast,loadImage(string)),string)
        println("Alteraões efetuadas á imagem")
        chooseFunction(string)}
      case 7=>{saveImage(mapColourEffect(sepia,loadImage(string)),string)
        println("Alteraões efetuadas á imagem")
        chooseFunction(string)}
    //  case 8=>{saveImage(mapColourEffect(sepia,loadImage(string)),string)
    //    chooseFunction(string)}
      case 0=>{}

    }
  }


  def main(args: Array[String]): Unit = {
    chooseFunction(iniciar())

  }
}
