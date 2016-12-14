import java.util.Arrays;

/**
 * This class implements six different comparison sorts (and an optional
 * seventh sort for extra credit):
 * <ul>
 * <li>selection sort</li>
 * <li>insertion sort</li>
 * <li>merge sort</li>
 * <li>quick sort</li>
 * <li>heap sort</li>
 * <li>selection2 sort</li>
 * <li>(extra credit) insertion2 sort</li>
 * </ul>
 * It also has a method that runs all the sorts on the same input array and
 * prints out statistics.
 */

public class ComparisonSort {

	private static int numDataSwaps = 0;
	
    /**
     * Sorts the given array using the selection sort algorithm. You may use
     * either the algorithm discussed in the on-line reading or the algorithm
     * discussed in lecture (which does fewer data moves than the one from the
     * on-line reading). Note: after this method finishes the array is in sorted
     * order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void selectionSort(E[] A) {
        for (int i = 0; i < A.length - 1; i++) {
        	int minPos = i;
        	for (int j = i + 1; j < A.length; j++) {
        		if (A[j].compareTo(A[minPos]) < 0) { minPos = j; }
        	}
        	if (minPos > i) { swapElements(A, i, minPos); }
        }
    }

    /**
     * Sorts the given array using the insertion sort algorithm. Note: after
     * this method finishes the array is in sorted order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void insertionSort(E[] A) {
        for (int i = 1; i < A.length; i++) {
        	E cache = cacheElement(A, i);
        	int swapPos = i;
        	for (int j = i; j > 0; j--) {
        		if (A[j - 1].compareTo(cache) > 0) { 
        			moveElement(A, j - 1, j);
        			swapPos--;
        		}
        		else { break; }
        	}
        	unpackElement(A, swapPos, cache);
        }
    }

    /**
     * Sorts the given array using the merge sort algorithm. Note: after this
     * method finishes the array is in sorted order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void mergeSort(E[] A) {
        // TODO: implement this sorting algorithm
    }

    /**
     * Sorts the given array using the quick sort algorithm, using the median of
     * the first, last, and middle values in each segment of the array as the
     * pivot value. Note: after this method finishes the array is in sorted
     * order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A   the array to sort
     */
    public static <E extends Comparable<E>> void quickSort(E[] A) {
        // TODO: implement this sorting algorithm
    }


    /**
     * Sorts the given array using the heap sort algorithm outlined below. Note:
     * after this method finishes the array is in sorted order.
     * <p>
     * The heap sort algorithm is:
     * </p>
     * 
     * <pre>
     * for each i from 1 to the end of the array
     *     insert A[i] into the heap (contained in A[0]...A[i-1])
     *     
     * for each i from the end of the array up to 1
     *     remove the max element from the heap and put it in A[i]
     * </pre>
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void heapSort(E[] A) {
        // TODO: implement this sorting algorithm
    }

    /**
     * Sorts the given array using the selection2 sort algorithm outlined
     * below. Note: after this method finishes the array is in sorted order.
     * <p>
     * The selection2 sort is a bi-directional selection sort that sorts
     * the array from the two ends towards the center. The selection2 sort
     * algorithm is:
     * </p>
     * 
     * <pre>
     * begin = 0, end = A.length-1
     * 
     * // At the beginning of every iteration of this loop, we know that the 
     * // elements in A are in their final sorted positions from A[0] to A[begin-1]
     * // and from A[end+1] to the end of A.  That means that A[begin] to A[end] are
     * // still to be sorted.
     * do
     *     use the MinMax algorithm (described below) to find the minimum and maximum 
     *     values between A[begin] and A[end]
     *     
     *     swap the maximum value and A[end]
     *     swap the minimum value and A[begin]
     *     
     *     ++begin, --end
     * until the middle of the array is reached
     * </pre>
     * <p>
     * The MinMax algorithm allows you to find the minimum and maximum of N
     * elements in 3N/2 comparisons (instead of 2N comparisons). The way to do
     * this is to keep the current min and max; then
     * </p>
     * <ul>
     * <li>take two more elements and compare them against each other</li>
     * <li>compare the current max and the larger of the two elements</li>
     * <li>compare the current min and the smaller of the two elements</li>
     * </ul>
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void selection2Sort(E[] A) {
        // TODO: implement this sorting algorithm
    }

    
    /**
     * <b>Extra Credit:</b> Sorts the given array using the insertion2 sort 
     * algorithm outlined below.  Note: after this method finishes the array 
     * is in sorted order.
     * <p>
     * The insertion2 sort is a bi-directional insertion sort that sorts the 
     * array from the center out towards the ends.  The insertion2 sort 
     * algorithm is:
     * </p>
     * <pre>
     * precondition: A has an even length
     * left = element immediately to the left of the center of A
     * right = element immediately to the right of the center of A
     * if A[left] > A[right]
     *     swap A[left] and A[right]
     * left--, right++ 
     *  
     * // At the beginning of every iteration of this loop, we know that the elements
     * // in A from A[left+1] to A[right-1] are in relative sorted order.
     * do
     *     if (A[left] > A[right])
     *         swap A[left] and A[right]
     *  
     *     starting with with A[right] and moving to the left, use insertion sort 
     *     algorithm to insert the element at A[right] into the correct location 
     *     between A[left+1] and A[right-1]
     *     
     *     starting with A[left] and moving to the right, use the insertion sort 
     *     algorithm to insert the element at A[left] into the correct location 
     *     between A[left+1] and A[right-1]
     *  
     *     left--, right++
     * until left has gone off the left edge of A and right has gone off the right 
     *       edge of A
     * </pre>
     * <p>
     * This sorting algorithm described above only works on arrays of even 
     * length.  If the array passed in as a parameter is not even, the method 
     * throws an IllegalArgumentException
     * </p>
     *
     * @param  A the array to sort
     * @throws IllegalArgumentException if the length or A is not even
     */    
    public static <E extends Comparable<E>> void insertion2Sort(E[] A) { 
        // TODO: implement this sorting algorithm 
    }

