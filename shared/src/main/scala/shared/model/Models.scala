package shared.model

//Traits

sealed trait GameBox

// This is user objects
case class User(name: String)

case class Account(user: User)

//This is Game objects
case class GameRule()

case class GameType(rules: Seq[GameRule])

case class Game(name: String, `type`: GameType)

case class Character(name: String, game: Game)

case class Desk(name: String, game: Game)

//This is Chat objects
case class Chat(name: String, linked: Option[GameBox] = None)

case class Msg(chat: Chat, owner: Option[User], text: String, time: Long)
