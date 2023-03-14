data class KafkaConfig(
    val bootstrapServer: String = "localhost:9092",
    val adminClientId: String = "admin-client-id",
    val streamsApplicationId: String = "streams-application-id",
    val topicOne: String = "source_1",
    val topicTwo: String = "target_1",
    val numPartitions: Int = 1,
    val replicationFactor: Short = 1
)