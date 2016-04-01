package nl.jqno.mutable.java;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MutateObjectTest {
    @Test
    public void mutateString() {
        byte[] original = "xxx".getBytes();

        Mutate.setString("xxx", "yyy");

        byte[] mutated = "xxx".getBytes();
        assertFalse(Arrays.equals(original, mutated));

        byte[] newValue = "yyy".getBytes();
        assertTrue(Arrays.equals(newValue, mutated));
    }

    @Test
    public void mutatePojosIntField() throws Exception {
        Pojo p = new Pojo(42, "hello");
        Field f = getField(Pojo.class, "someInt");
        int original = (Integer)f.get(p);

        Mutate.setPrivateField(Pojo.class, "someInt", p, 1337);

        int actual = (Integer)f.get(p);
        assertNotEquals(original, actual);
        assertEquals(1337, actual);
    }

    @Test
    public void mutatePojosStringField() throws Exception {
        Pojo p = new Pojo(42, "forty-two");
        Field f = getField(Pojo.class, "someString");
        String original = (String)f.get(p);

        Mutate.setPrivateField(Pojo.class, "someString", p, "leet");

        String actual = (String)f.get(p);
        assertNotEquals(original, actual);
        assertEquals("leet", actual);
    }

    @Test
    public void instantiateVoid() {
        Void v = Mutate.newVoid();
        assertTrue(v instanceof Void);
    }

    private <T> Field getField(Class<T> type, String fieldName) throws NoSuchFieldException {
        Field result = type.getDeclaredField(fieldName);
        result.setAccessible(true);
        return result;
    }
}
