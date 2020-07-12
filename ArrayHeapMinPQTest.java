package pq;

import org.junit.Test;


import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    /* Be sure to write randomized tests that can handle millions of items. To
     * test for runtime, compare the runtime of NaiveMinPQ vs ArrayHeapMinPQ on
     * a large input of millions of items. */

    @Test
    public void testAdd() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add("p 1", 1);
        test.add("p 4", 4);
        test.add("p 2", 2);
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.add("p 1", 1);
        test.add("p 4", 4);
        test.add("p 2", 2);


        assertTrue(test.contains("p 1"));
        assertTrue(test.contains("p 4"));
        assertFalse(test.contains("p 3"));
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.add("p 1", 1);
        test.add("p 4", 4);
        test.add("p 2", 2);

        assertEquals(test.getSmallest(), "p 1");
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.add("p 1", 1);
        test.add("p 4", 4);
        test.add("p 2", 2);

        assertEquals(test.removeSmallest(), "p 1");
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.add("p 1", 1);
        test.add("p 4", 4);
        test.add("p 2", 2);
        test.add("p 5", 5);
        test.add("p 6", 6);
        test.add("p 3", 3);

        test.changePriority("p 1", 5);
        assertEquals(test.getSmallest(), "p 2");
        test.removeSmallest();
        test.changePriority("p 4", 10);
        assertEquals(test.getSmallest(), "p 3");
    }

    // a001 : add in decreasing order, then get smallest
    @Test
    public void testAdd001() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add("p 5", 5);
        test.add("p 4", 4);
        test.add("p 2", 2);
        test.add("p 1", 1);

        assertEquals("p 1", test.getSmallest());
    }

    // a002: add in increasing order, change biggest to smallest.
    @Test
    public void testAdd002() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add("p 1", 1);
        test.add("p 2", 2);
        test.add("p 4", 4);
        test.add("p 5", 5);

        test.changePriority("p 5", 1);
        assertEquals(test.getSmallest(), "p 1");
    }

    // a003: Add in decreasing order, change biggest to smallest
    @Test
    public void testAdd003() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add("p 5", 5);
        test.add("p 4", 4);
        test.add("p 2", 2);
        test.add("p 1", 1);

        test.changePriority("p 5", 1);
        test.removeSmallest();
        assertEquals("p 5", test.getSmallest());
    }

    @Test
    public void testInsertAndRemove() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add("p 5", 5);
        test.add("p 4", 4);
        test.add("p 2", 2);
        test.add("p 1", 1);

        test.removeSmallest();
        test.removeSmallest();
        test.removeSmallest();
        test.removeSmallest();
    }

    // Add in increasing order, then removeSmallest twice. (0.0/0.45)
    @Test
    public void testAdd006() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        for (int i = 1; i < 100; i++) {
            test.add(i, i);
        }

        test.removeSmallest();
        test.removeSmallest();
        assertEquals(test.getSmallest(), "1");
    }

    // Add in decreasing order, then removeSmallest twice. (0.0/0.45)
    @Test
    public void testAdd007() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add("p 5", 5);
        test.add("p 4", 4);
        test.add("p 2", 2);
        test.add("p 1", 1);

        test.removeSmallest();
        test.removeSmallest();
        test.removeSmallest();
        test.removeSmallest();
        assertEquals(test.size(), 0);
    }
}
