package scaleGame


//has just x and y, and goes to the grid on that place.
class Location( x: Int, y: Int) {
  private var onTopOfScale: Option[Scale] = None
  private var thing: Option[Item] = None //initialized as None because makes the graphics easier
   
  
  // gives the scale in this location, if the item is scale (can be a weight as well)
  //
  def getScale: Option[Scale] = { 
    thing match {
      case Some(scale: Scale) => Option(scale)
      case _ => None
    }
  }
   
  //adds the scale which the location is on top of. Aka gives the scale that is "under" this location (bad name)
  def insertOnTopScale(scale: Scale) = onTopOfScale = Some(scale)
  
  //gives the scale "under" this location. (bad name 2)
  def getOnTopOfScale: Option[Scale] = onTopOfScale
  
  
  //gives the item. Could probably just use this instead of getScale but then would have to change stuff elsewhere
  def getItem: Option[Item] = thing
  
  
  //changes the item to Scale. Weight is discarded.
  def itemToScale(radius: Int) = thing = Option(new Scale(radius, this))
  
  
  //changes item to weight. Needs the distance from the center to calculate stuff easier.
  def itemToWeight(distance: Int) = thing = Option(new Weight(this, distance))
  
  def getLocX = x
  
}