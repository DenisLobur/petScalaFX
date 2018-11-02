package presenter

import java.io.File

import model.{Poll, PollRepository, PollTable}
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._
import util.PollPeriod._
import util.{PollParser, PollPeriod}
import view.{GeneralView, PollView}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Presenter {

  private var currentPeriod: String = PollPeriod.May2011.show
  private var currentCity: String = "вся Украина"
  private var currentPosition = "Junior Software Engineer"
  private var currentLanguage: String = "1C"

  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), Duration.Inf)

  val db = Database.forURL("jdbc:postgresql://localhost/poll?user=postgres&password=123456789")

  val pollRepository = new PollRepository(db)

  exec(PollTable.table.schema.drop.asTry andThen PollTable.table.schema.create)

  println(PollTable.table.schema.createStatements.mkString)

  def selectCities(): Unit = {
    val cities = pollRepository.table.map(_.language).result
    exec(cities).foreach(println)
  }

  def clearTable(): Unit = {
    val clearTable = pollRepository.table.delete
    exec(clearTable)
  }

  def createTable(parsedData: List[Poll]): Unit = {
    exec(PollTable.table.schema.drop.asTry andThen PollTable.table.schema.create)
    exec(PollTable.table ++= parsedData)
  }

  private def selectPeriod(period: String): Unit = {
    clearTable()
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
      case December2017.show => December2017.name
      case May2017.show => May2017.name
      case May2018.show => May2018.name
      case _ => throw new NoSuchElementException(s"Period $period is unknown")
    }

    val parsedData: List[Poll] = PollParser.parseDataFile(new File(getClass.getResource(s"/$fileToParse").getPath))
    println(s"dataset size: ${parsedData.size}")
    createTable(parsedData)
  }

  def updatePeriod(pollPeriod: String): Unit = {
    currentPeriod = pollPeriod
    selectPeriod(pollPeriod)
    updatePoll()
  }

  def updateCity(city: String): Unit = {
    currentCity = city
    updatePoll()
  }

  def updatePosition(position: String): Unit = {
    currentPosition = position
    updatePoll()
  }

  def updateLanguage(language: String): Unit = {
    currentLanguage = language
    updatePoll()
  }

  val view: GeneralView = PollView


  def updatePoll(): Unit = {
    println(s"updated:\nperiod: $currentPeriod\ncity: $currentCity\nposition: $currentPosition\nlanguage: $currentLanguage")
    currentCity match {
      case "вся Украина" => {
        val query = pollRepository.table.filter(raw => {
          raw.language === currentLanguage && raw.position === currentPosition
        }).size.result
        val updatedValue = exec(query)
        view.updateTotalRespondents(updatedValue.toString)
      }
      case "не Киев" => {
        val query = pollRepository.table.filter(raw => {
          raw.language === currentLanguage && raw.position === currentPosition && raw.city =!= "Киев"
        }).size.result
        val updatedValue = exec(query)
        view.updateTotalRespondents(updatedValue.toString)
      }
      case a: String => {
          val query = pollRepository.table.filter(raw => {
            raw.language === currentLanguage && raw.position === currentPosition && raw.city === a}).size.result
          val updatedValue = exec(query)
          view.updateTotalRespondents(updatedValue.toString)
        }
    }

    val allSalariesQuery = pollRepository.table
      .filter(raw => {
        raw.language === currentLanguage && raw.position === currentPosition
      })
      .map(raw => raw.salary).result

    val allSalariesVector = exec(allSalariesQuery)
    if (allSalariesVector.nonEmpty) {
      val allSalariesVectorSorted = allSalariesVector.sortWith(_ < _)
      val median = allSalariesVectorSorted.drop(allSalariesVector.size / 2).head
      view.updateMedian(s"$$ ${Math.round(median).toString}")

      val q1 = allSalariesVectorSorted.take(allSalariesVectorSorted.size / 4).last
      view.updateQ1(s"$$ ${Math.round(q1).toString}")

      val q3 = allSalariesVectorSorted.drop(allSalariesVectorSorted.size / 2).drop(allSalariesVectorSorted.size / 4).head
      view.updateQ3(s"$$ ${Math.round(q3).toString}")
    } else {
      view.updateMedian(s"$$ 0")
      view.updateQ1(s"$$ 0")
      view.updateQ3(s"$$ 0")
    }
  }
}
