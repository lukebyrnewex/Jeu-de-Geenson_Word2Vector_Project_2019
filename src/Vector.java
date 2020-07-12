/**
* The Vector class defines the central data structure of the project,
* a vector of 'd' dimensions, which contains a String key (name, word)
* and an array of doubles which are the doubles in the specified
* dimensions
*
* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
* @since   26-04-2019
*/
public class Vector {
	
	/**
	* The instance variables of the Vector class.
	* String name = the word in question
	* double[] vectors = the vectors associated with the word
	* int dimensions = the amount of dimensions, and therefore the
	* 	amount of vectors, associated with each word
	* 
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	*/
	private String name;
	private double[] vectors;
	private int dimensions;
	
	/**
	* Standard constructor with parametres for all three
	* instance variables described above
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   name = the word from the vector and its key
	* @param   vectors = the list of vectors for this word
	* @param   dimensions = the amount of dimensions this word has,
	* 	or in other words the amount of vectors it has
	*/
	public Vector(String name, double[] vectors, int dimensions) {
		this.name = name;
		this.vectors = vectors;
		this.dimensions = dimensions;
	}
	
	
	/**
	* Basic constructor which only takes a name/word as parameter.
	* The others, vectors and dimensions, are set to default values.
	* Used mostly in VectorUtil.java
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   name = the word
	*/
	public Vector(String name) {
		this.name = name;
		this.vectors = null;
		this.dimensions = 0;
	}
	
	
	/**
	* Standard toString method for the Vector class
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @return  String representation of Vector
	*/
	public String toString() {
		String str = "";
		str += this.name + " ";
		for (int i = 0; i < this.dimensions; i++) { // CHANGE 100
			str += this.vectors[i] + " ";
		}
		return str;
	}
	
	/**
	* toString method which prints to console rather than
	* returning a string
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	*/
	
	public void printVector() {
		System.out.println(this.name + " (with " + this.dimensions + " dimensions):");
		for (int i = 0; i < this.dimensions; i++) {
			System.out.print(this.vectors[i] + " ");
		}
	}
	
	/**
	* Getters and setters for Vector instance variables
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	*/
	public String getName() 					{ return name; }
	public void setName(String name) 			{ this.name = name; }
	public double[] getVectors() 				{ return vectors; }
	public void setVectors(double[] vectors)	{ this.vectors = vectors; }
	public int getDimensions() 					{ return dimensions;}
	public void setDimensions(int dimensions) 	{ this.dimensions = dimensions;}
}
