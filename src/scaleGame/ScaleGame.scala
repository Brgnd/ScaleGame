package scaleGame

import scala.collection.mutable.ArrayBuffer
import scala.math.abs


class ScaleGame (players: Vector[Player]) {
  
  private val locGrid = Array.tabulate[Location](25, 15){ (x, y) => new Location(x, y) }
   
  
  //array for scales. The first one in the array is always the base scale.
  private var scales: Vector[Scale] = Vector()
  
  
  //variable for the activeplayer. Will change after playeraction.
  var activePlayer = players(0)
  
  // adds a scale to the game, also makes the slots for the weights and slots on top of it and gives it to the scale
  def addScale(x: Int, y: Int, radius: Int): Unit = {
    val loc = locGrid(x)(y)
    loc.itemToScale(radius)
    
    val scale = loc.getScale.get
    scales = scales :+ scale
    
    val locArray = new ArrayBuffer[Location]()
    
    //adds an empty location in the middle for making other functions easier to compute with zipWithIndex etc.
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
  
  
  
  //the basic player action, location is always going to be a loc with weight, handled in the gameApp
  //addWeight returns true when successfully adding a weight. (probably not the best style of coding)
  
  
  def playerAction(loc: Location, weight: Weight) = {
    val scale = loc.getOnTopOfScale.get
    if(scale.addWeight(weight.getDistanceFromCentre)) {
      weight.changeAmount(1)
      weight.changeOwner(activePlayer)
    }
    
    //checking the underscale for tipping. (chain reactions possible)
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
    
    //updates the score and changes the active player (works with more than 2 players)
    players.foreach(_.calcScore(this))
    if (activePlayer == players.last) activePlayer = players(0)
    else activePlayer = players(players.indexOf(activePlayer)+1)
    
    
  }
  def getPlayers = players
  
  def getLocGrid = locGrid
  
  def calcScore(player: Player) = player.getScore
  
  def getScales = scales
}