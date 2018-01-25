package model

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

case class Poll(id: String, city: String, salary: Int, position: String, experience: Double, language: String)

class PollTable(tag: Tag) extends Table[Poll](tag, "poll") {
  val id = column[String]("id", O.PrimaryKey, O.AutoInc)
  val city = column[String]("city")
  val salary = column[Int]("salary")
  val position = column[String]("position")
  val experience = column[Double]("experience")
  val language = column[String]("language")

  def * = (id, city, salary, position, experience, language) <> (Poll.apply _ tupled, Poll.unapply)
}

object PollTable {
  val table = TableQuery[PollTable]
}

class PollRepository(db: Database) {
  val table = TableQuery[PollTable]

  def create(poll: Poll): Future[Poll] = {
    db.run(table returning table += poll)
  }

  def update(poll: Poll): Future[Int] = {
    db.run(table.filter(_.id === poll.id).update(poll))
  }

  def delete(poll: Poll): Future[Int] = {
    db.run(table.filter(_.id === poll.id).delete)
  }
}