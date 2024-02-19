/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals( 5 );
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> { setB.add(10); });

        setD.add(10);
        assertThrows(IllegalArgumentException.class, () -> { setD.add(10); });

        assertThrows(IllegalArgumentException.class, () -> { setD.add(-10); });
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }
    
    @Test
    public void testIntersects(){
        assertTrue( setB.intersects(setC) );
        assertFalse( setC.intersects(setB));
        assertTrue( setA.intersects(setD) );
    }

    @Test
    public void testEquals(){
        assertTrue( setB.equals(setB) );
        assertFalse( setB.equals(null) );
        assertFalse( setB.equals("") );
        setD.add(50);
        setD.add(60);
        assertTrue( setC.equals(setD) );
        assertTrue( setD.equals(setC) );

    }

}
