package package1

import package1.Manipulation.{Coords, Point, Section}
import qtrees.{QLeaf, QNode, QTree}
import random.MyRandom

import java.awt.Color

object main {

  val path: String = System.getProperty("user.dir") + "\\Projeto_ppm\\ppm\\src\\imagens"


  val l1: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 0): Point): Coords, Color.red): Section)
  val l2: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 0): Point): Coords, Color.blue): Section)
  val l3: QLeaf[Coords, Section] = QLeaf((((0, 1): Point, (0, 1): Point): Coords, Color.yellow): Section)
  val l4: QLeaf[Coords, Section] = QLeaf((((1, 1): Point, (1, 1): Point): Coords, Color.green): Section)
  val qt: QTree[Coords] = QNode(((0, 0), (1, 1)), l1, l2, l3, l4)

  val l5: QLeaf[Coords, Section] = QLeaf((((2, 2): Point, (2, 2): Point): Coords, Color.red): Section)
  val l6: QLeaf[Coords, Section] = QLeaf((((3, 2): Point, (3, 2): Point): Coords, Color.blue): Section)
  val l7: QLeaf[Coords, Section] = QLeaf((((2,3): Point, (2, 3): Point): Coords, Color.yellow): Section)
  val l8: QLeaf[Coords, Section] = QLeaf((((3, 3): Point, (3, 3): Point): Coords, Color.green): Section)
  val qt_2: QTree[Coords] = QNode(((2, 2), (3, 3)), l5, l6, l7, l8)


  val l9: QLeaf[Coords, Section] = QLeaf((((2, 0): Point, (3, 1): Point): Coords, Color.red): Section)
  val l10: QLeaf[Coords, Section] = QLeaf((((0, 2): Point, (1, 3): Point): Coords, Color.blue): Section)
  val qt2: QTree[Coords] = qtrees.QNode(((0, 0), (3, 3)), qt, l9, l10, qt_2)

  val l11: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 1): Point): Coords, Color.red): Section)
  val l12: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 1): Point): Coords, Color.yellow): Section)
  val l13: QLeaf[Coords, Section] = QLeaf((((0, 2): Point, (0, 3): Point): Coords, Color.green): Section)
  val l14: QLeaf[Coords, Section] = QLeaf((((1, 2): Point, (1, 3): Point): Coords, Color.blue): Section)
  val qtImpar: QTree[Coords] = qtrees.QNode(((0, 0), (3, 3)), l11, l12, l13, l14)

  val ex = Manipulation(qt)
  val exa = Manipulation(qt2)
  val exam = Manipulation(qtImpar)

  val r = MyRandom(5)

  val bitOrig1 = ex.makeBitMap()
  val bitOrig2 = exa.makeBitMap()
  val bitOrig3 = exam.makeBitMap()

  val qtScale = exam.scale(4)
  val exScale = Manipulation(qtScale)
  val bitScale = exScale.makeBitMap()

  val qtMirrorV = ex.mirrorV()
  val exMirrorV = Manipulation(qtMirrorV)
  val bitMirrorV = exMirrorV.makeBitMap()

  val qtRotateR = exa.rotateR()
  val exRotateR = Manipulation(qtRotateR)
  val bitRotateR = exRotateR.makeBitMap()

  val qtContrast = exa.mapColourEffectContrast()
  val exContrast = Manipulation(qtContrast)
  val bitContrast = exContrast.makeBitMap()

  val qtSepia = ex.mapColourEffectSepia()
  val exSepia = Manipulation(qtSepia)
  val bitSepia = exSepia.makeBitMap()

  val qtNoise = exa.mapColourEffectNoise(r)
  val exNoise = Manipulation(qtNoise)
  val bitNoise = exNoise.makeBitMap()



  def main(args: Array[String]): Unit = {

    bitOrig1.generateImageFromBitMap(path + "\\" + "imgOrig1.png")
    bitOrig2.generateImageFromBitMap(path + "\\" + "imgOrig2.png")
    bitOrig3.generateImageFromBitMap(path + "\\" + "imgOrig3.png")
    bitScale.generateImageFromBitMap(path + "\\" + "imgScale.png")
    bitMirrorV.generateImageFromBitMap(path + "\\" + "imgMirrorV.png")
    bitRotateR.generateImageFromBitMap(path + "\\" + "imgRotateR.png")
    bitContrast.generateImageFromBitMap(path + "\\" + "imgContrast.png")
    bitSepia.generateImageFromBitMap(path + "\\" + "imgSepia.png")
    bitNoise.generateImageFromBitMap(path + "\\" + "imgNoise.png")

  }

}
