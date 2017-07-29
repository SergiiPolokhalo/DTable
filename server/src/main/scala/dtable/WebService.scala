package dtable

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives
import dtable.dblayer.DBLayer
import shared.SharedMessages
import shared.model.User
import spray.json.DefaultJsonProtocol

trait Jsonizated extends DefaultJsonProtocol {
  implicit val userJson = jsonFormat3(User.apply)
}
class WebService() extends Directives with Jsonizated {

  val dblayer =  DBLayer
  val route =
    get {
      pathSingleSlash {
        complete(dtable.html.index.render(SharedMessages.itWorks))
      } ~
      path("user") {
        complete(dtable.html.user.render(dblayer.all()))
      } ~
      path("user" / IntNumber) { id =>
        val u = dblayer.userById(id)
        complete(u)
      } ~
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
