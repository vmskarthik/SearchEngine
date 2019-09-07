# Basic Search Engine

A command line driven text search engine for text files in a given directory.

The search engine has a FileIndexer which reads all the text files in the given directory, builds and maintains an in memory representation of the file content for the whole session.

When given a search string, the RankCalculator will calculate the percentage of matching in each files and returns a list of the top 10 matching filenames with ranks.


## Requirements

Scala 2.12

Java 8

sbt

## Run

```bash
sbt "run TextFilesDirectory"
```

This will give a prompt to enter search string

```bash
search>
```

To exit the application, type
```bash
:quit
```
