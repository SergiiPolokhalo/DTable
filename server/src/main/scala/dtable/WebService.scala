package dtable

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives._
import dtable.dblayer.DBLayer
import shared.SharedMessages

class WebService() extends Directives {
  val route = 
    get {
      pathSingleSlash {
        complete(dtable.html.index.render(SharedMessages.itWorks))
      } ~
      path("user") {
        val dblayer =  new DBLayer()
        complete(dtable.html.user.render(dblayer.all()))
      } ~
//      path("user" / IntNumber) {
//        complete(HttpResponse(User(Some(1),"2","3")))
//      } ~
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
