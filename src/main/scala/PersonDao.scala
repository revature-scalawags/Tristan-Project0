package project0

import org.mongodb.scala.bson.codecs.Macros._

import org.mongodb.scala.MongoClient
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoCollection
import scala.concurrent.Await
import org.mongodb.scala.Observable
import scala.concurrent.duration.{Duration, SECONDS}
import org.mongodb.scala.model.Filters._

class PersonDao(mongoClient: MongoClient) {
  val codecRegistry = fromRegistries(
    fromProviders(classOf[Person]),
    MongoClient.DEFAULT_CODEC_REGISTRY
  )
  val db = mongoClient.getDatabase("persondb").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Person] = db.getCollection("person")

  private def getResults[T](obs: Observable[T]): Seq[T] = {
      Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  def addPerson(person : Person) : Unit = {
    getResults(collection.insertOne(person))
  }

  def getAll(): Seq[Person] = getResults(collection.find())

  def getByName(name: String) : Seq[Person] = {
    getResults(collection.find(equal("name", name)))
  }

  def getAdults() : Seq[Person] = {
    getResults(collection.find(gte("age",18)))
  }

  def deleteAllPeople() {
    getResults(collection.deleteMany(gte("age",0)))
  }
}
