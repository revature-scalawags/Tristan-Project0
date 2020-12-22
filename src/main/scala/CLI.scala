package project0

import scala.io._

object CLI {

    //The following are ID strings used to create a URL to lookup a leaderboard from speedrun.com. 
    //These URLs require both a game ID and a category ID for the leaderboard desired. 
    //These are contained in CLI.scala as the URL strings are built based on command line interaction.

    //Super Mario 64
    private val sm64_ID = "o1y9wo6q" //Game ID
    private val sm64_120Star = "wkpoo02r" //---Category IDs---
    private val sm64_70Star = "7dgrrxk4"
    private val sm64_16Star = "n2y55mko"
    private val sm64_1Star = "7kjpp4k3"
    private val sm64_0Star = "xk9gg6d0"

    //The Legend of Zelda: Breath of the Wild
    private val botw_ID = "76rqjqd8" //Game ID
    private val botw_AnyPercent = "vdoq4xvk" //---Category IDs---
    private val botw_100Percent = "xk9jv4gd"
    private val botw_AllShrines = "wkpqmw8d"

    //Minecraft: Java Edition
    private val minecraft_ID = "j1npme6p" //Game ID
    private val minecraft_AnyPercentGlitchless = "mkeyl926" //---Category IDs---
    private val minecraft_AnyPercent = "wkpn0vdr"

    //Spongebob Squarepants: Battle for Bikini Bottom
    private val bfbb_ID = "m1mxx362" //Game ID
    private val bfbb_AnyPercent = "zd3ymr2n" //---Category IDs---
    private val bfbb_100Percent = "9kvxg32g"

    //Portal
    private val portal_ID = "4pd0n31e" //Game ID
    private val portal_OutOfBounds = "lvdowokp" //---Category IDs---
    private val portal_Inbounds = "7wkp6v2r"
    private val portal_Glitchless = "wk6pexd1"

    //Dark Souls
    private val darksouls_ID = "w6jve26j" //Game ID
    private val darksouls_AnyPercent = "9d86vww2" //---Category IDs---
    private val darksouls_NoWrongWarp = "7dg75ld4"
    private val darksouls_AllBosses = "mke7jn26"

    //Grand Theft Auto: San Andreas
    private val gtasa_ID = "yo1yv1q5" //Game ID
    private val gtasa_AnyPercentNoAGS = "4xk906k0" //---Category IDs---
    private val gtasa_AnyPercent = "824gp6g2"
    private val gtasa_100Percent = "wz27gz20"

    //Undertale
    private val undertale_ID = "4d73n317" //Game ID
    private val undertale_Neutral = "02qgm7jd" //---Category IDs---
    private val undertale_TruePacifist = "824xj9md"
    private val undertale_Genocide = "jdz36o32"


    // toInt: Attempts to convert a string to an integer. Returns 0 on failure.
    def toInt(s: String): Int = {
    try {
        s.toInt
    } catch {
        case e: Exception => 0
    }
    }

    // getGameID: Gets and returns the user's choice of game. Handles invalid input (anything other than the numbers 1-8)
    // Returns the ID of the game to be used in URL creation.
    def getGameID() : String = 
    {
        println("Which game are you interested in retrieving a speedrunning leaderboard for?")
        println("(1) Super Mario 64 \n(2) The Legend of Zelda: Breath of the Wild \n(3) Minecraft: Java Edition \n(4) Spongebob Squarepants: Battle for Bikini Bottom")
        println("(5) Portal \n(6) Dark Souls \n(7) Grand Theft Auto: San Andreas \n(8) Undertale")
  
        var gameChoice = 0
        var validGameChoice = false
        while (!validGameChoice)
        {
            println("Enter a number (1-8): ")
            var gameChoiceString = scala.io.StdIn.readLine()
            gameChoice = toInt(gameChoiceString)
            if (gameChoice >= 1 && gameChoice <= 8) {
                validGameChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 8.")
            }
        }

        var gameID = ""
        gameChoice match {
            case 1 => gameID = sm64_ID
            case 2 => gameID = botw_ID
            case 3 => gameID = minecraft_ID
            case 4 => gameID = bfbb_ID
            case 5 => gameID = portal_ID
            case 6 => gameID = darksouls_ID
            case 7 => gameID = gtasa_ID
            case 8 => gameID = undertale_ID
        }
        gameID
    }

    // Gets and returns the user's choice of category for Super Mario 64. Handles invalid input.
    def get_sm64_category_ID() : String = {
        println("Which category of Super Mario 64 do you wish to retrieve a speedrunning leaderboard for?")
        println("(1) 120 Star \n(2) 70 Star \n(3) 16Star \n(4) 1Star \n(5) 0Star")
  
        var categoryChoice = 0
        var validCategoryChoice = false
        while (!validCategoryChoice)
        {
            println("Enter a number (1-5): ")
            var categoryChoiceString = scala.io.StdIn.readLine()
            categoryChoice = toInt(categoryChoiceString)
            if (categoryChoice >= 1 && categoryChoice <= 5) {
                validCategoryChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 5.")
            }
        }

        var categoryID = ""
        categoryChoice match {
            case 1 => categoryID = sm64_120Star
            case 2 => categoryID = sm64_70Star
            case 3 => categoryID = sm64_16Star
            case 4 => categoryID = sm64_1Star
            case 5 => categoryID = sm64_0Star
        }
        categoryID
    }

