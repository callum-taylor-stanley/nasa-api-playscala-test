package models

import play.api.libs.json.Json

case class NasaAssetData(
  center: Option[String],
  date_created: String,
  description: Option[String],
  keywords: Option[List[String]],
  media_type: String,
  nasa_id: String,
  title: String,
  photographer: Option[String],
  location: Option[String]
)

object NasaAssetData {
  implicit val nasaAssetDataReads = Json.reads[NasaAssetData]

  val example = NasaAssetData(
    center = Some("JSC"),
    date_created = "1969-07-21T00:00:00Z",
    description = Some("\"AS11-40-5874 (20 July 1969) ---\nAstronaut Edwin E. Aldrin Jr., lunar module pilot\nof the first lunar landing mission, poses for a\nphotograph beside the deployed United States flag\nduring Apollo 11 extravehicular activity (EVA) on\nthe lunar surface. The Lunar Module (LM) is on the\nleft, and the footprints of the astronauts are\nclearly visible in the soil of the moon. Astronaut\nNeil A. Armstrong, commander, took this picture\nwith a 70mm Hasselblad lunar surface camera. While\nastronauts Armstrong and Aldrin descended in the LM\nthe \\\"Eagle\\\" to explore the Sea of Tranquility\nregion of the moon, astronaut Michael Collins,\ncommand module pilot, remained with the Command and\nService Modules (CSM) \\\"Columbia\\\" in lunar\norbit."),
    keywords = Some(List("APOLLO 11 FLIGHT",
      "MOON",
      "LUNAR SURFACE",
      "LUNAR BASES",
      "LUNAR MODULE",
      "ASTRONAUTS",
      "EXTRAVEHICULAR ACIVITY")),
    media_type = "image",
    nasa_id = "as11-40-5874",
    title = "Apollo 11 Mission image - Astronaut Edwin Aldrin\nposes beside th",
    photographer = None,
    location = None
  )
}

case class NasaAssetLinks(rel: String, href: String, render: Option[String])

object NasaAssetLinks {
  implicit val nasaAssetLinksReads = Json.reads[NasaAssetLinks]

  val example = NasaAssetLinks(
    rel = "preview",
    href = "https:\\/\\/images-assets.nasa.gov\\/image\\/PIA07009\\/PIA07009~thumb.jpg",
    render = Some("image")
  )
}

case class NasaAssetItem(links: Option[List[NasaAssetLinks]], href: String, data: List[NasaAssetData])

object NasaAssetItem {
  implicit val nasaAssetItemReads = Json.reads[NasaAssetItem]

  val example = List(Some(NasaAssetLinks.example), "", NasaAssetData.example)
}

case class NasaAssetMetadata(total_hits: Int)

object NasaAssetMetadata {
  implicit val nasaAssetMetadataReads = Json.reads[NasaAssetMetadata]

  val example = List(22)
}

case class NasaAssetItems(version: String, href: String, items: List[NasaAssetItem], metadata: NasaAssetMetadata)

object NasaAssetItems {
  implicit val nasaAssetItemsReads = Json.reads[NasaAssetItems]

  val example = List(NasaAssetItem.example)
}

case class NasaAssetsCollection(collection: NasaAssetItems)

object NasaAssetsCollection {
  implicit val nasaAssetsCollectionReads = Json.reads[NasaAssetsCollection]

  val example = List(NasaAssetItems.example)
}


