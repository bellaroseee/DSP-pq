package pq;

import java.util.ArrayList;

public class PrintHeapDemo {

    /**
     * Prints out a vey basic drawing of the given array of Objects assuming it
     * is a heap starting at index 1. You're welcome to copy and paste code
     * from this method into your code with a citation.
     */
    public static void printSimpleHeapDrawing(ArrayList heap) {
        int depth = ((int) (Math.log(heap.size()) / Math.log(2)));
        int level = 0;
        int itemsUntilNext = (int) Math.pow(2, level);
        for (int j = 0; j < depth; j++) {
            System.out.print(" ");
        }

        for (int i = 1; i < heap.size(); i++) {
            System.out.printf("%d ", heap.get(i));
            if (i == itemsUntilNext) {
                System.out.println();
                level++;
                itemsUntilNext += Math.pow(2, level);
                depth--;
                for (int j = 0; j < depth; j++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }

    /**
     * Prints out a drawing of the given array of Objects assuming it is a heap
     * starting at index 1. You're welcome to copy and paste code from this
     * method into your code with a citation.
     */
    public static void printFancyHeapDrawing(Object[] items) {
        String drawing = fancyHeapDrawingHelper(items, 1, "");
        System.out.println(drawing);
    }

    /** Recursive helper method for toString. */
    private static String fancyHeapDrawingHelper(Object[] items, int index, String soFar) {
        if (index >= items.length || items[index] == null) {
            return "";
        } else {
            String toReturn = "";
            int rightIndex = 2 * index + 1;
            toReturn += fancyHeapDrawingHelper(items, rightIndex, "        " + soFar);
            if (rightIndex < items.length && items[rightIndex] != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + items[index] + "\n";
            int leftIndex = 2 * index;
            if (leftIndex < items.length && items[leftIndex] != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += fancyHeapDrawingHelper(items, leftIndex, "        " + soFar);
            return toReturn;
        }
    }

    public static void main(String[] args) {
        Integer[] example = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        ArrayList<Integer> ex = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            ex.add(i);
        }
        printSimpleHeapDrawing(ex);
        printFancyHeapDrawing(example);
    }
}
