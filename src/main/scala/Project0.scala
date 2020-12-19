package project0

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer
// import scala.concurrent.Await
// import scala.concurrent.duration.Duration
// import scala.concurrent.Future
// import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import play.api.libs.json._

import org.mongodb.scala.MongoClient
import org.bson.codecs.configuration.CodecRegistries.{
  fromProviders,
  fromRegistries
}
import com.mongodb.BasicDBObject
import java.util.concurrent.TimeUnit
import org.mongodb.scala.bson.collection.immutable.Document
import java.awt.Taskbar.State
import java.io.FileNotFoundException

/** Project0
  */
object Project0 extends App {

  def parseJson(jsonString : String) : JsValue = {
    return Json.parse(jsonString)
  }

  def gatherSpeedruns(jsObj: JsValue, speedruns: ArrayBuffer[Speedrun]) : Unit ={
    val positions = (jsObj \ "runs").get

    val places = positions \\ "place"
    val runs = positions \\ "run"

    for (i <- 0 to runs.length-1){
      val place = places(i) 
      val weblink = (runs(i) \ "weblink").get
      val date = (runs(i) \ "date").get
      val time = (runs(i) \ "times" \ "primary_t").get

      println(place)
    }
  }

  //TODO: implement the ability for user to pick from a variety of different json leaderboards
  val jsoObj = parseJson("70StarTop10.json")

  var speedruns = ArrayBuffer[Speedrun]()
  //gatherSpeedruns(jsoObj, speedruns)
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