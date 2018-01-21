import model.{PollRepository, PollTable}
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Presenter {

  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), Duration.Inf)

  val db = Database.forURL("jdbc:postgresql://localhost/poll?user=postgres&password=123456789")

  val pollRepository = new PollRepository(db)

  exec(PollTable.table.schema.drop.asTry andThen PollTable.table.schema.create)

  println(PollTable.table.schema.createStatements.mkString)

  //TODO: parse csv file into pollData
  exec(PollTable.table ++= new PollParser().pollData)


}
