ReadMe 

We designed and developed a REST API from scratch to crawl data from Ecommerce websites like Amazon and Ebay. 

Input: A user-supplied config file that specifies 
	1) seed url 
	2) Xpath/CSS selectors of urls 
Output: id, image, sales, price of all the items sold

Challenges:
build a distributed, scalable, high-performance (50 million docs per day), polite(set a cap on pages downloaded for each server), continuous, extensible, and portable. 


Why another webcrawler? 
Despite existing powerful crawler such as wget, for my project it required a webcrawler that allowed easy customization. Moreover, Sun's tutorial webcrawler lacks some important features since it's not really multithreaded (although the actual crawling is spawned off in a separate thread). 

Multithreading
Webcrawling speed is governed not only by the speed of one's one internet connection, but also by the speed of the sites that are to be crawled. Especially if one is crawling sites from multiple servers, the total crawling time can be significantly reduced, if many downloads are done in parallel. 

Algorithm 
When the crawler visits a web page, it extracts links to other web pages and puts these URLs at the end of a queue, and continues crawling to a URL that it removes from the front of the queue. Java provides easy-to-use classes for both multithreading and handling of lists. It is desirable that one and the same webpage is not crawled multiple times. For that reason we use a hashset to keep track of URLs that have been visited. 

Crawling a web page 
The two main tasks of a webcrawler are saving data from a URL and extracting hyperlinks. 

Maven 
Our crawler runs on an Apache server. We choose Maven to manage our project. It automates complilation of classes and deployment to the server.
"run clean intall" automatically generates .jar 
pom.xml specifies 1) project info, 2) dependencies, 3) plugins 
It will pull in the dependencies declared in the pom.xml doc in local repositories and remote repositories. 
Plugins are actions that take place to run our application, such as compile and test phase, and install phase. 
Benefits: 
	1) reduce script complexity
	2) externalize dependency management
	3) create consistent builds through standardized commands
Maven automates the following activities easy with one click!
- multi developer environment 
- version control of source code 
- run unit test 
- deployment  
Independent IDE, continuous integration(CI), 
  

Design patterns 
The best design pattern for such a procedure is Producer-Consumer design pattern
- use wait and notify method to communicate between P and C thread and blocking each of them on individual condition like full queue or empty queue. 
Implements interface 
Implements factory design pattern 
Data Assess Object(DAO) pattern to separate low level data accessing API from high level business services mainly to perform CRUD (create, get, update, and delete) operations. 


Data structure: 
- we use BlockingQueue (thread safe) to avoid deliberately handling low-level synchronization


Questions: 
- crawler run on server or not? 
- check if a url has been visited when reopening the computer
- give config file as a prameter of ECommerceCrawler
- pom.xml has some underlines 
- use two threads to test, is there a problem? Should we specify which thread puts and which thread takes? 
- occational exception
- Our crawler runs on server? deploy on server? pom (groupID)? 
- Where's plugins? Mojo? 
- can we make our primary key to be the item Url?  
- where's your JE_HOME? JE_HOME/lib/je-<version>.jar to the app's classpath? do you need to specify the path or does it automatically search for the jar locally and remotely? 
- What does transactions do? 
- why isn't there EntityStore? 

TODO: 
- auto-test for CSS selector
- handle when the selector results in empty results 
- absolute URL vs. relative URL 
- handle date type 
- add parser 
- handle multiple points (itemSelectors): maybe add try/catch
	(e.g. price) 
- allows multiple config inputs


Before running the program: 
update JRE System Library 1.5 to 1.8
Ask for absolute Path to database (Where would you want to store data)

