

package scaleGame

import scala.math._


class Scale(radius: Int, loc: Location) extends Item {
  private var slots: Vector[Location] = null
  private var weight: Int = 0 //total weight of the scale
  private var unbalance: Int = 0 //how much the scale is unbalanced, negative for left and positive for right side
  
  def getRadius = radius
  
  def getLoc: Location = loc
  
//  def getItem(num: Int): Item = slots(num + radius).getItem.get
  
  
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
  
  
   // calculates the unbalance of the scale.
  def updateBalance() =  unbalance = slots.map(_.getItem).map(_.get.getWeight).zipWithIndex.foldLeft(0)
                                         { (total, x) => total + (x._1 * (x._2 - radius)) } 
  def changeAmount(blah: Int) = ???
  def changeOwner(player: Player) = ???
  //returns current weight
  def getWeight = weight
  
  //aka tipping. resets the weight amount on all of the slots and also resets
   // the slots on the scales in the slots
   // get all the items that are weights on the scale and reset their amount to zero.
 
  def resetWeight():Unit = {
    slots.map(_.getItem).foreach(_.get.resetWeight) // ADD SCORE RESET!!!!!!!! (Do a score calculator in a different place)
    
    weight = 0
    unbalance = 0
  }
//  
  // adds a single weight if the slot is a weight, if it's a scale, does nothing.
  // (maybe will add error or smthing else in case of scale)
  // slot will be the spot away from the centre, e.g. -3 means three to the 
  //left of the centre
  def addWeight(change: Int): Boolean = {
    unbalance += change
    weight += 1
    if(this.checkTip) {
      this.resetWeight
      true
    }
    else false
  }
  def addSlots(newSlots: Vector[Location]) = slots = newSlots
  
  
  // changes item on the slot to a scale. Unreversable action, should only be used while initializing the game board
  // (will be changed if there is need for the players to put up scales)
//  def changeSlotToScale(slot: Int, radius:Int) = {
//    slots(slot + radius).itemToScale(radius)
//  }
  
}