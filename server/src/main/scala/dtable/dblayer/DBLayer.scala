package dtable.dblayer

object DBLayer {

//  val names = List("Alba", "Bruno", "Carol", "Dorothy", "Emanuel", "Frank")
//  val lastNames = List("Appo", "Beltz", "Craight", "Dombre", "Ezzye", "Fuzzy")
//
//  val createUsers = DBIO.seq(
//    users.schema.create,
//    users ++= mkUsers()
//  )
//
//  def mkUsers(): List[User] = {
//    for {fn <- names; ln <- lastNames} yield User(None, fn, ln, "", "")
//  }
//
//  db.run(createUsers)



  val users = new UserDB
}
