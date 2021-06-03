import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

val THRESHOLD = 5_000

// single threaded
fun compute(array: IntArray, low: Int, high: Int): Long {
    if (high - low < THRESHOLD) {
        return (low until high).map { array[it].toLong() }
            .sum()
    } else {
        val mid = low + (high - low) / 2
        val left = compute(array, low, mid)
        val right = compute(array, mid, high)
        return left + right
    }
}

suspend fun computeRoutine(array: IntArray, low: Int, high: Int): Long {
    if (high - low < THRESHOLD) {
        return (low until high).map { array[it].toLong() }
            .sum()
    } else {
        val mid = low + (high - low) / 2
        val left = coroutineScope { async { computeRoutine(array, low, mid) } }
//        val right = coroutineScope { async { computeRoutine(array, mid, high) } }
        val right = computeRoutine(array, mid, high)
        return left.await() + right
    }
}

fun main(args: Array<String>) = runBlocking {
    val limit = 20_000_000
    val list = 1.rangeTo(limit).toList()

    var result = 0L
    var time = measureTimeMillis {
        result = computeRoutine(list.toIntArray(), 0, list.toIntArray().size)
    }

    result = 0L
    time = measureTimeMillis {
        result = computeRoutine(list.toIntArray(), 0, list.toIntArray().size)
    }

    println("$result in ${time}")
}