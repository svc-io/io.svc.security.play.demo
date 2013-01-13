package controllers

import play.api.mvc.{AnyContent, BodyParsers, Controller}
import io.svc.security.play.demo.demoBasicAuth.DemoBasicAuthSecurity

/**
 * @author Rintcius Blok
 */
object BasicAuthDemo extends Controller with DemoBasicAuthSecurity[AnyContent] {

  def index = securedAction(BodyParsers.parse.anyContent) { case (req,user) =>
    Ok(views.html.index("Welcome " + user.username + "! Your new application is ready."))
  }
}
