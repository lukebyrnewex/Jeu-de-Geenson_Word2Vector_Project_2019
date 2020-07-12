import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* The VectorList class is, as the name would imply, a data structure based
* around a list of Vectors. It could more accurately be described as a 
* VectorMap, as it is based on a HashMap with the Vector name as key and
* Vector double array as its value. However, it is not strictly used as a
* HashMap, as its variable implementation as a TreeMap would suggest. 
*
* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
* @since   26-04-2019
*/
public class VectorList {
	public Map<String, double[]> vectorList = new HashMap<String, double[]>();
	private int nombreDeRepresentations;
	private int dimensions;
	private String vectorFileName;
	
	/**
	* VectorList constructor. It takes in a filename, and parses from that file
	* all the relevant information to construct the VectorList, which includes all
	* the instance variables described above.
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   filename = the name of the file from which the vector
	* 	list comes
	* @throws  IOException : if file can't be read properly
	*/
	public VectorList(String filename) throws IOException {
		this.vectorFileName = filename;
		
		FileReader fr = new FileReader(this.vectorFileName);
    	BufferedReader br = new BufferedReader(fr);
    	String line = br.readLine();
    	String contents[] = line.split(" ");
    	
    	// First and second ints on line 1 define the nombre de représentations and dimensions
    	this.nombreDeRepresentations = Integer.parseInt(contents[0]);
		this.dimensions = Integer.parseInt(contents[1]);
		
		String vectorName = "";
		double[][] vectors = new double[this.nombreDeRepresentations][this.dimensions];
		
		int wordCount = 0;
		while ((line = br.readLine()) != null) {
			contents = line.split(" ");
			
			// Read in vector names
			vectorName = contents[0];
			
			// Read in vectors
			for (int i = 1; i <= this.dimensions; i++) {
				vectors[wordCount][i-1] = Double.parseDouble(contents[i]);
			}
			
			// Add to HashMap
			this.vectorList.put(vectorName, vectors[wordCount]);
			
			// Next line in vector list
			wordCount++;
			
			// Skip every second line (as they're empty)
			line = br.readLine();
		}
		br.close();
	}
	
	/**
	* The createVector() function takes in a word from the VectorList
	* and returns its associated Vector
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   word - the word in the vector list whose vector
	* 	one is constructing with this function
	* @return  Vector of the param word
	*/
	public Vector createVector(String word) {
		return new Vector(word, this.vectorList.get(word), this.dimensions);
	}
	
	/**
	* The randomVector() function takes a word at random from the VectorList
	* it is used on and returns it as a single Vector
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @return  Vector = a random vector from the VectorList
	*/
	public Vector randomVector() {
		Random random = new Random();
		List<String> keys = new ArrayList<String>(this.vectorList.keySet());
		String randomKey = keys.get(random.nextInt(keys.size()));
		double[] value = this.vectorList.get(randomKey);
		
		return new Vector(randomKey, value, this.dimensions);
	}
	
	/**
	* The printVectorListDetails() method prints out the relevant information
	* of the VectorList in a chart-form, including the filename, the dimensions
	* and the amount of words within the VectorList
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	*/
	public void printVectorListDetails() {
		System.out.println("VectorList from file " + this.vectorFileName);
		System.out.println("\tDimensions: " + this.dimensions);
		System.out.println("\tAmount of words: " + this.nombreDeRepresentations);
	}
	
	/**
	* The printVectorList() function prints out every key ('word') and its
	* associated values ('vectors') in the VectorList
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	*/
	public void printVectorList() {
		printVectorListDetails();
		for (String name : this.vectorList.keySet()) {
            String key = name;
            double[] value = this.vectorList.get(name);  
            
            System.out.print(key + " \t\t[");
            for (int i = 0; i < this.dimensions; i++) {
            	System.out.print(value[i] + ",");
            }
            System.out.println("]");
		} 
	}
	
	/**
	* The Getters and Setters for the VectorList class
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	*/
	public int getDimensions() 							 { return dimensions;					 }
	public void setDimensions(int dimensions) 			 { this.dimensions = dimensions;		 }
	public String getVectorFileName() 					 { return vectorFileName;				 }
	public void setVectorFileName(String vectorFileName) { this.vectorFileName = vectorFileName; }
	public int getNombreDeRepresentations() 			 { return nombreDeRepresentations;		 }
	public void setNombreDeRepresentations(int n) 		 { this.nombreDeRepresentations = n; 	 }
}