package scalegame




class Location(scale: Option[Scale]) {
  private var thing: Item = new Weight(this) //at first initialize as a weight, changed to scale if needed via changeItem
  private var base: Boolean = scale.isEmpty // only the base location gets true
  
  def getItem: Item = thing
  
  def getScaleAttachedTo: Option[Scale] = scale
  
  def isBase: Boolean = base
  
  
  //changes the item to Scale. Weight is discarded.
  def itemToScale(radius: Int) = thing = new Scale(radius, this)
  
}