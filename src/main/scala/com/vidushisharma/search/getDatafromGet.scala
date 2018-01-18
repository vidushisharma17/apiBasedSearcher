package com.vidushisharma.search
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.vidushisharma.search.Searcher

import scala.io.StdIn
object getDatafromGet {

  def mainSearcher(str: String, str1: String): Unit ={
    val pt2=new Searcher(str).searchIndex(str1)
  }

  def main(args:Array[String]): Unit ={
    implicit val actorSystem=ActorSystem("ActorSystem")
    implicit val actorMaterializer=ActorMaterializer()
    implicit val executionContext = actorSystem.dispatcher
    var temp:String=null
    var fieldkey:String=null
    var valuekey:String=null
    val route={
      path("path") {
        get {
          parameters('key.as[String], 'value.as[String]) { (key, value) =>
            complete {
              fieldkey=key
              valuekey=value
              val pt=mainSearcher(fieldkey,valuekey)
              "Hello"+key+"And Value is "+value


            }
          }
        }
      }
    }
    val bindingFuture=Http().bindAndHandle(route, "localhost", 8080)
    println("Server started at 8080")

    //val pt=mainSearcher("hello","value")



    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => actorSystem.terminate())


  }
}


