# Hadoop Sentiment Analysis Project

Hadoop big data integration project which uses sentiment analysis on product comments to suggest changes in product line. The accompanying report and presentation highlight challenges overcome during each step of big data integration (schema alignment, record linkage, data fusion). February 2018. 


Dataset
--------------------
Cloudera provides a dataset of Dualcore's sales and product reviews. Dualcore is a web-based company which sells technology products. I combined this dataset with a sentiment analysis library to create measurable opinion scores. 



Process
--------------------
The original dataset includes 1,662,951 sales, 1,114 unique products, and 21,997 rating comments. I filtered this dataset to focus on the tablet, laptop, and server lines. 

I then created a count of positive and negative words for each customer rating comment. Processing text involves turning words into numbers. I quantified these product opinions by counting the words which matched libraries of positive and negative words. After counting, I aggregated these opinions by product.

The result was 68 products. From these opinion counts, I calculated Sentiment and Positivity for each product.  


Tools
--------------------
Since text processing lends itself well to key-value format, I used the Hadoop ecosystem as the system for processing the text comments. Scoop was used to ingest the data by chunks into the HDFS where the relationships could be stored. MapReduce, Hive, and Impala were used to process the data.  After processing, the results were exported to a diminsional table for customized reporting. 


Links
--------------------
Report: https://github.com/BethHilbert/Hadoop-SentimentAnalysis/blob/master/Hadoop-Sentiment%20Report.pdf

Presentation: https://github.com/BethHilbert/Hadoop-SentimentAnalysis/blob/master/Hadoop-Sentiment%20Presentation.pdf

Data Structure: https://github.com/BethHilbert/Hadoop-SentimentAnalysis/blob/master/DualCore%20Data%20Structure.pdf

HiveQL Code: https://github.com/BethHilbert/Hadoop-SentimentAnalysis/blob/master/Code/DualCore%20HiveQL%20Code