package ch.akermann.connectfourchallenge.server.game;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MaxSizeHashMapTest {

    @Test
    public void removeEldestEntry() {
        Map<String, String> map = new MaxSizeHashMap<>(1);

        map.put("a", "value a");
        map.put("b", "value b");

        assertThat(map.size(), is(1));
        assertThat(map.get("b"), is("value b"));
    }
}
