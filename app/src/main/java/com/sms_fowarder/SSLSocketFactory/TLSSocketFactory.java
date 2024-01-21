package com.sms_fowarder.SSLSocketFactory;

import android.annotation.SuppressLint;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class TLSSocketFactory extends SSLSocketFactory {

    private final SSLSocketFactory factory;

    @SuppressLint({"CustomX509TrustManager", "TrustAllX509TrustManager"})
    public TLSSocketFactory(boolean ignoreSsl) throws KeyManagementException, NoSuchAlgorithmException {
        if (ignoreSsl) {
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) { }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) { }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
            };

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, trustAllCerts, null);

            factory = context.getSocketFactory();
        } else {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            factory = context.getSocketFactory();
        }
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return factory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return factory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket() throws IOException {
        return enableTLSOnSocket(factory.createSocket());
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return enableTLSOnSocket(factory.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return enableTLSOnSocket(factory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return enableTLSOnSocket(factory.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableTLSOnSocket(factory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return enableTLSOnSocket(factory.createSocket(address, port, localAddress, localPort));
    }

    private Socket enableTLSOnSocket(Socket socket) {
        if ((socket instanceof SSLSocket)) {
            String[] supportedProtocols = ((SSLSocket) socket).getSupportedProtocols();
            ((SSLSocket) socket).setEnabledProtocols(supportedProtocols);
        }

        return socket;
    }
}