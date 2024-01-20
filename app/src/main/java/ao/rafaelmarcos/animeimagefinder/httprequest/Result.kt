package ao.rafaelmarcos.animeimagefinder.httprequest

import java.net.URL

data class Result(
    val anilist: Long,
    val filename: String,
    val episode: Any?, // refer to aniep, IDK HOW TO REPRESENT THAT... YET
    val from: Float,
    val to: Float,
    val similarity: Double,
    val video: URL,
    val image: URL
)
