Tristan Cates' Project 0

Overview:

In {project0}, the user is able to request a speedrun.com leaderboard of their choice through the parsing of 
speedrunning leaderboard retrieved as JSON files from the speedrun.com API. The user is then able to request
and analyze particular information in this leaderboard through a CLI, utlizing MongoDB for analysis.

To run the app, enter the following while in the {project0} folder:
sbt run

To run all tests written for {project0}:
sbt test


Program Explanation:

First, {Project0} creates an HTTP request to speedrun.com for a JSON of a leaderboard based upon the leaderboard
selected by the user. {Project0} retrieves this JSON as a string, parses it into a JSON object using the play-json
library, and then creates a SpeedrunDAO which contains all speedruns in the leaderboard requested.

The SpeedrunDAO consists of a MongoDB collection, where speedruns are the documents and their associated variables
are the fields. {Project0} contains many methods which utilize MongoDB to anaylze and manipulate the data contained
in a DAO. The analysis executed depends on user input, just as the leaderboard retrieved does.


Leaderboard Requesting Options:

Leaderboard Game and Category options are to come. 

User can input a number to limit the size of the leaderboard to be retrieved and analyzed. The maximum value allowed
by {project0} is 1000, but for many leaderboards there are likely to be fewer than 1000 entries.

70StarTop2.json contains an example JSON string of a leaderboard, one you would get from the speedrun.com API when 
requesting Super Mario 64, 70 star category, limited to the top 2 places on the leaderboard.


Leaderboard Analysis Options:

Leaderboard analysis options are to come.
