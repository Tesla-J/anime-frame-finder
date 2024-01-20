package ao.rafaelmarcos.animeimagefinder.httprequest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.DataOutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import java.util.Random

class Request(val url: String ) {
    private val apiUrl = URL(url)
    private val lineEnd = "\r\n"
    private val boundary = "Boundary-${Random().nextInt()}"

    suspend fun sendPostRequest(imageBytes: ByteArray): String{
        val connection = apiUrl.openConnection() as HttpsURLConnection
        connection.requestMethod = "POST"
        connection.addRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
        connection.doOutput = true

        val outputStream = DataOutputStream(connection.outputStream)

        // sending image
        outputStream.writeBytes("--$boundary$lineEnd")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"image.jpg\"$lineEnd")
        outputStream.writeBytes("Content-Type: image/*$lineEnd") // General image type
        outputStream.writeBytes(lineEnd)
        outputStream.write(imageBytes)
        outputStream.writeBytes(lineEnd)
        outputStream.writeBytes("--$boundary--$lineEnd")
        outputStream.flush()

        // TODO check response code
        val responseCode = connection.responseCode

        // getting result
        val responseString = withContext(Dispatchers.IO){
            connection.inputStream.bufferedReader().use{it.readText()}
        }

        return responseString
    }
}