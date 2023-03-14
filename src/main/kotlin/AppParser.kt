import com.google.gson.JsonParser

object AppParser {
    fun sumMarks(jsonString: String): String {
        try {
            var sum = 0

            val jsonArray = JsonParser.parseString(jsonString).asJsonArray

            for (jsonElement in jsonArray) {
                val jsonObject = jsonElement.asJsonObject

                val marks = jsonObject.get("marks").asInt

                sum += marks
            }

            return sum.toString()

        } catch (e: Exception) {
            throw Exception("Failed to parse json string: ${e.message}")
        }
    }
}