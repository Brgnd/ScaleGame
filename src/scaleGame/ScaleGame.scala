package scaleGame




class ScaleGame (players: Vector[Player]) {
  
  private val baseLoc = new Location(None)
  private val locGrid = List.fill(20)(List.fill(20)(new Location(None)))
   
  private val scales= Array[Scale]()
  
  val activePlayer = players(0)
  
  
  // adds a scale to the game
  def addScale(x: Int, y: Int, radius: Int) = {
    val loc = locGrid(x)(y)
    loc.
  }
  
  //def addWeight(scale: Scale, loc: Int) = scale.addWeight(loc)
  
  //"basic scale" for adding just radius 3 scales to the game
  def addscale (x: Int, y: Int) = locGrid(x)(y).
  
  
  //the basic player action, which is adding a weight to a slot on a scale. Might add error handling for clicking on a
  //scale. Still need to add something, forgot what.
  def playerAction(loc: Location, player: Player) = {
    val scale = loc.getScaleAttachedTo.get
    val slot = loc.getNum
    if (!loc.isBase) {
      var newScore = scale.addWeight(slot, player)
      player.addScore(newScore)
    }
  }
  def getLocGrid = locGrid
  
  def calcScore(player: Player) = player.getScore
}