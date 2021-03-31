import java.awt.Color

type Point = (Int, Int)
type Coords = (Point, Point)
type Section = (Coords, Color)
trait QTree[+A]
case class QNode[A](value: A, one: QTree[A], two: QTree[A], three: QTree[A], four: QTree[A]) extends QTree[A]
case class QLeaf[A, B](value: B) extends QTree[A]
case object QEmpty extends QTree[Nothing]


//Exemplo de QTree[Coords]

val l1: QLeaf[Coords, Section] = QLeaf((((0,0):Point,(0,0):Point):Coords, Color.red):Section)
val l2: QLeaf[Coords, Section] = QLeaf((((1,0):Point,(1,0):Point):Coords, Color.blue):Section)
val l3: QLeaf[Coords, Section] = QLeaf((((0,1):Point,(0,1):Point):Coords, Color.yellow):Section)
val l4: QLeaf[Coords, Section] = QLeaf((((1,1):Point,(1,1):Point):Coords, Color.green):Section)

val qt: QTree[Coords] = QNode(((0,0),(1,1)), l1, l2, l3, l4)

def makeQTree(b: BitMap ):QTree[A]={
  b match {
    case
  }
}


def scale(scale:Double, qt:QTree):QTree[A]={

}