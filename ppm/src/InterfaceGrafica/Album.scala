package InterfaceGrafica

import javafx.beans.property.{SimpleStringProperty, StringProperty}
import package1.BitMap

case class Album(name: String, bitMap: BitMap){
  def nameProperty : StringProperty = new SimpleStringProperty(this.name)
  def bitMapPrperty : BitMap = this.bitMap
}
