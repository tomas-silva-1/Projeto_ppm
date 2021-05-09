package InterfaceGrafica

import package1.BitMap.generateImageFromBitMap
import package1.{BitMap, Manipulation}
import package1.Manipulation.{Coords, contrast, generateBitMapFromImage, makeBitMap, makeQTree, mapColourEffect, mirrorH, mirrorV, rotateL, rotateR, scale, sepia}
import qtrees.QTree
import random.MyRandom

import scala.io.StdIn.{readDouble, readInt, readLine}

case class AppConsola() {
}
object AppConsola{

  def iniciar(): String={
    println("Insira o path da imagem que deseja alterar:")
    readLine()

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
    println("1- Dar scale à imagem")
    println("2- Espelhar a imagem verticalmente")
    println("3- Espelhar a imagem horizontalmente")
    println("4- Rodar a imagem para a esquerda")
    println("5- Rodar a imagem para a direita")
    println("6- Aplicar contraste à imagem ")
    println("7- Aplicar sepia à imagem ")
    println("8- Aplicar noise à imagem ")
    println("0- salvar e enviar")
    val n = readInt()
    n match {
      case 1 =>{saveImage(scale(getScaleFactor(),loadImage(string)),string)
        println("Alterações efetuadas à imagem")
        chooseFunction(string)}
      case 2=>{saveImage(editImage(loadImage(string),mirrorV),string)
        println("Alterações efetuadas à imagem")
      chooseFunction(string)}
      case 3=>{saveImage(editImage(loadImage(string),mirrorH),string)
        println("Alterações efetuadas à imagem")
        chooseFunction(string)}
      case 4=>{saveImage(editImage(loadImage(string),rotateL),string)
        println("Alterações efetuadas à imagem")
        chooseFunction(string)}
      case 5=>{saveImage(editImage(loadImage(string),rotateR),string)
        println("Alterações efetuadas à imagem")
        chooseFunction(string)}
      case 6=>{saveImage(mapColourEffect(contrast,loadImage(string)),string)
        println("Alterações efetuadas à imagem")
        chooseFunction(string)}
      case 7=>{saveImage(mapColourEffect(sepia,loadImage(string)),string)
        println("Alterações efetuadas à imagem")
        chooseFunction(string)}
      case 8=>{val r = MyRandom(3)
        saveImage(Manipulation(loadImage(string)).mapColourEffectNoise(r),string)
        chooseFunction(string)}
      case 0=>{}

    }
  }


  def main(args: Array[String]): Unit = {
    chooseFunction(iniciar())

  }
}
