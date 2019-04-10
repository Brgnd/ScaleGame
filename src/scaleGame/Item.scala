package scaleGame


abstract class Item {
  def resetWeight
  
  def getWeight:Int
  
  def changeAmount(howMuch: Int)
  
  def changeOwner(player: Player)
}