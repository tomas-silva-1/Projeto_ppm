package package1

import package1.Example.{Coords, Point, Section}

import java.awt.Color

object main {

  val l1: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 0): Point): Coords, Color.red): Section)
  val l2: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 0): Point): Coords, Color.blue): Section)
  val l3: QLeaf[Coords, Section] = QLeaf((((0, 1): Point, (0, 1): Point): Coords, Color.yellow): Section)
  val l4: QLeaf[Coords, Section] = QLeaf((((1, 1): Point, (1, 1): Point): Coords, Color.green): Section)
  val qt: QTree[Coords] = QNode(((0, 0), (1, 1)), l1, l2, l3, l4)

  val l5: QLeaf[Coords, Section] = QLeaf((((2, 0): Point, (3, 1): Point): Coords, Color.red): Section)
  val l6: QLeaf[Coords, Section] = QLeaf((((0, 2): Point, (1, 3): Point): Coords, Color.blue): Section)
  val l7: QLeaf[Coords, Section] = QLeaf((((2, 2): Point, (3, 3): Point): Coords, Color.yellow): Section)
  val qt2: QTree[Coords] = QNode(((0, 0), (3, 3)), qt, l5, l6, l7)

  val bit = BitMap({val image : Array[Array[Int]] = ImageUtil.readColorImage("C:\\Users\\tomas\\Documents\\Docs\\Iscte\\ppm\\projeto\\Projeto_ppm\\ppm\\src\\package1\\img.png")
    image.map(_.toList).toList})

  val example = Example(qt)
  val qt1 :QTree[Coords] = example.mapColourEffectContrast()
  val qt5 :QTree[Coords] = example.mapColourEffectSepia()
  println(qt1)
  val ex2 = Example(qt1)
  val ex3 = Example(qt5)
  val ex4 = Example (qt2)
  val qt6 = ex4.mapColourEffectSepia()
  val ex6 = Example(qt6)
  val bit2 : BitMap= example.makeBitMap()
  val bit3 : BitMap= ex2.makeBitMap()
  val bit4 :BitMap = ex3.makeBitMap()
  val bit5 : BitMap = ex6.makeBitMap()



  def main(args: Array[String]): Unit = {





    //val qt2 :QTree[Coords] = example.rotateL()
    //val qt3 :QTree[Coords] = example.mirrorH()

    bit.generateImageFromBitMap("newImage.png")
    bit2.generateImageFromBitMap("newImage2.png")
    bit3.generateImageFromBitMap("image3.png")
    bit4.generateImageFromBitMap("image4.png")
    bit5.generateImageFromBitMap("img5.png")

    println(bit)
    println(bit2)



  }

}
