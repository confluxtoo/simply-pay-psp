package org.apache.fineract.infrastructure.upi.util;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.fineract.infrastructure.upi.xsd.HbtMsgType;
import org.apache.fineract.infrastructure.upi.xsd.HeadType;
import org.apache.fineract.infrastructure.upi.xsd.ObjectFactory;
import org.apache.fineract.infrastructure.upi.xsd.PayConstant;
import org.apache.fineract.infrastructure.upi.xsd.PayTrans;
import org.apache.fineract.infrastructure.upi.xsd.ReqHbt;
import org.apache.fineract.infrastructure.upi.xsd.ReqHbt.HbtMsg;

public class UtilApis {

	public final static String URL = "https://103.14.161.149:443/upi/" ;
	
	
	public static String marshallRequest(Calendar cal) {
		try {
			SimpleDateFormat format1 = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ssXXX");
			ObjectFactory factory = new ObjectFactory();

			HeadType headType = factory.createHeadType();
			headType.setVer("1.0");
			headType.setOrgId("700037");
			headType.setTs(format1.format(cal.getTime()));
			headType.setMsgId(new Long(cal.getTimeInMillis()).toString());

			PayTrans payTrans = factory.createPayTrans();
			payTrans.setId(new Long(cal.getTimeInMillis()).toString());
			payTrans.setNote("Heart beat");
			payTrans.setRefId(new Long(cal.getTimeInMillis()).toString());
			payTrans.setRefUrl("http://www.npci.org.in/");
			payTrans.setTs(format1.format(cal.getTime()));
			payTrans.setType(PayConstant.HBT);

			HbtMsg hbtMsg = factory.createReqHbtHbtMsg();
			hbtMsg.setType(HbtMsgType.ALIVE);
			hbtMsg.setValue("NA");

			ReqHbt reqHbt = factory.createReqHbt();
			reqHbt.setHead(headType);
			reqHbt.setTxn(payTrans);
			reqHbt.setHbtMsg(hbtMsg);
			java.io.StringWriter sw = new StringWriter();
			JAXBContext context = JAXBContext
					.newInstance("com.finflux.upi.xsd");

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(reqHbt, sw);
			String requestString = sw.toString().trim();
			requestString = requestString
					.replace(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
							"");
			requestString = requestString.replace("\r\n", "").replace("\n", "");

			System.out.println("Request String:" + requestString);
			return requestString;
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return null;
	}
}
