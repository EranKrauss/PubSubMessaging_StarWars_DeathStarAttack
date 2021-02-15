package bgu.spl.mics;

import bgu.spl.mics.application.DummyBroadcast;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.DummyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MessagesBusTest {
    private MessageBusImpl messageBus;

    @BeforeEach
    public void setUp(){
        messageBus = MessageBusImpl.getInstance();
    }

    @Test
    public void testComplete() throws InterruptedException {
        Event<Boolean> e = new DummyEvent();
        Future<Boolean> f = messageBus.sendEvent(e);
        assertFalse(f.isDone());
        boolean result = true;
        messageBus.complete(e , result);
        assertTrue(f.isDone());
        assertTrue(f.get());
    }

    @Test
    public void testSendBroadcast(){ //create dummyBroadcas
        DummyBroadcast dummyBroadcast = new DummyBroadcast();
        MicroService microService = new C3POMicroservice();
        messageBus.register(microService);
        messageBus.sendBroadcast(dummyBroadcast);
        try {
            Message m = messageBus.awaitMessage(microService);
            assertTrue(true);
        } catch (InterruptedException e){
            assertTrue(false);
        }
    }

    @Test
    public void testSendEvent(){
        List<Integer> lst = new LinkedList<Integer>();
        AttackEvent ae = new AttackEvent(new Attack(lst , 1000));
        MicroService microService = new C3POMicroservice();
        messageBus.register(microService);
        messageBus.sendEvent(ae);
        try {
            Message m = messageBus.awaitMessage(microService);
            assertTrue(true);
        } catch (InterruptedException e){
            assertTrue(false);
        }
      }

    @Test
    public void testAwaitMessage(){
        Message m;
        MicroService microService = new C3POMicroservice();
        messageBus.register(microService);
        messageBus.unregister(microService);
        try{
            m = messageBus.awaitMessage(microService);
            assertTrue(false);
        }
        catch(InterruptedException e){
            assertTrue(true);
        }

    }



}
