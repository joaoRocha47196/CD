package umstubs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: UMService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UMServiceGrpc {

  private UMServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "UMService.UMService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<umstubs.ResumeInfo,
      umstubs.NotificationResponse> getResumeSalesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "resumeSales",
      requestType = umstubs.ResumeInfo.class,
      responseType = umstubs.NotificationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<umstubs.ResumeInfo,
      umstubs.NotificationResponse> getResumeSalesMethod() {
    io.grpc.MethodDescriptor<umstubs.ResumeInfo, umstubs.NotificationResponse> getResumeSalesMethod;
    if ((getResumeSalesMethod = UMServiceGrpc.getResumeSalesMethod) == null) {
      synchronized (UMServiceGrpc.class) {
        if ((getResumeSalesMethod = UMServiceGrpc.getResumeSalesMethod) == null) {
          UMServiceGrpc.getResumeSalesMethod = getResumeSalesMethod =
              io.grpc.MethodDescriptor.<umstubs.ResumeInfo, umstubs.NotificationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "resumeSales"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  umstubs.ResumeInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  umstubs.NotificationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UMServiceMethodDescriptorSupplier("resumeSales"))
              .build();
        }
      }
    }
    return getResumeSalesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<umstubs.FileIdentifier,
      umstubs.FileResponse> getDownloadFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "downloadFile",
      requestType = umstubs.FileIdentifier.class,
      responseType = umstubs.FileResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<umstubs.FileIdentifier,
      umstubs.FileResponse> getDownloadFileMethod() {
    io.grpc.MethodDescriptor<umstubs.FileIdentifier, umstubs.FileResponse> getDownloadFileMethod;
    if ((getDownloadFileMethod = UMServiceGrpc.getDownloadFileMethod) == null) {
      synchronized (UMServiceGrpc.class) {
        if ((getDownloadFileMethod = UMServiceGrpc.getDownloadFileMethod) == null) {
          UMServiceGrpc.getDownloadFileMethod = getDownloadFileMethod =
              io.grpc.MethodDescriptor.<umstubs.FileIdentifier, umstubs.FileResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "downloadFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  umstubs.FileIdentifier.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  umstubs.FileResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UMServiceMethodDescriptorSupplier("downloadFile"))
              .build();
        }
      }
    }
    return getDownloadFileMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UMServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UMServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UMServiceStub>() {
        @java.lang.Override
        public UMServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UMServiceStub(channel, callOptions);
        }
      };
    return UMServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UMServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UMServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UMServiceBlockingStub>() {
        @java.lang.Override
        public UMServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UMServiceBlockingStub(channel, callOptions);
        }
      };
    return UMServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UMServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UMServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UMServiceFutureStub>() {
        @java.lang.Override
        public UMServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UMServiceFutureStub(channel, callOptions);
        }
      };
    return UMServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void resumeSales(umstubs.ResumeInfo request,
        io.grpc.stub.StreamObserver<umstubs.NotificationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getResumeSalesMethod(), responseObserver);
    }

    /**
     */
    default void downloadFile(umstubs.FileIdentifier request,
        io.grpc.stub.StreamObserver<umstubs.FileResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadFileMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service UMService.
   */
  public static abstract class UMServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return UMServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service UMService.
   */
  public static final class UMServiceStub
      extends io.grpc.stub.AbstractAsyncStub<UMServiceStub> {
    private UMServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UMServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UMServiceStub(channel, callOptions);
    }

    /**
     */
    public void resumeSales(umstubs.ResumeInfo request,
        io.grpc.stub.StreamObserver<umstubs.NotificationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getResumeSalesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void downloadFile(umstubs.FileIdentifier request,
        io.grpc.stub.StreamObserver<umstubs.FileResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDownloadFileMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service UMService.
   */
  public static final class UMServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<UMServiceBlockingStub> {
    private UMServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UMServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UMServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public umstubs.NotificationResponse resumeSales(umstubs.ResumeInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getResumeSalesMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<umstubs.FileResponse> downloadFile(
        umstubs.FileIdentifier request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDownloadFileMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service UMService.
   */
  public static final class UMServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<UMServiceFutureStub> {
    private UMServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UMServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UMServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<umstubs.NotificationResponse> resumeSales(
        umstubs.ResumeInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getResumeSalesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RESUME_SALES = 0;
  private static final int METHODID_DOWNLOAD_FILE = 1;

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
          serviceImpl.resumeSales((umstubs.ResumeInfo) request,
              (io.grpc.stub.StreamObserver<umstubs.NotificationResponse>) responseObserver);
          break;
        case METHODID_DOWNLOAD_FILE:
          serviceImpl.downloadFile((umstubs.FileIdentifier) request,
              (io.grpc.stub.StreamObserver<umstubs.FileResponse>) responseObserver);
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
              umstubs.ResumeInfo,
              umstubs.NotificationResponse>(
                service, METHODID_RESUME_SALES)))
        .addMethod(
          getDownloadFileMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              umstubs.FileIdentifier,
              umstubs.FileResponse>(
                service, METHODID_DOWNLOAD_FILE)))
        .build();
  }

  private static abstract class UMServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UMServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return umstubs.UMServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UMService");
    }
  }

  private static final class UMServiceFileDescriptorSupplier
      extends UMServiceBaseDescriptorSupplier {
    UMServiceFileDescriptorSupplier() {}
  }

  private static final class UMServiceMethodDescriptorSupplier
      extends UMServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    UMServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (UMServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UMServiceFileDescriptorSupplier())
              .addMethod(getResumeSalesMethod())
              .addMethod(getDownloadFileMethod())
              .build();
        }
      }
    }
    return result;
  }
}
