/** Tristan Cates' Project 0
 * 
 * This program creates an HTTP request to speedrun.com for a JSON of a leaderboard based upon the leaderboard
 * selected by the user (leaderboard game, category, and size can be specified). The program retrieves this JSON as 
 * a string, parses it into a JSON object using the play-json library, and then creates a SpeedrunDAO which contains 
 * all speedruns in the leaderboard requested.

 * The SpeedrunDAO consists of a MongoDB collection, where speedruns are the documents and their associated variables
 * are the fields. The program contains many methods which utilize MongoDB to anaylze and manipulate the data contained
 * in a DAO. The analysis executed depends on user input, just as the leaderboard retrieved does.
 * 
 * CLI.scala: Methods for getting user input and building a URL to send an HTTP request to speedrun.com
 * LeaderboardUtlities.scala: Methods for parsing data into speedrun objects from a JSON string retrieved from speedrun.com
 * Speedrun.scala: Case class for speedruns. SpeedrunDao.scala uses this case class to represent its members, speedruns on a leaderboard.
 * SpeedrunDao.scala: A data access object which is used to instantiate a MongoDB database and collection for speedruns.
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

    var sessionConcluded = false
    while (!sessionConcluded)
    {
      val userRequest = CLI.getUserRequest()
      if (userRequest == 0){
        sessionConcluded = true
      } 
      else {
      //call functions depending on which action user requests
        userRequest match {
          case 1 => val avgTime = speedrunDao.avgTime()
                    println("\nThe average time of speedruns in this leaderboard is (in seconds):")
                    println(avgTime)
          case 2 => val medianTime = speedrunDao.medianTime()
                    println("\n The median speedrunning time in this leaderboard is (in seconds):")
                    println(medianTime)
          case 3 => val speedrunSeq = speedrunDao.getAll()
                    println("")
                    for (run <- speedrunSeq) {
                      println(run)
                    }
          case 4 => println("Which place on the leaderboard do you want to display a speedrun for? (Min = 1, Max = 1000)")
                    println("If you enter a number greater than the total number of speedruns (places) retrieved, the program will return the speedrun with the highest place.")
                    val place = CLI.getPlaceChoice()-1
                    val speedrunAtPlace = speedrunDao.lookupByPlace(place)
                    println("\n" + speedrunAtPlace)
        }
      }
    }

    speedrunDao.deleteAll()
  
  } else {
    println("No leaderboard retrieved. Exiting program...")
  }
}