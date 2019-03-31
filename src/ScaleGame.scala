package scalegame


class ScaleGame (players: Vector[Player]) {
  
  private val baseLoc = new Location(None)
   
  private val scales= Array[Scale]()
  
  val activePlayer = players(0)
  
  
  // adds a scale to the game
  def addScale(radius: Int, loc: Location) = scales :+ new Scale(radius, loc)
  
  //def addWeight(scale: Scale, loc: Int) = scale.addWeight(loc)
  
  //"basic scale" for adding just radius 3 scales to the game
  def addscale (loc: Location) = scales :+ new Scale(3, loc)
  
  def playerAction(loc: Location, player: Player) = {
    val scale = loc.getScaleAttachedTo.get
    val slot = loc.getNum
    if (!loc.isBase) {
      var newScore = scale.addWeight(slot, player)
      player.addScore(newScore)
    }
  }
  
  
  def calcScore(player: Player) = player.getScore
}