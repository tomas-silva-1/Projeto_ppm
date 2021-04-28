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

  val bit = BitMap({val image : Array[Array[Int]] = ImageUtil.readColorImage("img.png")
                        image.map(_.toList).toList})



  def main(args: Array[String]): Unit = {

    bit.generateImageFromBitMap("newImage.png")

    val example = Example(qt)
    val valor: Double = 3
    val qt1 :QTree[Coords] = example.scale(valor)
    val qt2 :QTree[Coords] = example.rotateL()
    val qt3 :QTree[Coords] = example.mirrorH()

    //val matrix: Array[Array[Int]] = readColorImage("img.png")
    println("ola")
    println(qt)
    //println(qt1)
    println(qt2)
    println(qt3)

  }

}
