import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: CalcService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ImageServiceGrpc {

  private ImageServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ImageService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<CalcService.ImageRequest,
      CalcService.ImageIdentifier> getProcessImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ProcessImage",
      requestType = CalcService.ImageRequest.class,
      responseType = CalcService.ImageIdentifier.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<CalcService.ImageRequest,
      CalcService.ImageIdentifier> getProcessImageMethod() {
    io.grpc.MethodDescriptor<CalcService.ImageRequest, CalcService.ImageIdentifier> getProcessImageMethod;
    if ((getProcessImageMethod = ImageServiceGrpc.getProcessImageMethod) == null) {
      synchronized (ImageServiceGrpc.class) {
        if ((getProcessImageMethod = ImageServiceGrpc.getProcessImageMethod) == null) {
          ImageServiceGrpc.getProcessImageMethod = getProcessImageMethod =
              io.grpc.MethodDescriptor.<CalcService.ImageRequest, CalcService.ImageIdentifier>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ProcessImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CalcService.ImageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CalcService.ImageIdentifier.getDefaultInstance()))
              .setSchemaDescriptor(new ImageServiceMethodDescriptorSupplier("ProcessImage"))
              .build();
        }
      }
    }
    return getProcessImageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<CalcService.ImageIdentifier,
      CalcService.ImageResponse> getCheckImageStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckImageStatus",
      requestType = CalcService.ImageIdentifier.class,
      responseType = CalcService.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<CalcService.ImageIdentifier,
      CalcService.ImageResponse> getCheckImageStatusMethod() {
    io.grpc.MethodDescriptor<CalcService.ImageIdentifier, CalcService.ImageResponse> getCheckImageStatusMethod;
    if ((getCheckImageStatusMethod = ImageServiceGrpc.getCheckImageStatusMethod) == null) {
      synchronized (ImageServiceGrpc.class) {
        if ((getCheckImageStatusMethod = ImageServiceGrpc.getCheckImageStatusMethod) == null) {
          ImageServiceGrpc.getCheckImageStatusMethod = getCheckImageStatusMethod =
              io.grpc.MethodDescriptor.<CalcService.ImageIdentifier, CalcService.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckImageStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CalcService.ImageIdentifier.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CalcService.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ImageServiceMethodDescriptorSupplier("CheckImageStatus"))
              .build();
        }
      }
    }
    return getCheckImageStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<CalcService.ImageIdentifier,
      CalcService.ImageResponse> getDownloadProcessedImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadProcessedImage",
      requestType = CalcService.ImageIdentifier.class,
      responseType = CalcService.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<CalcService.ImageIdentifier,
      CalcService.ImageResponse> getDownloadProcessedImageMethod() {
    io.grpc.MethodDescriptor<CalcService.ImageIdentifier, CalcService.ImageResponse> getDownloadProcessedImageMethod;
    if ((getDownloadProcessedImageMethod = ImageServiceGrpc.getDownloadProcessedImageMethod) == null) {
      synchronized (ImageServiceGrpc.class) {
        if ((getDownloadProcessedImageMethod = ImageServiceGrpc.getDownloadProcessedImageMethod) == null) {
          ImageServiceGrpc.getDownloadProcessedImageMethod = getDownloadProcessedImageMethod =
              io.grpc.MethodDescriptor.<CalcService.ImageIdentifier, CalcService.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DownloadProcessedImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CalcService.ImageIdentifier.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CalcService.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ImageServiceMethodDescriptorSupplier("DownloadProcessedImage"))
              .build();
        }
      }
    }
    return getDownloadProcessedImageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ImageServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ImageServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ImageServiceStub>() {
        @java.lang.Override
        public ImageServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ImageServiceStub(channel, callOptions);
        }
      };
    return ImageServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ImageServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ImageServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ImageServiceBlockingStub>() {
        @java.lang.Override
        public ImageServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ImageServiceBlockingStub(channel, callOptions);
        }
      };
    return ImageServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ImageServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ImageServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ImageServiceFutureStub>() {
        @java.lang.Override
        public ImageServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ImageServiceFutureStub(channel, callOptions);
        }
      };
    return ImageServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<CalcService.ImageRequest> processImage(
        io.grpc.stub.StreamObserver<CalcService.ImageIdentifier> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getProcessImageMethod(), responseObserver);
    }

    /**
     */
    default void checkImageStatus(CalcService.ImageIdentifier request,
        io.grpc.stub.StreamObserver<CalcService.ImageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckImageStatusMethod(), responseObserver);
    }

    /**
     */
    default void downloadProcessedImage(CalcService.ImageIdentifier request,
        io.grpc.stub.StreamObserver<CalcService.ImageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadProcessedImageMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ImageService.
   */
  public static abstract class ImageServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ImageServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ImageService.
   */
  public static final class ImageServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ImageServiceStub> {
    private ImageServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ImageServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<CalcService.ImageRequest> processImage(
        io.grpc.stub.StreamObserver<CalcService.ImageIdentifier> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getProcessImageMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void checkImageStatus(CalcService.ImageIdentifier request,
        io.grpc.stub.StreamObserver<CalcService.ImageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckImageStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void downloadProcessedImage(CalcService.ImageIdentifier request,
        io.grpc.stub.StreamObserver<CalcService.ImageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDownloadProcessedImageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ImageService.
   */
  public static final class ImageServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ImageServiceBlockingStub> {
    private ImageServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ImageServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public CalcService.ImageResponse checkImageStatus(CalcService.ImageIdentifier request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckImageStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<CalcService.ImageResponse> downloadProcessedImage(
        CalcService.ImageIdentifier request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDownloadProcessedImageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ImageService.
   */
  public static final class ImageServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ImageServiceFutureStub> {
    private ImageServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ImageServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<CalcService.ImageResponse> checkImageStatus(
        CalcService.ImageIdentifier request) {
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
          serviceImpl.checkImageStatus((CalcService.ImageIdentifier) request,
              (io.grpc.stub.StreamObserver<CalcService.ImageResponse>) responseObserver);
          break;
        case METHODID_DOWNLOAD_PROCESSED_IMAGE:
          serviceImpl.downloadProcessedImage((CalcService.ImageIdentifier) request,
              (io.grpc.stub.StreamObserver<CalcService.ImageResponse>) responseObserver);
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
              (io.grpc.stub.StreamObserver<CalcService.ImageIdentifier>) responseObserver);
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
              CalcService.ImageRequest,
              CalcService.ImageIdentifier>(
                service, METHODID_PROCESS_IMAGE)))
        .addMethod(
          getCheckImageStatusMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              CalcService.ImageIdentifier,
              CalcService.ImageResponse>(
                service, METHODID_CHECK_IMAGE_STATUS)))
        .addMethod(
          getDownloadProcessedImageMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              CalcService.ImageIdentifier,
              CalcService.ImageResponse>(
                service, METHODID_DOWNLOAD_PROCESSED_IMAGE)))
        .build();
  }

  private static abstract class ImageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ImageServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return CalcService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ImageService");
    }
  }

  private static final class ImageServiceFileDescriptorSupplier
      extends ImageServiceBaseDescriptorSupplier {
    ImageServiceFileDescriptorSupplier() {}
  }

  private static final class ImageServiceMethodDescriptorSupplier
      extends ImageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ImageServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ImageServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ImageServiceFileDescriptorSupplier())
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
