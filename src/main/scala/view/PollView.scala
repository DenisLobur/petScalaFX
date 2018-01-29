package view


import model.{City, Language, Periods, Position}
import presenter.Presenter

import scalafx.Includes._
import scalafx.application
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}

object PollView extends GeneralView {
  private val presenter = new Presenter
  private val pollPeriods: Seq[String] = Periods.periods
  val rightCenter = new VBox(10)

  Main.stage = new application.JFXApp.PrimaryStage {
    title = "salaries"
    scene = new Scene(800, 600) {

      val text = new Label
      text.text = "Developers salaries - "
      val periodDropDown = new ComboBox[String](pollPeriods)
      periodDropDown.value = "Select Period"
      periodDropDown.onAction = (event: ActionEvent) => {
        println(s"selected: ${periodDropDown.value.value}")
        val pollPeriod = periodDropDown.value.value
        presenter.updatePeriod(pollPeriod)
      }
      val horizontalBox = new HBox()
      horizontalBox.children ++= List(text, periodDropDown)

      val rootPane = new BorderPane
      rootPane.top = horizontalBox
      val cityDropDown: VBox = center("city", City.cities, "Select city")
      val positionDropDown: VBox = center("position", Position.positions, "Select position")
      val languageDropDown: VBox = center("language", Language.languages, "Select language")
      val experienceSlider: VBox = bottom()
      val leftCenter = new VBox(10)
      leftCenter.children ++= List(cityDropDown, positionDropDown, languageDropDown, experienceSlider)


      rightCenter.children ++= List(
        twoLabelWidget("I quarter", "1000$"),
        twoLabelWidget("Median", "3000$"),
        twoLabelWidget("III quarter", "5000$"),
        twoLabelWidget("Total respondents", "0"))
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
    comboBox.onAction = (event: ActionEvent) => {
      println(s"selected $label: ${comboBox.value.value}")
      label match {
        case "city" => presenter.updateCity(comboBox.value.value)

        case "position" => presenter.updatePosition(comboBox.value.value)

        case "language" => presenter.updateLanguage(comboBox.value.value)
      }
    }

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

  override def updateView(updatedValue: String): Unit = {
    rightCenter.children(3) = twoLabelWidget("Total respondents", updatedValue)
  }

  def updateMedian(updatedValue: String): Unit = {
    rightCenter.children(1) = twoLabelWidget("Median", updatedValue)
  }
}
