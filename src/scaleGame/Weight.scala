package scaleGame

import scala.math.abs

//Weights know their location, could probably do without. distanceFromCentre is for making the balance calculations and
//score calculations easier. (even though I don't use it in places because I've used zipWithIndex.

class Weight(loc: Location, distanceFromCentre: Int) extends Item {
  private var owner: Option[Player] = None //Initialized as not owned to anyone, if a player puts a weight it will be changed to be his
  private var amount: Int = 0
  
  def getWeight = amount
  
  def resetWeight() = amount = 0
  
  def changeAmount(howMuch: Int) = amount += howMuch
  
  
  //changes the owner when a player puts his weight on top of an other players.
  def changeOwner(player: Player) = {
    owner match {
      case Some(currentOwner: Player) => if (currentOwner != player) owner = Some(player) 
      case None => owner = Some(player)
    }
  }
  
  //the givescore function for weights. This returns the amount according to the distance
  def giveScore(player:Player): Int = {
    if (player == owner.getOrElse(None)) abs(distanceFromCentre) * amount
    else 0
  }  
  
  def getDistanceFromCentre: Int = distanceFromCentre
}