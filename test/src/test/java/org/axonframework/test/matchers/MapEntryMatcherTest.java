package org.axonframework.test.matchers;

import org.hamcrest.StringDescription;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.Collections.*;
import static org.axonframework.test.matchers.EqualsMatcher.equalTo;
import static org.junit.Assert.*;

public class MapEntryMatcherTest {

    private static final Map<String, Object> EXPECTED = new HashMap<>();

    static {
        EXPECTED.put("a", new ValueItem("a"));
        EXPECTED.put("b", new ValueItem("b"));
        EXPECTED.put("c", new ValueItem("c"));
    }

    private final MapEntryMatcher matcher = new MapEntryMatcher(EXPECTED);

    @Test
    public void nullSafe() {
        assertFalse(matcher.matches(null));
    }

    @Test
    public void testExpectedEntriesNotPresent() {
        assertFalse(matcher.matches(singletonMap("a", new ValueItem("a"))));

        assertThat(matcher.getMissingEntries(), equalTo(newHashMap("b", new ValueItem("b"), "c", new ValueItem("c"))));
    }

    @Test
    public void testTooManyEntries() {
        assertFalse(matcher.matches(newHashMap("a", new ValueItem("a"), "b", new ValueItem("b"), "c", new ValueItem("c"),
                                               "d", new ValueItem("d"), "e", new ValueItem("e"))));

        assertThat(matcher.getAdditionalEntries(), equalTo(newHashMap("d", new ValueItem("d"), "e", new ValueItem("e"))));
    }

    @Test
    public void testIncorrectValue() {
        assertFalse(matcher.matches(newHashMap("a", new ValueItem("a"), "b", new ValueItem("b"), "c", new ValueItem("CCCC"))));

//        assertThat(matcher.getMissingEntries(), equalTo(newHashMap("c", new ValueItem("c"))));
        assertThat(matcher.getAdditionalEntries(), equalTo(newHashMap("c", new ValueItem("CCCC"))));

        assertFalse(matcher.matches(newHashMap("a", new ValueItem("a"), "b", new ValueItem("b"), "c", null)));

//        assertThat(matcher.getMissingEntries(), equalTo(newHashMap("c", new ValueItem("c"))));
        assertThat(matcher.getAdditionalEntries(), equalTo(newHashMap("c", null)));

    }

    @Test
    public void testIncorrectKey() {
        assertFalse(matcher.matches(newHashMap("a", new ValueItem("a"), "b", new ValueItem("b"), "CCCC", new ValueItem("c"))));

//        assertThat(matcher.getMissingEntries(), equalTo(newHashMap("c", new ValueItem("c"))));
        assertThat(matcher.getAdditionalEntries(), equalTo(newHashMap("CCCC", new ValueItem("c"))));

        assertFalse(matcher.matches(newHashMap("a", new ValueItem("a"), "b", new ValueItem("b"), null, new ValueItem("c"))));

//        assertThat(matcher.getMissingEntries(), equalTo(newHashMap("c", new ValueItem("c"))));
        assertThat(matcher.getAdditionalEntries(), equalTo(newHashMap(null, new ValueItem("c"))));

    }

    @Test
    public void testAnyOrder() {
        TreeMap<String, Object> sortedMap = new TreeMap() {{
            putAll(EXPECTED);
        }};

        assertTrue(matcher.matches(sortedMap));
        assertTrue(matcher.matches(sortedMap.descendingMap()));
    }

    @Test
    public void matchEmptyMap() {
        assertTrue(new MapEntryMatcher(emptyMap()).matches(emptyMap()));
    }

    @Test
    public void testNull() {
        assertFalse(new MapEntryMatcher(emptyMap()).matches(null));
    }

    @Test
    public void testNonMapType() {
        assertFalse(new MapEntryMatcher(emptyMap()).matches(new Object()));
        assertFalse(new MapEntryMatcher(emptyMap()).matches(emptySet()));
    }

    @Test
    public void testToString() {
        StringDescription description = new StringDescription();
        matcher.describeTo(description);
        assertEquals(description.toString(), "map containing " + String.format("[<%s=%s>,<%s=%s>,<%s=%s>]", EXPECTED.entrySet().stream().flatMap(entry -> Stream.of(entry.getKey(), entry.getValue())).toArray()));
    }

    private Map<String, Object> newHashMap(Object... keysAndValues) {
        if (!(keysAndValues.length % 2 == 0)) {
            throw new IllegalArgumentException("Must has even number of items");
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keysAndValues.length; i = i + 2) {
            map.put((String) keysAndValues[i], keysAndValues[i + 1]);
        }
        return map;
    }

    private static class ValueItem {

        private String name;

        public ValueItem(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ValueItem valueItem = (ValueItem) o;

            return name.equals(valueItem.name);
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("ValueItem{");
            sb.append("name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

}