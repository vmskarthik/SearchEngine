package com.basic.searc.searchengine

import java.io.File

import com.basic.searchengine.{FileIndexer, Index, WordCounter}
import com.basic.searchengine.FileIndexer.{MissingPathArg, NotDirectory}
import org.specs2.mutable._

class SearchEngineTest extends Specification {

  val testFileDir = "./src/test/resources"

  "'FileIndexer.readFile'" should {
    "return File object, if the path is valid" in {
      val file = new File(testFileDir)
      FileIndexer.readFile(Array(testFileDir)) shouldEqual Right(file)
    }

    "return exception if path is not exists" in {
      FileIndexer.readFile(Array("./src/test/non_exist_dir")) shouldEqual Left(NotDirectory("Path [./src/test/non_exist_dir] is not a directory"))
    }

    "throws MissingPath exception if no path given" in {
      FileIndexer.readFile(Array.empty[String]) shouldEqual Left(MissingPathArg)
    }
  }
  "Given a directory 'FileIndexer.index'" should {
    "Index all files in the directory" in {
      val dir = new File(testFileDir)

      val list = (List(WordCounter("File2.txt", "not", 1), WordCounter("File2.txt", "or", 1), WordCounter("File2.txt", "be", 1), WordCounter("File2.txt", "to", 1),
        WordCounter("File3.txt", "not", 1), WordCounter("File3.txt", "or", 1), WordCounter("File3.txt", "be", 1), WordCounter("File3.txt", "to", 2),
        WordCounter("File1.txt", "question", 1), WordCounter("File1.txt", "is", 1), WordCounter("File1.txt", "or", 1), WordCounter("File1.txt", "to", 2),
        WordCounter("File1.txt", "not", 1), WordCounter("File1.txt", "be", 2), WordCounter("File1.txt", "the", 1)))

      FileIndexer.index(dir).indexedFiles.toSet shouldEqual list.toSet
    }
  }

}