    /**
     * Internal helper for printing rows of the output table.
     * 
     * @param sort          name of the sorting algorithm
     * @param compares      number of comparisons performed during sort
     * @param moves         number of data moves performed during sort
     * @param milliseconds  time taken to sort, in milliseconds
     */
    private static void printStatistics(String sort, int compares, int moves,
                                        long milliseconds) {
        System.out.format("%-23s%,15d%,15d%,15d\n", sort, compares, moves, 
                          milliseconds);
    }

    /**
     * Sorts the given array using the six (seven with the extra credit)
     * different sorting algorithms and prints out statistics. The sorts 
     * performed are:
     * <ul>
     * <li>selection sort</li>
     * <li>insertion sort</li>
     * <li>merge sort</li>
     * <li>quick sort</li>
     * <li>heap sort</li>
     * <li>selection2 sort</li>
     * <li>(extra credit) insertion2 sort</li>
     * </ul>
     * <p>
     * The statistics displayed for each sort are: number of comparisons, 
     * number of data moves, and time (in milliseconds).
     * </p>
     * <p>
     * Note: each sort is given the same array (i.e., in the original order) 
     * and the input array A is not changed by this method.
     * </p>
     * 
     * @param A  the array to sort
     */
    static public void runAllSorts(SortObject[] A) {
        System.out.format("%-23s%15s%15s%15s\n", "algorithm", "data compares", 
                          "data moves", "milliseconds");
        System.out.format("%-23s%15s%15s%15s\n", "---------", "-------------", 
                          "----------", "------------");

        SortObject[] clone = null;
        long startTime = 0;
        
        // Selection Sort
        clone = copyArray(A);
        startTime = preSortSteps();
        selectionSort(clone);
        postSortSteps("Selection", startTime);
    	validateSort(clone);
        
        // Insertion Sort
        clone = copyArray(A);
        startTime = preSortSteps();
        insertionSort(clone);
        postSortSteps("Insertion", startTime);
    	validateSort(clone);
    }
    
    private static long preSortSteps() {
    	clearSwapCounter();
    	SortObject.resetCompares();
    	return System.currentTimeMillis();
    }
    
    private static void postSortSteps(String sortType, Long startTime) {
    	long duration = System.currentTimeMillis() - startTime;
    	printStatistics(sortType, SortObject.getCompares(), numDataSwaps,
    			duration);
    }
    
    //TODO
    private static void validateSort(SortObject[] A) {
    	boolean badSort = false;
    	for (int i = 0; i < A.length - 1; i++) {
    		//System.out.print(A[i] + ",");
    		if (A[i + 1].compareTo(A[i]) < 0) { badSort = true; }
    	}
    	//System.out.println("");
    	if (badSort) { System.out.println("Bad Sort"); }
    }
    
    private static <E extends Comparable<E>> void swapElements(E[] A, int pos1,
    		int pos2) {
    	E cache = cacheElement(A, pos1);
    	moveElement(A, pos2, pos1);
    	unpackElement(A, pos2, cache);
    }
    
    private static <E extends Comparable<E>> void moveElement(E[] A, int pos1,
    		int pos2) {
    	incrementSwapCounter();
    	A[pos2] = A[pos1];
    }
    
    private static <E extends Comparable<E>> E cacheElement(E[] A, int pos) {
    	incrementSwapCounter();
    	return A[pos];
    }
    
    private static <E extends Comparable<E>> void unpackElement(E[] A, int pos,
    		E cache) {
    	incrementSwapCounter();
    	A[pos] = cache;
    }
    
    private static void clearSwapCounter() { numDataSwaps = 0; }
    
    private static void incrementSwapCounter() { numDataSwaps += 1; }
    
    private static SortObject[] copyArray(SortObject[] A) {
    	SortObject[] returnArr = new SortObject[A.length];
    	for (int i = 0; i < A.length; i++) {
    		returnArr[i] = new SortObject(A[i].getData());
    	}
    	return returnArr;
    }
}
