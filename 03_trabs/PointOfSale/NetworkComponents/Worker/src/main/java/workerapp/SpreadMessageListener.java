package workerapp;

import spread.SpreadMessage;
import spread.BasicMessageListener;

import java.util.Arrays;

public class SpreadMessageListener implements BasicMessageListener {

    @Override
    public void messageReceived(SpreadMessage spreadMessage) {
        System.out.println("Received multicast message: " + Arrays.toString(spreadMessage.getData()));
        // Process the multicast message as needed
    }
}
