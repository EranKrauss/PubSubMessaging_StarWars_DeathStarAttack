package bgu.spl.mics;

import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl singeltonInstance = null;
	private HashMap<String , Queue<Message>> hashMessage;
	private HashMap<Class , Queue<MicroService>> hashTypeOfEvent;
	private HashMap<Class , Queue<MicroService>> hashTypeOfBroadcast;
	private HashMap<Event , Future> hashMapFuture;
	private Object lock1;

	private static class SingeltonHolder{
		private static MessageBusImpl instance = new MessageBusImpl();
	}


	public static MessageBusImpl getInstance(){
		return SingeltonHolder.instance;
	}

	private MessageBusImpl(){
		this.hashMessage = new HashMap();
		this.hashTypeOfEvent = new HashMap();
		this.hashTypeOfBroadcast = new HashMap();
		this.hashMapFuture = new HashMap();
		lock1 = new Object();
	}





	@Override
	public synchronized <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
			if (!this.hashTypeOfEvent.containsKey(type)) {
				this.hashTypeOfEvent.put(type, new LinkedList<MicroService>());
			}
			this.hashTypeOfEvent.get(type).add(m);

	}

	@Override
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
			if (!this.hashTypeOfBroadcast.containsKey(type)) {
				this.hashTypeOfBroadcast.put(type, new LinkedList<MicroService>());
			}
			this.hashTypeOfBroadcast.get(type).add(m);

    }

	@Override @SuppressWarnings("unchecked")
	public synchronized <T> void complete(Event<T> e, T result) {
		if (hashMapFuture.containsKey(e)) {
			hashMapFuture.get(e).resolve(result);
			notifyAll();
		}
	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {
			if (hashTypeOfBroadcast.containsKey(b.getClass())) {
				Iterator<MicroService> itr = hashTypeOfBroadcast.get(b.getClass()).iterator();
				while (itr.hasNext()) {
					hashMessage.get(itr.next().getName()).add(b);
				}
			}
			this.notifyAll();

	}

	@Override
	public synchronized <T> Future<T> sendEvent(Event<T> e) {
			if (hashTypeOfEvent.containsKey(e.getClass())) {
				MicroService curr = hashTypeOfEvent.get(e.getClass()).remove();
				System.out.println(curr.getName() + " got a message from a type:   " + e.getClass().toString());
				hashMessage.get(curr.getName()).add(e);
				hashTypeOfEvent.get(e.getClass()).add(curr);
				Future<T> curr_ = new Future<T>();
				hashMapFuture.put(e, curr_);
				notifyAll();
				return curr_;
			}
			return null;

	}

	@Override
	public synchronized void register(MicroService m) {
			this.hashMessage.put(m.getName(), new LinkedList<Message>());

	}

	@Override
	public synchronized void unregister(MicroService m) {
			this.hashMessage.remove(m.getName());

	}

	@Override
	public synchronized Message awaitMessage(MicroService m) throws InterruptedException {
			if (!hashMessage.containsKey(m.getName())) {
				throw new IllegalStateException("Not register yet");
			}
			if (hashMessage.get(m.getName()).isEmpty()) {
				wait();
			}

			try {
				return hashMessage.get(m.getName()).remove();
			} catch (NoSuchElementException e){
				return null;
			}

	}
}
