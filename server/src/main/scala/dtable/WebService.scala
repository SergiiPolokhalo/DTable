package dtable

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives._
import shared.SharedMessages

class WebService() extends Directives {
  val route = 
    get {
      pathSingleSlash {
        complete(dtable.html.index.render(SharedMessages.itWorks))
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
