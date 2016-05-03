#ReadMe 

We designed and built a REST API from scratch that allow users to access the eCommerce sales database constructed through crawling major eCommerce websites such as Ebay, Amazon and Macy's. We used Bereley DB to implement a disk-backed data store. Both our database and REST service are hosted on Linode (Amazon EC2 takes too much time to configure). The input parameters of our API is site name and keys to get. The output is a JSON data streams of product information. 

###Why another webcrawler? 

Despite existing powerful crawler such as wget, we believe there's a need for webcrawler that allowed easy customization. Moreover, the traditional webcrawler lacks some important features since it's not really multithreaded (although the actual crawling is spawned off in a separate thread). 

###Algorithm 

The two main tasks of a webcrawler are saving data from a URL and extracting hyperlinks. When the crawler visits a web page, it downloads the html page and extracts URLs it needs to visit next and puts them into a queue. For example, the crawler enters www.amazon.com and needs to navigate itself through to reach the level of an individual item. These URLs are then visited later when they were dequeued. When the crawler hits the product information levle, it will download sales information for each product.  

###Design patterns 

- Producer-Consumer: The best design pattern to implement the above algorithm is Producer-Consumer design pattern. A producer puts tasks into the queue and a worker/consumer gets it to process depending on what type of task it is.
- Factory pattern: since there are potentially many websites to crawl and each is structured in different ways, we wrote a config file for each site to specify the XPath in order to help the crawler navigate through the site and locate product page. The ConfigFactory decides which config file to instantiate depending on the site parameter. All the config files (AmazonConfig, EbayConfig, etc.) implements the Config interface.  
- Data Assess Object(DAO) pattern is used to facilitate communications between database and application. It serves as interface that specifies how to save and fetch data into BerekeleyDB. 

###Multithreading

We used a threadpool to help crawling more efficient. Each thread is responsible to crawl one website. There are only two workers now but the benefit from reducing overhead will increase with more and more sites being added. 

###Data structure: 
We used BlockingQueue (thread safe) to avoid deliberately handling low-level synchronization. Other data structures include HashMap, ArrayList, etc. 

###Reasons behind choice of technology: 
REST API 
There are various reasons for adopting REST as the underlying layer for data access. It makes the extracting, transforming and loading of data easier. And it enables fast web services and the ability to get a quick response. In addition, JSON results are mobile friendly so require no translation layer. 

BerekeleyDB
We chose BerekeleyDB (a NoSQL database) to store cralwed data because it's a popular embedded database and is relatively easy to use. Our store holds the product information such as sales, price, date, and image. The path to the BerkeleyDB data store is specified as the BDBstore context in our web.xml file. 

Maven 
We choose Maven to manage our project. It automates complilation of classes and deployment to the server. The pom.xml specifies 1) project info, 2) dependencies, 3) plugins. It will pull in the dependencies declared in the pom.xml doc in local repositories and remote repositories. 

###Division of work
Ting Wang: main crawler classes, domain and web content classes 
Ting Yin: set up the Maven, BerekeleyDB implementation, project deployment on cloud-hosting server 

