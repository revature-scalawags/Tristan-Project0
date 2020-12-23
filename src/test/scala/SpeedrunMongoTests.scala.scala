package project0

import org.scalatest.funsuite.AnyFunSuite

import org.mongodb.scala.MongoClient
import com.mongodb.BasicDBObject
import org.mongodb.scala.bson.collection.immutable.Document
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

class SpeedrunMongoTests extends AnyFunSuite {
    
    test("getLeaderboard gathers correct speedrun.com leaderboard") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=2&embed=players"
        val json = LeaderboardUtilities.getLeaderboard(exampleURL)
        val desiredJsonString = """{"data":{"weblink":"https://www.speedrun.com/sm64#70_Star","game":"o1y9wo6q","category":"7dgrrxk4","level":null,"platform":null,"region":null,"emulators":null,"video-only":false,"timing":"realtime","values":{},"runs":[{"place":1,"run":{"id":"znkq498z","weblink":"https://www.speedrun.com/sm64/run/znkq498z","game":"o1y9wo6q","level":null,"category":"7dgrrxk4","videos":{"links":[{"uri":"https://youtu.be/z8ptUGgsnjg"},{"uri":"https://youtu.be/31K19gJVorM"}]},"comment":"https://youtu.be/z8ptUGgsnjg","status":{"status":"verified","examiner":"qjo5lrl8","verify-date":"2020-06-12T23:41:59Z"},"players":[{"rel":"user","id":"zx7gnyx7","uri":"https://www.speedrun.com/api/v1/users/zx7gnyx7"}],"date":"2020-06-12","submitted":"2020-06-12T18:02:14Z","times":{"primary":"PT46M59S","primary_t":2819,"realtime":"PT46M59S","realtime_t":2819,"realtime_noloads":null,"realtime_noloads_t":0,"ingame":null,"ingame_t":0},"system":{"platform":"w89rwelk","emulated":false,"region":"pr184lqn"},"splits":null,"values":{"e8m7em86":"9qj7z0oq","kn04ewol":"5q8e86rq"}}},{"place":2,"run":{"id":"zn3pr37y","weblink":"https://www.speedrun.com/sm64/run/zn3pr37y","game":"o1y9wo6q","level":null,"category":"7dgrrxk4","videos":{"links":[{"uri":"https://youtu.be/xMMR5tDQyMo"}]},"comment":null,"status":{"status":"verified","examiner":"zx7gkrx7","verify-date":"2020-11-12T00:03:55Z"},"players":[{"rel":"user","id":"zx76v0j7","uri":"https://www.speedrun.com/api/v1/users/zx76v0j7"}],"date":"2020-11-11","submitted":"2020-11-11T01:42:44Z","times":{"primary":"PT47M18S","primary_t":2838,"realtime":"PT47M18S","realtime_t":2838,"realtime_noloads":null,"realtime_noloads_t":0,"ingame":null,"ingame_t":0},"system":{"platform":"w89rwelk","emulated":false,"region":"pr184lqn"},"splits":null,"values":{"e8m7em86":"9qj7z0oq","kn04ewol":"5q8e86rq"}}}],"links":[{"rel":"game","uri":"https://www.speedrun.com/api/v1/games/o1y9wo6q"},{"rel":"category","uri":"https://www.speedrun.com/api/v1/categories/7dgrrxk4"}],"players":{"data":[{"rel":"user","id":"zx7gnyx7","names":{"international":"Dwhatever","japanese":null},"weblink":"https://www.speedrun.com/user/Dwhatever","name-style":{"style":"gradient","color-from":{"light":"#4646CE","dark":"#6666EE"},"color-to":{"light":"#4646CE","dark":"#6666EE"}},"role":"user","signup":"2015-02-09T12:24:53Z","location":{"country":{"code":"de","names":{"international":"Germany","japanese":null}}},"twitch":{"uri":"https://www.twitch.tv/Dwhatever"},"hitbox":null,"youtube":{"uri":"https://www.youtube.com/channel/UChCayuODvETZ9Zh2ZZB6UdQ"},"twitter":null,"speedrunslive":{"uri":"http://www.speedrunslive.com/profiles/#!/Dwhatever"},"links":[{"rel":"self","uri":"https://www.speedrun.com/api/v1/users/zx7gnyx7"},{"rel":"runs","uri":"https://www.speedrun.com/api/v1/runs?user=zx7gnyx7"},{"rel":"games","uri":"https://www.speedrun.com/api/v1/games?moderator=zx7gnyx7"},{"rel":"personal-bests","uri":"https://www.speedrun.com/api/v1/users/zx7gnyx7/personal-bests"}]},{"rel":"user","id":"zx76v0j7","names":{"international":"Taggo","japanese":null},"weblink":"https://www.speedrun.com/user/Taggo","name-style":{"style":"gradient","color-from":{"light":"#EE2222","dark":"#EE4444"},"color-to":{"light":"#EE2222","dark":"#EE4444"}},"role":"user","signup":"2015-01-03T01:40:42Z","location":{"country":{"code":"se","names":{"international":"Sweden","japanese":null}},"region":{"code":"se/bd","names":{"international":"Norrbotten, Sweden","japanese":null}}},"twitch":{"uri":"https://www.twitch.tv/Taggo"},"hitbox":null,"youtube":{"uri":"https://www.youtube.com/user/taggospeedrun"},"twitter":{"uri":"https://www.twitter.com/Taggo3435"},"speedrunslive":{"uri":"http://www.speedrunslive.com/profiles/#!/Taggo3435"},"links":[{"rel":"self","uri":"https://www.speedrun.com/api/v1/users/zx76v0j7"},{"rel":"runs","uri":"https://www.speedrun.com/api/v1/runs?user=zx76v0j7"},{"rel":"games","uri":"https://www.speedrun.com/api/v1/games?moderator=zx76v0j7"},{"rel":"personal-bests","uri":"https://www.speedrun.com/api/v1/users/zx76v0j7/personal-bests"}]}]}}}"""
        assert(json == desiredJsonString)
    }

    test("getLeaderboard properly times out and returns null if no read occurs within the readTimeout value") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=2&embed=players"
        val json = LeaderboardUtilities.getLeaderboard(exampleURL, 1)
        assert(json == null)
    }

    test("gatherSpeedruns successfully populates a SpeedrunDao with speedrun instances as documents") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=2&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        val speedrunSeq = speedrunDao.getAll()
        assert(speedrunSeq.length > 0)
        speedrunDao.deleteAll()
    }

    //FAILURE: IF NEW SPEEDRUNS ARE ADDED must reevaluate desiredAvgTime so this test will pass
    test("SpeedrunDao.avgTime() computes correct average time Test1 - Mario") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=100&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        val avgTime = speedrunDao.avgTime()
        val desiredAvgTime = 2943.63
        speedrunDao.deleteAll()
        assert((avgTime+.01) > desiredAvgTime && (avgTime-.01) < desiredAvgTime)   
        //actual average time goes on for many decimal places, the above logic is to allow a difference
        // of at most .01 between the average time retrieved and the desired average time
    }

    //FAILURE: IF NEW SPEEDRUNS ARE ADDED must reevaluate desiredAvgTime so this test will pass
    test("SpeedrunDao.avgTime() computes correct average time Test2 - Spongebob") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/m1mxx362/category/zd3ymr2n?top=100&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        val avgTime = speedrunDao.avgTime()
        val desiredAvgTime = 3553.82
        speedrunDao.deleteAll()
        assert((avgTime+.01) > desiredAvgTime && (avgTime-.01) < desiredAvgTime)  
        //actual average time goes on for many decimal places, the above logic is to allow a difference
        // of at most .01 between the average time retrieved and the desired average time 
    }

    //FAILURE: IF NEW SPEEDRUNS ARE ADDED must reevaluate desiredAvgTime so this test will pass
    test("SpeedrunDao.avgTime() computes correct average time Test3 - Undertale") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/4d73n317/category/02qgm7jd?top=100&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        val avgTime = speedrunDao.avgTime()
        val desiredAvgTime = 3606.26
        speedrunDao.deleteAll()
        assert((avgTime+.01) > desiredAvgTime && (avgTime-.01) < desiredAvgTime) 
        //actual average time goes on for many decimal places, the above logic is to allow a difference
        // of at most .01 between the average time retrieved and the desired average time  
    }

    //FAILURE: IF NEW SPEEDRUNS ARE ADDED must reevaluate desiredMedTime so this test will pass
    test("SpeedrunDao.medianTime() computes correct median time Test1 - Mario") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=100&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        val medTime = speedrunDao.medianTime()
        val desiredMedTime = 2954.5
        speedrunDao.deleteAll()
        assert(medTime == desiredMedTime)   
    }

    //FAILURE: IF NEW SPEEDRUNS ARE ADDED must reevaluate desiredMedTime so this test will pass
    test("SpeedrunDao.medianTime() computes correct median time Test2 - Spongebob") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/m1mxx362/category/zd3ymr2n?top=100&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        val medTime = speedrunDao.medianTime()
        val desiredMedTime = 3580.5
        speedrunDao.deleteAll()
        assert(medTime == desiredMedTime)   
    }

    //FAILURE: IF NEW SPEEDRUNS ARE ADDED must reevaluate desiredMedTime so this test will pass
    test("SpeedrunDao.medianTime() computes correct median time Test3 - Undertale") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/4d73n317/category/02qgm7jd?top=100&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        val medTime = speedrunDao.medianTime()
        val desiredMedTime = 3611.0
        speedrunDao.deleteAll()
        assert(medTime == desiredMedTime)   
    }


    test("SpeedrunDao.lookupByPlace returns speedrun with correct place value") {
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=100&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        val desiredPlace = 36
        val speedrun = speedrunDao.lookupByPlace(desiredPlace)
        val place = speedrun.place
        speedrunDao.deleteAll()
        assert(place == desiredPlace)   
    }



    test("SpeedrunDao.deleteAll() removes all documents from the Speedrun DAO"){
        val exampleURL = "https://www.speedrun.com/api/v1/leaderboards/o1y9wo6q/category/7dgrrxk4?top=2&embed=players"
        val jsonString = LeaderboardUtilities.getLeaderboard(exampleURL)
        val jsoObj = LeaderboardUtilities.parseJson(jsonString)
        
        val speedrunDao = new SpeedrunDao(MongoClient())
        LeaderboardUtilities.gatherSpeedruns(jsoObj, speedrunDao)
        speedrunDao.deleteAll()
        val speedrunSeq = speedrunDao.getAll()
        assert(speedrunSeq.length == 0)
    }
}