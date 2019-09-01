package ru.otus.javadeveloper.hw16.ms;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.some.Addressee;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author tully
 */
@Service
@SuppressWarnings("LoopStatementThatDoesntLoop")
public final class MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());

    private final Map<Address, LinkedBlockingQueue<String>> messagesMap;
//    private final Map<Address, Addressee> addresseeMap;
//    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public MessageSystem() {
        messagesMap = new HashMap<>();
//        addresseeMap = new HashMap<>();
    }

//    public void addAddressee(Addressee addressee) {
//        addresseeMap.put(addressee.getAddress(), addressee);
//        messagesMap.put(addressee.getAddress(), new LinkedBlockingQueue<>());
//        executorService.submit(() -> this.startWatch(addressee.getAddress(), addressee));
//    }
//
//    public void sendMessage(Message message) {
//        messagesMap.get(message.getTo()).add(message);
//    }


    private void startWatch(Address address, Addressee addressee) {
        LinkedBlockingQueue<String> queue = messagesMap.get(address);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String message = queue.take(); //Blocks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
