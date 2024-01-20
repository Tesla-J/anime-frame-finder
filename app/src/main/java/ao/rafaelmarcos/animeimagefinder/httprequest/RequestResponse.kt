package ao.rafaelmarcos.animeimagefinder.httprequest

data class RequestResponse(
    val frameCount: Long,
    val error: String,
    val result: Array<Result>
)
