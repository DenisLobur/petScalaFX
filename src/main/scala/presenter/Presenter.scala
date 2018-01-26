package presenter

import java.io.File

import model.{Poll, PollRepository, PollTable}
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._
import util.PollPeriod._
import util.{PollParser, PollPeriod}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Presenter {

//  val parsedData: List[Poll] = PollParser.parseDataFile(new File(getClass.getResource("/2017may").getPath))

  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), Duration.Inf)

  val db = Database.forURL("jdbc:postgresql://localhost/poll?user=postgres&password=123456789")

  val pollRepository = new PollRepository(db)

  exec(PollTable.table.schema.drop.asTry andThen PollTable.table.schema.create)

  println(PollTable.table.schema.createStatements.mkString)

//  exec(PollTable.table ++= parsedData)

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
    //exec(PollTable.table ++= parsedData)
  }

  def selectPeriod(period: String): Unit = {
    val fileToParse = period match {
      case December2011.show => December2011.name
      case May2011.show => May2011.name
      case December2012.show => December2012.name
      case May2012.show => May2012.name
      case December2013.show => December2013.name
      case May2013.show => May2013.name
      case December2014.show => December2014.name
      case May2014.show => May2014.name
      case December2015.show => December2015.name
      case May2015.show => May2015.name
      case December2016.show => December2016.name
      case May2016.show => May2016.name
      case May2017.show => May2017.name
      case _ => throw new NoSuchElementException(s"Period $period is unknown")
    }

    val parsedData: List[Poll] = PollParser.parseDataFile(new File(getClass.getResource(s"/$fileToParse").getPath))
    exec(PollTable.table ++= parsedData)
  }
}