    // Gets and returns the user's choice of category for The Legend of Zelda: Breath of the Wild. Handles invalid input.
    def get_botw_category_ID() : String = {
        println("Which category of The Legend of Zelda: Breath of the Wild do you wish to retrieve a speedrunning leaderboard for?")
        println("(1) Any% \n(2) 100% \n(3) AllShrines")
  
        var categoryChoice = 0
        var validCategoryChoice = false
        while (!validCategoryChoice)
        {
            println("Enter a number (1-3): ")
            var categoryChoiceString = scala.io.StdIn.readLine()
            categoryChoice = toInt(categoryChoiceString)
            if (categoryChoice >= 1 && categoryChoice <= 3) {
                validCategoryChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 3.")
            }
        }

        var categoryID = ""
        categoryChoice match {
            case 1 => categoryID = botw_AnyPercent
            case 2 => categoryID = botw_100Percent
            case 3 => categoryID = botw_AllShrines
        }
        categoryID
    }

    // Gets and returns the user's choice of category for Minecraft: Java Edition. Handles invalid input.
    def get_minecraft_category_ID() : String = {
        println("Which category of Minecraft: Java Edition do you wish to retrieve a speedrunning leaderboard for?")
        println("The categories below are for single-segment seeds. There is no current functionality for random seed minecraft leaderboards.")
        println("(1) Any% Glitchless \n(2) Any%")
  
        var categoryChoice = 0
        var validCategoryChoice = false
        while (!validCategoryChoice)
        {
            println("Enter a number (1-2): ")
            var categoryChoiceString = scala.io.StdIn.readLine()
            categoryChoice = toInt(categoryChoiceString)
            if (categoryChoice >= 1 && categoryChoice <= 2) {
                validCategoryChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 2.")
            }
        }

        var categoryID = ""
        categoryChoice match {
            case 1 => categoryID = minecraft_AnyPercentGlitchless
            case 2 => categoryID = minecraft_AnyPercent
        }
        categoryID
    }

    // Gets and returns the user's choice of category for Spongebob Squarepants: Battle for Bikini Bottom. Handles invalid input.
    def get_bfbb_category_ID() : String = {
        println("Which category of Spongebob Squarepants: Battle for Bikini Bottom do you wish to retrieve a speedrunning leaderboard for?")
        println("(1) Any% \n(2) 100%")
  
        var categoryChoice = 0
        var validCategoryChoice = false
        while (!validCategoryChoice)
        {
            println("Enter a number (1-2): ")
            var categoryChoiceString = scala.io.StdIn.readLine()
            categoryChoice = toInt(categoryChoiceString)
            if (categoryChoice >= 1 && categoryChoice <= 2) {
                validCategoryChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 2.")
            }
        }

        var categoryID = ""
        categoryChoice match {
            case 1 => categoryID = bfbb_AnyPercent
            case 2 => categoryID = bfbb_100Percent
        }
        categoryID
    }

    // Gets and returns the user's choice of category for Portal. Handles invalid input.
    def get_portal_category_ID() : String = {
        println("Which category of Portal do you wish to retrieve a speedrunning leaderboard for?")
        println("(1) Out of Bounds \n(2) Inbounds \n(3) Glitchless")
  
        var categoryChoice = 0
        var validCategoryChoice = false
        while (!validCategoryChoice)
        {
            println("Enter a number (1-3): ")
            var categoryChoiceString = scala.io.StdIn.readLine()
            categoryChoice = toInt(categoryChoiceString)
            if (categoryChoice >= 1 && categoryChoice <= 3) {
                validCategoryChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 3.")
            }
        }

        var categoryID = ""
        categoryChoice match {
            case 1 => categoryID = portal_OutOfBounds
            case 2 => categoryID = portal_Inbounds
            case 3 => categoryID = portal_Glitchless
        }
        categoryID
    }

    // Gets and returns the user's choice of category for Dark Souls. Handles invalid input.
    def get_darksouls_category_ID() : String = {
        println("Which category of Dark Souls do you wish to retrieve a speedrunning leaderboard for?")
        println("(1) Any% \n(2) No Wrong Warp \n(3) AllBosses")
  
        var categoryChoice = 0
        var validCategoryChoice = false
        while (!validCategoryChoice)
        {
            println("Enter a number (1-3): ")
            var categoryChoiceString = scala.io.StdIn.readLine()
            categoryChoice = toInt(categoryChoiceString)
            if (categoryChoice >= 1 && categoryChoice <= 3) {
                validCategoryChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 3.")
            }
        }

        var categoryID = ""
        categoryChoice match {
            case 1 => categoryID = darksouls_AnyPercent
            case 2 => categoryID = darksouls_NoWrongWarp
            case 3 => categoryID = darksouls_AllBosses
        }
        categoryID
    }

