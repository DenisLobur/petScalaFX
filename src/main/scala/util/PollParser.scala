package util

import model.Poll

object PollParser {

  def parseDataFile(file: java.io.File): List[Poll] = {
    val start = System.currentTimeMillis()
    var pollData = List[Poll]()
    val bufferedSource = io.Source.fromFile(file)
    for (line <- bufferedSource.getLines.drop(1)) {
      val poll = periodToDataParser(file.getName, line)
      pollData ++= List(poll)
    }
    bufferedSource.close

    val stop = System.currentTimeMillis()
    println(s"Parse time: ${stop - start}")
    pollData
  }

  private def periodToDataParser(fileName: String, line: String): Poll = {
    val cols = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")
    val list = cols.map(entry => entry.replaceAll("\"", ""))

    val poll: Poll = fileName match {
      case PollPeriod.December2011.name | PollPeriod.May2012.name => {
        val id = list(0)
        val city = list(1)
        val salary = list(2).toDouble
        val position = list(3)
        val experience = list(4).toDouble
        val language = list(5)
        Poll(id, city, salary, position, experience, language)
      }
      case PollPeriod.May2011.name => {
        val id = list(0)
        val city = list(17)
        val salary = list(23).toDouble
        val position = list(4)
        val nums = "[0-9]".r
        val experience = {
          val period = list(10)
          val parsed = nums.findAllIn(period).mkString.toDouble
          parsed
        }
        val language = list(6)
        Poll(id, city, salary, position, experience, language)
      }
      case PollPeriod.December2012.name | PollPeriod.December2013.name | PollPeriod.May2013.name
           | PollPeriod.December2014.name | PollPeriod.May2014.name | PollPeriod.December2015.name
           | PollPeriod.May2015.name | PollPeriod.December2016.name | PollPeriod.May2016.name
           | PollPeriod.December2017.name | PollPeriod.May2017.name => {
        val id = list(0)
        val city = list(1)
        val salary = list(2).toDouble
        val position = list(4)
        val experience = list(5).toDouble
        val language = list(7)
        Poll(id, city, salary, position, experience, language)
      }
      case PollPeriod.May2018.name => {
        val id = list(0)
        val city = list(1)
        val salary = list(2).toDouble
        val position = list(4)
        val nums = "[0-9]".r
        val experience = list(5).toDouble
        val language = list(7)
        Poll(id, city, salary, position, experience, language)
      }
      case _ => throw new IllegalArgumentException("No such poll period")
    }

    poll
  }
}
