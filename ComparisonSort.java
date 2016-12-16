///////////////////////////////////////////////////////////////////////////////
// Title:            Prog5-SortComparison
// Files:            ComparisonSort.java
// Semester:         Fall 2016
//
// Author:           Alex McClain, gamcclain@wisc.edu
// CS Login:         gamcclain@wisc.edu
// Lecturer's Name:  Charles Fischer
///////////////////////////////////////////////////////////////////////////////

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
    	// Quit early if the minimum size is reached
    	if (A.length == 1) { return; }
    	
    	// Create the left sub-array and populate it
        @SuppressWarnings("unchecked")
		E[] leftArr = (E[])(new Comparable[A.length / 2]);
        for (int i = 0; i < leftArr.length; i++) {
        	leftArr[i] = cacheElement(A, i);
        }
        // Create the right sub-array and populate it
        @SuppressWarnings("unchecked")
		E[] rightArr = (E[])(new Comparable[(A.length + 1) / 2]);
        for (int i = 0; i < rightArr.length; i++) {
        	rightArr[i]= cacheElement(A, i + leftArr.length);
        }
        
        mergeSort(leftArr);
        mergeSort(rightArr);
        
        int leftPos = 0;
        int rightPos = 0;
        for (int i = 0; i < A.length; i++) {
        	if (rightPos >= rightArr.length) {
        		unpackElement(A, i, leftArr[leftPos]);
        		leftPos++;
        	}
        	else if (leftPos >= leftArr.length) {
        		unpackElement(A, i, rightArr[rightPos]);
        		rightPos++;
        	}
        	else if (leftArr[leftPos].compareTo(rightArr[rightPos]) < 0) {
        		unpackElement(A, i, leftArr[leftPos]);
        		leftPos++;
        	}
        	else {
        		unpackElement(A, i, rightArr[rightPos]);
        		rightPos++;
        	}
        }
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
    	quickSortRecursive(A, 0, A.length - 1);
    }
    
    
    /**
     * Recursive call for performing quick sort on an array. Reorders the
     * elements within the array itself  confined to the range of positions
     * passed in.
     * @param A The array of elements to be sorted.
     * @param leftPos The leftmost position of the range to sort.
     * @param rightPos The rightmost position of the range to sort.
     */
    private static <E extends Comparable<E>> void quickSortRecursive (E[] A,
    		int leftPos, int rightPos) {
    	int length = rightPos - leftPos + 1;
        if (length <= 1) { return; }
        if (length == 2) {
        	if (A[leftPos].compareTo(A[rightPos]) > 0) {
        		swapElements(A, leftPos, rightPos);
        	}
        	return;
        }
        E pivot = medianOfThree(A, leftPos, rightPos);
        if (pivot == null) { return; }
        // Median of Three will sort an array of length 3
        if (length == 3) { return; }
        
        int leftInc = leftPos + 1;
        int rightInc = rightPos - 2;
        while (leftInc <= rightInc) {
        	while (A[leftInc].compareTo(pivot) < 0) { leftInc++; }
        	while (A[rightInc].compareTo(pivot) >= 0) { rightInc--; }
        	if (leftInc < rightInc) { swapElements(A, leftInc, rightInc); }
        }
        quickSortRecursive(A, leftPos, rightInc);
        quickSortRecursive(A, rightInc + 1, rightPos);
    }
    
    /**
     * Helper method for the quick sort that will determine the median of the
     * first, last, and middle elements in the array. Additionally, it will
     * rearrange those three elements so that the minimum is at the left, the
     * maximum is at the right, and the median is just to the left of the max.
     * @param A The array of elements to be sorted.
     * @param leftPos The leftmost position of the range to sort.
     * @param rightPos The rightmost position of the range to sort.
     * @return The median value to be used as a pivot in the quick sort.
     */
    private static <E extends Comparable<E>> E medianOfThree(E[] A,
    		int leftPos, int rightPos) {
    	int length = rightPos - leftPos + 1;
    	if (length < 3) { return null; }
    	E max, median, min;
    	int midPos = (leftPos + rightPos) / 2;
    	E secondToLast = null;
    	if (length > 3) { secondToLast = cacheElement(A, rightPos - 1); }
    	if (A[leftPos].compareTo(A[rightPos]) > 0) {
    		if (A[leftPos].compareTo(A[midPos]) > 0) {
    			max = cacheElement(A, leftPos);
    			if (A[midPos].compareTo(A[rightPos]) > 0) {
    				median = cacheElement(A, midPos);
    				min = cacheElement(A, rightPos);
    			}
    			else {
    				median = cacheElement(A, rightPos);
    				min = cacheElement(A, midPos);
    			}
    		}
    		else {
    			max = cacheElement(A, midPos);
				median = cacheElement(A, leftPos);
				min = cacheElement(A, rightPos);
    		}
    	}
    	else {
    		if (A[leftPos].compareTo(A[midPos]) < 0) {
    			min = cacheElement(A, leftPos);
    			if (A[midPos].compareTo(A[rightPos]) < 0) {
    				median = cacheElement(A, midPos);
    				max = cacheElement(A, rightPos);
    			}
    			else {
    				median = cacheElement(A, rightPos);
    				max = cacheElement(A, midPos);
    			}
    		}
    		else {
    			min = cacheElement(A, midPos);
				median = cacheElement(A, leftPos);
				max = cacheElement(A, rightPos);
    		}
    	}
    	unpackElement(A, leftPos, min);
    	if (length > 3) { unpackElement(A, midPos, secondToLast); }
    	unpackElement(A, rightPos - 1, median);
    	unpackElement(A, rightPos, max);
    	return median;
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
        @SuppressWarnings("unchecked")
		E[] heapArr = (E[])(new Comparable[A.length + 1]);
        for (int i = 0; i < A.length; i++) { heapInsert(heapArr, A[i], i + 1); }
        for (int i = A.length; i > 0; i--) {
        	unpackElement(A, i - 1, heapRemove(heapArr, i));
        }
    }
    
    /**
     * Inserts a new element into the heap array at the end, then reorders the
     * array in order to preserve the heap properties.
     * @param heap The heap array into which the insertion is made.
     * @param item The item to insert into the heap.
     * @param insertPos The position where the element should be inserted. This
     * should be the position to the right of the last element. 
     */
    private static <E extends Comparable<E>> void heapInsert(E[] heap, E item,
    		int insertPos) {
        heap[insertPos] = item;
        incrementSwapCounter();
        // Fix the heap order
    	boolean done = false;
        while (!done && insertPos > 1) {
        	int parentPos = insertPos / 2;
        	if (heap[insertPos].compareTo(heap[parentPos]) > 0) {
        		swapElements(heap, insertPos, parentPos);
        		insertPos = parentPos;
        	}
        	else { done = true; }
        }
    }
    
    /**
     * Removes and returns the maximum element from the heap, then reorders the
     * array in order to preserve the heap properties.
     * @param heap The heap array from which to get the max element.
     * @param lastPos The position of the last element in the heap.
     * @return The maximum element stored at the root of the heap.
     */
    private static <E extends Comparable<E>> E heapRemove(E[] heap,
    		int lastPos) {
    	E returnValue = cacheElement(heap, 1);
    	unpackElement(heap, 1, heap[lastPos]);
    	boolean done = false;
    	// Fix the heap order
    	int currPos = 1;
    	while (!done) {
    		int leftPos = currPos * 2;
    		if (leftPos >= lastPos) { done = true; }
    		else {
    			int rightPos = currPos * 2 + 1;
    			if (rightPos >= lastPos) {
    				if (heap[currPos].compareTo(heap[leftPos]) < 0) {
    					swapElements(heap, currPos, leftPos);
    					currPos = leftPos;
    				}
    				else { done = true; }
    			}
    			else {
    				if (heap[currPos].compareTo(heap[leftPos]) < 0) {
						if (heap[leftPos].compareTo(heap[rightPos]) < 0) {
							swapElements(heap, currPos, rightPos);
							currPos = rightPos;
						}
						else {
							swapElements(heap, currPos, leftPos);
							currPos = leftPos;
						}
    				}
    				else if (heap[currPos].compareTo(heap[rightPos]) < 0) {
    					swapElements(heap, currPos, rightPos);
    					currPos = rightPos;
    				}
    				else { done = true; }
    			}
    		}
    	}
    	return returnValue;
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
        for (int i = 0; i <= (A.length - 2) / 2; i++) {
        	int leftFill = i;
        	int rightFill = A.length - 1 - i;
        	int minPos = leftFill;
        	int maxPos = rightFill;
        	for (int j = i; j <= (A.length - 1) / 2; j++) {
        		int leftTest = j;
        		int rightTest = A.length - 1 - j;
        		if (A[leftTest].compareTo(A[rightTest]) < 0) {
        			if (A[leftTest].compareTo(A[minPos]) < 0) {
        				minPos = leftTest;
        			}
        			if (A[rightTest].compareTo(A[maxPos]) > 0) {
        				maxPos = rightTest;
        			}
        		}
        		else {
        			if (A[rightTest].compareTo(A[minPos]) < 0) {
        				minPos = rightTest;
        			}
        			if (A[leftTest].compareTo(A[maxPos]) > 0) {
        				maxPos = leftTest;
        			}
        		}
        	}
        	if (maxPos == leftFill) {
        		if (minPos == rightFill) {
        			swapElements(A, minPos, maxPos);
        		}
        		else {
        			swapElements(A, maxPos, rightFill);
        			swapElements(A, minPos, leftFill);
        		}
        	}
        	else {
        		swapElements(A, minPos, leftFill);
        		swapElements(A, maxPos, rightFill);
        	}
        	
        }
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
        for (int i = A.length / 2 - 1; i >= 0; i--) {
        	int leftTest = i;
        	int rightTest = A.length - 1 - i;
        	if (A[leftTest].compareTo(A[rightTest]) > 0) {
        		swapElements(A, leftTest, rightTest);
        	}
        	E rightVal = cacheElement(A, rightTest);
        	for (int j = rightTest - 1; j >= leftTest; j--) {
        		if (A[j].compareTo(rightVal) > 0) {
        			moveElement(A, j, j + 1);
        		}
        		else {
        			unpackElement(A, j + 1, rightVal);
        			break;
        		}
        	}
        	E leftVal = cacheElement(A, leftTest);
        	for (int j = leftTest + 1; j <= rightTest; j++) {
        		if (A[j].compareTo(leftVal) < 0) {
        			moveElement(A, j, j - 1);
        		}
        		else {
        			unpackElement(A, j - 1, leftVal);
        			break;
        		}
        	}
        }
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
        
        // Merge Sort
        clone = copyArray(A);
        startTime = preSortSteps();
        mergeSort(clone);
        postSortSteps("Merge", startTime);
    	validateSort(clone);
        
        // Quick Sort
        clone = copyArray(A);
        startTime = preSortSteps();
        quickSort(clone);
        postSortSteps("Quick", startTime);
    	validateSort(clone);
        
        // Heap Sort
        clone = copyArray(A);
        startTime = preSortSteps();
        heapSort(clone);
        postSortSteps("Heap", startTime);
    	validateSort(clone);
        
        // Selection 2 Sort
        clone = copyArray(A);
        startTime = preSortSteps();
        selection2Sort(clone);
        postSortSteps("Selection2", startTime);
    	validateSort(clone);
        
        // Insertion 2 Sort
        clone = copyArray(A);
        startTime = preSortSteps();
        insertion2Sort(clone);
        postSortSteps("Insertion2", startTime);
    	validateSort(clone);
    }
    
    /**
     * Steps to take prior to running a sort in order to gather accurate
     * statistics. This includes clearing the data swap and compare counter and
     * retrieving the current system time.
     * @return The current system time in miliseconds.
     */
    private static long preSortSteps() {
    	clearSwapCounter();
    	SortObject.resetCompares();
    	return System.currentTimeMillis();
    }
    
    /**
     * Steps to take after the sort has finished running. This will calculate
     * the time duration of the sort and then print all relevant statistics.
     * @param sortType String value describing the type of sort.
     * @param startTime The system time, in miliseconds, when the sort started.
     */
    private static void postSortSteps(String sortType, Long startTime) {
    	long duration = System.currentTimeMillis() - startTime;
    	printStatistics(sortType, SortObject.getCompares(), numDataSwaps,
    			duration);
    }
    
    /**
     * Validates that the sort correctly reordered the array so the elements are
     * in order. Does this by simpily traversing the array and ensuring no two
     * elements are out of order.
     * @param A The array of elements to validate.
     */
    private static void validateSort(SortObject[] A) {
    	boolean badSort = false;
    	for (int i = 0; i < A.length - 1; i++) {
    		if (A[i + 1].compareTo(A[i]) < 0) {
    			badSort = true;
    		}
    	}
    	if (badSort) { System.out.println("!!!Bad Sort!!!"); }
    }
    
    /**
     * Swaps two elements in the array.
     * @param A The array containing the elements to be swapped.
     * @param pos1 Position of the first element to swap.
     * @param pos2 Position of the second element to swap.
     */
    private static <E extends Comparable<E>> void swapElements(E[] A, int pos1,
    		int pos2) {
    	E cache = cacheElement(A, pos1);
    	moveElement(A, pos2, pos1);
    	unpackElement(A, pos2, cache);
    }
    
    /**
     * Moves an element from one position in the array to another. The value in
     * the destination position is overwritten.
     * @param A The array containing the element to be moved.
     * @param pos1 The source position of the element to move.
     * @param pos2 The destination position for the element to be moved.
     */
    private static <E extends Comparable<E>> void moveElement(E[] A, int pos1,
    		int pos2) {
    	incrementSwapCounter();
    	A[pos2] = A[pos1];
    }
    
    /**
     * Returns the value of an element in the array.
     * @param A The array containing the desired element.
     * @param pos The position of the element to be returned.
     * @return The element in the given position.
     */
    private static <E extends Comparable<E>> E cacheElement(E[] A, int pos) {
    	incrementSwapCounter();
    	return A[pos];
    }
    
    /**
     * Inserts the given element into the array. Any element already at the
     * given position is overwritten.
     * @param A The array into which the element should be inserted.
     * @param pos The position to insert the element.
     * @param cache The element to be inserted.
     */
    private static <E extends Comparable<E>> void unpackElement(E[] A, int pos,
    		E cache) {
    	incrementSwapCounter();
    	A[pos] = cache;
    }
    
    /**
     * Resets the data swap counter to 0.
     */
    private static void clearSwapCounter() { numDataSwaps = 0; }
    
    /**
     * Incrememnts the swap counter by 1.
     */
    private static void incrementSwapCounter() { numDataSwaps += 1; }
    
    /**
     * Performs a deep copy of the given array, but only for SortObjects. The
     * new array will have new SortObject objects with the same values.
     * @param A The array to copy.
     * @return A copy of the given array with entirely new Object references.
     */
    private static SortObject[] copyArray(SortObject[] A) {
    	SortObject[] returnArr = new SortObject[A.length];
    	for (int i = 0; i < A.length; i++) {
    		returnArr[i] = new SortObject(A[i].getData());
    	}
    	return returnArr;
    }
}
