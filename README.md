# javaremotetest

I have completed the java remote coding exercise around 8hours in totals.

Based on the problem given, I had come out with program which will cater scenarios below:
1. Normal equation without brackets
2. Equation with brackets
3. Equation with nested brackets.
4. Priority calculation of division and multiplication.

The assumption would be the String parameter are consisting of numbers, operators and brackets separated by spaces.

Summary of my program:
1. Split the string parameter by spaces into an arrays.
2. Find the brackets pairs and store into the maps.
3. Sort the maps keys to process the brackets pairs correctly.
4. Calculate the equation.
5. Print out the result.

I also added the error handling exception by throwing the error to the caller.

I have added the comments each of method and certain line of code in order to make it easier to understand. I am also using constant instead of hard coded it.

Added extra scenario - Question#11 and Question#12;

To understand more on the flow of the calculation, you may uncomment the System.out.println that available in the code.



