package com.basic.searchengine

import java.io.File

import scala.io.Source
import scala.util.Try
import scala.io.StdIn.readLine

object SearchEngine extends App {

  FileIndexer
    .readFile(args)
    .fold(
      println,
      file => FileIndexer.iterate(FileIndexer.index(file))
    )
}

case class Index(indexedFiles: List[WordCounter])

case class WordCounter(fileName: String, word: String, count: Int)

object FileIndexer {

  sealed trait ReadFileError

  case object MissingPathArg extends ReadFileError

  case class NotDirectory(error: String) extends ReadFileError

  case class FileNotFound(t: Throwable) extends ReadFileError

  def readFile(args: Array[String]): Either[ReadFileError, File] = {
    for {
      path <- args.headOption.toRight(MissingPathArg)
      file <- Try(new java.io.File(path))
        .fold(throwable => Left(FileNotFound(throwable)),
          file =>
            if (file.isDirectory) Right(file)
            else Left(NotDirectory(s"Path [$path] is not a directory"))
        )
    } yield file
  }

  def index(dir: File): Index = {

    val files = dir.listFiles().toList

    val wordCounters = files.flatMap { file =>
      val fileSource = Source.fromFile(file)
      val words = fileSource.getLines().flatMap(_.split(" ")).map(_.toLowerCase).toList

      words.map { word => (word, 1) }.groupBy(_._1).map { case (word, list) => WordCounter(file.getName, word, list.size) }

    }
    Index(wordCounters)
  }

  def iterate(indexedFiles: Index): Unit = {
    print(s"search> ")
    val searchString = readLine()

    if (searchString.trim == ":quit") {
      println("Good Bye!")
      sys.exit(0)
    }

    if (searchString.trim.size > 0) {

      val fileRanks = FileRankCalculator.calculateRank(indexedFiles, searchString)

      if (fileRanks.isEmpty) println("No Matches found!")

      fileRanks.foreach { fileRank =>
        println(s"${fileRank.fileName} ${fileRank.percentage}")
      }
    }

    iterate(indexedFiles)
  }
}