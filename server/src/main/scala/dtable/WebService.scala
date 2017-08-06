package dtable

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives
import dtable.dblayer.DBLayer
import shared.SharedMessages
import shared.model.User
import spray.json.DefaultJsonProtocol

trait Jsonizated extends DefaultJsonProtocol {
  implicit val userJson = jsonFormat5(User.apply)
}
class WebService() extends Directives with Jsonizated {

  import dtable.dblayer._
  val dblayer =  DBLayer
  val route =
    get {
      pathSingleSlash {
        complete(dtable.html.index.render(SharedMessages.itWorks))
      } ~
      path("user") {
        //complete(dtable.html.user.render(dblayer.users.all()))
        val u = dblayer.userDb.all()
        complete(dtable.html.user.render(u))
      } ~
      path("user" / IntNumber) { id =>
        val u = dblayer.userDb.userById(id)
        complete(u.head)
      } ~
      path("accounts") {
        val u = dblayer.accountDb.all()
        complete(dtable.html.index(u.toString))
      }~
      path("BOOM") {
        complete("Boooom!!!")
      }
    } ~
      pathPrefix("assets" / Remaining) { file =>
        // optionally compresses the response with Gzip or Deflate
        // if the client accepts compressed responses
        encodeResponse {
          getFromResource("public/" + file)
        }
      }

}
