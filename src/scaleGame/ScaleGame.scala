package scaleGame

import scala.collection.mutable.ArrayBuffer
import scala.math.abs


class ScaleGame (players: Vector[Player]) {
  
  private val locGrid = Array.tabulate[Location](20, 15){ (x, y) => new Location(x, y) }
   
  private val scales= ArrayBuffer[Scale]()
  
  var activePlayer = players(0)
  
  
  // adds a scale to the game, also makes the slots for the weights and slots on top of it and gives it to the scale
  def addScale(x: Int, y: Int, radius: Int): Unit = {
    val loc = locGrid(x)(y)
    loc.itemToScale(radius)
    val scale = loc.getScale.get
    scales.append(scale)
    val locArray = new ArrayBuffer[Location]()
    for (i <- -radius to radius) if(i != 0 ){
      val weightLoc = locGrid(x+i)(y-2)
      weightLoc.itemToWeight(i)
      locArray.append(weightLoc)
    }
    scale.addSlots(locArray.toVector)
  }
  
  //def addWeight(scale: Scale, loc: Int) = scale.addWeight(loc)
  
  //"basic scale" for adding just radius 3 scales to the game
  def addScale (x: Int, y: Int) = {
    locGrid(x)(y).itemToScale(3)
    scales.append(locGrid(x)(y).getScale.get)
  }
  
  
  //the basic player action, which is adding a weight to a slot on a scale. Might add error handling for clicking on a
  //scale. Still need to add something, forgot what.
  def playerAction(loc: Location, weight: Weight) = {
    val scale = loc.getOnTopOfScale.get
    scale.addWeight(weight.getDistanceFromCentre)
    weight.changeOwner(activePlayer)
    if (activePlayer == players.last) activePlayer = players(0)
    else activePlayer = players(players.indexOf(activePlayer)+1)
    
    
  }
  def getLocGrid = locGrid
  
  def calcScore(player: Player) = player.getScore
  
  def getScales = scales
}