package webcrawler

import scala.concurrent.Future
import com.ning.http.client.AsyncHttpClient
import scala.concurrent.Promise
import java.util.concurrent.Executor

trait WebClient {
  def get(url: String)(implicit exec: Executor): Future[String]
}

case class BadStatus(status: Int) extends RuntimeException

object AsyncWebClient extends WebClient {

  private val client = new AsyncHttpClient

  def get(url: String)(implicit exec: Executor): Future[String] = {
    val f = client.prepareGet(url).execute();
    val p = Promise[String]()
    f.addListener(new Runnable {
      def run = {
        val response = f.get
        if (response.getStatusCode / 100 < 4)
          p.success(response.getResponseBodyExcerpt(131072))
        else p.failure(BadStatus(response.getStatusCode))
      }
    }, exec)
    p.future
  }

  def shutdown(): Unit = client.close()

}

object WebClientTest extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val executor = global.asInstanceOf[Executor]
  
  AsyncWebClient get "http://cs.nyu.edu/wies/teaching/oop-sp18" map println foreach (_ => AsyncWebClient.shutdown())
}