/** Project0 TODO: add title comments to each file
  */
package project0

// import scala.collection.mutable.Map
// import scala.collection.mutable.ArrayBuffer
// import scala.concurrent.Await
// import scala.concurrent.duration.Duration
// import scala.concurrent.Future
// import scala.concurrent.ExecutionContext.Implicits.global
// import scala.util.{Failure, Success}

import org.mongodb.scala.MongoClient
import com.mongodb.BasicDBObject
import org.mongodb.scala.bson.collection.immutable.Document
import org.bson.codecs.configuration.CodecRegistries.{
  fromProviders,
  fromRegistries
}

//import play.api.libs.json._
import java.awt.Taskbar.State
import java.io.FileNotFoundException
import java.util.concurrent.TimeUnit

object Project0 extends App {

  //TODO: Implement CLI with the following steps:
  //(1) First, have user select from a list of popular games
  //(2) Then have user select from a list of the games' categories
  //(3) Then have user input the size they want the retrieved leaderboard to be 
  //(#places, #speedruns will be greater than #places with ties)
  //(4) Finally, use this information to create a url string to pass to getLeaderboard in LeaderboardUtilities

  //We can also have CLI functionality for what the user wants to do when the Speedrundao
  //is created.
  
  val jsonString = LeaderboardUtilities.getLeaderboard("https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=2&embed=players")

  if (jsonString != null){
    val jsoObj = LeaderboardUtilities.parseJson(jsonString)
    val speedrunDao = new SpeedrunDao(MongoClient())
    LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)

    val speedrunSeq = speedrunDao.getAll()
    println("\n\n\n")
    for (run <- speedrunSeq){
      println(run)
    }
    speedrunDao.deleteAll()
  
  } else {
    println("No leaderboard retrieved. Exiting program...")
  }
}