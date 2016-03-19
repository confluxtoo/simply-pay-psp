package org.apache.fineract.infrastructure.upi.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Service
public class SignatureServiceImpl implements SignatureService {

	private PrivateKey privateKey = null;
	private PublicKey publicKey = null;

	@Override
	@PostConstruct
	public void init() {
		try {
			// Read database and get the files. As of now hardcoded
			String PKCS12File = "/home/ubuntu/rahul/upi.p12";
			String PKCS12Password = "finflux";
			String CertFile = "/home/ubuntu/rahul/simplyPay-ssl.cer";

			/* getting data for keystore */
			File signerFile = new File(PKCS12File);
			FileInputStream is = new FileInputStream(signerFile);
			KeyStore keystore = KeyStore.getInstance("PKCS12");

			/* Information for certificate to be generated */
			String password = PKCS12Password;
			String alias = "1";

			/* getting the key */
			keystore.load(((InputStream) is), password.toCharArray());
			PrivateKey key = (PrivateKey) keystore.getKey(alias,
					password.toCharArray());

			privateKey = key;

			/* Get certificate of public key */
			java.security.cert.Certificate cert = getCertificate(CertFile);

			// System.out.println("Not
			// after:"+((X509Certificate)cert).getNotAfter());

			publicKey = cert.getPublicKey();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Certificate getCertificate(String file)
			throws CertificateException, FileNotFoundException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream is = new FileInputStream(new File(file));
		InputStream caInput = new BufferedInputStream(is);
		Certificate ca;
		try {
			ca = cf.generateCertificate(caInput);
			return ca;
		} finally {
			try {
				caInput.close();
			} catch (IOException e) {
			}
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public String generateXMLDigitalSignature(String filePath) {

		// Get the XML Document object
		Document doc = getXmlDocument(filePath);
		System.out.println("Got the document out of the xml");
		XMLSignatureFactory xmlSigFactory = XMLSignatureFactory
				.getInstance("DOM");
		DOMSignContext domSignCtx = new DOMSignContext(privateKey,
				doc.getDocumentElement());
		Reference ref = null;
		SignedInfo signedInfo = null;
		try {
			ref = xmlSigFactory
					.newReference("", xmlSigFactory.newDigestMethod(
							DigestMethod.SHA256, null), Collections
							.singletonList(xmlSigFactory.newTransform(
									Transform.ENVELOPED,
									(TransformParameterSpec) null)), null, null);

			System.out.println("xmlSigFactory.getProvider():"
					+ xmlSigFactory.getProvider());
			signedInfo = xmlSigFactory.newSignedInfo(xmlSigFactory
					.newCanonicalizationMethod(
							CanonicalizationMethod.INCLUSIVE,
							(C14NMethodParameterSpec) null), xmlSigFactory
					.newSignatureMethod(SignatureMethod.RSA_SHA1,
							(SignatureMethodParameterSpec) null), Collections
					.singletonList(ref));

		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (InvalidAlgorithmParameterException ex) {
			ex.printStackTrace();
		}
		// Pass the Public Key File Path
		KeyInfo keyInfo = getKeyInfo(xmlSigFactory);
		// Create a new XML Signature

		XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo,
				keyInfo);

		try {
			// Sign the document
			xmlSignature.sign(domSignCtx);
		} catch (MarshalException ex) {
			ex.printStackTrace();
		} catch (XMLSignatureException ex) {
			ex.printStackTrace();
		}
		// Store the digitally signed document inta a location
		String signedXML = storeSignedDoc(doc);
		return signedXML;
	}

	private Document getXmlDocument(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			doc = dbf.newDocumentBuilder().parse(
					new ByteArrayInputStream(xml.getBytes()));
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	private KeyInfo getKeyInfo(XMLSignatureFactory xmlSigFactory) {
		KeyInfo keyInfo = null;
		KeyValue keyValue = null;
		KeyInfoFactory keyInfoFact = xmlSigFactory.getKeyInfoFactory();

		try {
			keyValue = keyInfoFact.newKeyValue(publicKey);
		} catch (KeyException ex) {
			ex.printStackTrace();
		}
		keyInfo = keyInfoFact.newKeyInfo(Collections.singletonList(keyValue));
		return keyInfo;
	}

	private String storeSignedDoc(Document doc) {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer trans = null;
		try {
			trans = transFactory.newTransformer();
		} catch (TransformerConfigurationException ex) {
			ex.printStackTrace();
		}
		try {
			StringWriter writer = new StringWriter();
			StreamResult streamRes = new StreamResult(writer);
			trans.transform(new DOMSource(doc), streamRes);
			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
		}
		return null;
		// System.out.println("XML file with attached digital signature
		// generated successfully ...");
	}
}
