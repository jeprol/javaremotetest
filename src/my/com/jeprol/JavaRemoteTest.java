package my.com.jeprol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Program calculate equation based on input in string which consist of numbers,
 * brackets and operators separated by spaces
 * 
 * @author jeprol
 *
 */
public class JavaRemoteTest {

	// list of constant used in this program
	public static final String OPERATOR_PLUS = "+";
	public static final String OPERATOR_MULTIPLY = "*";
	public static final String OPERATOR_DIVIDE = "/";
	public static final String OPERATOR_MINUS = "-";
	public static final String BRACKET_START = "(";
	public static final String BRACKET_END = ")";
	public static final String EMPTY_STRING = "";
	public static final String SPACE_STRING = " ";

	public static void main(String[] args) {

		try {

			// Without brackets
			String string1 = "1 + 1";
			System.out.println("Question#1 => " + string1);
			System.out.println("Expected Answer :" + Double.valueOf((1 + 1)));

			System.out.println("Result :" + calculate(string1));
			System.out.println("\n");

			String string2 = "2 * 2";
			System.out.println("Question#2 => " + string2);
			System.out.println("Expected Answer :" + Double.valueOf((2 * 2)));
			System.out.println("Result :" + calculate(string2));
			System.out.println("\n");

			String string3 = "1 + 2 + 3";
			System.out.println("Question#3 => " + string3);
			System.out.println("Expected Answer :" + Double.valueOf((1 + 2 + 3)));
			System.out.println("Result :" + calculate(string3));
			System.out.println("\n");

			String string4 = "6 / 2";
			System.out.println("Question#4 => " + string4);
			System.out.println("Expected Answer :" + Double.valueOf((6 / 2)));
			System.out.println("Result :" + calculate(string4));
			System.out.println("\n");

			String string5 = "11 + 23";
			System.out.println("Question#5 => " + string5);
			System.out.println("Expected Answer :" + Double.valueOf((11 + 23)));
			System.out.println("Result :" + calculate(string5));
			System.out.println("\n");

			String string6 = "11.1 + 23";
			System.out.println("Question#6 => " + string6);
			System.out.println("Expected Answer :" + Double.valueOf((11.1 + 23)));
			System.out.println("Result :" + calculate(string6));
			System.out.println("\n");

			String string7 = "1 + 1 * 3";
			System.out.println("Question#7 => " + string7);
			System.out.println("Expected Answer :" + Double.valueOf((1 + 1 * 3)));
			System.out.println("Result :" + calculate(string7));
			System.out.println("\n");

			// With brackets
			String string8 = "( 11.5 + 15.4 ) + 10.1";
			System.out.println("Question#8 => " + string8);
			System.out.println("Expected Answer :" + Double.valueOf(((11.5 + 15.4) + 10.1)));
			System.out.println("Result :" + calculate(string8));
			System.out.println("\n");

			String string9 = "23 - ( 29.3 - 12.5 )";
			System.out.println("Question#9 => " + string9);
			System.out.println("Expected Answer :" + Double.valueOf((23 - (29.3 - 12.5))));
			System.out.println("Result :" + calculate(string9));
			System.out.println("\n");

			// With nested brackets
			String string10 = "10 - ( 2 + 3 * ( 7 - 5 ) )";
			System.out.println("Question#10 => " + string10);
			System.out.println("Expected Answer :" + Double.valueOf((10 - (2 + 3 * (7 - 5)))));
			System.out.println("Result :" + calculate(string10));
			System.out.println("\n");

			String string11 = "( 10 - 8 ) + ( ( 2 + 3 ) * ( 7 - 5 ) )";
			System.out.println("Question#11 => " + string11);
			System.out.println("Expected Answer :" + Double.valueOf(((10 - 8) + ((2 + 3) * (7 - 5)))));
			System.out.println("Result :" + calculate(string11));
			System.out.println("\n");

			String string12 = "( ( 2 + 4 ) * 10 ) / 5";
			System.out.println("Question#12 => " + string12);
			System.out.println("Expected Answer :" + Double.valueOf((((2 + 4) * 10) / 5)));
			System.out.println("Result :" + calculate(string12));
			System.out.println("\n");

		} catch (Exception e) {
			System.err.println(
					"Please check the input. Make sure numbers, operations, brackets are separated by spaces.");
			e.printStackTrace();

		}

	}

