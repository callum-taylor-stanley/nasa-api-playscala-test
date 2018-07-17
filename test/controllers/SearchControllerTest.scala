package controllers

import org.scalatest._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import play.api.test._
import play.api.test.CSRFTokenHelper._
import play.api.test.Helpers._
import services.SearchService
import play.api.libs.ws.{ WSClient, WSRequest }
import models._
import scala.concurrent.Future
import play.api.mvc.{ AnyContent, Request }

class SearchControllerTest extends PlaySpec with MockitoSugar {

  val mockService = mock[SearchService]
  val wsClient = mock[WSClient]
  val controller = new SearchController(wsClient, mockService)


  "SearchController" should {

    "redirect to the search page" in {
        val result = controller.redirectToSearch().apply(FakeRequest())

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.SearchController.search.toString)
    }

    "render the search page" in {
      val result = controller.search().apply(FakeRequest().withCSRFToken)

      status(result) mustBe OK
      contentAsString(result) must include ("NASA Search")
    }

    "run the searchPost endpoint with valid form data" in {
      when(mockService.getAssetList(any[WSRequest]))(any[Request[AnyContent]]).thenReturn(Future.successful(NasaAssetsCollection.example))

      val formData = AssetSearch.form.fill(AssetSearch.example).data

      val result = controller.searchPost().apply(FakeRequest().withFormUrlEncodedBody(formData.toSeq: _*).withCSRFToken)

      status(result) mustBe OK
      contentAsString(result) must include ("NASA Search")
    }
  }
}
