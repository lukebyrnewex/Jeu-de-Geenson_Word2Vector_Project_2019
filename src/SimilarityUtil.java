import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
* The class SimilarityUtil contains functions directly related
* to aiding the calculation the similarity between words.
*
* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
* @since   26-04-2019
*/
public class SimilarityUtil {
	
	/**
	* The method findAllSimilarities() finds *all* the
	* similarities between the input vector and all the vectors within
	* the VectorList. This is just one word's similarities calculated
	* at a time.
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   v = vector/word whose similarities is being calculate
	* @param   list = the list of vectors against which the similarities are
	* 	being calculated
	* @return  similarities = the mapping of each word in the VectorList
	* 	and its corresponding similarity to the input word
	*/
	private static Map<String, Double> findAllSimilarities(Vector v, VectorList list) {
		// Compare each indice against the VectorList
		Map<String, Double> similarities = new HashMap<String, Double>();
		double wordSimilarity = 0.0;
		
		// Compare such using an iterator over the entry set (input VectorList 'list')
		Iterator<Map.Entry<String, double[]>> iterator = list.vectorList.entrySet().iterator();
		
		// while list hasn't reached the end
		while(iterator.hasNext()) {
			// entry at this iteration
			Map.Entry<String, double[]> current = iterator.next();
			
			// cosinus similarity comparison between users choice and current vector
			wordSimilarity = VectorUtil.similarity(v, new Vector(current.getKey(), current.getValue(), list.getDimensions()));
			
			// make word similarity hashmap
			similarities.put(current.getKey(), wordSimilarity);
		}
		
		return similarities;
	}
	
	/**
	* findMeanOfSimilarities() takes in a list of an assumed correct input of 3 vectors,
	* and calculates the mean similarities between the three "similarity lists" which
	* were created with the previous function, findAllSimilarities(). This gets a 
	* mean similarity between all three words.
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   vecs = the vectors whose similarities are being averaged,
	* @param   list = the vector list against whom the similarities are being calculated
	* @return  similarityMeans = the mean similarity list, which contains
	* 	every word in the VectorList and its corresponding mean similarity to the three input
	*   word. *NOTE*: the key/value pair has been inversed in the return, for ease of use for
	*   the third and final similarityUtil function
	*/
	private static Map<Double, String> findMeanOfSimilarities(Vector[] vecs, VectorList list) {
		Map<Double, String> similarityMeans = new TreeMap<Double, String>(Collections.reverseOrder());
		Map<String, Double> similaritiesIndice1 = findAllSimilarities(vecs[0], list);
		Map<String, Double> similaritiesIndice2 = findAllSimilarities(vecs[1], list);
		Map<String, Double> similaritiesIndice3 = findAllSimilarities(vecs[2], list);
		
		for (Map.Entry<String, Double> entry : similaritiesIndice1.entrySet()) {
			double similarity2 = similaritiesIndice2.get(entry.getKey());
			double similarity3 = similaritiesIndice3.get(entry.getKey());
			double mean = meanOf3(entry.getValue(), similarity2, similarity3);
			similarityMeans.put(mean, entry.getKey());
		}
		
		return similarityMeans;
	}
	
	/**
	* findClosestSimilarities() is the only public and usable method, and uses the two
	* previous functions to achieve this goal. It once again takes in as input the
	* three "guesses" vectors, and the VectorList. Its primary goal is to organise the
	* mean similarities by ascending order, and then take the top 10 similarities in a
	* relatively easy manner as they're sorted by the TreeMap! The only constraint is that
	* it will skip over a word, if it appears in the top 10 similarities, if it is indeed
	* one of the three guesses.
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   vecs = the guesses, VectorList = the list of words and their vectors
	* @param   list = the list from which the vectors come
	* @param   k = the number of closest similarities we must find
	* @return  the mapping of the k closest similarities
	*/
	public static Map<Double, String> findClosestSimilarities(Vector[] vecs, VectorList list, int k) {
		Map<Double, String> similarities = findMeanOfSimilarities(vecs, list);
		
		// as it's in a TreeMap, just add the first 10 under 1 to closestSimilarities
		Map<Double, String> closestSimilarities = new TreeMap<Double, String>();
		
		// ... but must skip over word if it's one of the guessed words
		for (Map.Entry<Double, String> entry : similarities.entrySet()) {
			if (entry.getValue().compareTo(vecs[0].getName()) == 0
					|| entry.getValue().compareTo(vecs[1].getName()) == 0
					|| entry.getValue().compareTo(vecs[2].getName()) == 0) {
				continue;
			}
			else if (closestSimilarities.size() < k) {
				closestSimilarities.put(entry.getKey(), entry.getValue());
			}
		}
		
		return closestSimilarities;
	}
	
	/**
	* meanOf3 is a very simple function for, indeed, finding the mean
	* of three numbers (double, in this case)
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   a = number 1
	* @param   b = number 2
	* @param   c = number 3
	* @return  the mean of the three numbers,  (a + b + c) / 3
	*/
	static double meanOf3(double a, double b, double c) {
		return (a + b + c) / 3;
	}
}
