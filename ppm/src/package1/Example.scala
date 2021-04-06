package package1

import package1.Example.Coords

import java.awt.Color


sealed trait QTree[+A]
case class QNode[A](value: A, one: QTree[A], two: QTree[A], three: QTree[A], four: QTree[A]) extends QTree[A]
case class QLeaf[A, B](value: B) extends QTree[A]
case object QEmpty extends QTree[Nothing]
//case class BitMap(matrix: Array[Array[Int]])


case class Example[A](myField: QTree[Coords]){
  def scale()=Example.scale(1,this.myField)
}

object Example{

  type Point = (Int , Int)
  type Coords = (Point, Point)
  type Section = (Coords, Color)

  // def makeQTree[A](b: BitMap):QTree[A]={

  //}

  //def makeBitMap[A](qt:QTree[A]):BitMap={

  //}

  def multiplier(c:Coords, s:Double): Coords = {
    val px : Point = ((c._1._1 * s).toInt, (c._1._2 * s).toInt)
    val py : Point = ((c._2._1 * s).toInt, (c._2._2 * s).toInt)
    (px,py)
  }


  def scale(s:Double, qt:QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty => QEmpty
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) => {
        val c : Coords = multiplier( ((x1: Int, y1: Int), (x2: Int, y2: Int)) :Coords ,s)
        val newSection : Section = (c,color)
        QLeaf(newSection)
      }
      case QNode(value,one,two,three,four) => {
        val newCoords: Coords = multiplier(value,s)
        QNode(newCoords,scale(s,one),scale(s,two), scale(s,three), scale(s,four))
      }
    }
  }

   def mirrorV(qt :QTree[Coords]):QTree[Coords]={
     qt match {
       case QEmpty=> QEmpty
       case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>{
         QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))
       }
       case QNode(value,one,two,three,four) => {
         QNode(value,mirrorV(two),mirrorV(one), mirrorV(four), mirrorV(three))
       }
     }
   }
   def mirrorH(qt :QTree[Coords]):QTree[Coords]={
     qt match {
       case QEmpty=> QEmpty
       case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>{
         QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))
       }
       case QNode(value,one,two,three,four) => {
         QNode(value,mirrorH(three),mirrorH(four), mirrorH(one), mirrorH(two))
       }
     }
   }

  val l1: QLeaf[Coords, Section] = QLeaf((((0, 0): Point, (0, 0): Point): Coords, Color.red): Section)
  val l2: QLeaf[Coords, Section] = QLeaf((((1, 0): Point, (1, 0): Point): Coords, Color.blue): Section)
  val l3: QLeaf[Coords, Section] = QLeaf((((0, 1): Point, (0, 1): Point): Coords, Color.yellow): Section)
  val l4: QLeaf[Coords, Section] = QLeaf((((1, 1): Point, (1, 1): Point): Coords, Color.green): Section)

  val qt: QTree[Coords] = QNode(((0, 0), (1, 1)), l1, l2, l3, l4)

  def main(args: Array[String]): Unit = {

      val valor: Double = 2
      val qt1 :QTree[Coords] = scale(valor,qt)
      val qt2 :QTree[Coords]= mirrorV(qt)
    val qt3 :QTree[Coords]= mirrorH(qt)

    //val matrix: Array[Array[Int]] = readColorImage("img.png")
    println("ola")
    println(qt)
    println(qt1)
   // println(qt2)
    //println(qt3)
  }


}

//  println(s"Maximum element of the tree: ${ Example.maximum(tree1)}")
//  println(s"Depth of the tree: ${ Example.depth(tree1)}")
//  println(s"Map: ${ Example.map(tree1)((x:Int)=>2*x)}")
