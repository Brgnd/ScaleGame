package scaleGame

import scala.math._


//player class. Just needs a playerName as a constructor. human is for making the AI (which is not implemented)
//
class Player(playerName: String, human: Boolean, color: String) {
  
//  private var brain: Some[Brain] = if (!human) Some[BasicBrain] else None
  
  private var score = 0
  
//  def addBrain(newBrain: Brain): Unit = brain = Some(newBrain)
  
  def getName = playerName
  
//  def getBrain = brain
  
  def addScore(amount: Int) = score += abs(amount)
  
  
  //calculates the score for the player. the base scale is always the first one in the array.
  def calcScore(game: ScaleGame): Unit= {
    val baseScale = game.getScales.head
    score = baseScale.giveScore(this)
  }
  
  //returns the score. Should only be used when not changing the game state
  def getScore = score
}