package com.pm.patientservice.grpc;


import com.pm.billing.BillingRequest;
import com.pm.billing.BillingResponse;
import com.pm.billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
            @Value("${BILLING_SERVICE_ADDRESS:localhost}") String serverAddress,
            @Value("${BILLING_SERVICE_GRPC_PORT:9001}") int serverPort) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        this.blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();
        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info(response.toString());
        return response;
    }
}
