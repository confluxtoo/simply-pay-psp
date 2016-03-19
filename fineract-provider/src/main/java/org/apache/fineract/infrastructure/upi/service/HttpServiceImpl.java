package org.apache.fineract.infrastructure.upi.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class HttpServiceImpl implements HttpService{

	@Override
	public String setRequest(String data, String url) {

		System.setProperty("jsse.enableSNIExtension", "false");	
			
			System.out.println("Reaching inside SendXML");
			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("SSL");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				sslContext.init(null, new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
			} catch (KeyManagementException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		HttpClient client = HttpClientBuilder.create().setSSLContext(sslContext).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/xml");
		post.setHeader("Accept", "*/*");
		String result=null;
		try {
			HttpEntity entity = new StringEntity(data);
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			
			result = EntityUtils.toString(response.getEntity());
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static class DefaultTrustManager implements X509TrustManager {


        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}
    }
}
