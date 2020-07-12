/**
* The VectorUtil class contains the necessary functions
* for manipulation of the Vectors in the Vector and
* VectorList classes. It contains operations such as
* addition, subtraction, average etc. as defined in the
* project guidelines.
*
* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
* @since   26-04-2019
*/
public class VectorUtil {
	
	/**
	* addition() adds two Vectors' respective vector arrays
	* of 'd' dimension together
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   a = first vector
	* @param   b = second vector
	* @return  sum = the Vector containing their
	*	joint additive values
	*/
	public Vector addition(Vector a, Vector b) {
		Vector sum = new Vector("sum");
		
		if (a.getDimensions() != b.getDimensions()) { 
			System.err.println("Dimensional space not equal, try again!");
		}
		else {
			sum.setVectors(new double[a.getDimensions()]);
			for (int i = 0; i <= a.getDimensions(); i++) {
				sum.getVectors()[i] = a.getVectors()[i] + b.getVectors()[i];
			}
		}
		return sum;
	}
	
	/**
	* subtraction() substracts one Vector's respective vector arrays
	* of 'd' dimension from another
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   a = first vector
	* @param   b = second vector
	* @return Vector diff, the Vector containing their
	*	subtracted values, otherwise known as difference
	*/
	public Vector subtraction(Vector a, Vector b) {
		Vector diff = new Vector("diff");
		
		if (a.getDimensions() != b.getDimensions()) { 
			System.err.println("Dimensional space not equal, try again!");
		}
		else {
			diff.setVectors(new double[a.getDimensions()]);
			for (int i = 0; i <= a.getDimensions(); i++) {
				diff.getVectors()[i] = a.getVectors()[i] - b.getVectors()[i];
			}
		}
		return diff;
	}
	
	/**
	* average() adds two Vectors respective vector arrays
	* of 'd' dimension together and divides each vector pair
	* in half, in effect finding their average
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   a = first vector
	* @param   b = second vector
	* @return av = contains the vectors' averages
	*/
	public Vector average(Vector a, Vector b) {
		Vector av = new Vector("av");
		
		if (a.getDimensions() != b.getDimensions()) { 
			System.err.println("Dimensional space not equal, try again!");
		}
		else {
			av.setVectors(new double[a.getDimensions()]);
			for (int i = 0; i <= a.getDimensions(); i++) {
				av.getVectors()[i] = (a.getVectors()[i] + b.getVectors()[i]) / 2;
			}
		}
		return av;
	}
	
	/**
	* scalarMultiplication() takes one vector and multiplies its
	* vector components by a scalar (a whole number which changes the
	* magnitude, but not the direction, of a vector).
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   v = the vector
	* @param   scalar = the scalar to multiply the vector by
	* @return  v = the vector from input with all its components
	* 	multiplied by the scalar from input
	*/
	public Vector scalarMultiplication(Vector v, int scalar) {
		for (int i = 0; i <= v.getDimensions(); i++) {
			v.getVectors()[i] *= scalar;
		}
		return v;
	}
	
	/**
	* norm() squares each of the vector components, adds them
	* up, and then finds the square root of their total sum
	* i.e. Math.sqrt(v.vec1^2 + v.vec2^2 + ... v.vecd^2), 
	* which effectively calculates the magnitude of the
	* Vector
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   v = the vector for whom the norm is calculated
	* @return Math.sqrt(sum) - returns the equation described
	* 	above
	*/
	public double norm(Vector v) {
		double sum = 0.0;
		for (int i = 0; i <= v.getDimensions(); i++) {
			sum += (v.getVectors()[i] * v.getVectors()[i]);
		}
		return Math.sqrt(sum);
	}
	
	/**
	* distance() calculates the aptly-named distance between
	* two Vectors. This is calculated with the following formula:
	* 	- Math.sqrt((va.vec1 - vb.vec1)^2) + (va.vec2 - vb.vec2)^2 + ... 
	* 			... + (va.vecd - vb.vecd)^2)
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   a = first vector
	* @param   b = second vector
	* @return Math.sqrt(sum) - the result of the formula described above
	*/
	public double distance(Vector a, Vector b) {
		double sum = 0.0;
		double temp = 0.0;
		if (a.getDimensions() != b.getDimensions()) { 
			System.err.println("Dimensional space not equal, try again!");
		}
		else {
			for (int i = 0; i <= a.getDimensions(); i++) {
				temp = (a.getVectors()[i] - b.getVectors()[i]);
				sum += temp * temp;
			}
		}
		return Math.sqrt(sum);
	}
	
	/**
	* similarity() calculates the apparent closeness of two words
	* using a mathematical formula known as the cosinus similarity,
	* whose formula is written below:
	* 
	* similarity(va, vb) = 
	* 	((va.vec1 * vb.vec1) + ... (va.vecd * vb.vecd)) / (divided by next line) [[[
	* 	Math.sqrt((va.vec1)^2 + ... (va.vecd)^2)) * Math.sqrt((vb.vec1)^2 ... (vb.vecd)^2) ]]]
	* 
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   a = first vector
	* @param   b = second vector
	* @return  numerator / denominator = i.e. returns the cosinus similarity 
	* 	of the two vectors
	*/
	public static double similarity(Vector a, Vector b) {
		double numerator = 0;
		double denominatorLeft = 0;
		double denominatorRight = 0;
		double denominator = 0;
		
		if (a.getDimensions() == b.getDimensions()) {
			for (int i = 0; i < a.getDimensions(); i++) {
				numerator += (a.getVectors()[i] * b.getVectors()[i]);
				denominatorLeft += (a.getVectors()[i] * a.getVectors()[i]);
				denominatorRight += (b.getVectors()[i] * b.getVectors()[i]);
			}
			denominator = (Math.sqrt(denominatorLeft) * Math.sqrt(denominatorRight));
		}
		return numerator / denominator;
	}
}