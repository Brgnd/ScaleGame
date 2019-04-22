package scaleGame

import scala.math._

class Player(playerName: String, human: Boolean, color: String) {
  
//  private var brain: Some[Brain] = if (!human) Some[BasicBrain] else None
  
  private var score = 0
  
//  def addBrain(newBrain: Brain): Unit = brain = Some(newBrain)
  
  def getName = playerName
  
//  def getBrain = brain
  
  def addScore(amount: Int) = score += abs(amount)
  
  def calcScore(game: ScaleGame): Unit= {
    val baseScale = game.getScales.head
    score = baseScale.giveScore(this)
  }
  
  def getScore = score
}