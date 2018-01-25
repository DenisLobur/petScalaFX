package view

import presenter.Presenter

import scalafx.application.JFXApp


object Main extends JFXApp {


//  readFile()
//  To split string by commas not touching commas inside double quotes
//  val outQuotesRegex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"

//  private def readFile(): Unit = {
//    val resourcesPath = getClass.getResource("/data")
//    val bufferedSource = io.Source.fromResource("2017may")
//    val folder = new File(resourcesPath.getPath)
//    if (folder.exists && folder.isDirectory)
//      folder.listFiles
//        .toIterator
//          .filter(_.isFile)
//        .foreach(file => println(file.getName))
//    for (line <- bufferedSource.getLines) {
//      val cols = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")
//      cols.foreach(entry => println(entry))
//      println(s"${cols(0)}|${cols(1)}|${cols(2)}|${cols(3)}")
//    }
//    bufferedSource.close
//  }

  val presenter = new Presenter
  val view = PollView

}
