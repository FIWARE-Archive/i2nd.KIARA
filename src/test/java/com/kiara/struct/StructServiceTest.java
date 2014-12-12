package com.kiara.struct;

import com.google.common.util.concurrent.SettableFuture;
import com.kiara.Context;
import com.kiara.client.Connection;
import com.kiara.serialization.Serializer;
import com.kiara.server.Server;
import com.kiara.server.Service;
import com.kiara.test.TestSetup;
import com.kiara.test.TestUtils;
import com.kiara.test.TypeFactory;
import com.kiara.transport.ServerTransport;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class StructServiceTest {

    static {
        //System.setProperty("java.util.logging.config.file", "/home/rubinste/.kiara/logging.properties");
    }

    public static class StructServiceServantImpl extends StructServiceServant {

        public static PrimitiveTypesStruct createPrimitiveTypesStruct() {
            PrimitiveTypesStruct s = new PrimitiveTypesStruct();
            s.setmyChar('a');
            s.setmyByte((byte)42);
            s.setmyUShort((short)65534);
            s.setmyShort((short)-40001);
            s.setmyBoolean(true);
            s.setmyUInt(80000);
            s.setmyInt(-80000);
            s.setmyULong(90000);
            s.setmyLong(-90000);
            s.setmyString("Test");
            s.setmyFloat((float) -2.0);
            s.setmyDouble(-100000.0);
            s.setmyBoolean(true);
            s.setmyString("Test123456789");
            s.setmyString5("12345");
            return s;
        }

        @Override
        public PrimitiveTypesStruct sendReceivePrimitives(PrimitiveTypesStruct value) {
            return value;
        }

        @Override
        public OuterStruct sendReceiveStruct(OuterStruct value) {
            return value;
        }
    }

    public static class StructServiceSetup extends TestSetup<StructServiceClient> {

        private final ExecutorService serverDispatchingExecutor;

        public StructServiceSetup(int port, String transport, String protocol, String configPath, TypeFactory<ExecutorService> serverDispatchingExecutorFactory) {
            super(port, transport, protocol, configPath);
            this.serverDispatchingExecutor = serverDispatchingExecutorFactory != null ? serverDispatchingExecutorFactory.create() : null;
            System.out.printf("Testing port=%d transport=%s protocol=%s configPath=%s serverDispatchingExecutor=%s%n", port, transport, protocol, configPath, serverDispatchingExecutor);
        }

        public ExecutorService getServerDispatchingExecutor() {
            return serverDispatchingExecutor;
        }

        @Override
        protected Server createServer(Context context, int port, String transport, String protocol, String configPath) throws Exception {
            Service service = context.createService();

            System.out.printf("Register server functions ....%n");

            StructServiceServant impl = new StructServiceServantImpl();
            service.register(impl);

            System.out.printf("Starting server...%n");

            Server server = context.createServer();
            //server.addService(service, makeServerTransportUri(transport, port), protocol);

            ServerTransport serverTransport = context.createServerTransport(makeServerTransportUri(transport, port));
            Serializer serializer = context.createSerializer(protocol);

            serverTransport.setDispatchingExecutor(this.serverDispatchingExecutor);

            server.addService(service, serverTransport, serializer);

            return server;
        }

        @Override
        protected StructServiceClient createClient(Connection connection) throws Exception {
            return connection.getServiceProxy(StructServiceClient.class);
        }

        @Override
        protected String makeServerTransportUri(String transport, int port) {
            if ("tcp".equals(transport)) {
                return "tcp://0.0.0.0:" + port;
            }
            throw new IllegalArgumentException("Unknown transport " + transport);
        }

        @Override
        protected String makeClientTransportUri(String transport, int port, String protocol) {
            if ("tcp".equals(transport)) {
                return "tcp://0.0.0.0:" + port + "/?serialization=" + protocol;
            }
            throw new IllegalArgumentException("Unknown transport " + transport);
        }

        @Override
        public void shutdown() throws Exception {
            super.shutdown();
            if (serverDispatchingExecutor != null) {
                serverDispatchingExecutor.shutdown();
                serverDispatchingExecutor.awaitTermination(10, TimeUnit.MINUTES);
            }
        }

    }

    private final StructServiceSetup structServiceSetup;
    private StructServiceClient structService = null;
    private ExecutorService executor = null;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        if (structServiceSetup.getServerDispatchingExecutor() != null) {
            Assert.assertFalse(structServiceSetup.getServerDispatchingExecutor().isShutdown());
        }
        structService = structServiceSetup.start(100);
        Assert.assertNotNull(structService);
        executor = Executors.newCachedThreadPool();
    }

    @After
    public void tearDown() throws Exception {
        structServiceSetup.shutdown();
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
    }

    @Parameterized.Parameters
    public static Collection configs() {
        return TestUtils.createDefaultTestConfig();
    }

    public StructServiceTest(String transport, String protocol, TypeFactory<ExecutorService> serverExecutorFactory) {
        structServiceSetup = new StructServiceSetup(9090, transport, protocol, "", serverExecutorFactory);
    }

    @Test
    public void testStructTypesSync() throws Exception {
        PrimitiveTypesStruct value = StructServiceServantImpl.createPrimitiveTypesStruct();
        for (int i = 0; i < 10; i++) {
            value.setmyInt(i);
            PrimitiveTypesStruct result = structService.sendReceivePrimitives(value);

            Assert.assertNotNull(result);
            Assert.assertEquals(value, result);
        }
    }

    @Test
    public void testStructTypesParallel() throws Exception {
        Assert.assertFalse(executor.isShutdown());
        // Synchronous parallel test
        Future<PrimitiveTypesStruct>[] result = new Future[100];

        // Addition
        for (int i = 0; i < result.length; i++) {
            final int arg = i;
            result[arg] = executor.submit(new Callable<PrimitiveTypesStruct>() {

                @Override
                public PrimitiveTypesStruct call() throws Exception {
                    PrimitiveTypesStruct value = StructServiceServantImpl.createPrimitiveTypesStruct();
                    value.setmyInt(arg);
                    PrimitiveTypesStruct s = structService.sendReceivePrimitives(value);
                    Assert.assertNotNull(s);
                    return s;
                }
            });
        }

        for (int i = 0; i < result.length; i++) {
            assertEquals(i, result[i].get().getmyInt());
            PrimitiveTypesStruct value = StructServiceServantImpl.createPrimitiveTypesStruct();
            value.setmyInt(i);
            assertEquals(value, result[i].get());
        }
    }

    @Test
    public void testStructTypesAsync() throws Exception {
        Assert.assertFalse(executor.isShutdown());
        Future<PrimitiveTypesStruct>[] result = new Future[100];

        for (int i = 0; i < result.length; i++) {
            final SettableFuture<PrimitiveTypesStruct> resultValue = SettableFuture.create();
            final int arg = i;
            PrimitiveTypesStruct value = StructServiceServantImpl.createPrimitiveTypesStruct();
            value.setmyInt(arg);
            structService.sendReceivePrimitives(value, new StructServiceAsync.sendReceivePrimitives_AsyncCallback() {

                @Override
                public void onSuccess(PrimitiveTypesStruct result) {
                    resultValue.set(result);
                }

                @Override
                public void onFailure(Throwable caught) {
                    resultValue.setException(caught);
                }

            });
            result[arg] = resultValue;
        }

        for (int i = 0; i < result.length; i++) {
            Assert.assertNotNull(result[i].get());
            Assert.assertEquals(i, result[i].get().getmyInt());
        }
    }

}
