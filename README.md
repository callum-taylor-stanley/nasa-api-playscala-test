# nasa-api-test

This application queries the [NASA Image and Video Library API](https://api.nasa.gov/api.html).
The application is intended to support as many users as possible, JavaScript/No JavaScript and a variety of browsers.

## Running

Run this using [sbt](http://www.scala-sbt.org/).

```bash
sbt run

sbt test - for tests
```

And then go to <http://localhost:9000> to see the running web application.

Overview of files

## Controllers

- SearchController.scala:

  Controls the search and assets pages.

## Services

- SearchService.scala:

  Calls the NASA APIs

## Models

- AssetSearch.scala:

  The model containing the fields we want to query the NASA API with
  
- NasaAsset.scala

  Contains all the fields that an asset in the NASA API should return
  
## TODO
  
  - Implement proper error handling
  - Improve styling for image searches
  - Improve layout for all results, display information tidier
  - Finish implementing tests
  - Implement some sort of spinner control to show when content is loading
  - JavaScript unit tests?
  
