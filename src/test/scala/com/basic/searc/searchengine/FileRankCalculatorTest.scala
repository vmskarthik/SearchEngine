package com.basic.searc.searchengine

import com.basic.searchengine.{FileRank, FileRankCalculator, Index, WordCounter}
import org.specs2.mutable.Specification

class FileRankCalculatorTest extends Specification {

  "Given file Index 'FileRankCalculator.calculateRank'" should {
    "calculate and retun FileRanks" in {

      val index = Index(List(WordCounter("File1.txt", "abcd", 1), WordCounter("File1.txt", "efgh", 1), WordCounter("File2.txt", "abcd", 1)))

      val searchString = "abcd efgh"

      val result = List(FileRank("File1.txt", "100%"), FileRank("File2.txt", "50%"))

      FileRankCalculator.calculateRank(index, searchString).toSet shouldEqual result.toSet
    }
  }
  "FileRankCalculator.calculatePercentage" should {
    "compute and return a correct percentage" in {
      FileRankCalculator.calculatePercentage(1000, 1000) shouldEqual (100)
      FileRankCalculator.calculatePercentage(400, 500) shouldEqual (80)
    }
  }
}
