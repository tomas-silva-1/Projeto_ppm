package package1


case class BitMap(matrix: List[List[Int]]){

  def getRGB(valor:Int) : Array[Int] = ImageUtil.decodeRgb(valor)
}

object BitMap{

 type rgb =(Int,Int,Int)

/* def generateBitMapFromImage(s:String) : BitMap ={
   val image : Array[Array[Int]] = ImageUtil.readColorImage(s)   //Array[Array[Int]]

 }
 def arrayToList(image: Array[Array[Int]] ): Unit ={
   image match {
     case Nil => Nil
     case   =>{

   }
   }
 } */

}
