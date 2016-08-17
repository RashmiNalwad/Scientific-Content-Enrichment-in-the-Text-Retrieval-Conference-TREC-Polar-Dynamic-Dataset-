import pysolr
from fileinput import filename


def connectToSolr():
    # Setup a Solr instance.
    solr = pysolr.Solr('http://localhost:8983/solr/', timeout=10)
    #Query for all result
    results = solr.search('q=*:*')
    #Iterate through all results and write each result to file.
    for result in results:
        filename = result['Keyword']  #Keyword is the unique Id for each document
        target = open(filename, 'w')
        target.write(result)
        target.close()
    
if __name__ == "__main__":
    connectToSolr() 
    