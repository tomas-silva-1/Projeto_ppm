package package1

import package1.Example.Coords

import java.awt.Color


sealed trait QTree[+A]
case class QNode[A](value: A, one: QTree[A], two: QTree[A], three: QTree[A], four: QTree[A]) extends QTree[A]
case class QLeaf[A, B](value: B) extends QTree[A]
case object QEmpty extends QTree[Nothing]


case class Example[A](myField: QTree[Coords]){
  def scale(d:Double)=Example.scale(d,this.myField)
  def mirrorV()=Example.mirrorV(this.myField)
  def mirrorH()=Example.mirrorH(this.myField)
  def rotateL()=Example.rotateL(this.myField)

}

object Example{

  type Point = (Int , Int)
  type Coords = (Point, Point)
  type Section = (Coords, Color)

  // def makeQTree[A](b: BitMap):QTree[A]={

  //}

  def paint(p: Point, color: Color, list:List[List[Int]]): List[List[Int]] = {
    /*list match {
      case Nil => Nil
      case y::yx =>
    }
    list(p._1)(p._2) = ImageUtil.encodeRgb(color.getBlue,color.getGreen,color.getRed)*/
  }

  def makeBitMap[A](qt:QTree[A]):List[List[Int]]={
    val list: List[List[Int]] =
    qt match {
      case QEmpty => Nil
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>{

        /*list(x1)(y1)
        def aux1(list:List[List[Int]], cord:Coords): List[List[Int]] = {
          if(x1+1<=x2) aux1(list,((x1+1: Int, y1: Int), (x2: Int, y2: Int)))
          paint((x1, y1), color,list:List[List[Int]])
        }
        aux1(list,((x1: Int, y1: Int), (x2: Int, y2: Int)))*/

      }
      case QNode(value, one, two, three, four) =>{
        makeBitMap(one)
        makeBitMap(two)
        makeBitMap(three)
        makeBitMap(four)
      }

    }
  }

  def multiplier(c:Coords, s:Double): Coords = {
    val px : Point = ((c._1._1 * s).toInt, (c._1._2 * s).toInt)
    val py : Point = ((c._2._1 * s+(s-1)).toInt, (c._2._2 * s+(s-1)).toInt)
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
  def rotateL(qt :QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty=> QEmpty
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>{
        QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))
      }
      case QNode(value,one,two,three,four) => {
        QNode(value,rotateL(two),rotateL(four), rotateL(one), rotateL(three))
      }
    }
  }

  def noise(c:Color):Color={
    new Color(255,255,255)
  }

  def contrast(c:Color):Color={
    val lumi = ImageUtil.luminance(c.getRed,c.getGreen,c.getBlue)
    if(lumi > 127){
      var r = 0
      var g = 0
      var b = 0
      if(c.getRed - 100 > 0){  r = c.getRed - 100} else{  r=0}
      if(c.getGreen - 100 > 0){ g = c.getGreen - 100} else{  g=0}
      if(c.getBlue - 100 > 0){ b = c.getBlue - 100} else{  b=0}
      new Color(r,g,b)
    }else{
      var r = 0
      var g = 0
      var b = 0
      if(c.getRed + 100 < 255){  r = c.getRed + 100} else{ r=255}
      if(c.getGreen + 100 < 255){ g = c.getGreen + 100} else{ g=255}
      if(c.getBlue + 100 < 255){ b = c.getBlue + 100} else{ b=255}
      new Color(r,g,b)
    }
  }

  def sepia(c:Color):Color={
    val r = ((c.getRed * .393) + (c.getGreen *.769) + (c.getBlue * .189)).toInt
    val g = ((c.getRed * .349) + (c.getGreen *.686) + (c.getBlue * .168)).toInt
    val b = ((c.getRed * .272) + (c.getGreen *.534) + (c.getBlue * .131)).toInt
    new Color(r,g,b)
  }

  def mapColourEffect(f:Color => Color, qt:QTree[Coords]):QTree[Coords] = {
    qt match {
      case QEmpty=> QEmpty
      case QLeaf((value,color:Color)) =>{
        QLeaf((value,f(color)))
      }
      case QNode(value,one,two,three,four) => {
        QNode(value,mapColourEffect(f,one),mapColourEffect(f,two),mapColourEffect(f,three),mapColourEffect(f,four))
      }
    }
  }

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

//  println(s"Maximum element of the tree: ${ Example.maximum(tree1)}")
//  println(s"Depth of the tree: ${ Example.depth(tree1)}")
//  println(s"Map: ${ Example.map(tree1)((x:Int)=>2*x)}")
