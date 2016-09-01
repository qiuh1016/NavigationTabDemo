package com.cetcme.zytyumin.jecInfoHttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

public class GetWebDataWithHttpGet {

	public GetWebDataWithHttpGet() {
		super();
		// TODO Auto-generated constructor stub
	}

	boolean isFirstTimeout = true;

	public String executeGet(String url) {

		BufferedReader in = null;

		String content = null;
		try {
			// 获取HttpClient
			HttpClient client = new DefaultHttpClient();

			// 请求超时
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					20000);
			// 获取HTTPGet
			HttpGet request = new HttpGet();

			request.setURI(new URI(url));

			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent(), "UTF-8"));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} catch (SocketTimeoutException e) {

			e.printStackTrace();
			content = "TIME_OUT";

		} catch (org.apache.http.conn.ConnectTimeoutException e) {
			// TODO: handle exception

			e.printStackTrace();
			content = "TIME_OUT";
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			// TODO: handle exception

			e.printStackTrace();
			content = "TIME_OUT";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}

}
