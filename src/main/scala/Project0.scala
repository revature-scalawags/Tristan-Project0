package project0

import scala.collection.mutable.Map
// import scala.concurrent.Await
// import scala.concurrent.duration.Duration
// import scala.concurrent.Future
// import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

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
  
  val personDao = new PersonDao(MongoClient())

  var fileName : String = ""
  while (fileName == ""){
    println("Please insert a valid file name for parsing")
    fileName = scala.io.StdIn.readLine()
    try{
      val file = io.Source.fromFile(fileName)
    }
    catch{
      case one: java.io.FileNotFoundException => println("Invalid file name supplied, please try again.")
      fileName = ""
    }
  }

  val file = io.Source.fromFile(fileName)
  for (line <- file.getLines){
        var name = line.split(",")(0).trim()
        var age = line.split(",")(1).trim()
        var person = Person(name, age.toInt)
        personDao.addPerson(person)
        println("here")
  }



  println("\n"+personDao.getAll())
  println("\n"+personDao.getByName("John"))
  println("\n"+personDao.getAdults()+ "\n")
  personDao.deleteAllPeople()
}