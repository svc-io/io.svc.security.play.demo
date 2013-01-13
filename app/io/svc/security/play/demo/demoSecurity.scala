package io.svc.security.play.demo

import io.svc.security.user.{UsernamePasswordCredentialsAuthenticationService, CredentialsValidator, UserWithUsername}
import io.svc.security.std._
import scalaz.{Failure, Success}

/**
 * @author Rintcius Blok
 */
object demoSecurity {

  case class DemoUser(username: String, password: String) extends UserWithUsername[String]

  val users = Seq(DemoUser("joe", "password4joe"), DemoUser("jane", "password4jane"))

  val demoUserService = new StdInMemoryUserService[String](users)

  /**
   * In this demo user.password is not encrypted; normally it will be encrypted
   */
  val demoCredentialsValidator = new CredentialsValidator[DemoUser, UsernamePasswordCredentials, AuthenticationFailure] {
    override def validate[A, B](user: A, credentials: B) = {
      //TODO get rid of asInstanceOf...
      if (credentials.asInstanceOf[UsernamePasswordCredentials].password == user.asInstanceOf[DemoUser].password) {
        Success(user)
      } else {
        Failure(AuthenticationServiceFailure("invalid password"))
      }
    }
  }

  val demoAuthService = new UsernamePasswordCredentialsAuthenticationService[DemoUser] {
    val userService = demoUserService
    val credentialsValidator = demoCredentialsValidator
  }


  val unauthorizedHtml = play.api.mvc.Results.Unauthorized(views.html.unauthorized())

}