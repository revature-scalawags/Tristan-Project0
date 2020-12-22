/** Project0 TODO: add title comments to each file
 * 
 * 
 */

package project0

import org.mongodb.scala.MongoClient
import com.mongodb.BasicDBObject
import org.mongodb.scala.bson.collection.immutable.Document
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

import java.awt.Taskbar.State
import java.util.concurrent.TimeUnit



object Project0 extends App {

  //FOR TESTING: import org.scalatest.funsuite.AnyFunSuite

  //TODO: Implement CLI with the following steps:
  //(1) First, have user select from a list of popular games
  //(2) Then have user select from a list of the games' categories
  //(3) Then have user input the size they want the retrieved leaderboard to be 
  //(#places, #speedruns will be greater than #places with ties)
  //(4) Finally, use this information to create a url string to pass to getLeaderboard in LeaderboardUtilities

  //We can also have CLI functionality for what the user wants to do when the Speedrundao
  //is created.
  

  println("\nWelcome to Tristan's Project0 -- Speedrun.com Leaderboard Analysis")
  val url = CLI.createURL()
  println("\nURL successfully created. Requesting leaderboard JSON from speedrun.com...")
  val jsonString = LeaderboardUtilities.getLeaderboard(url)

  //val jsonString = LeaderboardUtilities.getLeaderboard("https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=2&embed=players")

  if (jsonString != null) {
    println("Leaderboard JSON successfully retrieved")
    val jsoObj = LeaderboardUtilities.parseJson(jsonString)
    val speedrunDao = new SpeedrunDao(MongoClient())

    println("\nMongoDB initiated, parsing leaderboard json for speedrun data...")
    LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)

    val speedrunSeq = speedrunDao.getAll()
    println("")
    // for (run <- speedrunSeq) {
    //   println(run)
    // }
    speedrunDao.deleteAll()
  
  } else {
    println("No leaderboard retrieved. Exiting program...")
  }
}