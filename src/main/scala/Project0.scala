/** Project0 TODO: add title comments to each file
  */
package project0

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer
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

import play.api.libs.json._
import java.awt.Taskbar.State
import java.io.FileNotFoundException
import java.util.concurrent.TimeUnit

object Project0 extends App {

  /**
    * parseJson: Function for parsing json
    * @param jsonString to parse
    * @return JsValue of a speedrun.com leaderboard to further parse with the play API
    */
  def parseJson(jsonString : String) : JsValue = {
    return Json.parse(jsonString)
  }

    /**
    * gatherSpeedruns: Function for parsing JsValue into values, 
    * which are used to populate a SpeedrunDao.
    * @param JsValue of a speedrun.com leaderboard to parse with play API
    * @param speedrunDao to populate with documents of speedruns
    * @return None
    */
  def gatherSpeedruns(jsObj: JsValue, speedrunDao: SpeedrunDao) : Unit ={
    val data = jsObj \ "data"
    val positions = (data \ "runs")
    val places = positions \\ "place"
    val runs = positions \\ "run"

    for (i <- 0 to runs.length-1){
      val place = places(i).as[Int]
      val weblink = (runs(i) \ "weblink").as[String]
      val date = (runs(i) \ "date").as[String]
      val time = (runs(i) \ "times" \ "primary_t").as[Int]

      var speedrun = Speedrun(place, weblink, date, time)
      speedrunDao.addSpeedrun(speedrun)
      // val speedrun = new Speedrun(place, weblink, date, time)
      // println(speedrun)
      // speedruns.append(speedrun)
    }
  }

  //MAIN:
  val input_file = "70StarTop2.json" //TODO: implement the ability for user to pick from a variety of different json leaderboards
  val jsonString = scala.io.Source.fromFile(input_file).mkString
  val jsoObj = parseJson(jsonString)
  
  //var speedruns = ArrayBuffer[Speedrun]()
  val speedrunDao = new SpeedrunDao(MongoClient())
  gatherSpeedruns(jsoObj, speedrunDao)

  val speedrunSeq = speedrunDao.getAll()
  for (run <- speedrunSeq){
    println(run)
  }
  speedrunDao.deleteAll()
}

//initial import---
//   object Project0 extends App {
  
//   val personDao = new PersonDao(MongoClient())

//   var fileName : String = ""
//   while (fileName == ""){
//     println("Please insert a valid file name for parsing")
//     fileName = scala.io.StdIn.readLine()
//     try{
//       val file = io.Source.fromFile(fileName)
//     }
//     catch{
//       case one: java.io.FileNotFoundException => println("Invalid file name supplied, please try again.")
//       fileName = ""
//     }
//   }

//   val file = io.Source.fromFile(fileName)
//   for (line <- file.getLines){
//         var name = line.split(",")(0).trim()
//         var age = line.split(",")(1).trim()
//         var person = Person(name, age.toInt)
//         personDao.addPerson(person)
//         println("here")
//   }



//   println("\n"+personDao.getAll())
//   println("\n"+personDao.getByName("John"))
//   println("\n"+personDao.getAdults()+ "\n")
//   personDao.deleteAllPeople()
// }