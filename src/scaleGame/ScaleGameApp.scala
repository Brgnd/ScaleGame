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
  var gameAreaWidth: Int = _
  var gameAreaHeight: Int = _
  var squareSize: Int = 18
  
  
    
  
  
  def panel = new Panel {
    preferredSize = new Dimension(1200, 900)
    focusable = true
    reactions += {
      case MouseClicked(_, point: Point, _, _, _) =>
        ???
    }
    override def paint(g: Graphics2D){
      g setColor (new Color(20, 99, 99))
      g fillRect (0, 0, size.width, size.height)
      
    }
  }
  
  val button = new Button("Again!")
  val panelGrid = new GridBagPanel
 
  
  val scaleGameWindow = new MainFrame
  scaleGameWindow.preferredSize = new Dimension (1280, 960)
  scaleGameWindow.title = "Scale Game"
  scaleGameWindow.resizable= false
  scaleGameWindow.contents = panel
//  scaleGameWindow.contents += new Button("blah")

  
  def top = this.scaleGameWindow
  

}
