package testclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ringtoclientservice.RingToClientServiceGrpc;
import ringtoclientservice.Void;
import ringtoserverservice.Location;
import ringtoserverservice.RingToServerServiceGrpc;

public class ClientTest {
    private static String svcIP = "localhost"; private static int svcPort = 8000;
    private static ManagedChannel channel;
    private static RingToServerServiceGrpc.RingToServerServiceBlockingStub blockingStub1;
    private static RingToClientServiceGrpc.RingToClientServiceBlockingStub blockingStub2;
    public static void main(String[] args) {
        try {
            channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                    .usePlaintext()
                    .build();
            blockingStub1 = RingToServerServiceGrpc.newBlockingStub(channel);
            blockingStub2 = RingToClientServiceGrpc.newBlockingStub(channel);


            Location locationServer = blockingStub1.registerServer(Location.newBuilder().setIP("1.2.3.5").setPort("8888").build());
            System.out.println(locationServer);

            ringtoclientservice.Location locationClient = blockingStub2.getKvServer(Void.newBuilder().build());
            System.out.println(locationClient);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
