package util

import model.Poll

object PollParser {
  var pollData = List[Poll]()

  def parseDataFile(file: java.io.File): List[Poll] = {
    val start = System.currentTimeMillis()
    val bufferedSource = io.Source.fromFile(file)
    for (line <- bufferedSource.getLines.drop(1)) {
      val cols = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")
      val list = cols.map(entry => entry.replaceAll("\"", ""))
      val id = list(0)
      val city = list(1)
      val salary = list(2).toInt
      val position = list(3)
      val experience = list(4).toDouble
      val language = list(5)
      pollData ++= List(Poll(id, city, salary, position, experience, language))
    }
    bufferedSource.close

    //println(pollData)
    val stop = System.currentTimeMillis()
    println(stop - start)
    pollData
  }
}
