import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import kotlin.random.Random

fun interval(timeInMillis: Long): Flow<Long> = flow {
    var counter: Long = 0
    while (true) {
        delay(timeInMillis)
        emit(counter++)
    }
}

data class Price(val symbol: String, val bid: Double, val ask: Double)

val symbols = flowOf("EURUSD", "USDJPY", "USDAUD")

fun main() = runBlocking {
    symbols
        .flatMapMerge { createPriceStream(it) }
        .collect {
            println(it)
            println(LocalDateTime.now())
        }
}

fun createPriceStream(symbol: String) = interval(Random.nextLong(800, 1000))
    .map { Price(symbol, Random.nextDouble(51.32, 52.82), Random.nextDouble(52.90, 54.20)) }