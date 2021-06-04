package coroutine.context.unconfined

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// coroutineContext is a dictionary. One of the keys is Job.Key
// which gives you access to the job properties
fun main(args: Array<String>) = runBlocking {

    val job = launch {
        println("isActive? ${coroutineContext[Job.Key]?.isActive}")
    }

    job.join()
}
