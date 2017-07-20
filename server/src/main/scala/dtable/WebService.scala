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
        println(dblayer.all())
        complete(dtable.html.user.render("username"))
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
