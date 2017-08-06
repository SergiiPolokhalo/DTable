package dtable

import com.typesafe.config.ConfigFactory
import shared.model.User

import scala.concurrent.{Await, Future}
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.duration._

package object dblayer {

  def oper[T](action: Future[T]): T = {
    Await.result(action, 10 second)
  }
  def call[T1,T2](query: Query[T1, T2, Seq])(implicit db:Database )={
    oper(db.run(query.result))
  }
  val config = ConfigFactory.load()
  val dbname = config.getString("db.uri")

  implicit lazy val db = Database.forURL(dbname, driver = "org.sqlite.JDBC")
}
