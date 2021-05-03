package package1

import package1.Manipulation.Coords
import qtrees.{QEmpty, QLeaf, QNode, QTree}
import random.{MyRandom, RandomWithState}

import java.awt.Color

case class Manipulation[A](myField: QTree[Coords]){
  def makeQTree(b: BitMap): QTree[Coords]= Manipulation.makeQTree(b)
  def makeBitMap(): BitMap = Manipulation.makeBitMap(this.myField)
  def scale(d:Double):QTree[Coords] = Manipulation.scale(d,this.myField)
  def mirrorV():QTree[Coords] = Manipulation.mirrorV(this.myField)
  def mirrorH():QTree[Coords] = Manipulation.mirrorH(this.myField)
  def rotateL():QTree[Coords] = Manipulation.rotateL(this.myField)
  def rotateR():QTree[Coords] = Manipulation.rotateR(this.myField)
  def mapColourEffectNoise():QTree[Coords] = Manipulation.mapColourEffectNoise(Manipulation.noise,this.myField)
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

  def isLeaf(map: BitMap, c: Coords):(Boolean,Int)={
    def aux1( cord:Coords,i:Int): (Boolean,Int) = {
      def aux2( cord:Coords,i:Int): (Boolean,Int) = {
        if(cord._1._2<=c._2._2){
          if(map.getListOfList()(cord._1._2)(cord._1._1) != i) {
            (false, i)
          }else{
            aux2(((cord._1._1,cord._1._2+1),(c._2._1,c._2._2)),i)
          }
        }else{
          aux1(((cord._1._1+1,c._1._2),(c._2._1,c._2._2)),i)
        }
      }
      if(cord._1._1 <= c._2._1) {
        aux2(((cord._1._1, cord._1._2), (c._2._1, c._2._2)),i)
      }else{
        (true, i)
      }
    }
    aux1(((c._1._1,c._1._2),(c._2._1,c._2._2)),map.getListOfList()(c._1._2)(c._1._1))
  }

  def makeColor(i:Int):Color={
    val list = ImageUtil.decodeRgb(i)
    new Color(list(0),list(1),list(2))
  }

  def makeQTree[A](b: BitMap):QTree[Coords]={
    val ySize = b.getListOfList().length
    val xSize = b.getListOfList().head.length
    val c:Coords = ((0,0),(xSize-1,ySize-1))

    def aux(c2:Coords):QTree[Coords]={
      val cOne = ((c2._1._1,c2._1._2),((((c2._1._1.toDouble+c2._2._1.toDouble)/2.toDouble) -0.5).toInt,(((c2._1._2.toDouble+c2._2._2.toDouble)/2.toDouble) -0.5).toInt))
      val cTwo = ((cOne._2._1+1,c2._1._2),(c2._2._1,cOne._2._2))
      val cThree = ((c2._1._1,cOne._2._2 + 1),(cOne._2._1,c2._2._2))
      val cFour = (((((c2._1._1.toDouble+c2._2._1.toDouble)/2.toDouble) +0.5).toInt,(((c2._1._2.toDouble+c2._2._2.toDouble)/2.toDouble) +0.5).toInt),(c2._2._1,c2._2._2))
      if(isLeaf(b, c2)._1){
        QLeaf(c2,makeColor(isLeaf(b,c2)._2))
      }else{
        QNode(c2,aux(cOne),aux(cTwo),aux(cThree),aux(cFour))
      }
    }
    aux(c)
  }

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
        qtrees.QNode(newCoords,scale(s,one),scale(s,two), scale(s,three), scale(s,four))
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

  def newQTree(qt:QTree[Coords],c2:Coords):QTree[Coords]={
    qt match{
      case QEmpty => QEmpty
      case QLeaf((value,color: Color)) => QLeaf(c2,color)
      case QNode(value,one,two,three,four) => {
        val cOne = ((c2._1._1,c2._1._2),((((c2._1._1.toDouble+c2._2._1.toDouble)/2.toDouble) -0.5).toInt,(((c2._1._2.toDouble+c2._2._2.toDouble)/2.toDouble) -0.5).toInt))
        val cTwo = ((cOne._2._1+1,c2._1._2),(c2._2._1,cOne._2._2))
        val cThree = ((c2._1._1,cOne._2._2 + 1),(cOne._2._1,c2._2._2))
        val cFour = (((((c2._1._1.toDouble+c2._2._1.toDouble)/2.toDouble) +0.5).toInt,(((c2._1._2.toDouble+c2._2._2.toDouble)/2.toDouble) +0.5).toInt),(c2._2._1,c2._2._2))
        QNode(c2,newQTree(one,cOne),newQTree(two,cTwo),newQTree(three,cThree),newQTree(four,cFour))
      }
    }
  }

   def mirrorV(qt :QTree[Coords]):QTree[Coords]={
     qt match {
       case QEmpty=> QEmpty
       case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
         QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))
       case QNode(value,one,two,three,four) => {
         val newOne = newQTree(two,cords(one))
         val newTwo = newQTree(one,cords(two))
         val newThree = newQTree(four,cords(three))
         val newFour = newQTree(three,cords(four))
         QNode(value, mirrorV(newOne), mirrorV(newTwo), mirrorV(newThree), mirrorV(newFour))
       }

     }
   }
   def mirrorH(qt :QTree[Coords]):QTree[Coords]={
     qt match {
       case QEmpty=> QEmpty
       case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
         QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))

       case QNode(value,one,two,three,four) =>{
         val newOne = newQTree(three,cords(one))
         val newTwo = newQTree(four,cords(two))
         val newThree = newQTree(one,cords(three))
         val newFour = newQTree(two,cords(four))
         QNode(value,mirrorH(newOne),mirrorH(newTwo), mirrorH(newThree), mirrorH(newFour))
       }
     }
   }
  def rotateL(qt :QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty=> QEmpty
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
        QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))

      case QNode(value,one,two,three,four) =>{
        val newOne = newQTree(two,cords(one))
        val newTwo = newQTree(four,cords(two))
        val newThree = newQTree(one,cords(three))
        val newFour = newQTree(three,cords(four))
        QNode(value,rotateL(newOne),rotateL(newTwo), rotateL(newThree), rotateL(newFour))
      }
    }
  }

  def rotateR(qt :QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty=> QEmpty
      case QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color)) =>
        QLeaf((((x1: Int, y1: Int), (x2: Int, y2: Int)),color: Color))

      case QNode(value,one,two,three,four) =>{
        val newOne = newQTree(three,cords(one))
        val newTwo = newQTree(one,cords(two))
        val newThree = newQTree(four,cords(three))
        val newFour = newQTree(two,cords(four))
        QNode(value,rotateR(newOne),rotateR(newTwo), rotateR(newThree), rotateR(newFour))
      }
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
        qtrees.QNode(value,mapColourEffect(f,one),mapColourEffect(f,two),mapColourEffect(f,three),mapColourEffect(f,four))

    }
  }
  def noise(c:Color, i:Int):Color={
    if(i == 0){
      new Color(max(c.getRed - 100,0),max(c.getGreen - 100,0),max(c.getBlue - 100,0))
    }else{
      c
    }
  }
  def rand(random: RandomWithState): (Int,RandomWithState) ={
    val i = random.nextInt(2)
    i._1 match{
      case 0 => (0,i._2)
      case 1 => (1,i._2)
    }
  }
  def mapColourEffectNoise(f:(Color,Int) => Color, qt:QTree[Coords]):QTree[Coords] = {
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
  }

}


