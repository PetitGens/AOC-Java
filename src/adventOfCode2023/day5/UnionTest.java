package adventOfCode2023.day5;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnionTest {
    @Test
    public void addTest(){
        Union union = new Union();
        union.add(new Interval(5, 10));
        union.add(new Interval(8, 12));

        assertTrue(union.contains(5));
        assertTrue(union.contains(12));
        assertTrue(union.contains(10));

        assertEquals(1, union.getIntervals().size());

        Union minus = union.minus(Union.fromInterval(new Interval(9, 10)));

        assertFalse(minus.contains(9));
        assertFalse(minus.contains(10));
        assertTrue(minus.contains(11));
        assertTrue(minus.contains(8));
    }
}