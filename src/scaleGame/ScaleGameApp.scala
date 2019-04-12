package scaleGame

import swing._
import swing.event._
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage


 
 
object ScaleGameApp extends SimpleSwingApplication {
  import event.MouseClicked._
  import java.awt.{Dimension, Graphics2D, Graphics, Image, Rectangle}
//  def top = new MainFrame {
//    title = "Hello, World!"
//    contents = new Button {
//    text = "Click Me!"
//     }
    
//  def main() {
//    println("blah")
//  }
  
  // the layout for the scales, will be changed to a file when it is done. Basicly a list of lists of 
  //tuples for each layer
  private val scales = List(
          List((10, 3)),
          List((7, 4), (12, 2)))
  val players = Vector(new Player("mikko", true, "red"), new Player("antti", true, "blue"))
  val game = new ScaleGame(players)
  for(subList <- scales)
    for(scale<- subList) game.addscale()
          
          
  case class Sprite(id: String, image: BufferedImage, width: Int, height: Int)
  
  def getSprites(imageFile:String) {
    val spriteSheet = ImageIO.read(new File("letters.png"))
    def makeSpriteGrid = {
      var grid: List[(Int, Int)] = List()
    
      for (i <- 0 until 8){
      for (j <- 0 until 13) grid = grid:+ (i, j)
      }
      grid
    }
    
    val grid = makeSpriteGrid
    for {
      slot <- grid
      id = (slot._1*slot._2).toString
      image = spriteSheet.getSubimage(slot._1*36, slot._2*44, 36, 44)
      } yield Sprite(id, image, 36, 44)
  }
//  var gameAreaWidth: Int = _
//  var gameAreaHeight: Int = _
//  var squareSize: Int = 18
  
  
    
  
  
//  def panel = new Panel {
//    preferredSize = new Dimension(1200, 900)
//    focusable = true
//    reactions += {
//      case MouseClicked(_, point: Point, _, _, _) =>
//        ???
//    }
//    override def paint(g: Graphics2D){
//      g setColor (new Color(20, 99, 99))
//      g fillRect (0, 0, size.width, size.height)
//      
//    }
//  }
//  
  
//  val button = new Button("Again!")
//  val panelGrid = new GridBagPanel
 
  
  def top = new MainFrame {
    title = "Scale Game Window"
    
    val width = 1200
    val height = 900
    val sprites = getSprites("letters.png")
    
    // val selection = 
    
    val base = new Component {
      
      
      // fill up the map with the base sprite, after that put the scale bases and the 
      val kartta = Array.fill(20)(Array.fill(20)(41))
      for (i <- 0 until scales.length) {
        for (scale <- scales(i)) {
          kartta(20 - i).update(scale._1, 18)
          kartta(20- i -1).p
        }
        
        if (i == 0) {
          kartta(20 - i).update(scales(i), 18)
          for(scale <- scales(i)) kartta(10 - i - 1). 
        }
        else kartta (10 - i).update(5 - scales(i).
      }
      // valittu kohta
      // var selection: Option[(Int, Int)] = None
      
      val padding = 100
      val tileWidth = 36
      val tileHeight = 44
      
      val positions = Vector.tabulate(10, 10) { (x: Int, y: Int) =>
        val xc = padding + x * tileWidth
        val yc = padding + y * tileHeight
        new Point (xc, yc)
      }
      
      override def paintComponent(g: Graphics2D) = {
        
      }
    }
  }

  
//  def top = this.scaleGameWindow
  

}
