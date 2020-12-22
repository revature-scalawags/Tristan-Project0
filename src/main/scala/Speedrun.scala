package project0

import play.api.libs.json._
import org.bson.types.ObjectId


//TODO: Add speedrunID and possibly other fields as well
case class Speedrun(
    _id : ObjectId,
    place: Int, //place in current leaderboard
    weblink: String, //link to speedrun's page on speedrun.com
    date: String,
    time: Float   //total time of speedrun (in seconds)
){

    override def toString: String = {
        s"place: $place \nweblink: $weblink \ndate: $date \ntime: $time"
    }
}

object Speedrun{
  def apply(place: Int, weblink: String, date: String, time: Float): Speedrun =
    Speedrun(new ObjectId(), place, weblink, date, time)
}