    // Gets and returns the user's choice of category for Grand Theft Auto: San Andreas. Handles invalid input.
    def get_gtasa_category_ID() : String = {
        println("Which category of Grand Theft Auto: San Andreas do you wish to retrieve a speedrunning leaderboard for?")
        println("(1) Any% No AGS \n(2) Any% \n(3) 100%")
  
        var categoryChoice = 0
        var validCategoryChoice = false
        while (!validCategoryChoice)
        {
            println("Enter a number (1-3): ")
            var categoryChoiceString = scala.io.StdIn.readLine()
            categoryChoice = toInt(categoryChoiceString)
            if (categoryChoice >= 1 && categoryChoice <= 3) {
                validCategoryChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 3.")
            }
        }

        var categoryID = ""
        categoryChoice match {
            case 1 => categoryID = gtasa_AnyPercentNoAGS
            case 2 => categoryID = gtasa_AnyPercent
            case 3 => categoryID = gtasa_100Percent
        }
        categoryID
    }

    // Gets and returns the user's choice of category for Undertale. Handles invalid input.
    def get_undertale_category_ID() : String = {
        println("Which category of Undertale do you wish to retrieve a speedrunning leaderboard for?")
        println("(1) Neutral \n(2) True Pacifist \n(3) Genocide")
  
        var categoryChoice = 0
        var validCategoryChoice = false
        while (!validCategoryChoice)
        {
            println("Enter a number (1-3): ")
            var categoryChoiceString = scala.io.StdIn.readLine()
            categoryChoice = toInt(categoryChoiceString)
            if (categoryChoice >= 1 && categoryChoice <= 3) {
                validCategoryChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 3.")
            }
        }

        var categoryID = ""
        categoryChoice match {
            case 1 => categoryID = undertale_Neutral
            case 2 => categoryID = undertale_TruePacifist
            case 3 => categoryID = undertale_Genocide
        }
        categoryID
    }

    // getCategoryID: Gets and returns the user's choice of game category (for leaderboard request). Handles invalid input 
    // (anything other than the range of numbers corresponding to the game's categories. Returns the ID of the game category 
    // to be used in URL creation.
    def getCategoryID(gameID : String) : String =
    {
        var categoryID = ""
        gameID match {
            case `sm64_ID` => categoryID = get_sm64_category_ID()
            case `botw_ID` => categoryID = get_botw_category_ID()
            case `minecraft_ID` => categoryID = get_minecraft_category_ID()
            case `bfbb_ID` => categoryID = get_bfbb_category_ID()
            case `portal_ID` => categoryID = get_portal_category_ID()
            case `darksouls_ID` => categoryID = get_darksouls_category_ID()
            case `gtasa_ID` => categoryID = get_gtasa_category_ID()
            case `undertale_ID` => categoryID = get_undertale_category_ID()
        }
        categoryID
    }


    // createURL: Creates the URL to create an HTTP GET request to speedrun.com with. URL is created based on which leaderboard 
    // a user selects. The user has a choice of game and a choice of category. The URL is then created and returned.
    def createURL() : String = 
    {
        println("")
        val gameID = getGameID()
        println("")
        val categoryID = getCategoryID(gameID)
        println("\nDecide how many places on the leaderboard you wish to limit your results to (Min = 1, Max = 1000)")
        println("For instance, enter 10 to retrieve a leaderboard of the top 10 speedruns in this category.")

        var placesChoice = 0
        var validPlacesChoice = false
        while (!validPlacesChoice)
        {
            println("Enter a number (1-1000): ")
            var placesChoiceString = scala.io.StdIn.readLine()
            placesChoice = toInt(placesChoiceString)
            if (placesChoice >= 1 && placesChoice <= 1000) {
                validPlacesChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 1000.")
            }
        }
        
        //BUILD URL and return
        f"https://www.speedrun.com/api/v1/leaderboards/$gameID/category/$categoryID?top=$placesChoice&embed=players"
    }

    // getUserRequest: Gets and returns user input for what action to take once speedrunning data has already
    // been gathered into a SpeedrunDao.
    def getUserRequest() : Int = 
    {
        println("What do you wish to do with the speedrunning data retrieved?")
        println("(1) Do this \n(2) Do this \n(3) Do this \n(0) Exit program")
       
        var userChoice = -1
        var validUserChoice = false
        while (!validUserChoice)
        {
            println("Enter a number (0-3): ")
            var userChoiceString = scala.io.StdIn.readLine()
            userChoice = toInt(userChoiceString)
            if (userChoice >= 0 && userChoice <= 3) {
                validUserChoice = true
            }
            else {
                println("Invalid input. Please enter a number between 1 and 1000.")
            }
        }
        userChoice
    }
}