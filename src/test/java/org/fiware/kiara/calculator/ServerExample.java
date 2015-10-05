/* KIARA - Middleware for efficient and QoS/Security-aware invocation of services and exchange of messages
 *
 * Copyright (C) 2014 Proyectos y Sistemas de Mantenimiento S.L. (eProsima)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file ServerExample.java
 * This file contains the main server example entry point.
 *
 * This file was generated by using the tool Kiaragen.
 *
 */
package org.fiware.kiara.calculator;

import org.fiware.kiara.Context;
import org.fiware.kiara.Kiara;
import org.fiware.kiara.dynamic.DynamicValueBuilder;
import org.fiware.kiara.dynamic.data.DynamicPrimitive;
import org.fiware.kiara.dynamic.services.DynamicFunctionHandler;
import org.fiware.kiara.dynamic.services.DynamicFunctionRequest;
import org.fiware.kiara.dynamic.services.DynamicFunctionResponse;
import org.fiware.kiara.server.Server;
import org.fiware.kiara.server.Service;
import org.fiware.kiara.typecode.TypeDescriptorBuilder;
import org.fiware.kiara.typecode.TypeKind;
import org.fiware.kiara.typecode.data.PrimitiveTypeDescriptor;
import org.fiware.kiara.typecode.services.FunctionTypeDescriptor;

/**
 * Class that acts as the main server entry point.
 *
 * @author Kiaragen tool.
 *
 */
public class ServerExample {

    public static void main(String[] args) throws Exception {
        int port;
        String protocol;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        if (args.length > 1) {
            protocol = args[1];
        } else {
            protocol = "cdr";
        }

        Context context = Kiara.createContext();
        Server server = context.createServer();

        server.enableNegotiationService("0.0.0.0", port, "/service");

        CalculatorServant Calculator_impl = new CalculatorServantExample();

        Service service = context.createService();

        //service.register(Calculator_impl);


        service.loadServiceIDLFromString(IDLText.contents);

        final TypeDescriptorBuilder tdbuilder = Kiara.getTypeDescriptorBuilder();
        final DynamicValueBuilder builder = Kiara.getDynamicValueBuilder();
        final PrimitiveTypeDescriptor intTy = tdbuilder.createPrimitiveType(TypeKind.INT_32_TYPE);

        DynamicFunctionHandler handler = new DynamicFunctionHandler() {

            @Override
            public void process(DynamicFunctionRequest request, DynamicFunctionResponse response) {
                int a = (Integer)((DynamicPrimitive)request.getParameterAt(0)).get();
                int b = (Integer)((DynamicPrimitive)request.getParameterAt(1)).get();

                final DynamicPrimitive intVal = (DynamicPrimitive)builder.createData(intTy);

                if ("add".equals(((FunctionTypeDescriptor)request.getTypeDescriptor()).getName())) {
                    System.out.println("add result = " + (a+b));

                    intVal.set(a+b);
                } else if ("subtract".equals(((FunctionTypeDescriptor)request.getTypeDescriptor()).getName())) {
                    System.out.println("subtract result = " + (a-b));

                    intVal.set(a-b);
                }
                response.setReturnValue(intVal);
            }
        };

        service.register("Calculator.add", handler);
        service.register("Calculator.subtract", handler);

        //Add service waiting on TCP with CDR serialization
        server.addService(service, "tcp://0.0.0.0:9090", protocol);

        server.run();

    }

}
