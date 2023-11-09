package srstubs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: SRService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SRServiceGrpc {

  private SRServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "srservice.SRService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<srstubs.ServerRegistration,
      srstubs.EmptyResponse> getRegisterServerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterServer",
      requestType = srstubs.ServerRegistration.class,
      responseType = srstubs.EmptyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<srstubs.ServerRegistration,
      srstubs.EmptyResponse> getRegisterServerMethod() {
    io.grpc.MethodDescriptor<srstubs.ServerRegistration, srstubs.EmptyResponse> getRegisterServerMethod;
    if ((getRegisterServerMethod = SRServiceGrpc.getRegisterServerMethod) == null) {
      synchronized (SRServiceGrpc.class) {
        if ((getRegisterServerMethod = SRServiceGrpc.getRegisterServerMethod) == null) {
          SRServiceGrpc.getRegisterServerMethod = getRegisterServerMethod =
              io.grpc.MethodDescriptor.<srstubs.ServerRegistration, srstubs.EmptyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterServer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  srstubs.ServerRegistration.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  srstubs.EmptyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SRServiceMethodDescriptorSupplier("RegisterServer"))
              .build();
        }
      }
    }
    return getRegisterServerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SRServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SRServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SRServiceStub>() {
        @java.lang.Override
        public SRServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SRServiceStub(channel, callOptions);
        }
      };
    return SRServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SRServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SRServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SRServiceBlockingStub>() {
        @java.lang.Override
        public SRServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SRServiceBlockingStub(channel, callOptions);
        }
      };
    return SRServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SRServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SRServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SRServiceFutureStub>() {
        @java.lang.Override
        public SRServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SRServiceFutureStub(channel, callOptions);
        }
      };
    return SRServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void registerServer(srstubs.ServerRegistration request,
        io.grpc.stub.StreamObserver<srstubs.EmptyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterServerMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SRService.
   */
  public static abstract class SRServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SRServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SRService.
   */
  public static final class SRServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SRServiceStub> {
    private SRServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SRServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SRServiceStub(channel, callOptions);
    }

    /**
     */
    public void registerServer(srstubs.ServerRegistration request,
        io.grpc.stub.StreamObserver<srstubs.EmptyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterServerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SRService.
   */
  public static final class SRServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SRServiceBlockingStub> {
    private SRServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SRServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SRServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public srstubs.EmptyResponse registerServer(srstubs.ServerRegistration request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterServerMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SRService.
   */
  public static final class SRServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SRServiceFutureStub> {
    private SRServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SRServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SRServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<srstubs.EmptyResponse> registerServer(
        srstubs.ServerRegistration request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterServerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_SERVER = 0;

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
        case METHODID_REGISTER_SERVER:
          serviceImpl.registerServer((srstubs.ServerRegistration) request,
              (io.grpc.stub.StreamObserver<srstubs.EmptyResponse>) responseObserver);
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
          getRegisterServerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              srstubs.ServerRegistration,
              srstubs.EmptyResponse>(
                service, METHODID_REGISTER_SERVER)))
        .build();
  }

  private static abstract class SRServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SRServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return srstubs.SRServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SRService");
    }
  }

  private static final class SRServiceFileDescriptorSupplier
      extends SRServiceBaseDescriptorSupplier {
    SRServiceFileDescriptorSupplier() {}
  }

  private static final class SRServiceMethodDescriptorSupplier
      extends SRServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SRServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SRServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SRServiceFileDescriptorSupplier())
              .addMethod(getRegisterServerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
