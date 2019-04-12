

package scaleGame

import scala.math._


class Scale(radius: Int, loc: Location) extends Item {
//  val slots: Vector[Location] = makeSlots(radius)
  private var weight: Int = 0 //total weight of the scale
  private var unbalance: Int = 0 //how much the scale is unbalanced, negative for left and positive for right side
  
  def getRadius = radius
  
  def getItem(num: Int): Item = slots(num + radius).getItem.get
  
  
  // function to initialize the places for putting on weights and new scales
//  def makeSlots(rad: Int): Vector[Location] = { 
//    var locArray = new Array[Location](2*rad + 1)
//    for (i <- 0 to rad * 2) {
//      locArray(i) = new Location(Option(this)) //the middle slot will always be "empty", it just exists for making it
//                                           // to manipulate the slots
//    }
//    locArray.toVector
//  }
  
  def checkTip():Boolean = abs(unbalance) > radius //checking if the scale is unbalanced
  
  
   // calculates the unbalance of the scale, the middle ones index will be zero so it will never affect the balance
  def updateBalance() =  unbalance = slots.map(_.getItem).map(_.getWeight).zipWithIndex.foldLeft(0)
                                              { (total, x) => total + (x._1 * (x._2 - radius)) } 
  def changeAmount(blah: Int) = ???
  def changeOwner(player: Player) = ???
  //returns current weight
  def getWeight = weight
  
  //aka tipping. resets the weight amount on all of the slots and also resets
   // the slots on the scales in the slots
   // get all the items that are weights on the scale and reset their amount to zero.
 
  def resetWeight():Unit = { 
    slots.map(_.getItem).foreach(_.resetWeight) // ADD SCORE RESET!!!!!!!!
    
    weight = 0
    unbalance = 0
    if (!loc.isBase) {
      val underScale = loc.getScaleAttachedTo.get
      underScale.updateBalance
      if(underScale.checkTip) underScale.resetWeight()
    }
  }
  
  // adds a single weight if the slot is a weight, if it's a scale, does nothing.
  // (maybe will add error or smthing else in case of scale)
  // slot will be the spot away from the centre, e.g. -3 means three to the 
  //left of the centre
  def addWeight(slot: Int, player:Player): Int = { 
    val item = slots(slot + radius).getItem 
    if (item.isInstanceOf[Weight]) { 
      item.changeAmount(1)
      unbalance += slot
      if (this.checkTip) {
        this.resetWeight()
        0
      }
      else {
        item.changeOwner(player)
        slot
      }
    }
    0
  }
  
  
  
  // changes item on the slot to a scale. Unreversable action, should only be used while initializing the game board
  // (will be changed if there is need for the players to put up scales)
  def changeSlotToScale(slot: Int, radius:Int) = {
    slots(slot + radius).itemToScale(radius)
  }
  
}