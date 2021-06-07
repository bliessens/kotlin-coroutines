package coroutine.context.unconfined

import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

// Thread launched with newSingleThreadedContext must be closed, do this with '.use{ thread-> ... }'
fun badPractice(args: Array<String>) = runBlocking {

    val outer = launch(newSingleThreadContext("STC")) {
        println("'SingleThreadedContext': I'm working in thread '${Thread.currentThread().name}'")
    }

    outer.join()

}

// this is better:
fun main(args: Array<String>) = runBlocking {

    newSingleThreadContext("STC").use { context ->
        val outer = launch(context) {
            println("'SingleThreadedContext': I'm working in thread '${Thread.currentThread().name}'")
        }
        outer.join()
    }

}
