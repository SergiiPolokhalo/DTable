package dtable.dblayer

import com.typesafe.config.ConfigFactory
import scala.concurrent.Await
import spray.json._
import shared.model.User

class DBLayer {


  import slick.jdbc.SQLiteProfile.api._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  val config = ConfigFactory.load()
  val dbname = config.getString("db.uri")

  lazy val db = Database.forURL(dbname, driver = "org.sqlite.JDBC")

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def first = column[String]("first")
    def last = column[String]("last")
    def * = (id.?, first, last) <> (User.tupled, User.unapply)
  }
  val users = TableQuery[Users]

  val names = List("Alba","Bruno","Carol","Dorothy","Emanuel","Frank")
  val lastNames = List("Appo","Beltz","Craight","Dombre","Ezzye","Fuzzy")
  def mkUsers():List[User] = {
    val n = for { fn <- names; ln <- lastNames } yield User(None, fn, ln)
    n.toList
  }

  val actions = DBIO.seq(
    users.schema.create,
    users ++= mkUsers()
  )

  db.run(actions)

  def all() = {
    val r = db.run(users.filter(u => u.id > 3).result)
    val res = Await.result(r, 10 second)
    res
  }
}
