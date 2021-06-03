package coroutine.cleanup

import kotlinx.coroutines.*


// cancel is co-operative:
// the coroutine need to check for cancellation, if not it won't cancel
// built-in suspending functions (e.g. delay() and yield() are cooperative
fun main(args: Array<String>) {
    cleanUp()
}

// if you need to call a suspending function in finally{} you do NOT
// want that function to be cancelled (and not free some resources)
// therefor you must wrap that code inside withContext(NonCancellable){}
fun cleanUp() = runBlocking {

    val job = launch {
        try {
            repeat(100) {
                yield()
                print(".")
                Thread.sleep(10)
            }
        } catch (ex: CancellationException) {
            println()
            println("Cancelled: ${ex.message}")
        } finally {
            withContext(NonCancellable) {
                println("This Finally block cannot be cancelled")
            }
        }
    }

    delay(100)
    job.cancel(CancellationException("Too many jobs"))
    job.join()
    println("done")
}
