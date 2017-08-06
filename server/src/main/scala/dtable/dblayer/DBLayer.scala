package dtable.dblayer

import slick.jdbc.SQLiteProfile.api._
import shared.model.{Account, User}

object DBLayer {
  import slick.jdbc.SQLiteProfile.api._

  val userDb =  UserDB

  val names = List("Alba", "Bruno", "Carol", "Dorothy", "Emanuel", "Frank")
  val lastNames = List("Appo", "Beltz", "Craight", "Dombre", "Ezzye", "Fuzzy")

  val createUsers = DBIO.seq(
    userDb.users.schema.create,
    userDb.users  ++= mkUsers()
  )

  def mkUsers(): List[User] = {
    for {fn <- names; ln <- lastNames} yield User(None, fn, ln, "", "")
  }

  db.run(createUsers)
//insert accounts
  val accountDb = AccountDB
  def mkAccounts() = {
    val ids = for {
      u <- userDb.users
    } yield u.id
    call(ids).map(Account(_,"",false))
  }
  val createAccounts = DBIO.seq(
    accountDb.accounts.schema.create,
    accountDb.accounts ++= mkAccounts()
  )
  db.run(createAccounts)




}
