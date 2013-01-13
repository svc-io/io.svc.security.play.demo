package controllers

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.ws.WS
import com.ning.http.client.Realm.AuthScheme

/**
 * @author Rintcius Blok
 */
class BasicAuthDemoTest extends Specification {

  val port = 3333
  val url = "http://localhost:" + port + "/basicAuthDemo"

  "Application" should {

    "deny access if no username/password in supplied" in {
      val result = controllers.BasicAuthDemo.index(FakeRequest())
      status(result) must equalTo(UNAUTHORIZED)
    }

    "have proper security" in {
      running(TestServer(3333)) {
        await(WS.url(url).get).status must equalTo(UNAUTHORIZED)

        await(WS.url(url).withAuth("joe", "invalid", AuthScheme.BASIC).get).status must equalTo(UNAUTHORIZED)

        await(WS.url(url).withAuth("joe", "password4joe", AuthScheme.BASIC).get).status must equalTo(OK)
      }
    }
  }


}