

package scaleGame

import scala.math.abs


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
  
  
  //gives the score of the given player. Recursive function, if the item is scale, it will count the score of the 
  //scales weight and multiply it by the slot
  def giveScore(player: Player): Int = {
    val sum = slots.map(_.getItem).zipWithIndex.foldLeft(0)((total, x) => total + (x._1 match {
      case Some(weight: Weight) => weight.giveScore(player)
      case Some(scale: Scale) => scale.giveScore(player) * (abs(x._2 - radius)) 
      case _ => 0
    }))
    sum
  }
   // calculates the unbalance of the scale.
  def updateBalance() = {
    val slotsWeights = slots.map(_.getItem).map(_ match {
      case Some(item: Item) => item.getWeight
      case _ => 0
    })
    unbalance = slotsWeights.zipWithIndex.foldLeft(0) { (total, x) => total + (x._1 * (x._2 - radius)) }
    weight = slotsWeights.foldLeft(0)(_ + _)
  }
                                          
  def changeAmount(blah: Int) = ???
  def changeOwner(player: Player) = ???
  //returns current weight
  def getWeight = weight
  
  //aka tipping. resets the weight amount on all of the slots and also resets
   // the slots on the scales in the slots
   // get all the items that are weights on the scale and reset their amount to zero.
 
  def resetWeight():Unit = {
    slots.map(_.getItem).foreach(_ match {
      case Some(item: Item) => item.resetWeight
      case None => // ADD SCORE RESET!!!!!!!! (Do a score calculator in a different place)
    })
    
    weight = 0
    unbalance = 0
  }

  // adds weight according to the weight added via the ScaleGame class. This only handles the the scale calculations
  // weight calculations are done 
  def addWeight(change: Int): Boolean = {
    unbalance += change
    weight += 1
    if(this.checkTip) {
      this.resetWeight
      false
    }
    else true
  }
  
  //function to add slots when initializing the game
  def addSlots(newSlots: Vector[Location]) = slots = newSlots
  
  
  // changes item on the slot to a scale. Unreversable action, should only be used while initializing the game board
  // (will be changed if there is need for the players to put up scales)
//  def changeSlotToScale(slot: Int, radius:Int) = {
//    slots(slot + radius).itemToScale(radius)
//  }
  
}