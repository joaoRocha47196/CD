package crstubs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: CRService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CRServiceGrpc {

  private CRServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "crservice.CRService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<crstubs.GetServerRequest,
      crstubs.ServerEndpoint> getGetServerEndpointMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetServerEndpoint",
      requestType = crstubs.GetServerRequest.class,
      responseType = crstubs.ServerEndpoint.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<crstubs.GetServerRequest,
      crstubs.ServerEndpoint> getGetServerEndpointMethod() {
    io.grpc.MethodDescriptor<crstubs.GetServerRequest, crstubs.ServerEndpoint> getGetServerEndpointMethod;
    if ((getGetServerEndpointMethod = CRServiceGrpc.getGetServerEndpointMethod) == null) {
      synchronized (CRServiceGrpc.class) {
        if ((getGetServerEndpointMethod = CRServiceGrpc.getGetServerEndpointMethod) == null) {
          CRServiceGrpc.getGetServerEndpointMethod = getGetServerEndpointMethod =
              io.grpc.MethodDescriptor.<crstubs.GetServerRequest, crstubs.ServerEndpoint>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetServerEndpoint"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  crstubs.GetServerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  crstubs.ServerEndpoint.getDefaultInstance()))
              .setSchemaDescriptor(new CRServiceMethodDescriptorSupplier("GetServerEndpoint"))
              .build();
        }
      }
    }
    return getGetServerEndpointMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CRServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CRServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CRServiceStub>() {
        @java.lang.Override
        public CRServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CRServiceStub(channel, callOptions);
        }
      };
    return CRServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CRServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CRServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CRServiceBlockingStub>() {
        @java.lang.Override
        public CRServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CRServiceBlockingStub(channel, callOptions);
        }
      };
    return CRServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CRServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CRServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CRServiceFutureStub>() {
        @java.lang.Override
        public CRServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CRServiceFutureStub(channel, callOptions);
        }
      };
    return CRServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getServerEndpoint(crstubs.GetServerRequest request,
        io.grpc.stub.StreamObserver<crstubs.ServerEndpoint> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetServerEndpointMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CRService.
   */
  public static abstract class CRServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CRServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CRService.
   */
  public static final class CRServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CRServiceStub> {
    private CRServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CRServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CRServiceStub(channel, callOptions);
    }

    /**
     */
    public void getServerEndpoint(crstubs.GetServerRequest request,
        io.grpc.stub.StreamObserver<crstubs.ServerEndpoint> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetServerEndpointMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CRService.
   */
  public static final class CRServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CRServiceBlockingStub> {
    private CRServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CRServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CRServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public crstubs.ServerEndpoint getServerEndpoint(crstubs.GetServerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetServerEndpointMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CRService.
   */
  public static final class CRServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CRServiceFutureStub> {
    private CRServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CRServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CRServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<crstubs.ServerEndpoint> getServerEndpoint(
        crstubs.GetServerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetServerEndpointMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SERVER_ENDPOINT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SERVER_ENDPOINT:
          serviceImpl.getServerEndpoint((crstubs.GetServerRequest) request,
              (io.grpc.stub.StreamObserver<crstubs.ServerEndpoint>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetServerEndpointMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              crstubs.GetServerRequest,
              crstubs.ServerEndpoint>(
                service, METHODID_GET_SERVER_ENDPOINT)))
        .build();
  }

  private static abstract class CRServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CRServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return crstubs.CRServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CRService");
    }
  }

  private static final class CRServiceFileDescriptorSupplier
      extends CRServiceBaseDescriptorSupplier {
    CRServiceFileDescriptorSupplier() {}
  }

  private static final class CRServiceMethodDescriptorSupplier
      extends CRServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CRServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CRServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CRServiceFileDescriptorSupplier())
              .addMethod(getGetServerEndpointMethod())
              .build();
        }
      }
    }
    return result;
  }
}
