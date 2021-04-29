package package1

import package1.Manipulation.Coords

import java.awt.Color

case class Example[A](myField: QTree[Coords]){
  def makeBitMap(): BitMap = Manipulation.makeBitMap(this.myField)
  def scale(d:Double):QTree[Coords] = Manipulation.scale(d,this.myField)
  def mirrorV():QTree[Coords] = Manipulation.mirrorV(this.myField)
  def mirrorH():QTree[Coords] = Manipulation.mirrorH(this.myField)
  def rotateL():QTree[Coords] = Manipulation.rotateL(this.myField)
  def rotateR():QTree[Coords] = Manipulation.rotateR(this.myField)
 // def mapColourEffectNoise():QTree[Coords] = Manipulation.mapColourEffectNoise(Manipulation.noise,this.myField)
  def mapColourEffectContrast():QTree[Coords] = Manipulation.mapColourEffect(Manipulation.contrast,this.myField)
  def mapColourEffectSepia():QTree[Coords] = Manipulation.mapColourEffect(Manipulation.sepia,this.myField)
}

object Manipulation{

  type Point = (Int , Int)
  type Coords = (Point, Point)
  type Section = (Coords, Color)

  def generateBitMapFromImage(s:String) : BitMap ={
    val image : Array[Array[Int]] = ImageUtil.readColorImage(s)
    new BitMap(image.map(_.toList).toList)
  }

  /* def makeQTree[A](b: BitMap):QTree[Coords]={
    val ySize = b.getListOfList().length
    val xSize = b.getListOfList().head.length

    def aux(xS:Int,yS:Int):QTree[Coords]={

    }

  }   */

  def qTreeSize(qt:QTree[Coords]):(Int,Int)={
    qt match {
      case QEmpty => (0,0)
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)), c: Color)) => (x2+1,y2+1)
      case QNode(((x1: Int, y1: Int), (x2: Int, y2: Int)), one, two, three, four) => (x2+1,y2+1)
    }
  }


  def makeBitMap(qt:QTree[Coords]):BitMap={
   val list: Array[Array[Int]] = Array.ofDim[Int](qTreeSize(qt)._1,qTreeSize(qt)._2)
    def makeArray(qt2:QTree[Coords]):Unit={
      qt2 match {
        case QEmpty => Nil
        case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),c: Color)) =>{

          def aux1( cord:Coords): Unit = {
            def aux2( cord:Coords): Unit = {
              if(cord._1._2<=y2){
                list(cord._1._2)(cord._1._1) = ImageUtil.encodeRgb(c.getRed,c.getGreen,c.getBlue)
                aux2(((cord._1._1,cord._1._2+1),(x2,y2)))
              }else{
                aux1(((cord._1._1+1,y1),(x2,y2)))
              }
            }
            if(cord._1._1 <= x2) aux2(((cord._1._1,cord._1._2),(x2,y2)))
          }
          aux1(((x1,y1),(x2,y2)))
        }
        case QNode(value, one, two, three, four) =>{
          makeArray(one)
          makeArray(two)
          makeArray(three)
          makeArray(four)
        }
      }
    }
    makeArray(qt)
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

  def cords(qt:QTree[Coords]):Coords={
    qt match{
      case QEmpty => ((0,0),(0,0))
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) => ((x1: Int, y1: Int), (x2: Int, y2: Int))
      case QNode(value,one,two,three,four) => value
    }
  }

  def tree(qt:QTree[Coords],i:Int):QTree[Coords]={
    qt match{
      case QEmpty => QEmpty
      case QLeaf((value,color: Color)) => QLeaf(value,color)
      case QNode(value,one,two,three,four) => {
        i match{
          case 1 => one
          case 2 => two
          case 3 => three
          case 4 => four
        }
      }
    }
  }

  def newQTree(qt:QTree[Coords], qt2:QTree[Coords]):QTree[Coords]={
    qt match{
      case QEmpty => QEmpty
      case QLeaf((value,color: Color)) => QLeaf(cords(qt2),color)
      case QNode(value,one,two,three,four) => {
        QNode(cords(qt2),newQTree(two,tree(qt2,1)),newQTree(one,tree(qt2,2)),newQTree(four,tree(qt2,3)),newQTree(three,tree(qt2,4)))
        //QNode(cords(qt2),one,two,three,four)
      }
    }
  }

   def mirrorV(qt :QTree[Coords]):QTree[Coords]={
     qt match {
       case QEmpty=> QEmpty
       case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
         QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))
       case QNode(value,one,two,three,four) => {
         val newOne = newQTree(two,one)
         val newTwo = newQTree(one,two)
         val newThree = newQTree(four,three)
         val newFour = newQTree(three,four)
         QNode(value, mirrorV(newOne), mirrorV(newTwo), mirrorV(newThree), mirrorV(newFour))
       }

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

  def noise(c:Color, i:Int):Color={
    if(i == 0){
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
    new Color(min(max(r,0),255),min(max(g,0),255),min(max(b,0),255))
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
  /*def mapColourEffectNoise(f:(Color,RandomWithState) =>(Color,RandomWithState) , qt:QTree[Coords], r:RandomWithState):QTree[Coords] = {
    qt match {
      case QEmpty=> QEmpty
      case QLeaf((value,color:Color)) =>
        QLeaf((value,f(color)._1))

      case QNode(value,one,two,three,four) =>
        val random = rand(r)
        QNode(value,mapColourEffectNoise(f,one,random._2),mapColourEffectNoise(f,two,random._2),mapColourEffectNoise(f,three,random._2),mapColourEffectNoise(f,four,random._2))

    }
  }*/

 /* def mapColourEffectNoise(f:(Color,Int) => Color, qt:QTree[Coords]):QTree[Coords] = {
    val r = MyRandom(2)
    val list = makeBitMap(qt).getListOfList()
    def aux(f:(Color,Int) => Color, l:List[List[Int]],random:RandomWithState): List[List[Int]] = {
      l match {
        case Nil => List()
        case x::xs =>{
          val ra= rand(random)
          (x map (z => { val v1 = ImageUtil.decodeRgb(z).toList
            val c1: Color = f(new Color(v1(0),v1(1),v1(2)),ra._1)
            ImageUtil.encodeRgb(c1.getRed,c1.getGreen,c1.getBlue)} )) :: aux(f,xs,ra._2)
        }
      }
    }
    val newList = aux(noise,list,r)
    val bit = new BitMap(newList)
    makeQTree(bit)
  } */

}


