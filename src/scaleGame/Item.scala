package scaleGame

//class for both weights and scales. Used basically for making the score calculations at this point.
abstract class Item {
  
  def resetWeight
  
  
  def giveScore(player: Player): Int
  
  def getWeight:Int
  
}