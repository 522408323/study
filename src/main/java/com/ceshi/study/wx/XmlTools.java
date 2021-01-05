//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ceshi.study.wx;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class XmlTools {
    private static Provider prvd = null;
    private static SSLSocketFactory sslFactory;

    public XmlTools() {
    }

    private static URLConnection createRequest(String strUrl, String strMethod) throws Exception {
        URL url = new URL(strUrl);
        URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
            httpsConn.setRequestMethod(strMethod);
            httpsConn.setSSLSocketFactory(getSSLSF());
            httpsConn.setHostnameVerifier(hv);
        } else if (conn instanceof HttpURLConnection) {
            HttpURLConnection httpConn = (HttpURLConnection)conn;
            httpConn.setRequestMethod(strMethod);
        }
        return conn;
    }

    private static void close(InputStream c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception var2) {
        }

    }

    private static void close(OutputStream c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception var2) {
        }

    }

    public static synchronized SSLSocketFactory getSSLSF() throws Exception {
        if (sslFactory != null) {
            return sslFactory;
        } else {
            SSLContext sc =SSLContext.getInstance("TLS");
            sc.init(null, new X509TrustManager[] { X509 }, null);
            sslFactory = sc.getSocketFactory();
            return sslFactory;
        }
    }

    public static void initProvider(Provider p) {
        prvd = p;
    }


    public static String sendXml(String url, String xml) throws Exception {
        OutputStream reqStream = null;
        InputStream resStream = null;
        URLConnection request = null;
        String respText = null;

        try {
            byte[] postData = xml.getBytes("UTF-8");
            request = createRequest(url, "POST");
            request.setRequestProperty("Content-type", "text/xml");
            request.setRequestProperty("Content-length", String.valueOf(postData.length));
            request.setRequestProperty("Keep-alive", "false");
            reqStream = request.getOutputStream();
            reqStream.write(postData);
            reqStream.close();
            ByteArrayOutputStream ms = null;
            resStream = request.getInputStream();
            ms = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];

            int count;
            while((count = resStream.read(buf, 0, buf.length)) > 0) {
                ms.write(buf, 0, count);
            }

            resStream.close();
            respText = new String(ms.toByteArray(), "UTF-8");
            return respText;
        } catch (Exception var13) {
            throw var13;
        } finally {
            close(reqStream);
            close(resStream);
        }
    }

    private static class SSLHandler implements X509TrustManager, HostnameVerifier {
        private SSLHandler() {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    }

    private static X509TrustManager X509 = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    private static HostnameVerifier hv = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

    };
}
