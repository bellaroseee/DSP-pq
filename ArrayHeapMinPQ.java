package pq;

import java.util.*;

/**
 * developing ARRAY BASED implementation of ExtrinsicMinPQ interface
 * OPTIMIZE access to the MINIMUM priority item.
 * <p>
 * 1. access the min priority item
 * 2. remove min priority item
 * 3. add new items
 * <p>
 * This is a heap with starting index 1.
 */

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> items;
    //key: item / priority
    //value: index
    private Map<T, Double> priorityMap; //key: items, values: priority

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        priorityMap = new HashMap<>();
    }

    private int pNode(int index) {
        return index / 2;
    }

    private int lChild(int index) {
        return (2 * index);
    }

    private int rChild(int index) {
        return (2 * index) + 1;
    }

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        T tempA = items.get(a).item;
        double aPriority = items.get(a).priority;
        T tempB = items.get(b).item;
        double bPriority = items.get(b).priority;
        items.get(a).setItem(tempB);
        items.get(a).setPriority(bPriority);
        items.get(b).setItem(tempA);
        items.get(b).setPriority(aPriority);
    }

    /**
     * A helper method for swimming : swapping a node up until parent is smaller than itself
     * smaller = lower priority
     */
    private void swim(int index) {
        //means that a is of the lowest priority. no parent.
        if (index == 1) {
            return;
        }
        //swapping with parent
        int pIndex = pNode(index);
        PriorityNode a = items.get(index);
        PriorityNode p = items.get(pIndex); //parent node
        if (a.priority < p.priority) { //swim up
            swap(index, pIndex);
        }
        swim(pIndex);
    }

    /**
     * A helper method for sinking: swapping node down the tree until smaller than both children.
     * smaller = lower priority
     */
    private void sink(int index) {
        if (index == items.size() - 1) { //cannot sink the last item in the Heap
            return;
        }
        int lIndex = lChild(index); // 2 * index
        int rIndex = rChild(index); // 2* index + 1

        /* BASE CASE:
        no sink when it's the leaf node -> check the children.
        no sink when the children's priority are higher then current node's priority
         */
        if (lIndex >= items.size() && rIndex >= items.size()) {
            return;
        }
        if (rIndex >= items.size()) {
            return;
        }
        if (items.get(lIndex).priority > items.get(index).priority &&
                items.get(rIndex).priority > items.get(index).priority) {
            return;
        }
        /* RECURSIVE CASE:
        sink as long as the current node priority is lesser than the children's priority.
        swap with the child with lower priority
        don't swap if child has same priority with current node
         */
        else {
            if (items.get(lIndex).priority > items.get(rIndex).priority) {
                swap(rIndex, index);
                sink(rIndex);
            }
            if (items.get(lIndex).priority < items.get(rIndex).priority) {
                swap(lIndex, index);
                sink(lIndex);
            }
        }
    }


    /**
     * A helper method to find the element with desired priority
     * takes in the node
     * returns the index of the item, return 0 otherwise.

     private int postOrder (PriorityNode x, double priority) {
     if (x == null) {
     return 0;
     }
     if (priorityMap.containsValue(priority)) {
     return items.indexOf(x);
     }
     postOrder(x.left)
     }
     *./
     /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing). -> shouldn't be an issue because using arraylist?
     * O(log N) = 2-3 tree
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        PriorityNode n = new PriorityNode(item, priority); //create the node
        if (priorityMap.containsKey(item)) { //does not allow for duplicate item.
            throw new IllegalArgumentException("item is already present in PQ");
        }
        if (items.size() == 0) { //if the list is empty, always add null placer
            items.add(new PriorityNode(null, -1.0));
        }
        items.add(n); //add the item to the list
        priorityMap.put(item, priority); //add the item to the priorityMap
        swim(items.size() - 1); //swim the item to the correct position
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return priorityMap.containsKey(item);
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return items.get(1).item;
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     *
     * 3 steps:
     * 1. swap root with the last leaf
     * 2. remove the last leaf
     * 3. sink the new root to proper place
     *
     */
    @Override
    public T removeSmallest() {
        if (items.size() == 0) { //if list is empty, throw exception
            throw new NoSuchElementException("PQ is empty");
        }
        int lastIndex = items.size() - 1;
        PriorityNode last = items.get(lastIndex); //the node at the end of the list
        if (priorityMap.size() == 1) { //if there is only 1 item in the list
            priorityMap.remove(items.get(lastIndex).item); //remove the item from priorityMap
            items.remove(0); //remove the null placer item
            items.remove(0); //remove the actual item.
            return last.item; //return the item that's just removed.
        }
        swap(1, lastIndex); //swap the root with the last leaf
        priorityMap.remove(items.get(lastIndex).item); //remove the item from priorityMap
        items.remove(lastIndex); //remove the item from the list
        sink(1); //sink the new root to proper place
        return last.item; //return the item that's just removed.
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     *
     * find the item, then change the priority.
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!priorityMap.containsKey(item)) {
            throw new NoSuchElementException("item is not present in PQ");
        }
        int i = 0;
        for (i = 1; i < items.size(); i++) { //finding the item
            T itemCompare = items.get(i).item;
            if (itemCompare.equals(item)) {
                break;
            }
        }
        PriorityNode n = items.get(i);
        n.setPriority(priority);
        priorityMap.replace(item, priority);
        swim(i);
        sink(i);
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return priorityMap.size();
    }

    /**
     * PriorityNode class from NaiveMinPQ.
     */
    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setItem(T item) {
            this.item = item;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        //comparing 2 nodes based on priority
        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) { //if object is null, or object are not same class
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem()); //equals if both items are the same.
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
