package dtable

import com.typesafe.config.ConfigFactory
import scala.concurrent.{Await, Future}
import slick.jdbc.SQLiteProfile.api._
import scala.concurrent.duration._

package object dblayer {

  def oper[T](action: Future[T]): T = {
    Await.result(action, 10 second)
  }
  val config = ConfigFactory.load()
  val dbname = config.getString("db.uri")

  implicit lazy val db = Database.forURL(dbname, driver = "org.sqlite.JDBC")

  def call[E,U,C](table:Query[E,U,C])(implicit db : Database) ={
    oper(db.run(table.result))
  }
}
