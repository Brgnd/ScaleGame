package scaleGame

import swing._
import scala.io.Source
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage
import java.awt.{Point, Rectangle}

 
 
object ScaleGameApp extends SimpleSwingApplication {
  import event.MouseClicked._
  
//  def top = new MainFrame {
//    title = "Hello, World!"
//    contents = new Button {
//    text = "Click Me!"
//     }
    
//  def main() {
//    println("blah")
//  }
  
  // the layout for the scales, will be changed to a file when it is done. Basicly a list of lists of 
  //tuples for each layer. First scale is the basescale so it is always the middle point, and rest of the scales have
  // distance to the middle point of the previous scale.
  private val scales = List(
          List((10, 3)),
          List((-3, 4), (2, 2)))
  val players = Vector(new Player("mikko", true, "red"), new Player("antti", true, "blue"))
  
  
  val game = new ScaleGame(players)
  
  // initializing the scales.
  for(i <- 0 until scales.length)
    for(scale<- scales(i)) 
      if (i == 0)  game.addScale(19, scale._1, scale._2)
      else game.addScale(19- i*2, scales(i-i)(0)._1 + scale._1, scale._2)
          
  // getting the sprites from the png file
  case class Sprite(id: String, image: BufferedImage, width: Int, height: Int)
  
  def getSprites(imageFile:String) =  {
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
    
    
    minimumSize = new Dimension(width, height)
    preferredSize = new Dimension(width, height)
    maximumSize = new Dimension(width, height)
    // val selection = 
    
    val base = new Component {
      
      
      // fill up the map with the base sprite, after that put the scale bases and the 
      
 
      // valittu kohta
      // var selection: Option[(Int, Int)] = None
      
      val padding = 100
      val tileWidth = 36
      val tileHeight = 44
      
      val positions = Vector.tabulate(20, 20) { (x: Int, y: Int) =>
        
        
        val xc = padding + x * tileWidth
        val yc = padding + y * tileHeight
        new Point (xc, yc)
      }
      
      override def paintComponent(g: Graphics2D) = {
        
        for {
          y <- 0 until 20
          x <- 19 to 0 by -1
        } {
          val loc = positions(x)(y)
          val spriteNum = game.getLocGrid(x)(y).getItem match { 
            case None => 62
            case Some(i: Weight) => 52 + i.getWeight
            case Some(i: Scale) => 18
          }
          g.drawImage(sprites(spriteNum).image, loc.x, loc.y, null) 
        }
          //selection
          
      }
    }
    contents = base
  }
  
  
//  def top = this.scaleGameWindow
  

}
