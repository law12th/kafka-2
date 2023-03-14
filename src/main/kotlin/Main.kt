import org.apache.kafka.streams.StreamsBuilder

fun main() {
    val config = KafkaConfig()

    try {
        val adminClient = AdminClient(config)

        adminClient.createTopic(config.topicOne, config.numPartitions, config.replicationFactor)
        adminClient.createTopic(config.topicTwo, config.numPartitions, config.replicationFactor)
        adminClient.close()

        val producer = Producer(config)

        producer.sendPayload(config.topicOne, Utils.jsonPayload)

        val builder = StreamsBuilder()

        builder.stream<String, String>(config.topicOne)
            .mapValues { value ->
                AppParser.sumMarks(value)
            }
            .peek { _ , value -> println("Sum is $value") }
            .to(config.topicTwo)

        val streams = Streams(config, builder)

        streams.start()

    } catch (e: Exception) {
        println("Something went wrong: ${e.message}")
    }
}