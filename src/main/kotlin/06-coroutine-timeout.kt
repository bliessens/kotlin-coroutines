package coroutine.cleanup

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield

fun main(args: Array<String>) {
    timeout()
}

fun timeout() = runBlocking {

    val job = withTimeoutOrNull(100L) {
        repeat(100) {
            yield()
            print(".")
            Thread.sleep(1)
        }
    }

    if (job == null) {
        println("timedout")
    }
    delay(100)
//    job.cancel(CancellationException("Too many jobs"))
//    job.join()
    println("done")
}
