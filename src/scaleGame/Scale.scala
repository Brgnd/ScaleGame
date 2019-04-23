

package scaleGame

import scala.math.abs


class Scale(radius: Int, loc: Location) extends Item {
  private var slots: Vector[Location] = null
  private var weight: Int = 0 //total weight of the scale
  private var unbalance: Int = 0 //how much the scale is unbalanced, negative for left and positive for right side
  
  def getRadius = radius
  
  def getLoc: Location = loc
  
  
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
   
  // calculates the balance of the scale.
  
  def updateBalance() = {
    val slotsWeights = slots.map(_.getItem).map(_ match {
      case Some(item: Item) => item.getWeight
      case _ => 0
    })
    unbalance = slotsWeights.zipWithIndex.foldLeft(0) { (total, x) => total + (x._1 * (x._2 - radius)) }
    weight = slotsWeights.foldLeft(0)(_ + _)
  }
                                          
  
  //returns current weight
  def getWeight = weight
  
  //aka tipping. resets the weight amount on all of the slots and also resets
   // the slots on the scales on top of the scale
   // get all the items that are weights on the scale and reset their amount to zero.
 
  def resetWeight():Unit = {
    slots.map(_.getItem).foreach(_ match {
      case Some(item: Item) => item.resetWeight
      case None =>
    })
    
    weight = 0
    unbalance = 0
  }

  // adds weight according to the weight added via the ScaleGame class. This only handles the the scale calculations
  // weight calculations are done for each weight in a different place (could be a bit less disjointed)
  // also tips the scale if not in balance
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
  
}