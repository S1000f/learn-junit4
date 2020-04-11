package my.junit.ch03;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class HamcrestTest {

    private List<String> list;

    @Before
    public void setList() {
        list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
    }

    @Test
    public void testWithoutHamcrest() {

        assertTrue(list.contains("one") || list.contains("two") || list.contains("three"));
    }

    @Test
    public void testWithHamcrest() {

        assertThat(list, hasItem(anyOf(equalTo("one"), equalTo("two"), equalTo("Three"))));
    }
}
