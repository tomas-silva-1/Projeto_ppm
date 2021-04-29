package package1

import package1.Functions.Album

case class ImagesAlbum( list: Album ){
  def add(st:String,bit:BitMap):Album = Functions.add(st,bit,this.list)
  def remove(st:String):Album = Functions.remove(st,this.list)
  def get(st:String):(String,BitMap) = Functions.get(st,this.list)
  def editBitMap(st:String,bit:BitMap) :Album = Functions.editBitMap(st,bit,this.list)
  def editName(st:String, newSt:String):Album = Functions.editName(st,newSt,this.list)
  def changePositions(st:String,st2:String):Album = Functions.changePositions(st,st2,this.list)
}

object Functions{

  type Album = List[(String,BitMap)]

  def add(st:String,bit:BitMap,ab:Album):Album={
    val tuple = (st,bit)
    ab match{
      case Nil => List(tuple)
      case x::xs => x :: add(st,bit,xs)
    }
  }

  def remove(st:String,ab:Album): Album ={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) xs else x :: remove(st,xs)
    }
  }

  def get(st:String,ab:Album): (String,BitMap) ={
    ab match{
      case Nil => (st,new BitMap(List()))
      case x::xs => if( x._1.equalsIgnoreCase(st) ) x else get(st,xs)
    }
  }

  def editBitMap(st:String,bit:BitMap,ab:Album):Album={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (st,bit) :: xs else x :: editBitMap(st,bit,xs)
    }
  }

  def editName(st:String, newSt:String,ab:Album):Album={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (newSt,x._2) :: xs else x :: editName(st,newSt,xs)
    }
  }

  def changePositions(st:String,st2:String,ab:Album):Album={
    val tuple1= get(st,ab)
    val tuple2= get(st2,ab)
    ab match{
      case Nil => Nil
      case x::xs => if(x._1.equalsIgnoreCase(st)) tuple2 :: xs else if(x._1.equalsIgnoreCase(st2)) tuple1 :: xs else x :: changePositions(st,st2,xs)
    }
  }

}



