package presenter

import java.io.File

import model.{Poll, PollRepository, PollTable}
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._
import util.PollParser

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Presenter {

  val parsedData: List[Poll] = new PollParser().parseDataFile(new File(getClass.getResource("/2017may").getPath))

  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), Duration.Inf)

  val db = Database.forURL("jdbc:postgresql://localhost/poll?user=postgres&password=123456789")

  val pollRepository = new PollRepository(db)

  exec(PollTable.table.schema.drop.asTry andThen PollTable.table.schema.create)

  println(PollTable.table.schema.createStatements.mkString)

  exec(PollTable.table ++= parsedData)

  def selectCities(): Unit = {
    val cities = pollRepository.table.map(_.language).result
    exec(cities).foreach(println)
  }

  selectCities()

  def clearTable(): Unit = {
    val clearTable = pollRepository.table.delete
    exec(clearTable)
  }

  def createTable(): Unit = {
    exec(PollTable.table.schema.drop.asTry andThen PollTable.table.schema.create)
    exec(PollTable.table ++= parsedData)
  }


}
