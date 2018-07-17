package services

import models.{ NasaAssetsCollection, NasaImagesCollection }
import play.api.libs.ws.WSRequest
import play.api.mvc.{AnyContent, Request}
import play.api.Logger
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class SearchService {

  def getAssetList(ws: WSRequest)(implicit request: Request[AnyContent]): Future[NasaAssetsCollection] = {
    Logger.debug(s"\n\n-------------- Making call to ${ws.url}")
    ws.get() flatMap Future.successful map (resp => resp.json.as[NasaAssetsCollection])
  }

  def getAsset(ws: WSRequest)(implicit request: Request[AnyContent]): Future[NasaImagesCollection] = {
    Logger.debug(s"\n\n-------------- Making call to ${ws.url}")
    ws.get() flatMap Future.successful map (resp => resp.json.as[NasaImagesCollection])
  }
}
