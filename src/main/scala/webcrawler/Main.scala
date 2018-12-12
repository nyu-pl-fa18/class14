package webcrawler

import akka.actor.Actor
import akka.actor.Props
import scala.concurrent.duration._
import akka.actor.ReceiveTimeout

class Main extends Actor {
  
  import Receptionist._

  val receptionist = context.actorOf(Props[Receptionist], "receptionist")
  
  receptionist ! Get("https://cs.nyu.edu/wies/teaching/oop-sp18")
  
  context.setReceiveTimeout(10.seconds)
  
  def receive = {
    case Result(url, set) =>
      println(set.toVector.sorted.mkString(s"Results for '$url':\n", "\n", "\n"))
    case Failed(url) =>
      println(s"Failed to fetch '$url'\n")
    case ReceiveTimeout =>
      context.stop(self)
  }
  
  override def postStop(): Unit = {
    AsyncWebClient.shutdown()
  }
 
}

object Main extends App {
  val main = akka.Main.main(Array(classOf[Main].getName))
  
}
