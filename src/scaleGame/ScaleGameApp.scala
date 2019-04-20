package scaleGame

import swing._
import scala.io.Source
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage
import java.awt.{Point, Rectangle}
import scala.swing.event.MouseMoved
import scala.swing.event.MouseClicked

 
 
object ScaleGameApp extends SimpleSwingApplication {
  
  // the layout for the scales, will be changed to a file when it is done. Basicly a list of lists of 
  //tuples for each layer. First scale is the basescale so it is always the middle point, and rest of the scales have
  // distance to the middle point of the previous scale.
  private val scales = List(
          List((10, 7)),
          List((-7, 3), (4, 2)))
          
  val players = Vector(new Player("mikko", true, "red"), new Player("antti", true, "blue"))
  
  
  val game = new ScaleGame(players)
  // function for adding the scaleBeams
  def addScaleBeams(radius: Int,x: Int, y: Int) = {
    
  }
  
  // initializing the scales. the second set of scales is always 2 slots up from the previous scale. To make stacking
  // scales possible
  for(i <- 0 until scales.length)
    for(scale<- scales(i)) 
      if (i == 0) game.addScale(scale._1, 14, scale._2)
      else game.addScale(scales(i-i)(0)._1 + scale._1,14- i*2 ,  scale._2)
          
  // getting the sprites from the png file
  case class Sprite(id: String, image: BufferedImage, width: Int, height: Int)
  
  def getSprites(imageFile:String) =  {
    val spriteSheet = ImageIO.read(new File(imageFile))
    def makeSpriteGrid = {
      var grid: List[(Int, Int)] = List()
    
      for (i <- 0 until 13) {
      for (j <- 0 until 8) grid = grid:+ (i, j)
      }
      grid
    }
    
    val grid = makeSpriteGrid
    for {
      slot <- grid
      id = (slot._1*slot._2).toString
      image = spriteSheet.getSubimage(slot._2*36, slot._1*44, 36, 44)
      } yield Sprite(id, image, 36, 44)
  }
 
  
  def top = new MainFrame {
    
    title = "Scale Game Window"
    
    val width = 1200
    val height = 900
    val sprites = getSprites("letters.png")
    
    
    val selectionMask = ImageIO.read(new File("selection.png"))
    
    minimumSize = new Dimension(width, height)
    preferredSize = new Dimension(width, height)
    maximumSize = new Dimension(width, height)
    
    
    val base = new Component {
      
      
      // fill up the map with the base sprite, after that put the scale bases and the 
      
 
      // valittu kohta
      var selection: Option[(Int, Int)] = None
      
      val padding = 100
      val tileWidth = 36
      val tileHeight = 44
      val spriteMap = Array.fill(20, 15)(62)
      
      for {
          y <- 14 to 0 by -1
          x <- 0 until 20
        } {
          spriteMap(x)(y) = game.getLocGrid(x)(y).getItem match { 
            case Some(i: Weight) => 52 + i.getWeight
            case Some(i: Scale) => {
              for (j <- -i.getRadius to i.getRadius) spriteMap(x+j)(y-1) = 69 
              19
            }
            case _ => spriteMap(x)(y)
          }
        }
      
      
      val positions = Vector.tabulate(20, 15) { (x: Int, y: Int) =>
        
        
        val xc = padding + x * tileWidth
        val yc = padding + y * tileHeight
        new Point (xc, yc)
      }
      
      
      
      override def paintComponent(g: Graphics2D) = {
        
        for {
          y <- 14 to 0 by -1
          x <- 0 until 20
        } {
          val loc = positions(x)(y)
          val sprite = sprites(spriteMap(x)(y))
          
          g.drawImage(sprite.image, loc.x, loc.y, null) 
          
          selection.foreach {
            coords =>
              if (coords._1 == x && coords._2 == y) {
                g.drawImage(selectionMask, loc.x, loc.y + sprite.height - selectionMask.getHeight, null)
              }
          }
        } 
      }
      val boundaries = (
        for {
          y <- 14 to 0 by -1
          x <- 0 until 20
          pos = positions(x)(y)
          sprite = sprites(spriteMap(x)(y))
        } yield new Rectangle(pos.x, pos.y, sprite.width, sprite.height) -> (x, y))
      
     listenTo(mouse.moves)
     listenTo(mouse.clicks)
     
     reactions += {
        case MouseMoved(c, point, mods) => {
          
          selection = boundaries.find(box => box._1.contains(point)).map(_._2)
          
          repaint() 
        }
        case MouseClicked(c, point, mods, clicks, false) =>
          
          val placeOption = boundaries.find(box => box._1.contains(point)).map(_._2)
          if (placeOption.isDefined) {
            val place = placeOption.get
            val loc = game.getLocGrid(place._1)(place._2)
            loc.getItem match {
              case Some(item: Weight) => {
                game.playerAction(loc, item)
                  }
                }
              
              
            }
          }
      }
     
    }
    
    contents = base
  }
  
  

}
