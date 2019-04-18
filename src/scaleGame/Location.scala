package scaleGame



class Location( x: Int, y: Int) {
  private var scaleOnLocation: Option[Scale] = None
  private var thing: Option[Item] = None //initialized as None because makes the graphics easier
  private var base: Boolean = scaleOnLocation.isEmpty // only the base location gets true
//  private var locNum: Int = {
//    if (base) 0 
//    else {          
//       val underScale = scaleOnLocation.get
//       underScale.slots.indexOf(this) - underScale.getRadius
//     }
//  }
  
//  def getNum = locNum
   
  def getScale: Option[Scale] = if (thing.get.isInstanceOf[Scale]) thing.asInstanceOf[Option[Scale]]
                                else None
  
  def getScaleAttachedTo: Option[Scale] = scaleOnLocation
  
  def isBase: Boolean = base
  
  def getItem: Option[Item] = thing
  //changes the item to Scale. Weight is discarded.
  def itemToScale(radius: Int) = thing = Option(new Scale(radius, this))
  
  def itemToWeight() = thing = Option(new Weight(this))
  
}