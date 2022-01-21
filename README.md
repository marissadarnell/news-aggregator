# news-aggregator
This news aggregator extracts articles 
- from multiple sources (live url: newsapi.org, a calpoly news api, or a file) 
- in multiple formats (newsapi.org format, calpoly news api format)  

with user-specified filtering (see example below, or `input/config` for more examples).  
The user makes these specifications via passing a file as an argument when running the program.  
 
`url`  
`name: NewsAPI Headlines (Live)`  
`format: newsapi`  
`source: http://newsapi.org/v2/top-headlines?country=us&apiKey={NEWS_API_KEY}`  
`filter: Harris & ( washingtonpost | usatoday )`  
`delay:300`  

For "live" sources, a delay can be specified and the source will be queried once per the given interval. This will add any new articles each time while discarding repeats.  

This program supports concurrently (and repeatedly) retreiving articles by giving each specified news source a `Runnable`.  
A `BlockingQueue` is used to print the retreived articles without interleaving each article's output.  
The ANTLR library is used to parse the filtering expressions and the Visitor pattern is implemented to evaluate the filtering expressions.
