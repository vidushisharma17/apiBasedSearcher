package com.vidushisharma.search
import java.io.File
import java.util
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.index.IndexReader
import org.apache.lucene.queryParser.QueryParser
import org.apache.lucene.search.ScoreDoc
import org.apache.lucene.search.TopDocs
import org.apache.lucene.util.Version
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.Query
import org.apache.lucene.store.FSDirectory
class Searcher(str:String) {

  val INDEX_DIRECTORY = "/Users/vidushisharma/IdeaProjects/ApiSearchEngine/src/main/resources/IndexedFiles"
  private val FIELD_CONTENTS =str

  def searchIndex(instring :String): Unit ={

    println("\nSearching for '" + instring + "' using QueryParser")
    val reader: IndexReader=IndexReader.open(FSDirectory.open(new File(INDEX_DIRECTORY)))
    val searcher:IndexSearcher=new IndexSearcher(reader)
    val analyzer:Analyzer=new StandardAnalyzer(Version.LUCENE_34)
    val queryParser:QueryParser=new QueryParser(Version.LUCENE_34,FIELD_CONTENTS,analyzer) //Field Contents is defined above

    val query :Query=queryParser.parse(instring)
    val hits:TopDocs=searcher.search(query,5)
    val document:Array[ScoreDoc]=hits.scoreDocs
    println("Total Number of Hits according to content :"+hits.totalHits)
    /*var i:Int=0
    while({i<document.length}){
      val docId:Int=document(i).doc
      println(document(i).score)
      i=i+1

    }*/
    val scoreDocs:Array[ScoreDoc] = hits.scoreDocs
    var i:Int=0
    while({i<scoreDocs.length}){
      var sd:ScoreDoc=scoreDocs(i)
      println(sd.score+" is the score")
      var docId:Int=sd.doc
      val d = searcher.doc(docId)
      println(d.get("filename"))
      //println(docId+" is the document")
      i=i+1

    }




    /*var i:Int =0
    while ({i<document.length})
    {
      val doc:Document=searcher.doc(document(i).doc)
      val filename:String=doc.get("filename")
      println(filename)
      i+=1
    }*/

    /*

val hits: TopDocs = searcher.search(q, 10)
        val filterScoreDosArray: Array[ScoreDoc] = hits.topDocs.scoreDocs
        var i: Int = 0
while ( { i < filterScoreDosArray.length})  { val docId: Int = filterScoreDosArray(i).doc
val d: Nothing = is.doc(docId)
System.out.println((i + 1) + ". " + d.get("docno") + " Score: " + filterScoreDosArray(i).score)

{i += 1; i}}
     */

  }






}
