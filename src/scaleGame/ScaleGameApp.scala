package scaleGame


import swing._
import scala.io.Source
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage
import java.awt.{Point, Rectangle, Font, Color}
import scala.swing.event.MouseMoved
import scala.swing.event.MouseClicked

 
//The game application.
object ScaleGameApp extends SimpleSwingApplication {
  
  // the layout for the scales, will be changed to a file when it is done(probably not). Basicly a list of lists of 
  //tuples for each layer. First scale is the basescale so it is always the middle point, and rest of the scales have
  // distance to the middle point of the previous scale.
  
  //function to read a csv file. first thing is string, which has doesn't do anything. Then there is the info like this:
  // 0: Level of scale (0 is bottom, 1 is level up from it)
  // 1: What scale is it on (0 is the first scale inserted to the game and so on)
  // 2: Offset of the scale, aka which slot of the scale under the new scale is on
  // 3: the radius of the scale.
  // this is a bad implementation. Should be better.
  def getScales = {
    val scaleFile = Source.fromFile("scales.csv").getLines()
    for {
      line <- scaleFile.toList
      items = line.split(",")
      scaleInfo = items.takeRight(4).map(_.toInt)
    } yield List(scaleInfo(0), scaleInfo(1), scaleInfo(2), scaleInfo(3))
  }
  private val scales = getScales
  
          
  //the players. Could either make a local file or some UI solutions to get the input for players.
  //        
  
  val players = Vector(new Player("Player 1", true, "red"), new Player("Player 2", true, "blue"))
  
  
  val game = new ScaleGame(players)
  
  // initializing the scales. the next set of scales is always 2 slots up from the previous scale. To make stacking
  // scales possible. Read from a file which has this information
  for(scale <-scales) {
      if (scale(0) == 0) {
        game.addScale(10, 14, scale(3))
      }
      else {
        game.addScale((game.getScales(scale(1)-1).getLoc.getLocX + scale(2)), 14-scale(0)*2, scale(3)) 
      }

  }
  // getting the sprites from the png file
  case class Sprite(id: String, image: BufferedImage, width: Int, height: Int)
  
  def getSprites(imageFile:String) =  {
    val spriteSheet = ImageIO.read(new File(imageFile))
    
    //helper method to make the grid.
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
    
    //a green mask with half transparency to show the slot which the mouse is over on.
    val selectionMask = ImageIO.read(new File("selection.png"))
    
    minimumSize = new Dimension(width, height)
    preferredSize = new Dimension(width, height)
    maximumSize = new Dimension(width, height)
    var playersString = players.map(_.getName)
    var fullString = ""
    
    val base = new Component {
      
      
 
      // valittu kohta
      var selection: Option[(Int, Int)] = None
      
      val padding = 100
      val tileWidth = 36
      val tileHeight = 44
      val spriteMap = Array.fill(20, 15)(62)
      
      
      //method for updating the spritemap. The weight amounts take the next sprite automatically when a weight is added
      //going from bottom to the top cause the functions to initialize scales need to be after, cause the
      //locations will be originally initialized as weights, but changed to scales after.
      def updateSpriteMap() = {
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
      }
      
      updateSpriteMap()
      
      
      //All the top left corners of every grid
      val positions = Vector.tabulate(20, 15) { (x: Int, y: Int) =>
        
        
        val xc = padding + x * tileWidth
        val yc = padding + y * tileHeight
        new Point (xc, yc)
      }
      
      
      //method for drawing the playing field. Also draws players and their score. Updates after every playerAction
      override def paintComponent(g: Graphics2D) = {
        
        for {
          y <- 14 to 0 by -1
          x <- 0 until 20
        } {
          val loc = positions(x)(y)
          val sprite = sprites(spriteMap(x)(y))
          val scores = players.map(_.getScore)
          
          g.setFont(new Font("Serif", Font.BOLD, 36))
          
          g.drawImage(sprite.image, loc.x, loc.y, null) 
          
          //for drawing the players and their scores. The active player is drawn in green.
          for (player <- players) {
            if (player == game.activePlayer) g.setColor(Color.GREEN)
            else g.setColor(Color.RED)
            g.drawString((player.getName + " " + player.getScore.toString + "\n"), 1000, (100+40*players.indexOf(player)))
          }
          
          selection.foreach {
            coords =>
              if (coords._1 == x && coords._2 == y) {
                g.drawImage(selectionMask, loc.x, loc.y + sprite.height - selectionMask.getHeight, null)
              }
          }
        } 
      }
      
      //boundaries of the grid, so the mouseover sprite will be on the correct position
      val boundaries = (
        for {
          y <- 14 to 0 by -1
          x <- 0 until 20
          pos = positions(x)(y)
          sprite = sprites(spriteMap(x)(y))
        } yield new Rectangle(pos.x, pos.y, sprite.width, sprite.height) -> (x, y))
      
        
     //needs to follow both mouse moving and clicks.
        
     listenTo(mouse.moves)
     listenTo(mouse.clicks)
     
     
     //what happens when the mouse is moved and when it is clicked.
     reactions += {
        case MouseMoved(c, point, mods) => {
          
          selection = boundaries.find(box => box._1.contains(point)).map(_._2)
          
          repaint() 
        }
        
        //the playerAction. Only activates when one clicks on a location with a Weight.
        case MouseClicked(c, point, mods, clicks, false) => {
          
          boundaries.find(box => box._1.contains(point)).map(_._2) match {
            case Some(place: (Int, Int)) => {
               val loc = game.getLocGrid(place._1)(place._2) 
               loc.getItem match {
                 case Some(item: Weight) => {
                   game.playerAction(loc, item)
                   updateSpriteMap()
                 }
                 case _ => 
               }
            }
            case _ => 
              
              
          }
          repaint()
        }
      }
    }
    
    
    
    
    contents = base
     
  }
  
  

}
