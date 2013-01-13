package io.svc.security.play.demo

import io.svc.security.play.{authentication => playAuthentication}
import io.svc.security.play.authentication.{PlayBasicAuthenticationCredentialsExtractor, PlayAuth}
import io.svc.security.authentication.CredentialsInputValidator
import play.api.mvc.Request
import io.svc.security.std.{AuthenticationFailure, UsernamePasswordCredentials}
import io.svc.security.play.demo.demoSecurity.DemoUser
import io.svc.security.play.security.PlaySecurity

/**
 * @author Rintcius Blok
 */
object demoBasicAuth {

  trait DemoBasicAuth[A] extends PlayAuth[A, DemoUser] {
    val inputValidator = new CredentialsInputValidator[Request[A], UsernamePasswordCredentials, DemoUser, AuthenticationFailure] {
      val credentialsExtractor = new PlayBasicAuthenticationCredentialsExtractor[A]
      val authService = demoSecurity.demoAuthService
    }
    val authFailureHandler = playAuthentication.authFailureHandler[A](demoSecurity.unauthorizedHtml.withHeaders(("WWW-Authenticate", "Basic realm=\"Demo\"")))
  }

  trait DemoBasicAuthSecurity[A] extends PlaySecurity[A, DemoUser] {
    override val auth = new DemoBasicAuth[A] {}
  }
}
