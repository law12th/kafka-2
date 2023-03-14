import org.apache.kafka.streams.StreamsBuilder

fun main() {
    val config = KafkaConfig()

    val topicOne = config.topicOne
    val topicTwo = config.topicTwo
    val numPartitions = config.numPartitions
    val replicationFactor = config.replicationFactor

    val payload = Utils.jsonStringPayload

    try {
        val adminClient = AdminClient(config)

        adminClient.createTopic(topicOne, numPartitions, replicationFactor)
        adminClient.createTopic(topicTwo, numPartitions, replicationFactor)
        adminClient.close()

        val producer = Producer(config)

        producer.sendPayload(topicOne, payload)

        val builder = StreamsBuilder()

        builder.stream<String, String>(topicOne)
            .mapValues { value ->
                AppParser.sumMarks(value)
            }
            .peek { _ , value -> println("Total sum is $value") }
            .to(topicTwo)

        val streams = Streams(config, builder)

        streams.start()

    } catch (e: Exception) {
        println("Something went wrong: ${e.message}")
    }
}