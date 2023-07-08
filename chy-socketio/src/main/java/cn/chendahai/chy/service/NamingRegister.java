package cn.chendahai.chy.service;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;

public class NamingRegister {


    public static void main(String[] args) {

        NamingRegister namingRegister = new NamingRegister();
        namingRegister.registerInstance();

//        System.out.println(namingRegister.getIp());
        System.out.println("result:" + namingRegister.findFirstNonLoopbackAddress().getHostAddress());

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerInstance() {
        Properties properties = new Properties();

        // nacos地址
        properties.setProperty("serverAddr", "192.16.8.201:8848");
//        properties.setProperty("namespace", System.getProperty("namespace"));


        try {
            NamingService naming = NamingFactory.createNamingService(properties);

//            naming.registerInstance("chy-websocket", "192.16.21.102", 8099, "TEST1");

            String ip = findFirstNonLoopbackAddress().getHostAddress();
            // 注册名称为socketio
            naming.registerInstance("chy-socketio", ip, 2468, "socketio");
//            naming.registerInstance("chy-socketio", "192.16.21.102", 2468, "socketio");
            System.out.println(naming.getAllInstances("chy-websocket"));

//            naming.registerInstance("chy-websocket", "2.2.2.2", 9999, "DEFAULT");
//            System.out.println(naming.getAllInstances("chy-websocket"));


//        naming.deregisterInstance("chy-websocket", "2.2.2.2", 9999, "DEFAULT");

//        System.out.println(naming.getAllInstances("chy-websocket"));

//            naming.subscribe("chy-websocket", new EventListener() {
//                @Override
//                public void onEvent(Event event) {
//                    System.out.println(((NamingEvent) event).getServiceName());
//                    System.out.println(((NamingEvent) event).getInstances());
//                }
//            });

        } catch (NacosException e) {
            throw new RuntimeException(e);
        }


    }

    private String getIp() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;
        try {
            int lowest = Integer.MAX_VALUE;
            for (Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces(); nics
                    .hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
//                    this.log.trace("Testing interface: " + ifc.getDisplayName());
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else if (result != null) {
                        continue;
                    }

                    // @formatter:off
//                    if (!ignoreInterface(ifc.getDisplayName())) {
                        for (Enumeration<InetAddress> addrs = ifc
                                .getInetAddresses(); addrs.hasMoreElements();) {
                            InetAddress address = addrs.nextElement();

                            System.out.println(address.getHostAddress() + ":"+address.isLoopbackAddress());
                            if (address instanceof Inet4Address
                                    && !address.isLoopbackAddress()){
//                                    && isPreferredAddress(address)) {
//                                this.log.trace("Found non-loopback interface: "
//                                        + ifc.getDisplayName());
                                result = address;
                            }
                        }
//                    }
                    // @formatter:on
                }
            }
        } catch (IOException ex) {
//            this.log.error("Cannot get first non-loopback address", ex);
        }

        if (result != null) {
            return result;
        }

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
//            this.log.warn("Unable to retrieve localhost");
        }

        return null;
    }

}
