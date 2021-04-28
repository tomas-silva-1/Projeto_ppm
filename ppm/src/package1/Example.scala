package package1

import package1.Example.Coords

import java.awt.Color

case class Example[A](myField: QTree[Coords]){
  def makeBitMap(): BitMap = Example.makeBitMap(this.myField)
  def scale(d:Double):QTree[Coords] = Example.scale(d,this.myField)
  def mirrorV():QTree[Coords] = Example.mirrorV(this.myField)
  def mirrorH():QTree[Coords] = Example.mirrorH(this.myField)
  def rotateL():QTree[Coords] = Example.rotateL(this.myField)
  def rotateR():QTree[Coords] = Example.rotateR(this.myField)
  def mapColourEffectNoise():QTree[Coords] = Example.mapColourEffect(Example.noise,this.myField)
  def mapColourEffectContrast():QTree[Coords] = Example.mapColourEffect(Example.contrast,this.myField)
  def mapColourEffectSepia():QTree[Coords] = Example.mapColourEffect(Example.sepia,this.myField)
}

object Example{

  val r = MyRandom(2)
  type Point = (Int , Int)
  type Coords = (Point, Point)
  type Section = (Coords, Color)

   /*def makeQTree[A](b: BitMap):QTree[Coords]={
     val ySize = b.getListOfList().length
     val xSize = b.getListOfList().head.length

     def aux(xS:Int,yS:Int):QTree[Coords]={

     }

  }*/

  def makeBitMap[A](qt:QTree[A]):BitMap={
    val list: Array[Array[Int]] = Array.ofDim[Int](2,2)
    qt match {
      case QEmpty => Nil
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>{

        def aux1(list:Array[Array[Int]], cord:Coords, k:Int, c:Color): Unit = {
          def aux2(list:Array[Array[Int]], cord:Coords, k:Int, c:Color): Unit = {
              if(cord._1._1<=cord._2._1){
                list(cord._1._1)(cord._1._2) = ImageUtil.encodeRgb(c.getBlue,c.getGreen,c.getRed)
                aux2(list, ((cord._1._1+1,cord._1._2),(cord._2._1,cord._2._2)),k,c)
              }else{
                aux1(list,((k,cord._1._2+1),(cord._2._1,cord._2._2)),k,c)
              }
          }
          if(cord._1._2 <= cord._2._2) aux2(list,((cord._1._1,cord._1._2),(cord._2._1,cord._2._2)),k,c)
        }
        aux1(list,((x1,y1),(x2,y2)),x1,color)
      }
      case QNode(value, one, two, three, four) =>{
        makeBitMap(one)
        makeBitMap(two)
        makeBitMap(three)
        makeBitMap(four)
      }

    }
    new BitMap(list.map(_.toList).toList)
  }

  def multiplier(c:Coords, s:Double): Coords = {
    if(s >= 1) {
      val px: Point = ((c._1._1 * s).toInt, (c._1._2 * s).toInt)
      val py: Point = ((c._2._1 * s + (s - 1)).toInt, (c._2._2 * s + (s - 1)).toInt)
      (px, py)
    }else{
      val px: Point = ((c._1._1 * s).toInt, (c._1._2 * s).toInt)
      val py: Point = ((c._2._1 - (s - 1)).toInt, (c._2._2 - (s - 1)).toInt)
      (px, py)
    }
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
         QNode(value, mirrorV(two), mirrorV(one), mirrorV(four), mirrorV(three))

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


