package cn2223tf;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.1)",
    comments = "Source: CN2223TFService.proto")
public final class CN2223TFServiceGrpc {

  private CN2223TFServiceGrpc() {}

  public static final String SERVICE_NAME = "cn2223tf.CN2223TFService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cn2223tf.ImageUploadRequest,
      cn2223tf.IdentifierResponse> getImageSubmitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "imageSubmit",
      requestType = cn2223tf.ImageUploadRequest.class,
      responseType = cn2223tf.IdentifierResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<cn2223tf.ImageUploadRequest,
      cn2223tf.IdentifierResponse> getImageSubmitMethod() {
    io.grpc.MethodDescriptor<cn2223tf.ImageUploadRequest, cn2223tf.IdentifierResponse> getImageSubmitMethod;
    if ((getImageSubmitMethod = CN2223TFServiceGrpc.getImageSubmitMethod) == null) {
      synchronized (CN2223TFServiceGrpc.class) {
        if ((getImageSubmitMethod = CN2223TFServiceGrpc.getImageSubmitMethod) == null) {
          CN2223TFServiceGrpc.getImageSubmitMethod = getImageSubmitMethod =
              io.grpc.MethodDescriptor.<cn2223tf.ImageUploadRequest, cn2223tf.IdentifierResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "imageSubmit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn2223tf.ImageUploadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn2223tf.IdentifierResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CN2223TFServiceMethodDescriptorSupplier("imageSubmit"))
              .build();
        }
      }
    }
    return getImageSubmitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cn2223tf.IdentifierRequest,
      cn2223tf.LandmarksResponse> getGetLandmarksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLandmarks",
      requestType = cn2223tf.IdentifierRequest.class,
      responseType = cn2223tf.LandmarksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cn2223tf.IdentifierRequest,
      cn2223tf.LandmarksResponse> getGetLandmarksMethod() {
    io.grpc.MethodDescriptor<cn2223tf.IdentifierRequest, cn2223tf.LandmarksResponse> getGetLandmarksMethod;
    if ((getGetLandmarksMethod = CN2223TFServiceGrpc.getGetLandmarksMethod) == null) {
      synchronized (CN2223TFServiceGrpc.class) {
        if ((getGetLandmarksMethod = CN2223TFServiceGrpc.getGetLandmarksMethod) == null) {
          CN2223TFServiceGrpc.getGetLandmarksMethod = getGetLandmarksMethod =
              io.grpc.MethodDescriptor.<cn2223tf.IdentifierRequest, cn2223tf.LandmarksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getLandmarks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn2223tf.IdentifierRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn2223tf.LandmarksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CN2223TFServiceMethodDescriptorSupplier("getLandmarks"))
              .build();
        }
      }
    }
    return getGetLandmarksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cn2223tf.IdentifierRequest,
      cn2223tf.StaticMapResponse> getGetStaticMapMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getStaticMap",
      requestType = cn2223tf.IdentifierRequest.class,
      responseType = cn2223tf.StaticMapResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<cn2223tf.IdentifierRequest,
      cn2223tf.StaticMapResponse> getGetStaticMapMethod() {
    io.grpc.MethodDescriptor<cn2223tf.IdentifierRequest, cn2223tf.StaticMapResponse> getGetStaticMapMethod;
    if ((getGetStaticMapMethod = CN2223TFServiceGrpc.getGetStaticMapMethod) == null) {
      synchronized (CN2223TFServiceGrpc.class) {
        if ((getGetStaticMapMethod = CN2223TFServiceGrpc.getGetStaticMapMethod) == null) {
          CN2223TFServiceGrpc.getGetStaticMapMethod = getGetStaticMapMethod =
              io.grpc.MethodDescriptor.<cn2223tf.IdentifierRequest, cn2223tf.StaticMapResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getStaticMap"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn2223tf.IdentifierRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn2223tf.StaticMapResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CN2223TFServiceMethodDescriptorSupplier("getStaticMap"))
              .build();
        }
      }
    }
    return getGetStaticMapMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cn2223tf.ConfidenceRequest,
      cn2223tf.FilteredImagesResponse> getGetImagesByConfidenceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getImagesByConfidence",
      requestType = cn2223tf.ConfidenceRequest.class,
      responseType = cn2223tf.FilteredImagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cn2223tf.ConfidenceRequest,
      cn2223tf.FilteredImagesResponse> getGetImagesByConfidenceMethod() {
    io.grpc.MethodDescriptor<cn2223tf.ConfidenceRequest, cn2223tf.FilteredImagesResponse> getGetImagesByConfidenceMethod;
    if ((getGetImagesByConfidenceMethod = CN2223TFServiceGrpc.getGetImagesByConfidenceMethod) == null) {
      synchronized (CN2223TFServiceGrpc.class) {
        if ((getGetImagesByConfidenceMethod = CN2223TFServiceGrpc.getGetImagesByConfidenceMethod) == null) {
          CN2223TFServiceGrpc.getGetImagesByConfidenceMethod = getGetImagesByConfidenceMethod =
              io.grpc.MethodDescriptor.<cn2223tf.ConfidenceRequest, cn2223tf.FilteredImagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getImagesByConfidence"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn2223tf.ConfidenceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn2223tf.FilteredImagesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CN2223TFServiceMethodDescriptorSupplier("getImagesByConfidence"))
              .build();
        }
      }
    }
    return getGetImagesByConfidenceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CN2223TFServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CN2223TFServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CN2223TFServiceStub>() {
        @java.lang.Override
        public CN2223TFServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CN2223TFServiceStub(channel, callOptions);
        }
      };
    return CN2223TFServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CN2223TFServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CN2223TFServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CN2223TFServiceBlockingStub>() {
        @java.lang.Override
        public CN2223TFServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CN2223TFServiceBlockingStub(channel, callOptions);
        }
      };
    return CN2223TFServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CN2223TFServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CN2223TFServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CN2223TFServiceFutureStub>() {
        @java.lang.Override
        public CN2223TFServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CN2223TFServiceFutureStub(channel, callOptions);
        }
      };
    return CN2223TFServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CN2223TFServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<cn2223tf.ImageUploadRequest> imageSubmit(
        io.grpc.stub.StreamObserver<cn2223tf.IdentifierResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getImageSubmitMethod(), responseObserver);
    }

    /**
     */
    public void getLandmarks(cn2223tf.IdentifierRequest request,
        io.grpc.stub.StreamObserver<cn2223tf.LandmarksResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetLandmarksMethod(), responseObserver);
    }

    /**
     */
    public void getStaticMap(cn2223tf.IdentifierRequest request,
        io.grpc.stub.StreamObserver<cn2223tf.StaticMapResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStaticMapMethod(), responseObserver);
    }

    /**
     */
    public void getImagesByConfidence(cn2223tf.ConfidenceRequest request,
        io.grpc.stub.StreamObserver<cn2223tf.FilteredImagesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetImagesByConfidenceMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getImageSubmitMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                cn2223tf.ImageUploadRequest,
                cn2223tf.IdentifierResponse>(
                  this, METHODID_IMAGE_SUBMIT)))
          .addMethod(
            getGetLandmarksMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                cn2223tf.IdentifierRequest,
                cn2223tf.LandmarksResponse>(
                  this, METHODID_GET_LANDMARKS)))
          .addMethod(
            getGetStaticMapMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                cn2223tf.IdentifierRequest,
                cn2223tf.StaticMapResponse>(
                  this, METHODID_GET_STATIC_MAP)))
          .addMethod(
            getGetImagesByConfidenceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                cn2223tf.ConfidenceRequest,
                cn2223tf.FilteredImagesResponse>(
                  this, METHODID_GET_IMAGES_BY_CONFIDENCE)))
          .build();
    }
  }

  /**
   */
  public static final class CN2223TFServiceStub extends io.grpc.stub.AbstractAsyncStub<CN2223TFServiceStub> {
    private CN2223TFServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CN2223TFServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CN2223TFServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<cn2223tf.ImageUploadRequest> imageSubmit(
        io.grpc.stub.StreamObserver<cn2223tf.IdentifierResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getImageSubmitMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void getLandmarks(cn2223tf.IdentifierRequest request,
        io.grpc.stub.StreamObserver<cn2223tf.LandmarksResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLandmarksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStaticMap(cn2223tf.IdentifierRequest request,
        io.grpc.stub.StreamObserver<cn2223tf.StaticMapResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetStaticMapMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getImagesByConfidence(cn2223tf.ConfidenceRequest request,
        io.grpc.stub.StreamObserver<cn2223tf.FilteredImagesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetImagesByConfidenceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CN2223TFServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CN2223TFServiceBlockingStub> {
    private CN2223TFServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CN2223TFServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CN2223TFServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public cn2223tf.LandmarksResponse getLandmarks(cn2223tf.IdentifierRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLandmarksMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<cn2223tf.StaticMapResponse> getStaticMap(
        cn2223tf.IdentifierRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetStaticMapMethod(), getCallOptions(), request);
    }

    /**
     */
    public cn2223tf.FilteredImagesResponse getImagesByConfidence(cn2223tf.ConfidenceRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetImagesByConfidenceMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CN2223TFServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CN2223TFServiceFutureStub> {
    private CN2223TFServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CN2223TFServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CN2223TFServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cn2223tf.LandmarksResponse> getLandmarks(
        cn2223tf.IdentifierRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLandmarksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cn2223tf.FilteredImagesResponse> getImagesByConfidence(
        cn2223tf.ConfidenceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetImagesByConfidenceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_LANDMARKS = 0;
  private static final int METHODID_GET_STATIC_MAP = 1;
  private static final int METHODID_GET_IMAGES_BY_CONFIDENCE = 2;
  private static final int METHODID_IMAGE_SUBMIT = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CN2223TFServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CN2223TFServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_LANDMARKS:
          serviceImpl.getLandmarks((cn2223tf.IdentifierRequest) request,
              (io.grpc.stub.StreamObserver<cn2223tf.LandmarksResponse>) responseObserver);
          break;
        case METHODID_GET_STATIC_MAP:
          serviceImpl.getStaticMap((cn2223tf.IdentifierRequest) request,
              (io.grpc.stub.StreamObserver<cn2223tf.StaticMapResponse>) responseObserver);
          break;
        case METHODID_GET_IMAGES_BY_CONFIDENCE:
          serviceImpl.getImagesByConfidence((cn2223tf.ConfidenceRequest) request,
              (io.grpc.stub.StreamObserver<cn2223tf.FilteredImagesResponse>) responseObserver);
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
        case METHODID_IMAGE_SUBMIT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.imageSubmit(
              (io.grpc.stub.StreamObserver<cn2223tf.IdentifierResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CN2223TFServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CN2223TFServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cn2223tf.CN2223TFServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CN2223TFService");
    }
  }

  private static final class CN2223TFServiceFileDescriptorSupplier
      extends CN2223TFServiceBaseDescriptorSupplier {
    CN2223TFServiceFileDescriptorSupplier() {}
  }

  private static final class CN2223TFServiceMethodDescriptorSupplier
      extends CN2223TFServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CN2223TFServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CN2223TFServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CN2223TFServiceFileDescriptorSupplier())
              .addMethod(getImageSubmitMethod())
              .addMethod(getGetLandmarksMethod())
              .addMethod(getGetStaticMapMethod())
              .addMethod(getGetImagesByConfidenceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
