/** SpeedrunDao.scala
 *  This file contains SpeedrunDao, a data access object which is used to instantiate a MongoDB database and collection
 *  for speedruns. Speedruns are added to this DAO as documents, whose contents are analyzed using methods within this class.
 */
package project0

import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.Observable
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}


class SpeedrunDao(mongoClient: MongoClient) extends LazyLogging {
  val codecRegistry = fromRegistries(
    fromProviders(classOf[Speedrun]),
    MongoClient.DEFAULT_CODEC_REGISTRY
  )
  val db = mongoClient.getDatabase("speedrundb").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Speedrun] = db.getCollection("speedrun")

  private def getResults[T](obs: Observable[T]): Seq[T] = {
      Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  //addSpeedrun: Adds a speedrun to this DAO
  def addSpeedrun(speedrun : Speedrun) : Unit = {
    getResults(collection.insertOne(speedrun))
  }

  //getAll: Retrieves a sequence of all speedruns in this DAO
  def getAll(): Seq[Speedrun] = getResults(collection.find())

  //deleteAll: Deletes all speedruns (documents) in this DAO
  def deleteAll(): Unit = {
      getResults(collection.deleteMany(gte("place",0)))
  }

  //avgTime: Computes and returns the average time of speedruns in this collection
  def avgTime() : Float = {
    val speedruns = getAll() 
    val speedrunsLength = speedruns.length
    var totalTime : Float = 0
    
    for (i <- 0 to speedrunsLength-1){
      totalTime += speedruns(i).time    
    }
    val averageTime = totalTime / speedrunsLength
    averageTime
  }

  //avgTime: Computes and returns the median time of speedruns in this collection
  def medianTime() : Float = {
    val speedruns = getAll()
    val speedrunsLength = speedruns.length
    var medTime : Float = 0
    
    if (speedrunsLength % 2 == 0) {
      medTime = (speedruns((speedrunsLength/2)-1).time + speedruns(speedrunsLength/2).time) / 2
    } else {
      medTime = speedruns(speedrunsLength/2).time
    }
    medTime
  }

  //lookupByPlace: Gets and returns a speedrun with the specified place in the leaderboard
  def lookupByPlace(place : Int) : Speedrun = {
    val speedruns = getAll()
    val speedrunsLength = speedruns.length

    if (place >= speedrunsLength-1){
      speedruns(speedrunsLength-1)
    } else {
      speedruns(place)
    }
  }
}