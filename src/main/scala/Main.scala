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
      val comboBox = new ComboBox[String](Seq[String]("one", "two", "three"))
      comboBox.value = "one"
      //val pane = new SplitPane()
      val horizontalBox = new HBox()
      horizontalBox.children ++= List(text, comboBox)


      val slider = new Slider(0, 10, 0)
      //pane.orientation = Orientation.Horizontal
      //pane.items ++= List(text, comboBox)

      val rootPane = new BorderPane
      rootPane.center = horizontalBox
      root = rootPane
    }
  }

}