	/**
	 * To calculate the equation in String parameter which consist of numbers,
	 * brackets and operators separated by spaces
	 * 
	 * @param sum
	 * @return total calculation of the equation.
	 * @throws Exception
	 */
	public static double calculate(String sum) throws Exception {

		List<Integer> listBracketInOrder = new ArrayList<Integer>(); // to store brackets found in string.
		Map<Integer, List<Integer>> bracketsMap = new HashMap<Integer, List<Integer>>(); // using map to store index
		List<Integer> bracketsList = null; // temp list to store the brackets pairs
		List<Integer> bracketsListKey = new ArrayList<Integer>(); // to store temp brackets keys for sorting. // between
																	// brackets.

		// Step 1: Split based on space and store into array.
		String[] sumInArray = sum.split(SPACE_STRING);

		// Step 2: Loop the array and find brackets. Using recursive to handle the
		// nested brackets
		for (int i = 0; i < sumInArray.length; i++) {

			if (BRACKET_START.equals(sumInArray[i])) {

				listBracketInOrder.add(i); // add to the list if found

				findBracketEnd(sumInArray, listBracketInOrder, 0, i + 1);

			}
		}

		// Step 3. Store brackets pairs in the map for processing
		for (int i = 0; i < listBracketInOrder.size();) {
			bracketsList = new ArrayList<Integer>();
			bracketsList.addAll(listBracketInOrder.subList(i, i + 2));
			bracketsMap.put(listBracketInOrder.get(i + 1), bracketsList); // get the index of bracket end for sorting

			i += 2;

		}

		bracketsListKey.addAll(bracketsMap.keySet());

		// Step 4. Sort the brackets key to calculate the most inner nested brackets
		// first
		Collections.sort(bracketsListKey);

		// if the string has brackets, then calculate it recursively
		for (Integer integer : bracketsListKey) {

//			System.out.println("range of brackets to be processed >>" + bracketsMap.get(integer));

			recursiveCalculate(bracketsMap.get(integer).get(0), bracketsMap.get(integer).get(1), sumInArray);

		}

		return recursiveCalculate(0, 0, sumInArray);

	}

	/**
	 * 
	 * @param sumInArray         - array which contains numbers, brackets,
	 *                           operations
	 * @param listBracketInOrder
	 * @param countStart         - used to count nested brackets
	 * @param bracket            - value either "(" or ")"
	 * @param count              - position of index of sumInArray that being
	 *                           processed
	 */
	private static void findBracketEnd(String[] sumInArray, List<Integer> listBracketInOrder, int countStart, int count)
			throws Exception {

		// if found the pair, then add to the list
		if (BRACKET_END.equals(sumInArray[count]) && 0 == countStart ) {
			listBracketInOrder.add(count);

			// if found the bracket end but not its pair, then find recursively for the pair
		} else if (BRACKET_END.equals(sumInArray[count]) && 0 != countStart) {
			countStart -= 1;
			count += 1;

			findBracketEnd(sumInArray, listBracketInOrder, countStart, count);

			// if found the nested brackets, then find recursively for the pair
		} else if (BRACKET_START.equals(sumInArray[count])) {
			countStart += 1;
			count += 1;

			findBracketEnd(sumInArray, listBracketInOrder, countStart, count);

			// keep find recursively for the pair
		} else {
			count += 1;

			findBracketEnd(sumInArray, listBracketInOrder, countStart, count);

		}

	}

