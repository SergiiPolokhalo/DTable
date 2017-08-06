package dtable.dblayer

import shared.model.User

object UserDB {
  import slick.jdbc.SQLiteProfile.api._

  class Users(tag: Tag) extends Table[User](tag, "users") {
    import slick.jdbc.SQLiteProfile.api._

    def * = (id.?, first, last, login, password) <> (User.tupled, User.unapply)

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def first = column[String]("first")

    def last = column[String]("last")

    def login = column[String]("login")

    def password = column[String]("password")
  }
  val users = TableQuery[Users]

  def userById(id: Int) = {
    val query=users.filter(_.id === id)
    call(query)
  }

  def all() = {
    val query=users.filter(u => u.id >= 1)
    call(query)
  }
}
