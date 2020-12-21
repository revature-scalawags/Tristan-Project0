package project0

import play.api.libs.json._
//import scala.io.Source
import java.net.{URL, HttpURLConnection}
import java.io.IOException

object LeaderboardUtilities {
  
/**
  * getFromURL: Returns the text (content) from a REST URL as a String.
  * If the request takes too long, a ReadTimeout occurs.
  * 
  * @param url The full URL to connect to.
  * @param readTimeout If the timeout expires before there is data available
  * for read, a java.net.SocketTimeoutException is raised.
  */
@throws(classOf[java.io.IOException])
private def getFromURL(url: String,
        readTimeout: Int) : String = 
{  
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod("GET")
    val inputStream = connection.getInputStream
    val content : String = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
}

/**
  * getLeaderBoard: Calls getFromURL with error catching for potential ReadTimeouts 
  * (where the request to speedrun.com takes too long).
  * 
  * @param url The full URL to connect to.
  * @param readTimeout If the timeout expires before there is data available
  * for read, a java.net.SocketTimeoutException is raised. A timeout of zero
  * is interpreted as an infinite timeout. Defaults to 20000 ms.
  */
def getLeaderboard(leaderboardURL : String,
        readTimeout: Int = 20000) : String =
{
    try {
        val content : String = getFromURL(leaderboardURL, readTimeout)
        content
    } catch {
        case ioe: IOException =>  
            println("There is an issue reading data from speedrun.com. Servers may be down. If servers are up, try increasing the time allowed before a ReadTimeout occurs.")
            null
    }
}

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
    }
  }

}
