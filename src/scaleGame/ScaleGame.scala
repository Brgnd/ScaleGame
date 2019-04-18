package scaleGame

import scala.collection.mutable.ArrayBuffer


class ScaleGame (players: Vector[Player]) {
  
  private val locGrid = Array.tabulate[Location](20, 20){ (x, y) => new Location(x, y) }
   
  private val scales= ArrayBuffer[Scale]()
  
  val activePlayer = players(0)
  
  
  // adds a scale to the game
  def addScale(x: Int, y: Int, radius: Int): Unit = {
    val loc = locGrid(x)(y)
    loc.itemToScale(radius)
    scales.append(loc.getScale.get)
  }
  
  //def addWeight(scale: Scale, loc: Int) = scale.addWeight(loc)
  
  //"basic scale" for adding just radius 3 scales to the game
  def addScale (x: Int, y: Int) = {
    locGrid(x)(y).itemToScale(3)
    scales.append(locGrid(x)(y).getScale.get)
  }
  
  
  //the basic player action, which is adding a weight to a slot on a scale. Might add error handling for clicking on a
  //scale. Still need to add something, forgot what.
//  def playerAction(loc: Location, player: Player) = {
//    val scale = loc.getScaleAttachedTo.get
//    val slot = loc.getNum
//    if (!loc.isBase) {
//      var newScore = scale.addWeight(slot, player)
//      player.addScore(newScore)
//    }
//  }
  def getLocGrid = locGrid
  
  def calcScore(player: Player) = player.getScore
  
  def getScales = scales
}