package com.basic.searchengine

case class FileRank(fileName: String, percentage: String)


object FileRankCalculator {

  val MAX_FILES = 10
  val MAX_VALUE = 100

  def calculateRank(index: Index, searchString: String) = {

    val searchWords = searchString.split(" ").map(_.toLowerCase)

    val wordAndCount = searchWords.groupBy(identity).map { case (word, list) => (word, list.size) }

    val searchWordsCount = searchWords.size

    val filteredIndex = index.indexedFiles.filter(file => searchWords.contains(file.word)) // filtering out the files with no matching

    val fileNameAndCount = filteredIndex.foldLeft(Map.empty[String, Int]) { (acc, indexFile) =>

      val currentCount = Math.min(indexFile.count, wordAndCount.getOrElse(indexFile.word, 0))
      val prevCount = acc.getOrElse(indexFile.fileName, 0)
      acc + (indexFile.fileName -> (currentCount + prevCount))
    }.toList

    val topFiles = fileNameAndCount.sortBy(_._1).sortBy(_._2).reverse.take(MAX_FILES)

    topFiles.map { case (fileName, count) => FileRank(fileName, s"${calculatePercentage(count, searchWordsCount)}%") }

  }

  def calculatePercentage(totalWordCount: Int, searchWordCount: Int) = totalWordCount * MAX_VALUE / searchWordCount

}
