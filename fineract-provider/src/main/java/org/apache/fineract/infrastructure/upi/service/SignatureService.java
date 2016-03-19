package org.apache.fineract.infrastructure.upi.service;

public interface SignatureService {

	public void init() ;
	
	public String generateXMLDigitalSignature(String filePath) ;
}
