package view


import presenter.Presenter
import util.PollPeriod
import util.PollPeriod._

import scalafx.Includes._
import scalafx.application
import scalafx.scene.Scene
import scalafx.scene.control.{ComboBox, Label, Slider}
import scalafx.scene.layout.{BorderPane, HBox, VBox}

object PollView {
  private val presenter = new Presenter
  private val pollPeriods: Seq[String] = Seq(
    December2011.show, May2011.show,
    December2012.show, May2012.show,
    December2013.show, May2013.show,
    December2014.show, May2014.show,
    December2015.show, May2015.show,
    December2016.show, May2016.show,
    December2017.show)

  Main.stage = new application.JFXApp.PrimaryStage {
    title = "salaries"
    scene = new Scene(800, 600) {

      val text = new Label
      text.text = "Developers salaries - "
      val comboBox = new ComboBox[String](pollPeriods)
      comboBox.value = pollPeriods(0)
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


}
