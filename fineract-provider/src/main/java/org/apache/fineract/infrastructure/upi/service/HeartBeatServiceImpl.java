package org.apache.fineract.infrastructure.upi.service;

import java.util.Calendar;

import org.apache.fineract.infrastructure.upi.util.UtilApis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartBeatServiceImpl implements HeartBeatService{

	private final SignatureService signatureService ;
	private final HttpService httpService ;
	
	@Autowired
	public HeartBeatServiceImpl(final SignatureService signatureService,
			final HttpService httpService) {
		this.signatureService = signatureService ;
		this.httpService = httpService ;
	}
	@Override
	public void sendHeartBeatRequest() {
		Calendar cal = Calendar.getInstance();
		String hbtRequest = UtilApis.marshallRequest(cal) ;
        System.out.println("Reaching here inside run after the request XML");
        try {
			String signedXml = this.signatureService.generateXMLDigitalSignature(hbtRequest);
			String hbtUrl = UtilApis.URL + "ReqHbt/1.0/urn:txnid:" + new Long(cal.getTimeInMillis()).toString();
			httpService.setRequest(signedXml, hbtUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
