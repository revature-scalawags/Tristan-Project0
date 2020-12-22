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

  //We can also have CLI functionality for what the user wants to do when the Speedrundao
  //is created.
  

  println("\nWelcome to Tristan's Project0 -- Speedrun.com Leaderboard Analysis")
  val url = CLI.createURL()
  println("\nURL successfully created. Requesting leaderboard JSON from speedrun.com...")
  val jsonString = LeaderboardUtilities.getLeaderboard(url)

  if (jsonString != null) 
  {
    println("Leaderboard JSON successfully retrieved")
    val jsoObj = LeaderboardUtilities.parseJson(jsonString)
    val speedrunDao = new SpeedrunDao(MongoClient())

    println("\nMongoDB initiated, parsing leaderboard json for speedrun data...")
    LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
    println("")

    var sessionConcluded = false
    while (!sessionConcluded)
    {
      val userRequest = CLI.getUserRequest()
      if (userRequest == 0){
        sessionConcluded = true
      }
      //call functions depending on which action user requests
    }



    // val speedrunSeq = speedrunDao.getAll()
    // for (run <- speedrunSeq) {
    //   println(run)
    // }
    speedrunDao.deleteAll()
  
  } else {
    println("No leaderboard retrieved. Exiting program...")
  }
}