	/**
	 * To calculate normal equation and also range of array given of brackets
	 * 
	 * @param start      - starting index to be processed
	 * @param end        - ending index to be processed
	 * @param sumInArray - array that consists of numbers, brackets and operators
	 * @return total - calculation of the equation given range of array.
	 * @throws Exception
	 */
	private static double recursiveCalculate(Integer start, Integer end, String[] sumInArray) throws Exception {

		String[] stringRange;
		double total = 0d;
		boolean exitLoop = false;

		// check whether its normal equation or with brackets
		if ((0 == start && 0 == end)) {
			end = sumInArray.length;
		}

		stringRange = Arrays.copyOfRange(sumInArray, start, end);

		// this chuck of logic to handle operation priority which is multiply/divide
		// operation will be calculated first
		while ((Arrays.toString(stringRange).contains(OPERATOR_DIVIDE)
				|| Arrays.toString(stringRange).contains(OPERATOR_MULTIPLY)) && !exitLoop) {

//			System.out.println("List of string calculate of mutliply/divide >>>" + Arrays.toString(stringRange));

			for (int j = start; j <= end; j++) {

				if ((OPERATOR_MULTIPLY.equals(String.valueOf(sumInArray[j]))
						|| OPERATOR_DIVIDE.equals(String.valueOf(sumInArray[j])))
						&& (String.valueOf(sumInArray[j - 1]).isEmpty()
								|| String.valueOf(sumInArray[j + 1]).isEmpty())) {
					exitLoop = true;
					break;

				} else if (OPERATOR_MULTIPLY.equals(String.valueOf(sumInArray[j]))
						&& !String.valueOf(sumInArray[j - 1]).isEmpty()
						&& !String.valueOf(sumInArray[j + 1]).isEmpty()) {

//					System.out.println("Multiple operator>>>" + sumInArray[j]);

					total = Double.valueOf(sumInArray[j - 1]) * Double.valueOf(sumInArray[j + 1]);

					sumInArray[j - 1] = String.valueOf(total);
					sumInArray[j] = EMPTY_STRING;
					sumInArray[j + 1] = EMPTY_STRING;

					break;

				} else if (OPERATOR_DIVIDE.equals(String.valueOf(sumInArray[j]))
						&& !String.valueOf(sumInArray[j - 1]).isEmpty()
						&& !String.valueOf(sumInArray[j + 1]).isEmpty()) {

//					System.out.println("Divide operator>>>" + sumInArray[j]);

					total = Double.valueOf(sumInArray[j - 1]) / Double.valueOf(sumInArray[j + 1]);

					sumInArray[j - 1] = String.valueOf(total);
					sumInArray[j] = EMPTY_STRING;
					sumInArray[j + 1] = EMPTY_STRING;

					break;
				}

			}

			stringRange = Arrays.copyOfRange(sumInArray, start, end);

		}

		total = 0d; // reset the total since it used again for calculation

//		System.out.println("Movement of string when calculation perform>>>");

		for (int i = 0; i < sumInArray.length; i++) {

//			System.out.println(Arrays.toString(sumInArray));

			// calculate within the range given
			if (!sumInArray[i].isEmpty() && ((start <= i && i <= end) || (0 == start && 0 == end))) {

				if (!BRACKET_START.equals(String.valueOf(sumInArray[i]))
						&& !BRACKET_END.equals(String.valueOf(sumInArray[i]))
						&& !String.valueOf(sumInArray[i]).isEmpty()) {

					if (0d == total) {
						total = Double.valueOf(sumInArray[i]);
					} else if (OPERATOR_PLUS.equals(String.valueOf(sumInArray[i]))) {
						total += Double.valueOf(sumInArray[i + 1]);
						sumInArray[i + 1] = EMPTY_STRING;
					} else if (OPERATOR_MULTIPLY.equals(String.valueOf(sumInArray[i]))) {
						total *= Double.valueOf(sumInArray[i + 1]);
						sumInArray[i + 1] = EMPTY_STRING;
					} else if (OPERATOR_DIVIDE.equals(String.valueOf(sumInArray[i]))) {
						total /= Double.valueOf(sumInArray[i + 1]);
						sumInArray[i + 1] = EMPTY_STRING;
					} else if (OPERATOR_MINUS.equals(String.valueOf(sumInArray[i]))) {
						total -= Double.valueOf(sumInArray[i + 1]);
						sumInArray[i + 1] = EMPTY_STRING;
					}

					sumInArray[start] = String.valueOf(total);

				}

				sumInArray[i] = EMPTY_STRING; // after perform calculation, replace with empty string

			}
		}
		return total;
	}

}
