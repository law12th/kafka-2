import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import java.util.Properties

class Streams(
    config: KafkaConfig,
    builder: StreamsBuilder
) {
    private var streams: KafkaStreams

    init {
        val properties = Properties().apply {
            put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.bootstrapServer)
            put(StreamsConfig.APPLICATION_ID_CONFIG, config.streamsApplicationId)
            put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().javaClass.name)
            put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().javaClass.name)
        }

        val topology = builder.build()

        streams = KafkaStreams(topology, properties)
    }

    fun start() = streams.start()

}