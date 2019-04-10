package scaleGame



class Weight(loc: Location) extends Item {
  private var owner: Option[Player] = None //Initialized as not owned to anyone, if a player puts a weight it will be changed to be his
  private var amount: Int = 0
  
  def getWeight = amount
  def resetWeight() = amount = 0
  
  def changeAmount(howMuch: Int) = amount += howMuch
  
  def changeOwner(player: Player) = owner = Some(player)
}