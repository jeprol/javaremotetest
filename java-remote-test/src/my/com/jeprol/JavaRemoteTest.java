package my.com.jeprol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaRemoteTest {

	// list of constant used in this program
	public static final String OPERATOR_PLUS = "+";
	public static final String OPERATOR_MULTIPLY = "*";
	public static final String OPERATOR_DIVIDE = "/";
	public static final String OPERATOR_MINUS = "-";
	public static final String BRACKET_START = "(";
	public static final String BRACKET_END = ")";

	public static void main(String[] args) {

		try {

			// Without brackets
			String string1 = "1 + 1";
			System.out.println("Question => " + string1);
			System.out.println("Answer :" + Double.valueOf((1 + 1)));
			
			System.out.println("Result :" + calculate(string1));
			System.out.println("\n");

			String string2 = "2 * 2";
			System.out.println("Question => " + string2);
			System.out.println("Answer :" + Double.valueOf((2 * 2)));
			System.out.println("Result :" + calculate(string2));
			System.out.println("\n");

			String string3 = "1 + 2 + 3";
			System.out.println("Question => " + string3);
			System.out.println("Answer :" + Double.valueOf((1 + 2 + 3)));
			System.out.println("Result :" + calculate(string3));
			System.out.println("\n");

			String string4 = "6 / 2";
			System.out.println("Question => " + string4);
			System.out.println("Answer :" + Double.valueOf((6 / 2)));
			System.out.println("Result :" + calculate(string4));
			System.out.println("\n");

			String string5 = "11 + 23";
			System.out.println("Question => " + string5);
			System.out.println("Answer :" + Double.valueOf((11 + 23)));
			System.out.println("Result :" + calculate(string5));
			System.out.println("\n");

			String string6 = "11.1 + 23";
			System.out.println("Question => " + string6);
			System.out.println("Answer :" + Double.valueOf((11.1 + 23)));
			System.out.println("Result :" + calculate(string6));
			System.out.println("\n");

			String string7 = "1 + 1 * 3";
			System.out.println("Question => " + string7);
			System.out.println("Answer :" + Double.valueOf((1 + 1 * 3)));
			System.out.println("Result :" + calculate(string7));
			System.out.println("\n");

			// With brackets
			String string8 = "( 11.5 + 15.4 ) + 10.1";
			System.out.println("Question => " + string8);
			System.out.println("Answer :" + Double.valueOf(((11.5 + 15.4) + 10.1)));
			System.out.println("Result :" + calculate(string8));
			System.out.println("\n");

			String string9 = "23 - ( 29.3 - 12.5 )";
			System.out.println("Question => " + string9);
			System.out.println("Answer :" + Double.valueOf((23 - (29.3 - 12.5))));
			System.out.println("Result :" + calculate(string9));
			System.out.println("\n");

			// With nested brackets
			String string10 = "10 - ( 2 + 3 * ( 7 - 5 ) )";
			System.out.println("Question => " + string10);
			System.out.println("Answer :" + Double.valueOf((10 - ( 2 + 3 * ( 7 - 5 ) ))));
			System.out.println("Result :" + calculate(string10));
			System.out.println("\n");

			String string11 = "( 10 - 8 ) + ( ( 2 + 3 ) * ( 7 - 5 ) )";
			System.out.println("Question => " + string11);
			System.out.println("Answer :" + Double.valueOf(((10 - 8) + ((2 + 3) * (7 - 5)))));
			System.out.println("Result :" + calculate(string11));
			System.out.println("\n");
			
			String string12 = "2 * 4 + 10 / 5";
			System.out.println("Question => " + string11);
			System.out.println("Answer :" + Double.valueOf(((10 - 8) + ((2 + 3) * (7 - 5)))));
			System.out.println("Result :" + calculate(string11));
			System.out.println("\n");

		} catch (Exception e) {
			System.err.println(
					"Please check the input. Make sure numbers, operations, brackets are separated by spaces	");
			e.printStackTrace();

		}

	}

	/**
	 * To calculate the equation in String parameter which consist of numbers and
	 * operators separated by spaces
	 * 
	 * @param sum
	 * @return
	 * @throws Exception
	 */
	public static double calculate(String sum) throws Exception {

		List<Integer> listBracketInOrder = new ArrayList<Integer>(); // to store brackets found in string.
		Map<Integer, List<Integer>> bracketsMap = new HashMap<Integer, List<Integer>>(); // using map to store index
																							// between brackets.

		// Step 1: Split based on space and store into array.
		String[] sumInArray = sum.split(" ");

		// Step 2: Loop the array and find brackets. Using recursive to handle the
		// nested brackets
		for (int i = 0; i < sumInArray.length; i++) {

			if (BRACKET_START.equals(sumInArray[i])) {

				listBracketInOrder.add(i); // add to the list if found

				findBracketEnd(sumInArray, listBracketInOrder, 0, i + 1);

			}
		}

		List<Integer> bracketsList = null;
		List<Integer> bracketsListKey = new ArrayList<Integer>();

		for (int i = 0; i < listBracketInOrder.size();) {

			bracketsList = new ArrayList<Integer>();
			bracketsList.addAll(listBracketInOrder.subList(i, i + 2));
			bracketsMap.put(listBracketInOrder.get(i + 1), bracketsList);

			i += 2;

		}

		bracketsListKey.addAll(bracketsMap.keySet());

		Collections.sort(bracketsListKey);

		for (Integer integer : bracketsListKey) {

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
	 * @return
	 */
	private static int findBracketEnd(String[] sumInArray, List<Integer> listBracketInOrder, int countStart,
			int count) {

		// if found the pair, then add to the list
		if (BRACKET_END.equals(sumInArray[count]) && countStart == 0) {
			listBracketInOrder.add(count);

			// if found the bracket end but not its pair, then find recursively for the pair
		} else if (BRACKET_END.equals(sumInArray[count]) && countStart != 0) {
			countStart = countStart - 1;
			count = count + 1;

			findBracketEnd(sumInArray, listBracketInOrder, countStart, count);

			// if found the nested brackets, then find recursively for the pair
		} else if (BRACKET_START.equals(sumInArray[count])) {
			countStart = countStart + 1;
			count = count + 1;

			findBracketEnd(sumInArray, listBracketInOrder, countStart, count);

			// keep find recursively for the pair
		} else {
			count = count + 1;

			findBracketEnd(sumInArray, listBracketInOrder, countStart, count);

		}
		return count;

	}

	/**
	 * 
	 * @param start      - starting index to be processed
	 * @param end        - ending index to be processed
	 * @param sumInArray
	 * @return
	 * @throws Exception
	 */
	private static double recursiveCalculate(Integer start, Integer end, String[] sumInArray) throws Exception {

		String[] stringRange;
		double total = 0d;
		boolean exitLoop = false;

		if ((0 == start && 0 == end)) {
			stringRange = Arrays.copyOfRange(sumInArray, 0, sumInArray.length);
			start = 0;
			end = sumInArray.length;
		} else {
			stringRange = Arrays.copyOfRange(sumInArray, start, end);
		}

		// this chuck of logic to handle operation priority which is multiply/divide
		// operation will be calculated first
		while ((Arrays.toString(stringRange).contains(OPERATOR_DIVIDE)
				|| Arrays.toString(stringRange).contains(OPERATOR_MULTIPLY)) && !exitLoop) {

			for (int j = start; j <= end; j++) {
				if ((OPERATOR_MULTIPLY.equals(String.valueOf(sumInArray[j]))
						|| OPERATOR_DIVIDE.equals(String.valueOf(sumInArray[j])))
						&& (String.valueOf(sumInArray[j - 1]).isEmpty()
								|| String.valueOf(sumInArray[j + 1]).isEmpty())) {
					exitLoop = true;

				} else if (OPERATOR_MULTIPLY.equals(String.valueOf(sumInArray[j]))
						&& !String.valueOf(sumInArray[j - 1]).isEmpty()
						&& !String.valueOf(sumInArray[j + 1]).isEmpty()) {

//					System.out.println("multiple:::" + sumInArray[j]);

					total = Double.valueOf(sumInArray[j - 1]) * Double.valueOf(sumInArray[j + 1]);

					sumInArray[j - 1] = String.valueOf(total);
					sumInArray[j] = "";
					sumInArray[j + 1] = "";

					break;

				} else if (OPERATOR_DIVIDE.equals(String.valueOf(sumInArray[j]))
						&& !String.valueOf(sumInArray[j - 1]).isEmpty()
						&& !String.valueOf(sumInArray[j + 1]).isEmpty()) {

//					System.out.println("divide:::" + sumInArray[j]);

					total = Double.valueOf(sumInArray[j - 1]) / Double.valueOf(sumInArray[j + 1]);

					sumInArray[j - 1] = String.valueOf(total);
					sumInArray[j] = "";
					sumInArray[j + 1] = "";

					break;
				}

			}

//			System.out.println("full list: " + Arrays.toString(stringRange));

			if ((0 == start && 0 == end)) {
				stringRange = Arrays.copyOfRange(sumInArray, 0, sumInArray.length);
				start = 0;
				end = sumInArray.length;
			} else {
				stringRange = Arrays.copyOfRange(sumInArray, start, end);
			}
		}

		total = 0d; // reset the total since it used again for calculation

		for (

				int i = 0; i < sumInArray.length; i++) {

//			System.out.println("fullList: " + Arrays.toString(sumInArray));

			if (!sumInArray[i].isEmpty() && ((start <= i && i <= end) || (0 == start && 0 == end))) {

				if (!BRACKET_START.equals(String.valueOf(sumInArray[i]))
						&& !BRACKET_END.equals(String.valueOf(sumInArray[i]))
						&& !String.valueOf(sumInArray[i]).isEmpty()) {

					if (0d == total) {
						total = Double.valueOf(sumInArray[i]);
					} else if (OPERATOR_PLUS.equals(String.valueOf(sumInArray[i]))) {
						total = total + Double.valueOf(sumInArray[i + 1]);
					} else if (OPERATOR_MULTIPLY.equals(String.valueOf(sumInArray[i]))) {
						total = total * Double.valueOf(sumInArray[i + 1]);
					} else if (OPERATOR_DIVIDE.equals(String.valueOf(sumInArray[i]))) {
						total = total / Double.valueOf(sumInArray[i + 1]);
					} else if (OPERATOR_MINUS.equals(String.valueOf(sumInArray[i]))) {
						total = total - Double.valueOf(sumInArray[i + 1]);
					}

					sumInArray[start] = String.valueOf(total);

				}

				sumInArray[i] = "";

			}
		}
		return total;
	}

}
