package package1


case class ImagesAlbum( list : List[(String,BitMap)] ){
  def getlist():List[(String,BitMap)] = this.list
  def add(st:String,bit:BitMap):List[(String,BitMap)] = ImagesAlbum.add(st,bit,this.list)
  def remove(st:String):List[(String,BitMap)]= ImagesAlbum.remove(st,this.list)
  def get(st:String):(String,BitMap) = ImagesAlbum.get(st,this.list)
  def editBitMap(st:String,bit:BitMap) :List[(String,BitMap)] = ImagesAlbum.editBitMap(st,bit,this.list)
  def editName(st:String, newSt:String):List[(String,BitMap)] = ImagesAlbum.editName(st,newSt,this.list)
  def changePositions(st:String,st2:String):List[(String,BitMap)] = ImagesAlbum.changePositions(st,st2,this.list)
}

object ImagesAlbum{

  def add(st:String,bit:BitMap,ab:List[(String,BitMap)]):List[(String,BitMap)]={
    val tuple = (st,bit)
    ab match{
      case Nil => List(tuple)
      case x::xs => x :: add(st,bit,xs)
    }
  }

  def remove(st:String,ab:List[(String,BitMap)]): List[(String,BitMap)] ={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) xs else x :: remove(st,xs)
    }
  }

  def get(st:String,ab:List[(String,BitMap)]): (String,BitMap) ={
    ab match{
      case Nil => (st,new BitMap(List()))
      case x::xs => if( x._1.equalsIgnoreCase(st) ) x else get(st,xs)
    }
  }

  def editBitMap(st:String,bit:BitMap,ab:List[(String,BitMap)]):List[(String,BitMap)]={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (st,bit) :: xs else x :: editBitMap(st,bit,xs)
    }
  }

  def editName(st:String, newSt:String,ab:List[(String,BitMap)]):List[(String,BitMap)]={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (newSt,x._2) :: xs else x :: editName(st,newSt,xs)
    }
  }

  def changePositions(st:String,st2:String,ab:List[(String,BitMap)]):List[(String,BitMap)]={
    val tuple1= get(st,ab)
    val tuple2= get(st2,ab)
    ab match{
      case Nil => Nil
      case x::xs => if(x._1.equalsIgnoreCase(st)) tuple2 :: xs else if(x._1.equalsIgnoreCase(st2)) tuple1 :: xs else x :: changePositions(st,st2,xs)
    }
  }

}



