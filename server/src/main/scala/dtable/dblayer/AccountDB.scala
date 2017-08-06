package dtable.dblayer

import shared.model.{Account, User}

object  AccountDB {
  import slick.jdbc.SQLiteProfile.api._
  val userDb = UserDB

  class Accounts(tag: Tag) extends Table[Account](tag, "accounts") {
    import slick.jdbc.SQLiteProfile.api._

    def * = (user, token, active) <> (Account.tupled, Account.unapply)

    def user = column[Int]("user")

    def token = column[String]("token")

    def active = column[Boolean]("active")
  }
  val accounts = TableQuery[Accounts]


  def all() = {
   val res =  for {
     user <- userDb.users
     account <- accounts if user.id === account.user
   } yield (user, account)
   call(res)
  }

}
