package servertoserverservice;

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
    comments = "Source: ServiceServerToServer.proto")
public final class ServerToServerServiceGrpc {

  private ServerToServerServiceGrpc() {}

  public static final String SERVICE_NAME = "servertoserverservice.ServerToServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<servertoserverservice.Pair,
      servertoserverservice.Void> getWriteReadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "writeRead",
      requestType = servertoserverservice.Pair.class,
      responseType = servertoserverservice.Void.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<servertoserverservice.Pair,
      servertoserverservice.Void> getWriteReadMethod() {
    io.grpc.MethodDescriptor<servertoserverservice.Pair, servertoserverservice.Void> getWriteReadMethod;
    if ((getWriteReadMethod = ServerToServerServiceGrpc.getWriteReadMethod) == null) {
      synchronized (ServerToServerServiceGrpc.class) {
        if ((getWriteReadMethod = ServerToServerServiceGrpc.getWriteReadMethod) == null) {
          ServerToServerServiceGrpc.getWriteReadMethod = getWriteReadMethod =
              io.grpc.MethodDescriptor.<servertoserverservice.Pair, servertoserverservice.Void>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "writeRead"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servertoserverservice.Pair.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servertoserverservice.Void.getDefaultInstance()))
              .setSchemaDescriptor(new ServerToServerServiceMethodDescriptorSupplier("writeRead"))
              .build();
        }
      }
    }
    return getWriteReadMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerToServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerToServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerToServerServiceStub>() {
        @java.lang.Override
        public ServerToServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerToServerServiceStub(channel, callOptions);
        }
      };
    return ServerToServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerToServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerToServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerToServerServiceBlockingStub>() {
        @java.lang.Override
        public ServerToServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerToServerServiceBlockingStub(channel, callOptions);
        }
      };
    return ServerToServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServerToServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerToServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerToServerServiceFutureStub>() {
        @java.lang.Override
        public ServerToServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerToServerServiceFutureStub(channel, callOptions);
        }
      };
    return ServerToServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ServerToServerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<servertoserverservice.Pair> writeRead(
        io.grpc.stub.StreamObserver<servertoserverservice.Void> responseObserver) {
      return asyncUnimplementedStreamingCall(getWriteReadMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getWriteReadMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                servertoserverservice.Pair,
                servertoserverservice.Void>(
                  this, METHODID_WRITE_READ)))
          .build();
    }
  }

  /**
   */
  public static final class ServerToServerServiceStub extends io.grpc.stub.AbstractAsyncStub<ServerToServerServiceStub> {
    private ServerToServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerToServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerToServerServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<servertoserverservice.Pair> writeRead(
        io.grpc.stub.StreamObserver<servertoserverservice.Void> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getWriteReadMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class ServerToServerServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServerToServerServiceBlockingStub> {
    private ServerToServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerToServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerToServerServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class ServerToServerServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ServerToServerServiceFutureStub> {
    private ServerToServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerToServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerToServerServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_WRITE_READ = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServerToServerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServerToServerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_WRITE_READ:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.writeRead(
              (io.grpc.stub.StreamObserver<servertoserverservice.Void>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ServerToServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServerToServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return servertoserverservice.ServiceServerToServer.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ServerToServerService");
    }
  }

  private static final class ServerToServerServiceFileDescriptorSupplier
      extends ServerToServerServiceBaseDescriptorSupplier {
    ServerToServerServiceFileDescriptorSupplier() {}
  }

  private static final class ServerToServerServiceMethodDescriptorSupplier
      extends ServerToServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServerToServerServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ServerToServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerToServerServiceFileDescriptorSupplier())
              .addMethod(getWriteReadMethod())
              .build();
        }
      }
    }
    return result;
  }
}
