import java.io.File

import scalafx.Includes._
import scalafx.application
import scalafx.application.JFXApp
import scalafx.geometry.Orientation
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, Pane, VBox}


object Main extends JFXApp {
  stage = new application.JFXApp.PrimaryStage {
    title = "salaries"
    scene = new Scene(800, 600) {

      val text = new Label
      text.text = "Developers salaries - "
      val comboBox = new ComboBox[String](Seq[String]("2011", "2012", "2013"))
      comboBox.value = "2011"
      val horizontalBox = new HBox()
      horizontalBox.children ++= List(text, comboBox)


      val slider = new Slider(0, 10, 0)
      //pane.orientation = Orientation.Horizontal
      //pane.items ++= List(text, comboBox)

      val rootPane = new BorderPane
      rootPane.top = horizontalBox
      val topCenter: VBox = center("city", Seq[String]("Kyiv", "Lviv"), "Kyiv")
      val middleCenter: VBox = center("position", Seq[String]("Junior", "Middle", "Senior"), "Middle")
      val bottomCenter: VBox = center("language", Seq[String]("Java", "Scala", "Python"), "Java")
      val exprslider = bottom()
      val leftCenter = new VBox(10)
      leftCenter.children ++= List(topCenter, middleCenter, bottomCenter, exprslider)

      val rightCenter = new VBox(10)
      rightCenter.children ++= List(twoLabelWidget("I quarter", "1000$"), twoLabelWidget("Median", "3000$"), twoLabelWidget("III quarter", "5000$"))
      val centerContent = new HBox
      centerContent.children ++= List(leftCenter, rightCenter)
      rootPane.center = centerContent
      root = rootPane
    }
  }

  private def center(label: String, content: Seq[String], defaultValue: String): VBox = {
    val verticalBox = new VBox

    val text = new Label
    text.text = label

    val comboBox = new ComboBox[String](content)
    comboBox.value = defaultValue

    verticalBox.children ++= List(text, comboBox)
    verticalBox
  }

  private def bottom(): VBox = {
    val verticalBox = new VBox

    val text = new Label
    text.text = "Experience"

    val slider = new Slider(0, 10, 0)

    verticalBox.children ++= List(text, slider)
    verticalBox
  }

  private def twoLabelWidget(top: String, bottom: String): VBox = {
    val vBox = new VBox
    val topText = new Label
    topText.text = top

    val bottomText = new Label
    bottomText.text = bottom

    vBox.children ++= List(topText, bottomText)

    vBox
  }

  //readFile()
  // To split string by commas not touching commas inside double quotes
  val outQuotesRegex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"

  private def readFile(): Unit = {
//    val resourcesPath = getClass.getResource("/data")
    val bufferedSource = io.Source.fromResource("june2017")
//    val folder = new File(resourcesPath.getPath)
//    if (folder.exists && folder.isDirectory)
//      folder.listFiles
//        .toIterator
//          .filter(_.isFile)
//        .foreach(file => println(file.getName))
    for (line <- bufferedSource.getLines) {
      val cols = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")
      cols.foreach(entry => println(entry))
      //println(s"${cols(0)}|${cols(1)}|${cols(2)}|${cols(3)}")
    }
    bufferedSource.close
  }

  new PollParser().parseDataFile(new File(getClass.getResource("/june2017").getPath))

}
