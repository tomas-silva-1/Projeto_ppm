package package1


case class BitMap(matrix: List[List[Int]]){
  def getListOfList():List[List[Int]] = this.matrix
  def generateImageFromBitMap(str:String):Unit = BitMap.generateImageFromBitMap(this:BitMap,str)
}

object BitMap{

  def generateBitMapFromImage(s:String) : BitMap ={
    val image : Array[Array[Int]] = ImageUtil.readColorImage(s)
    new BitMap(image.map(_.toList).toList)
  }

  def generateImageFromBitMap(bit:BitMap,str:String):Unit={
    val image : Array[Array[Int]] = bit.getListOfList().map(_.toArray).toArray
    ImageUtil.writeImage(image,str,"png")
  }

}
