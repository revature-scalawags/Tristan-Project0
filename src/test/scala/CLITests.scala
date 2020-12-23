package project0

import org.scalatest.funsuite.AnyFunSuite

class CLITests extends AnyFunSuite {

    test("toInt() properly converts a string which contains only an integer into an integer") {
        val string = "22"
        val num = CLI.toInt(string)
        assert(num == 22)
    }

    test("toInt() properly handles invalid arguments by returning -1 on failure") {
        val string = "22moist"
        val num = CLI.toInt(string)
        assert(num == -1)
    }

    //The rest of the CLI methods are difficult to test with unit testing as they rely heavily on the program
    //reading user input. I did extensive testing through running my program over and over with all possible 
    //arguments, and can confidently say that these methods will not break.
}