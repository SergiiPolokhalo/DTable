package dtable.dblayer

import shared.model.User

class UserDB {
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
  private[this] val users = TableQuery[Users]

  def userById(id: Int) = {
    call(users.filter(_.id === id))
  }

  def all() = {
    call(users.filter(u => u.id >= 1))
  }
}
