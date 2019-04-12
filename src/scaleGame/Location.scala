package scaleGame



class Location(scaleOnLocation: Option[Scale]) {
  private var thing: Option[Item] = None //initialized as None because makes the graphics easier
  private var base: Boolean = scaleOnLocation.isEmpty // only the base location gets true
  private var locNum: Int = {
    if (base) 0 
    else {          
       val underScale = scaleOnLocation.get
       underScale.slots.indexOf(this) - underScale.getRadius
     }
  }
  
  def getNum = locNum
   
  def getItem: Option[Item] = thing
  
  def getScaleAttachedTo: Option[Scale] = scaleOnLocation
  
  def isBase: Boolean = base
  
  
  //changes the item to Scale. Weight is discarded.
  def itemToScale(radius: Int) = thing = Option(new Scale(radius, this))
  
  def itemToWeight
  
}