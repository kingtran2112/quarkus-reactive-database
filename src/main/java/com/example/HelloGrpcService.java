package com.example;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import java.util.UUID;

@GrpcService
@NonBlocking
public class HelloGrpcService extends ProductGrpcGrpc.ProductGrpcImplBase {

    @Inject
    ProductRepository productRepository;

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<CreateProductResponse> responseObserver) {
       ProductModel product = new ProductModel();
       product.setMessage(request.getMessage());
       productRepository.persist(product).subscribe().with(p -> {
           CreateProductResponse productGrpc = CreateProductResponse.newBuilder().setId(p.getId().toString()).setMessage(p.getMessage()).build();
           responseObserver.onNext(productGrpc);
           responseObserver.onCompleted();
       });
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<GetProductResponse> responseObserver) {
        productRepository.findById(UUID.fromString(request.getId())).subscribe().with(p -> {
            GetProductResponse productGrpc = GetProductResponse.newBuilder().setId(p.getId().toString()).setMessage(p.getMessage()).build();
            responseObserver.onNext(productGrpc);
            responseObserver.onCompleted();
        });
    }
}
