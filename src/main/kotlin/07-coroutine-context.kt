package coroutine.context

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val jobs = arrayListOf<Job>()

    createJobs(jobs)

    jobs.forEach { it.join() }


    println()
    println()
    println()
    val job = launch {
        println("   'launched Thread': In thread ${Thread.currentThread().name}")

        val jobs = arrayListOf<Job>()
        createJobs(jobs)
        jobs.forEach { it.join() }
    }

    job.join()
}

private fun CoroutineScope.createJobs(jobs: ArrayList<Job>) {
    jobs += launch {
        println("           'default': In thread ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.Default) {
        println(" 'defaultDispatcher': In thread ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.Unconfined) {
        // not confined, will work with main thread
        println("        'Unconfined': In thread ${Thread.currentThread().name}")
    }
    jobs += launch(coroutineContext) {
        // context of parent, runBlocking coroutine
        println("  'coroutineContext': In thread ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.IO) {
        println("                'IO': In thread ${Thread.currentThread().name}")
    }
//    jobs += launch(Dispatchers.Main) {
//        println("              'main': In thread ${Thread.currentThread().name}")
//    }
    jobs += launch(newSingleThreadContext("OwnThread")) {
        println("            'newSTC': In thread ${Thread.currentThread().name}")
    }
}

