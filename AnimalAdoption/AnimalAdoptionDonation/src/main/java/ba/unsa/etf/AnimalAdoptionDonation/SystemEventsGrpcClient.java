package ba.unsa.etf.AnimalAdoptionDonation;


import com.system.events.EventRequest;
import com.system.events.EventResponse;
import com.system.events.SystemEventServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SystemEventsGrpcClient {

    private final SystemEventServiceGrpc.SystemEventServiceBlockingStub stub;
    private final ManagedChannel channel;

    public SystemEventsGrpcClient() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.stub = SystemEventServiceGrpc.newBlockingStub(channel);
    }

    public void logEvent(String microservice, String user, String actionType, String resource, String responseType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

        EventRequest request = EventRequest.newBuilder()
                .setTimestamp(timestamp)
                .setMicroservice(microservice)
                .setUser(user)
                .setActionType(actionType)
                .setResource(resource)
                .setResponseType(responseType)
                .build();

        EventResponse response = stub.logEvent(request);

        System.out.println("SystemEvents response: " + response.getMessage());
    }

    public void shutdown() {
        channel.shutdown();
    }
}