package servercallers;

import srstubs.EmptyResponse;
import srstubs.SRServiceGrpc;
import srstubs.ServerRegistration;


public class SpreadGroupCaller {

    private static int workersServerPort;
    private static String workersServerIp; // "35.246.73.129";

    public SpreadGroupCaller( int workersServerPort, String workersServerIp) {
        this.workersServerPort = workersServerPort;
        this.workersServerIp = workersServerIp;
    }

}