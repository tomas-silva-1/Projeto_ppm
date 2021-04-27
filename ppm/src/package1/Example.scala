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
  def rotateR()=Example.rotateR(this.myField)
}

object Example{

  val r = MyRandom(2)
  type Point = (Int , Int)
  type Coords = (Point, Point)
  type Section = (Coords, Color)

   def makeQTree[A](b: BitMap):QTree[A]={

  }

  def paint(p: Point, color: Color, list:List[List[Int]]): List[List[Int]] = {
    /*list match {
      case Nil => Nil
      case y::yx =>
    }
    list(p._1)(p._2) = ImageUtil.encodeRgb(color.getBlue,color.getGreen,color.getRed)*/
  }

  def makeBitMap[A](qt:QTree[A]):BitMap={
    val list: List[List[Int]] =
    qt match {
      case QEmpty => Nil
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>{

        def aux1(list:List[List[Int]], cord:Coords): List[List[Int]] = {
          def aux2(list:List[List[Int]], cord:Coords): List[List[Int]] = {

          }
        }

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
    new BitMap(list)
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
       case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
         QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))

       case QNode(value,one,two,three,four) =>
         QNode(value,mirrorV(two),mirrorV(one), mirrorV(four), mirrorV(three))
     }
   }
   def mirrorH(qt :QTree[Coords]):QTree[Coords]={
     qt match {
       case QEmpty=> QEmpty
       case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
         QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))

       case QNode(value,one,two,three,four) =>
         QNode(value,mirrorH(three),mirrorH(four), mirrorH(one), mirrorH(two))
     }
   }
  def rotateL(qt :QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty=> QEmpty
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
        QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))

      case QNode(value,one,two,three,four) =>
        QNode(value,rotateL(two),rotateL(four), rotateL(one), rotateL(three))
    }
  }

  def rotateR(qt :QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty=> QEmpty
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
        QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))

      case QNode(value,one,two,three,four) =>
        QNode(value,rotateL(two),rotateL(four), rotateL(one), rotateL(three))
    }
  }

  def rand(random: RandomWithState): (Int,RandomWithState) ={
    val i = random.nextInt(2)
      i._1 match{
        case 0 => (0,i._2)
        case 1 => (1,i._2)
      }
  }

  def noise(c:Color):Color={
    val random = rand(r)
    if(random._1 == 0){
      new Color(max(c.getRed - 100,0),max(c.getGreen - 100,0),max(c.getBlue - 100,0))
    }else{
      c
    }
  }

  def max(x:Int,y:Int): Int = { if(x>=y) x else y}
  def min(x:Int,y:Int): Int = { if(x<=y) x else y}

  def contrast(c:Color):Color={
    val l = ImageUtil.luminance(c.getRed,c.getGreen,c.getBlue)
    if(l > 127){
      new Color(max(c.getRed - 100,0),max(c.getGreen - 100,0),max(c.getBlue - 100,0))
    }else{
      new Color(min(c.getRed + 100,255),min(c.getGreen + 100,255),min(c.getBlue + 100,255))
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
      case QLeaf((value,color:Color)) =>
        QLeaf((value,f(color)))

      case QNode(value,one,two,three,four) =>
        QNode(value,mapColourEffect(f,one),mapColourEffect(f,two),mapColourEffect(f,three),mapColourEffect(f,four))

    }
  }

  def mapColourEffectNoise(f:Color => Color,qt:QTree[Coords]) = mapColourEffect(noise,qt)
  def mapColourEffectContrast(f:Color => Color, qt:QTree[Coords]) = mapColourEffect(contrast,qt)
  def mapColourEffectSepia(f:Color => Color, qt:QTree[Coords]) = mapColourEffect(sepia,qt)

  /*def change(f:Color => Color, list:List[Int]): List[Int]= {
    list map (k => { val v1 = ImageUtil.decodeRgb(k).toList
      val c1: Color = f(new Color(v1(0),v1(1),v1(2)))
      ImageUtil.encodeRgb(c1.getRed,c1.getGreen,c1.getBlue)} )
  }

  def mapColourEffect2(f:Color => Color, qt:QTree[Coords]):QTree[Coords] = {
    val bit = makeBitMap(qt)
    def aux(f:Color => Color, l:List[List[Int]]):List[List[Int]] = {
      l match {
        case Nil => List()
        case x::xs => (change(f,x) ++ aux(f,xs))
      }
    }
    val newList = aux(f,list)
    val bit = new BitMap(newList)
    makeQTree(bit)
  }*/

}


