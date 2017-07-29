package dtable.dblayer

import com.typesafe.config.ConfigFactory
import shared.model.User

import scala.concurrent.{Await, Future}

object DBLayer {

  import slick.jdbc.SQLiteProfile.api._

  import scala.concurrent.duration._

  lazy val db = Database.forURL(dbname, driver = "org.sqlite.JDBC")
  val config = ConfigFactory.load()
  val dbname = config.getString("db.uri")
  val users = TableQuery[Users]
  val names = List("Alba", "Bruno", "Carol", "Dorothy", "Emanuel", "Frank")
  val lastNames = List("Appo", "Beltz", "Craight", "Dombre", "Ezzye", "Fuzzy")
  val actions = DBIO.seq(
    users.schema.create,
    users ++= mkUsers()
  )

  def mkUsers(): List[User] = {
    for {fn <- names; ln <- lastNames} yield User(None, fn, ln)
  }

  def all() = {
    val r = db.run(users.filter(u => u.id >= 1).result)
    oper(r)
  }

  db.run(actions)

  def oper[T](action: Future[T]): T = {
    val res = Await.result(action, 10 second)
    res
  }

  def userById(id: Int) = {
    val r = db.run(users.filter(_.id === id).result)
    oper(r)
  }

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def * = (id.?, first, last) <> (User.tupled, User.unapply)

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def first = column[String]("first")

    def last = column[String]("last")
  }
}
