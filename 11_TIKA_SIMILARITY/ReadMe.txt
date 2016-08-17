1) Measurement_similarity.py compares the measurements extracted from files and clusters the data with same units in to one cluster. For ex. Degree and Kelvin are two units of temperature data extracted from Polar Data set. 

2)Related_Publication _similarity.py compares the authors and related publications extracted from files and cluster the data with same author names in to one cluster.

3)Location_similarity.py compares the geographical locations extracted from files and clusters the data with same location names in to one cluster.

4)Sweet_similarity.py compares the sweet features extracted from files and clusters the data with same features names in to one cluster.

5) SolrPython.py uses PySolr library to connect to Apache Solr and query all the results which will be then fed to clustering algorithms for producing similarity scores