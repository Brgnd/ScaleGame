

package scalegame

import scala.math._


class Scale(radius: Int, loc: Location) extends Item {
  val slots: Vector[ScaleLocation] = makeSlots(radius)
  private var weight: Int = 0 //total weight of the scale
  private var unbalance: Int = 0 //how much the scale is unbalanced, negative for left and positive for right side
  


  def makeSlots(rad: Int): Vector[ScaleLocation] = { // function to initialize the places for putting on weights and new scales
    var locArray = new Array[ScaleLocation](2*rad)
    for (i <- 0 until rad * 2) {
      locArray(i) = new ScaleLocation(this)
    }
    locArray.toVector
  }
  
  def checkTip():Boolean = abs(unbalance) > radius //checking if the scale is unbalanced
  
  def tipping():Unit = {
    slots.map(_.getItem).foreach(_.resetAmount)
    weight = 0
    unbalance = 0
    if (!loc.isBase) loc.getScaleAttachedTo
  }
}