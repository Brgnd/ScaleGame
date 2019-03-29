package scalegame


abstract class Item {
  def resetWeight
  
  def getWeight:Int
  
  def changeAmount(howMuch: Int)
}