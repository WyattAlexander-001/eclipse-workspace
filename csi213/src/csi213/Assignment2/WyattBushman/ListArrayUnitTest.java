package csi213.Assignment2.WyattBushman;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class ListArrayUnitTest {

	@Before
	public void setUp() throws Exception {
	}

    @Test //Using Java's built in version
    public void testArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("foo");
        arrayList.add("bar");
        //assertEquals(2, arrayList.size());
        assertEquals("foo", arrayList.get(0));
        assertEquals("bar", arrayList.get(1));
    }
    
	@Test
	public void testGet() {
	    ListArray<String> list = new ListArray<>();
	    list.add("foo");
	    list.add("bar");
	    //assertEquals(2, list.size());
	    //assertEquals("foo", list.get(0));
        //assertEquals("bar", list.get(1));
	}
	
	@Test
	public void testSize() {
	    ListArray<Integer> list = new ListArray<>();
	    list.add(1);
	    list.add(2);
	    list.add(3);
	    assertEquals(3, list.size());
	    //assertEquals(Integer.valueOf(1), list.get(0));
	    //assertEquals(Integer.valueOf(2), list.get(1));
	    //assertEquals(Integer.valueOf(3), list.get(2));
	}
	



}
