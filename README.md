Basic Search Engine

A command line driven text search engine for text files in a given directory.

The search engine has a FileIndexer which reads all the text files in the given directory, builds and maintains an in memory representation of the file content for the whole session.

When given a search string, the RankCalculator will calculate the percentage of matching in each files and returns a list of the top 10 matching filenames in rank order.


Requirements

Scala 2.12
sbt

Run

sbt "run TextFilesDirectory"

Type :quit to stop the application