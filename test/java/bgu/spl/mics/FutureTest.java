package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    public void setUp(){
        future = new Future<String>();
    }

    @Test
    public void testGet()
    {
        assertFalse(future.isDone());
        future.resolve("");
        try {
            assertTrue(future.get().equals(""));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(future.isDone());
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        try {
            assertTrue(str.equals(future.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsDone(){
        String str = "someResult";
        assertFalse(future.isDone());
        future.resolve(str);
        assertTrue(future.isDone());
    }

    @Test
    public void testGetWithTimeOut() throws InterruptedException
    {
        assertFalse(future.isDone());
        future.get(100,TimeUnit.MILLISECONDS);
        assertFalse(future.isDone());
        future.resolve("foo");
        assertEquals(future.get(100,TimeUnit.MILLISECONDS),"foo");
    }








}
