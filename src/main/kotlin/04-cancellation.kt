package cancellation

import kotlinx.coroutines.*


// cancel is co-operative:
// the coroutine need to check for cancellation, if not it won't cancel
// built-in suspending functions (e.g. delay() and yield() are cooperative
fun main(args: Array<String>) {
    cancelCoroutineWithNonLocalReturn()
}


fun cancelCoroutineWithException() = runBlocking {

    val job = launch {
        repeat(100) {
            if (!isActive) throw CancellationException() // end coroutine
            print(".")
            Thread.sleep(1) // not cancellable
        }
    }

    delay(100)
    job.cancelAndJoin()
    println("done")
}

fun cancelCoroutineWithNonLocalReturn() = runBlocking {

    val job = launch {
        repeat(100) {
            if (!isActive) return@launch
            print(".")
            Thread.sleep(1) // not cancellable
        }
    }

    delay(100)
    job.cancelAndJoin()
    println("done")
}

fun cancelOngoingCancellableCoroutine() = runBlocking {

    val job = launch {
        repeat(100) {
//            delay(100)
            print(".")
            yield()
        }
    }

    delay(2500)
//    job.cancel()
//    job.join()
    job.cancelAndJoin()

    println("done")
}
