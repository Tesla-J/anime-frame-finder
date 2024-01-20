package ao.rafaelmarcos.animeimagefinder.httprequest

data class RequestResponseFormat(
    val frameCount: Long,
    val error: String,
    val result: Array<ResultFormat>
)
