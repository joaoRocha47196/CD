package csstubs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: CSService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CSServiceGrpc {

  private CSServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "csservice.CSService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<csstubs.ImageRequest,
      csstubs.ImageIdentifier> getProcessImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ProcessImage",
      requestType = csstubs.ImageRequest.class,
      responseType = csstubs.ImageIdentifier.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<csstubs.ImageRequest,
      csstubs.ImageIdentifier> getProcessImageMethod() {
    io.grpc.MethodDescriptor<csstubs.ImageRequest, csstubs.ImageIdentifier> getProcessImageMethod;
    if ((getProcessImageMethod = CSServiceGrpc.getProcessImageMethod) == null) {
      synchronized (CSServiceGrpc.class) {
        if ((getProcessImageMethod = CSServiceGrpc.getProcessImageMethod) == null) {
          CSServiceGrpc.getProcessImageMethod = getProcessImageMethod =
              io.grpc.MethodDescriptor.<csstubs.ImageRequest, csstubs.ImageIdentifier>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ProcessImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  csstubs.ImageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  csstubs.ImageIdentifier.getDefaultInstance()))
              .setSchemaDescriptor(new CSServiceMethodDescriptorSupplier("ProcessImage"))
              .build();
        }
      }
    }
    return getProcessImageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<csstubs.ImageIdentifier,
      csstubs.ImageResponse> getCheckImageStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckImageStatus",
      requestType = csstubs.ImageIdentifier.class,
      responseType = csstubs.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<csstubs.ImageIdentifier,
      csstubs.ImageResponse> getCheckImageStatusMethod() {
    io.grpc.MethodDescriptor<csstubs.ImageIdentifier, csstubs.ImageResponse> getCheckImageStatusMethod;
    if ((getCheckImageStatusMethod = CSServiceGrpc.getCheckImageStatusMethod) == null) {
      synchronized (CSServiceGrpc.class) {
        if ((getCheckImageStatusMethod = CSServiceGrpc.getCheckImageStatusMethod) == null) {
          CSServiceGrpc.getCheckImageStatusMethod = getCheckImageStatusMethod =
              io.grpc.MethodDescriptor.<csstubs.ImageIdentifier, csstubs.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckImageStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  csstubs.ImageIdentifier.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  csstubs.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CSServiceMethodDescriptorSupplier("CheckImageStatus"))
              .build();
        }
      }
    }
    return getCheckImageStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<csstubs.ImageIdentifier,
      csstubs.ImageResponse> getDownloadProcessedImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadProcessedImage",
      requestType = csstubs.ImageIdentifier.class,
      responseType = csstubs.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<csstubs.ImageIdentifier,
      csstubs.ImageResponse> getDownloadProcessedImageMethod() {
    io.grpc.MethodDescriptor<csstubs.ImageIdentifier, csstubs.ImageResponse> getDownloadProcessedImageMethod;
    if ((getDownloadProcessedImageMethod = CSServiceGrpc.getDownloadProcessedImageMethod) == null) {
      synchronized (CSServiceGrpc.class) {
        if ((getDownloadProcessedImageMethod = CSServiceGrpc.getDownloadProcessedImageMethod) == null) {
          CSServiceGrpc.getDownloadProcessedImageMethod = getDownloadProcessedImageMethod =
              io.grpc.MethodDescriptor.<csstubs.ImageIdentifier, csstubs.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DownloadProcessedImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  csstubs.ImageIdentifier.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  csstubs.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CSServiceMethodDescriptorSupplier("DownloadProcessedImage"))
              .build();
        }
      }
    }
    return getDownloadProcessedImageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CSServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CSServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CSServiceStub>() {
        @java.lang.Override
        public CSServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CSServiceStub(channel, callOptions);
        }
      };
    return CSServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CSServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CSServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CSServiceBlockingStub>() {
        @java.lang.Override
        public CSServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CSServiceBlockingStub(channel, callOptions);
        }
      };
    return CSServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CSServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CSServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CSServiceFutureStub>() {
        @java.lang.Override
        public CSServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CSServiceFutureStub(channel, callOptions);
        }
      };
    return CSServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<csstubs.ImageRequest> processImage(
        io.grpc.stub.StreamObserver<csstubs.ImageIdentifier> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getProcessImageMethod(), responseObserver);
    }

    /**
     */
    default void checkImageStatus(csstubs.ImageIdentifier request,
        io.grpc.stub.StreamObserver<csstubs.ImageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckImageStatusMethod(), responseObserver);
    }

    /**
     */
    default void downloadProcessedImage(csstubs.ImageIdentifier request,
        io.grpc.stub.StreamObserver<csstubs.ImageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadProcessedImageMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CSService.
   */
  public static abstract class CSServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CSServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CSService.
   */
  public static final class CSServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CSServiceStub> {
    private CSServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CSServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CSServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<csstubs.ImageRequest> processImage(
        io.grpc.stub.StreamObserver<csstubs.ImageIdentifier> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getProcessImageMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void checkImageStatus(csstubs.ImageIdentifier request,
        io.grpc.stub.StreamObserver<csstubs.ImageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckImageStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void downloadProcessedImage(csstubs.ImageIdentifier request,
        io.grpc.stub.StreamObserver<csstubs.ImageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDownloadProcessedImageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CSService.
   */
  public static final class CSServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CSServiceBlockingStub> {
    private CSServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CSServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CSServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public csstubs.ImageResponse checkImageStatus(csstubs.ImageIdentifier request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckImageStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<csstubs.ImageResponse> downloadProcessedImage(
        csstubs.ImageIdentifier request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDownloadProcessedImageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CSService.
   */
  public static final class CSServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CSServiceFutureStub> {
    private CSServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CSServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CSServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<csstubs.ImageResponse> checkImageStatus(
        csstubs.ImageIdentifier request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckImageStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK_IMAGE_STATUS = 0;
  private static final int METHODID_DOWNLOAD_PROCESSED_IMAGE = 1;
  private static final int METHODID_PROCESS_IMAGE = 2;

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
        case METHODID_CHECK_IMAGE_STATUS:
          serviceImpl.checkImageStatus((csstubs.ImageIdentifier) request,
              (io.grpc.stub.StreamObserver<csstubs.ImageResponse>) responseObserver);
          break;
        case METHODID_DOWNLOAD_PROCESSED_IMAGE:
          serviceImpl.downloadProcessedImage((csstubs.ImageIdentifier) request,
              (io.grpc.stub.StreamObserver<csstubs.ImageResponse>) responseObserver);
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
        case METHODID_PROCESS_IMAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.processImage(
              (io.grpc.stub.StreamObserver<csstubs.ImageIdentifier>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getProcessImageMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              csstubs.ImageRequest,
              csstubs.ImageIdentifier>(
                service, METHODID_PROCESS_IMAGE)))
        .addMethod(
          getCheckImageStatusMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              csstubs.ImageIdentifier,
              csstubs.ImageResponse>(
                service, METHODID_CHECK_IMAGE_STATUS)))
        .addMethod(
          getDownloadProcessedImageMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              csstubs.ImageIdentifier,
              csstubs.ImageResponse>(
                service, METHODID_DOWNLOAD_PROCESSED_IMAGE)))
        .build();
  }

  private static abstract class CSServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CSServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return csstubs.CSServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CSService");
    }
  }

  private static final class CSServiceFileDescriptorSupplier
      extends CSServiceBaseDescriptorSupplier {
    CSServiceFileDescriptorSupplier() {}
  }

  private static final class CSServiceMethodDescriptorSupplier
      extends CSServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CSServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (CSServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CSServiceFileDescriptorSupplier())
              .addMethod(getProcessImageMethod())
              .addMethod(getCheckImageStatusMethod())
              .addMethod(getDownloadProcessedImageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
