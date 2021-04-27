package package1

import package1.Example.{Coords, Point, Section, mirrorH, rotateL, scale}

import java.awt.Color

object main {

  val l1: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 0): Point): Coords, Color.red): Section)
  val l2: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 0): Point): Coords, Color.blue): Section)
  val l3: QLeaf[Coords, Section] = QLeaf((((0, 1): Point, (0, 1): Point): Coords, Color.yellow): Section)
  val l4: QLeaf[Coords, Section] = QLeaf((((1, 1): Point, (1, 1): Point): Coords, Color.green): Section)

  val qt: QTree[Coords] = QNode(((0, 0), (1, 1)), l1, l2, l3, l4)



  def main(args: Array[String]): Unit = {

    val valor: Double = 3
    val qt1 :QTree[Coords] = scale(valor,qt)
    val qt2 :QTree[Coords] = rotateL(qt)
    val qt3 :QTree[Coords] = mirrorH(qt)

    //val matrix: Array[Array[Int]] = readColorImage("img.png")
    println("ola")
    println(qt)
    //println(qt1)
    println(qt2)
    println(qt3)

  }

}
