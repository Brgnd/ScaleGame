package scaleGame

import scala.math.abs

class Weight(loc: Location, distanceFromCentre: Int) extends Item {
  private var owner: Option[Player] = None //Initialized as not owned to anyone, if a player puts a weight it will be changed to be his
  private var amount: Int = 0
  
  def getWeight = amount
  
  // need to add zeroing of player scores
  def resetWeight() = amount = 0
  
  def changeAmount(howMuch: Int) = amount += howMuch
  
  def changeOwner(player: Player) = {
    owner match {
      case Some(currentOwner: Player) => if (currentOwner != player) owner = Some(player) 
      case None => owner = Some(player)
    }
  }
  
  
  def giveScore(player:Player): Int = {
    if (player == owner.getOrElse(None)) abs(distanceFromCentre) * amount
    else 0
  }  
  
  def getDistanceFromCentre: Int = distanceFromCentre
}