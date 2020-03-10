import java.awt.*;
import javax.swing.*;
import java.math.*;

import java.awt.event.*;
import java.util.Random;
import java.lang.*;

public class SortGUI extends JPanel {
    // Getting the Random Variables that will be used for the program
    //Using the range of 100 to 300 to avoid an overflow
    final static int min = 100;
    final static int max = 300;
    private Timer T;

    private final int[] heapArray, quickArray;
    final static double ran = min + (double) Math.random() * (max);


    // This is to know the variables we are going to display
    private static final int AR_SIZE = (int) ran, BAR_WIDTH = 4, SPACE_APART = 3, MAX = 100, MIN = 1,
            MS_Y_START = 100, IS_Y_START = 300;
    private final JButton changeButton;
    // This helps in keeping track of the counter involved
    private int quickTracker = 0, heapTracker = 0;
//Placing the quick array and the heap Array in the sort GUI alogorithm
    public SortGUI() {
        heapArray = new int[AR_SIZE];
        quickArray = new int[AR_SIZE];

        // Generating Random numbers and assigning to arrays
        final Random rn = new Random();
        int randomNumber;
        for (int i = 0; i < AR_SIZE; i++) {
            randomNumber = MIN + rn.nextInt(MAX - MIN + 1);
            heapArray[i] = randomNumber;
            quickArray[i] = randomNumber;
        }

        setBackground(Color.black);
        setPreferredSize(new Dimension(800, 600));

        changeButton = new JButton("Begin the sorting process");
        add(changeButton);
        changeButton.addActionListener(new ButtonHandler());

    }

    public void paintComponent(final Graphics pen) {
        super.paintComponent(pen);

        int xPos = 20;

        pen.setColor(Color.yellow);
        for (int index = 0; index < AR_SIZE; index++) {
            pen.fillRect(xPos, Math.abs(MS_Y_START - quickArray[index]), BAR_WIDTH, quickArray[index]);
            xPos = xPos + BAR_WIDTH + SPACE_APART;
        }

        xPos = 20;

        pen.setColor(Color.blue);
        pen.drawString("Quick Sort", xPos, MS_Y_START + 20);

        pen.setColor(Color.yellow);
        for (int index = 0; index < AR_SIZE; index++) {
            pen.fillRect(xPos, IS_Y_START - heapArray[index], BAR_WIDTH, heapArray[index]);
            xPos = xPos + BAR_WIDTH + SPACE_APART; // adds width and spaces so
        }

        xPos = 20; // resets x position

        pen.setColor(Color.orange);
        pen.drawString("Heap Sort", xPos, IS_Y_START + 20);
        pen.drawString("Length of array: " + AR_SIZE, xPos, IS_Y_START + 40);

    }
    // Java program for implementation of QuickSort
 void QuickSort(){

	/* This function takes last element as pivot,
	places the pivot element at its correct
	position in sorted array, and places all
	smaller (smaller than pivot) to left of
	pivot and all greater elements to right
	of pivot */
	void partition(int arr[], int low, int high)
	{
		int pivot = arr[high];
		int i = (low-1); // index of smaller element
		for (int j=low; j<high; j++)
		{
			// If current element is smaller than the pivot
			if (arr[j] < pivot)
			{
				i++;

				// swap arr[i] and arr[j]
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		// swap arr[i+1] and arr[high] (or pivot)
		int temp = arr[i+1];
		arr[i+1] = arr[high];
		arr[high] = temp;

		return i+1;
	}


	/* The main function that implements QuickSort()
	arr[] --> Array to be sorted,
	low --> Starting index,
	high --> Ending index */
  	void sort(int arr[], int low, int high)
	{
		if (low < high)
		{
			/* pi is partitioning index, arr[pi] is
			now at right place */
			int pi = partition(arr, low, high);

			// Recursively sort elements before
			// partition and after partition
			sort(arr, low, pi-1);
			sort(arr, pi+1, high);
		}
	}
}

        //Heap Sort Algorithm
        private void HeapSort ()
      {
           void sort(int arr[])
          {
              int n = arr.length;

              // Build heap (rearrange array)
              for (int i = n / 2 - 1; i >= 0; i--)
                  heapify(arr, n, i);

              // One by one extract an element from heap
              for (int i=n-1; i>=0; i--)
              {
                  // Move current root to end
                  int temp = arr[0];
                  arr[0] = arr[i];
                  arr[i] = temp;

                  // call max heapify on the reduced heap
                  heapify(arr, i, 0);
              }
          }
          // To heapify a subtree rooted with node i which is
              // an index in arr[]. n is size of heap
              void heapify(int arr[], int n, int i)
              {
                  int largest = i; // Initialize largest as root
                  int l = 2*i + 1; // left = 2*i + 1
                  int r = 2*i + 2; // right = 2*i + 2

                  // If left child is larger than root
                  if (l < n && arr[l] > arr[largest])
                      largest = l;

                  // If right child is larger than largest so far
                  if (r < n && arr[r] > arr[largest])
                      largest = r;

                  // If largest is not root
                  if (largest != i)
                  {
                      int swap = arr[i];
                      arr[i] = arr[largest];
                      arr[largest] = swap;

                      // Recursively heapify the affected sub-tree
                      heapify(arr, n, largest);
                  }
              }
            }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(final ActionEvent e) {
            T = new Timer(250, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double quickTime = (AR_SIZE * Math.log(AR_SIZE)) / 1000;
                    double heapTime = (AR_SIZE * AR_SIZE) / 1000;
                    // calling quick and heap
                    QuickSort();
                    HeapSort();
                    // incrementing their counters to keep track of position in simulation
                    quickTracker++;
                    heapTracker++;

                    SortGUI.this.paintComponent(SortGUI.this.getGraphics());
                    // Check if end of array is reached
                    if ((quickTracker >= AR_SIZE) && (heapTracker >= AR_SIZE)) {
                        JOptionPane.showMessageDialog(null,
                                String.format(
                                        "Your sorting has now been completed! \n Quick sort runtime: %.2f s \n IHeap Sort Runtime: %.2f s",
                                        quickTime, heapTime));
                        T.stop();
                    }
                }
            });

            T.start();
        }
    }
// Main Method to ensure all the code is functioning as it it supposed to
    public static void main(final String[] args) {
        final SortGUI panel = new SortGUI();
        final JFrame frame = new JFrame("QuickSort vs HeapSort");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(600, 300);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
