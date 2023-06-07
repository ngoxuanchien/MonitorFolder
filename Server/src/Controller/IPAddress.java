package Controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPAddress {
    public static String getIPAddress() {
        String ip = null;

        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            ip = ipAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ip;
    }
}
