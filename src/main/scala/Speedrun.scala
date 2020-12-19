package project0

import play.api.libs.json._

case class Speedrun(
    place: Int, //place in current leaderboard
    weblink: String, //link to speedrun's page on speedrun.com
    date: String,
    time: Int   //time of speedrun (in seconds)
)