package scaleGame

import scala.collection.mutable.ArrayBuffer
import scala.math.abs


class ScaleGame (players: Vector[Player]) {
  
  private val locGrid = Array.tabulate[Location](20, 15){ (x, y) => new Location(x, y) }
   
  
  //array for scales. The first one in the array is always the base scale.
  private val scales= ArrayBuffer[Scale]()
  
  var activePlayer = players(0)
  
  // adds a scale to the game, also makes the slots for the weights and slots on top of it and gives it to the scale
  def addScale(x: Int, y: Int, radius: Int): Unit = {
    val loc = locGrid(x)(y)
    loc.itemToScale(radius)
    val scale = loc.getScale.get
    scales.append(scale)
    val locArray = new ArrayBuffer[Location]()
    for (i <- -radius to radius) {
      val weightLoc = locGrid(x+i)(y-2) 
      if (i != 0 ) {
      weightLoc.itemToWeight(i)
      weightLoc.insertOnTopScale(scale)
      }
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
  
  
  //the basic player action, location is always going to be a loc with weight, handled in the gameApp
  //addWeight returns true 
  def playerAction(loc: Location, weight: Weight) = {
    val scale = loc.getOnTopOfScale.get
    if(scale.addWeight(weight.getDistanceFromCentre)) {
      weight.changeAmount(1)
      weight.changeOwner(activePlayer)
    }
    var underScale = scale.getLoc.getOnTopOfScale
    while (underScale.isDefined) {
      underScale match {
      case Some(scale: Scale) =>{
        scale.updateBalance()
        if (scale.checkTip()) {
          scale.resetWeight()
        }
        underScale = scale.getLoc.getOnTopOfScale
      }
      case _ => underScale = None
    }
    }
    players.foreach(_.calcScore(this))
    if (activePlayer == players.last) activePlayer = players(0)
    else activePlayer = players(players.indexOf(activePlayer)+1)
    
    
  }
  def getPlayers = players
  
  def getLocGrid = locGrid
  
  def calcScore(player: Player) = player.getScore
  
  def getScales = scales
}