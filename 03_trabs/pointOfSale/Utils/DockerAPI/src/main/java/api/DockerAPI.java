package api;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DockerAPI {
    public static void main(String[] args) {
        // arg0 windows: tcp://localhost:2375 // arg0 linux: unix:///var/run/docker.sock
        // arg1 : container name
        // arg2 : volume name or filesystem directory
        try {
            String HOST_URI = args[0]; String containerName = args[1];
            String pathVolDir = args[2]; String imageName = args[3];
            System.out.println("Inside docker api");
            System.out.println(pathVolDir);
            List<String> command=new ArrayList<>();
            for (int i=4; i < args.length; i++) command.add(args[i]);
            System.out.println(command);
            DockerClient dockerclient = DockerClientBuilder
                    .getInstance()
                    .withDockerHttpClient(
                            new ApacheDockerHttpClient.Builder()
                                    .dockerHost(URI.create(HOST_URI)).build()
                    )
                    .build();
            HostConfig hostConfig = HostConfig.newHostConfig()
                    .withBinds(new Bind(pathVolDir, new Volume("/usr/datafiles")));
            CreateContainerResponse containerResponse = dockerclient
                    .createContainerCmd(imageName)
                    .withName(containerName)
                    .withHostConfig(hostConfig)
                    .withCmd(command)
                    .exec();
            System.out.println("ID:" + containerResponse.getId());
            dockerclient.startContainerCmd(containerResponse.getId()).exec();
            InspectContainerResponse inspResp = dockerclient
                    .inspectContainerCmd(containerName).exec();

            String status = inspResp.getState().getStatus();
            System.out.println("Container Status: " + status);

            //dockerclient.killContainerCmd(containerName).exec();
            //dockerclient.removeContainerCmd(containerName).exec();
            //System.out.println("container removed");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}