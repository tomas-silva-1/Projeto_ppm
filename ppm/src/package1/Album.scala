package package1

import package1.Example.Coords

//case class Album( list: List[(String,BitMap,QTree[Coords])] )

object Album{

  type Album = List[(String,BitMap)]

  def add(st:String,bit:BitMap)(implicit ab:Album):Album={
    val tuple = (st,bit)
    ab match{
      case Nil => List(tuple)
      case x::xs => x :: add(st,bit)(xs)
    }
  }

  def remove(st:String)(implicit ab:Album): Album ={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) xs else x :: remove(st)(xs)
    }
  }

  def get(st:String)(implicit ab:Album): (String,BitMap) ={
    ab match{
      case Nil => (st,new BitMap(List()))
      case x::xs => if( x._1.equalsIgnoreCase(st) ) x else get(st)(xs)
    }
  }

  def editBitMap(st:String,bit:BitMap)(implicit ab:Album):Album={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (st,bit) :: xs else x :: editBitMap(st,bit)(xs)
    }
  }

  def editName(st:String, newSt:String)(implicit ab:Album):Album={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (newSt,x._2) :: xs else x :: editName(st,newSt)(xs)
    }
  }

  def changePositions(st:String,st2:String)(implicit ab:Album):Album={
    val tuple1= get(st)
    val tuple2= get(st2)
    ab match{
      case Nil => Nil
      case x::xs => if(x._1.equalsIgnoreCase(st)) tuple2 :: xs else if(x._1.equalsIgnoreCase(st2)) tuple1 :: xs else x :: changePositions(st,st2)(xs)
    }
  }

}



