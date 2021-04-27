package package1


case class BitMap(matrix: List[List[Int]])

object BitMap{

  def getListOfList(s:String):List[List[Int]] = {
    val image : Array[Array[Int]] = ImageUtil.readColorImage(s)
    image.map(_.toList).toList
  }

  def generateBitMapFromImage(s:String) : BitMap ={
    val image : Array[Array[Int]] = ImageUtil.readColorImage(s)
    new BitMap(image.map(_.toList).toList)
  }

}
