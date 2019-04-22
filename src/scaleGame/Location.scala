package scaleGame



class Location( x: Int, y: Int) {
  private var onTopOfScale: Option[Scale] = None
  private var thing: Option[Item] = None //initialized as None because makes the graphics easier
//  private var base: Boolean = false // only the base location gets true
//  private var locNum: Int = {
//    if (base) 0 
//    else {          
//       val underScale = scaleOnLocation.get
//       underScale.slots.indexOf(this) - underScale.getRadius
//     }
//  }
  
//  def getNum = locNum
   
  def getScale: Option[Scale] = { 
    thing match {
      case Some(scale: Scale) => Option(scale)
      case _ => None
    }
  }
                                
  def insertOnTopScale(scale: Scale) = onTopOfScale = Some(scale)
  
  
  def getOnTopOfScale: Option[Scale] = onTopOfScale
  
//  def isBase: Boolean = base
  
  def getItem: Option[Item] = thing
  //changes the item to Scale. Weight is discarded.
  def itemToScale(radius: Int) = thing = Option(new Scale(radius, this))
  
  def itemToWeight(distance: Int) = thing = Option(new Weight(this, distance))
  
}