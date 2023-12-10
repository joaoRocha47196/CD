package csstubs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: UserAppService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UserAppServiceGrpc {

  private UserAppServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "UserAppService.UserAppService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<csstubs.ResumeSales,
      csstubs.ResumeNotification> getResumeSalesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "resumeSales",
      requestType = csstubs.ResumeSales.class,
      responseType = csstubs.ResumeNotification.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<csstubs.ResumeSales,
      csstubs.ResumeNotification> getResumeSalesMethod() {
    io.grpc.MethodDescriptor<csstubs.ResumeSales, csstubs.ResumeNotification> getResumeSalesMethod;
    if ((getResumeSalesMethod = UserAppServiceGrpc.getResumeSalesMethod) == null) {
      synchronized (UserAppServiceGrpc.class) {
        if ((getResumeSalesMethod = UserAppServiceGrpc.getResumeSalesMethod) == null) {
          UserAppServiceGrpc.getResumeSalesMethod = getResumeSalesMethod =
              io.grpc.MethodDescriptor.<csstubs.ResumeSales, csstubs.ResumeNotification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "resumeSales"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  csstubs.ResumeSales.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  csstubs.ResumeNotification.getDefaultInstance()))
              .setSchemaDescriptor(new UserAppServiceMethodDescriptorSupplier("resumeSales"))
              .build();
        }
      }
    }
    return getResumeSalesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserAppServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserAppServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserAppServiceStub>() {
        @java.lang.Override
        public UserAppServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserAppServiceStub(channel, callOptions);
        }
      };
    return UserAppServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserAppServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserAppServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserAppServiceBlockingStub>() {
        @java.lang.Override
        public UserAppServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserAppServiceBlockingStub(channel, callOptions);
        }
      };
    return UserAppServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserAppServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserAppServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserAppServiceFutureStub>() {
        @java.lang.Override
        public UserAppServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserAppServiceFutureStub(channel, callOptions);
        }
      };
    return UserAppServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void resumeSales(csstubs.ResumeSales request,
        io.grpc.stub.StreamObserver<csstubs.ResumeNotification> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getResumeSalesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service UserAppService.
   */
  public static abstract class UserAppServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return UserAppServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service UserAppService.
   */
  public static final class UserAppServiceStub
      extends io.grpc.stub.AbstractAsyncStub<UserAppServiceStub> {
    private UserAppServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserAppServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserAppServiceStub(channel, callOptions);
    }

    /**
     */
    public void resumeSales(csstubs.ResumeSales request,
        io.grpc.stub.StreamObserver<csstubs.ResumeNotification> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getResumeSalesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service UserAppService.
   */
  public static final class UserAppServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<UserAppServiceBlockingStub> {
    private UserAppServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserAppServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserAppServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public csstubs.ResumeNotification resumeSales(csstubs.ResumeSales request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getResumeSalesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service UserAppService.
   */
  public static final class UserAppServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<UserAppServiceFutureStub> {
    private UserAppServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserAppServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserAppServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<csstubs.ResumeNotification> resumeSales(
        csstubs.ResumeSales request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getResumeSalesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RESUME_SALES = 0;

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
        case METHODID_RESUME_SALES:
          serviceImpl.resumeSales((csstubs.ResumeSales) request,
              (io.grpc.stub.StreamObserver<csstubs.ResumeNotification>) responseObserver);
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
          getResumeSalesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              csstubs.ResumeSales,
              csstubs.ResumeNotification>(
                service, METHODID_RESUME_SALES)))
        .build();
  }

  private static abstract class UserAppServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserAppServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return csstubs.UserAppServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserAppService");
    }
  }

  private static final class UserAppServiceFileDescriptorSupplier
      extends UserAppServiceBaseDescriptorSupplier {
    UserAppServiceFileDescriptorSupplier() {}
  }

  private static final class UserAppServiceMethodDescriptorSupplier
      extends UserAppServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    UserAppServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (UserAppServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserAppServiceFileDescriptorSupplier())
              .addMethod(getResumeSalesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
