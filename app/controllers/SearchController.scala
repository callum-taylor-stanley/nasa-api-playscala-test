package controllers

import javax.inject._
import models.AssetSearch
import play.api.libs.ws.WSClient
import play.api.mvc._
import services.SearchService

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import utils.Utils
import java.net.URLEncoder

@Singleton
class SearchController @Inject()(wsClient: WSClient, service: SearchService) extends Controller {

  val NASA_API_SEARCH_URL = "https://images-api.nasa.gov/search?"
  val NASA_API_ASSET_URL = "https://images-api.nasa.gov/asset/"

  def redirectToSearch = Action {
    Redirect(controllers.routes.SearchController.search)
  }

  def search = Action { implicit request =>
    Ok(views.html.nasa.search(AssetSearch.form))
  }

  def searchPost: Action[AnyContent] = Action.async(parse.default) { implicit request =>
    var url = NASA_API_SEARCH_URL

    val media_type_image: Option[String] = request.body.asFormUrlEncoded.flatMap(m => m.get("media_type_image").flatMap(_.headOption))
    val media_type_audio: Option[String] = request.body.asFormUrlEncoded.flatMap(m => m.get("media_type_audio").flatMap(_.headOption))

    val media_type = getMediaTypeFromRequestValues(media_type_image, media_type_audio)

    var reqData = request.body.asFormUrlEncoded.get ++ Map("media_type" -> Seq(media_type))

    AssetSearch.form.bindFromRequest(reqData).fold(
      erroredForm => {
        Future.successful(BadRequest(views.html.nasa.search(erroredForm)))
      },
      boundForm => {
        service.getAssetList(wsClient.url(adaptUrlFromFormValues(boundForm, url))) map { result =>
          Ok(views.html.nasa.search(AssetSearch.form.fill(boundForm), Some(result.collection.items), true))
        }
      }
    )
  }

  def getMediaTypeFromRequestValues(media_type_image: Option[String], media_type_audio: Option[String]): String = {
    var media_type = ""

    if (media_type_image.nonEmpty && media_type_image.getOrElse("") == "true") {
      media_type+= "image,"
    }
    if (media_type_audio.nonEmpty && media_type_audio.getOrElse("") == "true") {
      media_type+= "audio"
    }

    if (media_type.takeRight(1) == ",") {
      media_type dropRight(1)
    } else {
      media_type
    }

  }

  def adaptUrlFromFormValues(form: AssetSearch, url: String): String = {
    var returnURL = url

    for ((k,v) <- Utils.getClassFields(form)) {
      if (v.toString.nonEmpty) {
        val encodedParam = URLEncoder.encode(v.toString.replaceAll("'", ""))
        returnURL += s"${k}=${encodedParam}&"
      }
    }

    returnURL
  }

  def asset(assetId: String) = Action.async(parse.default) { implicit request =>
    val url = NASA_API_ASSET_URL + assetId
    val assetInfoURL = NASA_API_SEARCH_URL + s"nasa_id=${URLEncoder.encode(assetId.replaceAll("'", ""))}"

    service.getAssetList(wsClient.url(assetInfoURL)) flatMap { filteredAssetsInfo =>
      service.getAsset(wsClient.url(url)) map { assetInfo =>
        Ok(views.html.nasa.asset(filteredAssetsInfo.collection.items(0), assetInfo.collection))
      }
    }
  }

}
