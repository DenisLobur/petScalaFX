package view

import presenter.Presenter

import scalafx.Includes._
import scalafx.application
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.BorderPane

object DumbView {
  val presenter = new Presenter
  Main.stage = new application.JFXApp.PrimaryStage {
    title = "Test"
    scene = new Scene(200, 200) {
      val create = new Button("create")
      create.onAction = (event: ActionEvent) => {
        System.out.println("click on create!")
        presenter.createTable()
      }

      val clear = new Button("clear")
      clear.onAction = (event: ActionEvent) => {
        System.out.println("click on cancel!")
        presenter.clearTable()
      }
      val rootPane = new BorderPane
      rootPane.top = clear
      rootPane.bottom = create

      root = rootPane
    }


  }
}
