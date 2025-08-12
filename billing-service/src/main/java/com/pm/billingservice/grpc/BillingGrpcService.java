package com.pm.billingservice.grpc;

import com.pm.billing.BillingRequest;
import com.pm.billing.BillingResponse;
import com.pm.billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;


@Slf4j
@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    @Override
    public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        log.info("Create billing account {}", request);
        BillingResponse response = BillingResponse
                .newBuilder()
                .setAccountId("1234")
                .setStatus("SUCCESS")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

