package models

import play.api.libs.json.Json

case class NasaImageUrl(href: String)

object NasaImageUrl {
  implicit val nasaImageUrlReads = Json.reads[NasaImageUrl]

  val example = NasaImageUrl(
    href = "https://images-api.nasa.gov/asset/as11-40-5874"
  )
}

case class NasaImage(
  href: String,
  items: List[NasaImageUrl]
)

object NasaImage {
  implicit val nasaImageReads = Json.reads[NasaImage]

  val example = NasaImage(
    href = "https://images-api.nasa.gov/asset/as11-40-5874",
    List(NasaImageUrl.example)
  )
}

case class NasaImagesCollection(collection: NasaImage)

object NasaImagesCollection {
  implicit val nasaImagesCollectionReads = Json.reads[NasaImagesCollection]

  val example = List(NasaAssetItems.example)
}


