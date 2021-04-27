package package1

import package1.Example.Coords

//case class Album( list: List[(String,BitMap,QTree[Coords])] )

object Album{

  type Album = List[(String,BitMap,QTree[Coords])]

  def add(st:String,bit:BitMap,qt:QTree[Coords])(implicit ab:Album):Album={
    val tuple = (st,bit,qt)
    ab match{
      case Nil => List(tuple)
      case x::xs => x :: add(st,bit,qt)(xs)
    }
  }

  def remove(st:String)(implicit ab:Album): Album ={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) xs else x :: remove(st)(xs)
    }
  }

  def get(st:String)(implicit ab:Album): (String,BitMap,QTree[Coords]) ={
    ab match{
      case Nil => (st,new BitMap(List()),QEmpty)
      case x::xs => if( x._1.equalsIgnoreCase(st) ) x else get(st)(xs)
    }
  }

  def editBitMap(st:String,bit:BitMap)(implicit ab:Album):Album={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (st,bit,Example.makeQTree(bit)) :: xs else x :: editBitMap(st,bit)(xs)
    }
  }

  def editQTree(st:String,qt:QTree[Coords])(implicit ab:Album):Album={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (st,Example.makeBitMap(qt),qt) :: xs else x :: editQTree(st,qt)(xs)
    }
  }

  def editName(st:String, newSt:String)(implicit ab:Album):Album={
    ab match{
      case Nil => Nil
      case x::xs => if( x._1.equalsIgnoreCase(st) ) (newSt,x._2,x._3) :: xs else x :: editName(st,newSt)(xs)
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



