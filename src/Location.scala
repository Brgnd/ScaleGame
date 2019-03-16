package scalegame

class Location { //class for the base position.
  private var thing: Option[Item] = None
  private var base = true
  
  
  def isBase = base 
  
}


class ScaleLocation(attachedTo: Scale) extends Location {
  private var thing = new Weight(this) //at first initialize as a weight, changed to scale if needed via changeItem
  private var base = false // only the base location gets true, can be set via changeToBase
  
  
  def getItem() = thing
  
  override def getScaleAttachedTo = attachedTo
}