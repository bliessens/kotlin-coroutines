package coroutine.context.unconfined

import kotlinx.coroutines.*

// with Dispatchers.Unconfined coroutine is started in calling Thread
// but only until first suspending function. Coroutine Resume on
// thread determined by suspending function.
// yield() and delay() use different threads
// useful for using RxHava with Kotlin coroutine
fun main(args: Array<String>) = runBlocking {
    val jobs = arrayListOf<Job>()

    jobs += launch(coroutineContext) {
        // context of the parent, runBlocking coroutine
        println("'coroutineContext': I'm working in thread '${Thread.currentThread().name}'")
        delay(100)
        println("'coroutineContext': After delay in thread '${Thread.currentThread().name}'")
    }
    jobs += launch(Dispatchers.Unconfined) {
        // context of the parent, runBlocking coroutine
        println("'      Unconfined': I'm working in thread '${Thread.currentThread().name}'")
        delay(100)
        println("'      Unconfined': After delay in thread '${Thread.currentThread().name}'")
    }
    jobs += launch(Dispatchers.Unconfined) {
        // context of the parent, runBlocking coroutine
        println("'      Unconfined': I'm working in thread '${Thread.currentThread().name}'")
        yield()
        println("'      Unconfined': After yield in thread '${Thread.currentThread().name}'")
    }

    jobs.forEach { it.join() }
}
