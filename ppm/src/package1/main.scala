package package1

import package1.Manipulation.{Coords, Point, Section}
import package1.Functions.Album
import qtrees.{QLeaf, QNode, QTree}

import java.awt.Color

object main {

  val path: String = "C:\\Users\\tomas\\Documents\\Docs\\Iscte\\ppm\\projeto\\Projeto_ppm\\ppm\\src\\imagens\\"


  val l1: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 0): Point): Coords, Color.red): Section)
  val l2: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 0): Point): Coords, Color.blue): Section)
  val l3: QLeaf[Coords, Section] = QLeaf((((0, 1): Point, (0, 1): Point): Coords, Color.yellow): Section)
  val l4: QLeaf[Coords, Section] = QLeaf((((1, 1): Point, (1, 1): Point): Coords, Color.green): Section)
  val qt: QTree[Coords] = QNode(((0, 0), (1, 1)), l1, l2, l3, l4)

  val l8: QLeaf[Coords, Section] = QLeaf((((2, 2): Point, (2, 2): Point): Coords, Color.red): Section)
  val l9: QLeaf[Coords, Section] = QLeaf((((3, 2): Point, (3, 2): Point): Coords, Color.blue): Section)
  val l10: QLeaf[Coords, Section] = QLeaf((((2,3): Point, (2, 3): Point): Coords, Color.yellow): Section)
  val l11: QLeaf[Coords, Section] = QLeaf((((3, 3): Point, (3, 3): Point): Coords, Color.green): Section)
  val qt_2: QTree[Coords] = QNode(((2, 2), (3, 3)), l8, l9, l10, l11)


  val l5: QLeaf[Coords, Section] = QLeaf((((2, 0): Point, (3, 1): Point): Coords, Color.red): Section)
  val l6: QLeaf[Coords, Section] = QLeaf((((0, 2): Point, (1, 3): Point): Coords, Color.blue): Section)
  val l7: QLeaf[Coords, Section] = QLeaf((((2, 2): Point, (3, 3): Point): Coords, Color.yellow): Section)
  val qt2: QTree[Coords] = qtrees.QNode(((0, 0), (3, 3)), qt, l5, l6, qt_2)

  val bit = BitMap({val image : Array[Array[Int]] = ImageUtil.readColorImage(path + "img.png")
    image.map(_.toList).toList})

  val ex = Example(qt)

  /*val bit2 : BitMap= ex.makeBitMap()


  val qt3 = ex.mirrorV()
  val ex1 = Example(qt3)
  val bit3 = ex1.makeBitMap()*/


  val exa = Example(qt2)
  val qt4 = exa.mirrorV()
  val ex2 = Example(qt4)
  val bit4 = ex2.makeBitMap()


  val bitOrig = exa.makeBitMap()


  def main(args: Array[String]): Unit = {


   // bit.generateImageFromBitMap("image.png")
    //bit2.generateImageFromBitMap("image2.png")
    //bit3.generateImageFromBitMap("image3.png")
    bit4.generateImageFromBitMap(path + "image4.png")
    bitOrig.generateImageFromBitMap(path +"imageOrig.png")

    /*println(bit)
    println(bit2)*/



  }

}
