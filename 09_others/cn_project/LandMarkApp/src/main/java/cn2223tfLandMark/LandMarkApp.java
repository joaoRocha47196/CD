package cn2223tfLandMark;


import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.*;

public class LandMarkApp {

    public static String PROJECT_ID = "cn2223-t1-g14";
    public static String BUCKET_NAME = "cn-europe";

    public static void main(String[] args) {
        if (args.length > 0) {
            PROJECT_ID = args[0];
            BUCKET_NAME = args[1];
        }
        else {
            System.out.println("pass the project id and the bucket name as a command line argument");
        }
        System.out.println("ProjectID = " + PROJECT_ID);
        System.out.println("Bucket Name = " + BUCKET_NAME);

        String subscriptionName = "subscription-cn";
        String projectID = PROJECT_ID;
        Subscriber subscriber = subscribeMessages(projectID, subscriptionName);
        System.out.println("Started listening...");
        subscriber.awaitTerminated();
        System.out.println("Terminating...");
    }

    public static Subscriber subscribeMessages(String projectID, String subscriptionName) {

        ProjectSubscriptionName projSubscriptionName = ProjectSubscriptionName.of(
            projectID, subscriptionName);
        Subscriber subscriber =
            Subscriber.newBuilder(projSubscriptionName, new MessageReceiveHandler(BUCKET_NAME))
                .build();
        subscriber.startAsync().awaitRunning();
        return subscriber;
    }
}
