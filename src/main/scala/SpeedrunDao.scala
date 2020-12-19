package project0

import org.mongodb.scala.bson.codecs.Macros._

import org.mongodb.scala.MongoClient
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoCollection
import scala.concurrent.Await
import org.mongodb.scala.Observable
import scala.concurrent.duration.{Duration, SECONDS}
import org.mongodb.scala.model.Filters._

class SpeedrunDao(mongoClient: MongoClient) {
  val codecRegistry = fromRegistries(
    fromProviders(classOf[Speedrun]),
    MongoClient.DEFAULT_CODEC_REGISTRY
  )
  val db = mongoClient.getDatabase("speedrundb").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Speedrun] = db.getCollection("speedrun")

  private def getResults[T](obs: Observable[T]): Seq[T] = {
      Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  def addSpeedrun(speedrun : Speedrun) : Unit = {
    getResults(collection.insertOne(speedrun))
  }

  def getAll(): Seq[Speedrun] = getResults(collection.find())

  def deleteAll(): Unit = {
      getResults(collection.deleteMany(gte("place",0)))
  }
  //TODO: Add many methods for transforming and analyzing of data. 
  //We can work with place, time, date, and speedrunID once that is added to Speedrun.scala
}