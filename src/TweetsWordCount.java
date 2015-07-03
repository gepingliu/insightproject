import java.io.*;
import java.util.*;
 
public class TweetsWordCount {
	
	//compute the median of numbers in a vector
	public static double median(Vector v){
		Integer[] count = new Integer[v.size()];
		Collections.sort(v);
		v.toArray(count);
		
		int middle = count.length / 2;
	    if (count.length % 2 == 1) {//total number is odd
	        return count[middle];
	    } else {//total number is even
	        return (count[middle-1] + count[middle]) / 2.0;
	    }
	}
 
    public static void main(String a[]) throws IOException{
    	
    	//writers for features 1 and 2
    	PrintWriter pw1 = new PrintWriter(new FileWriter("ft1.txt"));
    	PrintWriter pw2 = new PrintWriter(new FileWriter("ft2.txt"));
    	
    	//hash table for words of whole file
    	HashMap mapFile = new HashMap();
    	
    	//vector to store the unique word counts for each tweet
    	Vector uniqueWordCounts = new Vector();
    	
    	int numUniqueWord =0;//number of unique words in each tweet
    	
        String strLine = "";//each tweet is read into strLine
        
        BufferedReader bufferReader = null;
        
        try {
            bufferReader = new BufferedReader( new FileReader("tweets.txt"));
            
            //read each tweet into strLine
            while( (strLine = bufferReader.readLine()) != null){
                
            	//hash table for words in each tweet
                HashMap mapTweet = new HashMap();
                
                numUniqueWord = 0;
                StringTokenizer tokenizer = new StringTokenizer(strLine);
                
                // read each word in a single tweet to word
                while(tokenizer.hasMoreTokens()) {
                	String word = tokenizer.nextToken();
                	
                	//hash for each word for the whole file
                	if(mapFile.containsKey(word)) {
                        // if we have already seen this word before,
                        Integer count = (Integer)mapFile.get(word);
                        mapFile.put(word, new Integer(count.intValue() + 1));
                      } else {//this is a new word
                        mapFile.put(word, new Integer(1));
                        
                      }
                	
                	//hash for each word for a single tweet
                	if(mapTweet.containsKey(word)) {
                        // if we have seen this word before,
                        Integer count = (Integer)mapTweet.get(word);
                        mapTweet.put(word, new Integer(count.intValue() + 1));
                      } else {//this is a new word
                        mapTweet.put(word, new Integer(1));
                        numUniqueWord ++;
                      }
                }
                
                //for each tweet
                //add the number of unique words to uniqueWordCounts
                //then compute and output its median to ft2.txt
                uniqueWordCounts.addElement(new Integer(numUniqueWord));                
                pw2.println(median(uniqueWordCounts));
                
            }
            
            //now we have the hash table for words in the whole file
            //output to file ft1.txt
            ArrayList arraylist = new ArrayList(mapFile.keySet());
            Collections.sort(arraylist);
            
            for (int i = 0; i < arraylist.size(); i++) {
            	
              String key = (String)arraylist.get(i);
              Integer count = (Integer)mapFile.get(key);
              
              pw1.println(key + "  " + count);
            }        
            
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: tweets.txt");
        } catch (IOException e) {
            System.err.println("Unable to read the file: tweets.txt");
        }
        
        pw1.close();
        pw2.close();
    }
}
