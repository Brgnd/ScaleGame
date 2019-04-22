package scaleGame


abstract class Item {
  def resetWeight
  
  
  def giveScore(player: Player): Int
  
  def getWeight:Int
  
  def changeAmount(howMuch: Int)
  
  def changeOwner(player: Player)
}