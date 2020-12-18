package project0

import org.bson.types.ObjectId

case class Person(_id: ObjectId, name: String, age: Int) {}

object Person {
  def apply(name: String, count: Int): Person =
    Person(new ObjectId(), name, count)
}