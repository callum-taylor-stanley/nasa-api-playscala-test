package models

import play.api.data.{Form, Mapping}
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import utils.Utils

case class AssetSearch(
  //q: String = "default",
  center: String = "",
  description: String = "",
  description_508: String = "",
  keywords: String = "",
  location: String = "",
  media_type: String = "",
  nasa_id: String = "",
  photographer: String = "",
  secondary_creator: String = "",
  title: String = "",
  year_start: String = "",
  year_end: String = ""
)

object AssetSearch {

  val form: Form[AssetSearch] = Form(
    mapping(
      //"q" -> text,
      "center" -> text,
      "description" -> text,
      "description_508" -> text,
      "keywords" -> text,
      "location" -> text,
      "media_type" -> text,
      "nasa_id" -> text,
      "photographer" -> text,
      "secondary_creator" -> text,
      "title" -> text,
      "year_start" -> text,
      "year_end" -> text
    )(AssetSearch.apply)(AssetSearch.unapply)
      .verifying("At least one field must be filled in", srch =>
        (Utils.getClassFields(srch).toList.find(_._2.toString != "").isDefined)
    )
  )

  val example = AssetSearch(
    center = "test",
    description = "",
    description_508 = "",
    keywords = "",
    location = "",
    media_type = "",
    nasa_id = "",
    photographer = "",
    secondary_creator = "",
    title = "",
    year_start = "",
    year_end = ""
  )
}

