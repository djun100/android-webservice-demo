package com.cy;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.szy.webservice.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author 承影
 *
 */
public class MainActivity extends Activity {
	// 声明WS的命名空间，命名空间在WSDL的头部显示
	private static final String NAMESPACE = "";
	// 设置WS的路径
	private static String SERVICEURL = "";
	// 要调用的函数名称
	private static final String METHOD_NAME1 = "";
	public static SoapObject soapObject;

	Button btn1;
	TextView txt1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn1 = (Button) findViewById(R.id.btnSearch);
		txt1 = (TextView) findViewById(R.id.tvResult);
		btn1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// txt1.setText("结果为：" + GetUserWS(METHOD_NAME1,null));
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						GetUserWS(METHOD_NAME1, null);
					}
				}).start();
			}
		});

	}

	public String GetUserWS(String methodName, String[] parameterList) {
		// 创建SoapObject对象，并指定WebService的命名空间和调用的方法名
		SoapObject request = new SoapObject(NAMESPACE, methodName);
		// 调用的函数如果有参数，这里可以设置
		request.addProperty("param1", "");
		request.addProperty("param2", "");
		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.setOutputSoapObject(request);
		// 上边的一句等价于下边的这句 将SoapObject对象赋给envelope对象
		envelope.bodyOut = request;
		// 当前开发的是Java WS 这里需要不调用.net WS
		envelope.dotNet = false;
		/*
		 * 这里不要使用 AndroidHttpTransport ht = new AndroidHttpTransport(URL)；
		 * 这是一个要过期的类
		 * 创建HttpTransportSE对象。通过HttpTransportSE类的构造方法可以指定WebService的WSDL文档的URL
		 */
		HttpTransportSE ht = new HttpTransportSE(SERVICEURL);
		try {
			// 请求WS
			ht.call(null, envelope);
			if (envelope.getResponse() != null) {
				// 获得WS函数返回值信息
				System.out.println(envelope.getResponse().toString());
				return envelope.getResponse().toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
}