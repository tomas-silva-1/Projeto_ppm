import java.awt.Color
import java.lang.reflect.Array


type Point = (Int, Int)
type Coords = (Point, Point)
type Section = (Coords, Color)
trait QTree[+A]
case class QNode[A](value: A, one: QTree[A], two: QTree[A], three: QTree[A], four: QTree[A]) extends QTree[A]
case class QLeaf[A, B](value: B) extends QTree[A]
case object QEmpty extends QTree[Nothing]
case class BitMap(matrix: Array[Array[Int]])
case class Example[A](myField: QTree[A])

object Example{

  def makeQTree[A](b: BitMap):QTree[A]={

  }

  def makeBitMap[A](qt:QTree[A]):BitMap={

  }

  def multiplier(c:Coords, s:Double): Coords = {
    val px : Point = ((c._1._1 * s).toInt, (c._1._2 * s).toInt)
    val py : Point = ((c._2._1 * s).toInt, (c._2._2 * s).toInt)
    (px,py)
  }

  def scale(s:Double, qt:QTree[Coords]):QTree[Coords]={ qt match {
      case QEmpty => QEmpty
      case QLeaf(section) => {
        val c : Coords = multiplier(section[1],s)
        val newSection : Section = (c,section[2])
        QLeaf(newSection)
      }
      case QNode(value,one,two,three,four) => {
        val newCoords: Coords = multiplier(value,s)
        QNode(newCoords,scale(s,one),scale(s,two), scale(s,three), scale(s,four))
      }
      }
  }

  def main(args: Array[String]): Unit = {
    val l1: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 0): Point): Coords, Color.red): Section)
    val l2: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 0): Point): Coords, Color.blue): Section)
    val l3: QLeaf[Coords, Section] = QLeaf((((0, 1): Point, (0, 1): Point): Coords, Color.yellow): Section)
    val l4: QLeaf[Coords, Section] = QLeaf((((1, 1): Point, (1, 1): Point): Coords, Color.green): Section)

    val qt: QTree[Coords] = QNode(((0, 0), (1, 1)), l1, l2, l3, l4)

    //val matrix: Array[Array[Int]] = readColorImage("img.png")

    println(qt)
  }
}
//  println(s"Maximum element of the tree: ${ Example.maximum(tree1)}")
//  println(s"Depth of the tree: ${ Example.depth(tree1)}")
//  println(s"Map: ${ Example.map(tree1)((x:Int)=>2*x)}")





