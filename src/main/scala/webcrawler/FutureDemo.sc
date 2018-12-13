import scala.util.{Try, Success, Failure}
import scala.concurrent.{Future, Await, Promise}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

var x = 0

val f: Future[Int] = Future {
  42
}

val f1: Future[Int] = f.map(r => r + 1)

Await.result(f1, Duration.Inf)





def produceSomething() =
  "Hello World"
def doSomethingWithResult(r: String): Unit =
  println(r)
def startDoingSomething(): Unit =
  println("Producer Working...")
def continueDoingSomethingUnrelated(): Unit =
  println("Consumer Working...")

val p = Promise[String]()
val fp: Future[String] = p.future

val producer = Future {
  val r = produceSomething()
  p.success(r)
  continueDoingSomethingUnrelated()
}

val consumer = Future {
  startDoingSomething()
  fp.foreach { r =>
    doSomethingWithResult(r)
  }
}

Await.result(consumer, Duration.Inf)