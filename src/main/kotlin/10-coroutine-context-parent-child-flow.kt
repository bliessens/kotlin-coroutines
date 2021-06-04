package coroutine.context.unconfined

import kotlinx.coroutines.*

// coroutineContext of outer propagates to inner so
// outer.join() implicitly waits for the inner too (100 dots get printed)
fun mainOne(args: Array<String>) = runBlocking {

    val outer = launch {
        launch(/*coroutineContext*/) {
            repeat(1000) {
                print('.')
                delay(1)
            }
        }
    }

    outer.join()
    println()
    println("Finished")
}


// coroutineContext of outer propagates to inner so
// outer.cancel() implicitly cancels the inner too (NO dots get printed, not even a single one?)
fun mainTwo(args: Array<String>) = runBlocking {

    val outer = launch {
        launch(coroutineContext) {
            repeat(1000) {
                print('.')
                delay(1)
            }
        }
    }

    outer.cancel()
    delay(1000)
    println()
    println("Finished")
}


fun main(args: Array<String>) = runBlocking {

    val outer = launch {
        try {
            launch(/*coroutineContext*/) {
                repeat(1000) {
                    print('.')
                    delay(1)
                }
            }
            delay(2000)
        } catch (ex: CancellationException) {
            println("Caught exception")
        }
    }
    delay(200)
    outer.cancelChildren()
    delay(1000)
    println()
    println("Outer is cancelled? ${outer.isCancelled}")
}
