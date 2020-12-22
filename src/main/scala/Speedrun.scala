/** Speedrun.scala
 *  This file contains the speedrun case class, which represents a speedrun from a speedrun.com leaderboard
 *  and its relevant data. SpeedrunDao.scala uses this case class to represent its members, speedruns on a leaderboard.
 */
package project0

import play.api.libs.json._
import org.bson.types.ObjectId

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