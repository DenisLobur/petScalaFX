import model.Poll

class PollParser {
  var pollData = List[Poll]()

  def parseDataFile(file: java.io.File): Unit = {
    val bufferedSource = io.Source.fromFile(file)
    for (line <- bufferedSource.getLines.drop(1)) {
      val cols = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")
      val list = cols.map(entry => entry.replaceAll("\"", ""))
      pollData ++= List(Poll(list(0), list(1), list(2).toDouble, list(3).toInt, list(4), list(7)))
    }
    bufferedSource.close

    println(pollData)
  }
}
