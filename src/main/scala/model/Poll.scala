package model

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

case class Poll(id: String, city: String, experience: Double, salary: Int, position: String, language: String)

class PollTable(tag: Tag) extends Table[Poll](tag, "poll") {
  val id = column[String]("id", O.PrimaryKey, O.AutoInc)
  val city = column[String]("city")
  val experience = column[Double]("experience")
  val salary = column[Int]("salary")
  val position = column[String]("position")
  val language = column[String]("language")

  def * = (id, city, experience, salary, position, language) <> (Poll.apply _ tupled, Poll.unapply)
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