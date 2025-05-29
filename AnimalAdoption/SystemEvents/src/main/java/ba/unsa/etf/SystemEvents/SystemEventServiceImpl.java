package ba.unsa.etf.SystemEvents;

import ba.unsa.etf.SystemEvents.Entity.EventLog;
import ba.unsa.etf.SystemEvents.Repository.EventLogRepository;
import com.system.events.EventRequest;
import com.system.events.EventResponse;
import com.system.events.SystemEventServiceGrpc;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@GrpcService
public class SystemEventServiceImpl extends SystemEventServiceGrpc.SystemEventServiceImplBase {

    @Autowired
    private EventLogRepository eventLogRepository;

    @Override
    @Transactional
    public void logEvent(EventRequest request, StreamObserver<EventResponse> responseObserver) {
        System.out.println("logEvent pozvan! Podaci: " + request.toString());
        EventLog event = new EventLog();

        // Konvertuj timestamp iz stringa u LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        event.setTimestamp(LocalDateTime.parse(request.getTimestamp(), formatter));

        event.setMicroservice(request.getMicroservice());
        event.setUser(request.getUser());
        event.setActionType(request.getActionType());
        event.setResource(request.getResource());
        event.setResponseType(request.getResponseType());

        eventLogRepository.save(event);

        EventResponse response = EventResponse.newBuilder()
                .setMessage("Dogadjaj je uspjesno zabiljezen!")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @PostConstruct
    public void init() {
        System.out.println("gRPC Server je pokrenut na portu 9090.");
    }
}