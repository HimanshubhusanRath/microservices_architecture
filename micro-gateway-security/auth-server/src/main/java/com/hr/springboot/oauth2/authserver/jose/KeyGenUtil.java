package com.hr.springboot.oauth2.authserver.jose;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public final class KeyGenUtil {

	public static SecretKey generateSecretKey()
	{
		SecretKey secretKey;
		try
		{
			secretKey = KeyGenerator.getInstance("HmacSha256").generateKey();
		}
		catch(Exception e)
		{
			throw new IllegalStateException();
		}
		return secretKey;
	}

	public static KeyPair generateRSAKey()
	{
		KeyPair keyPair;
		try
		{
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(2048);
			keyPair = keyPairGen.generateKeyPair();
		}
		catch(Exception e)
		{
			throw new IllegalStateException();
		}
		return keyPair;
	}
	
	public static KeyPair generateECKey()
	{
		KeyPair keyPair;
		final EllipticCurve ellipticCurve = new EllipticCurve(
				new ECFieldFp(new BigInteger("13134232132132321312312312312354364880976753453254576787545643")),
				new BigInteger("6764564324366745231425356453454231313253564523124314"), 
				new BigInteger("44545432132312123123156745632143567543211"));
		
		final ECPoint ecPoint = new ECPoint(
				new BigInteger("12232324324234344234123218879809098789765754654756767"),
				new BigInteger("546321345675432134567543214567645321"));
		
		final ECParameterSpec ecParamSpec = new ECParameterSpec(
				ellipticCurve,
				ecPoint, 
				new BigInteger("456754321456785432142567686543211425675645321"),
				1);
		
		try
		{
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC");
			keyPairGen.initialize(ecParamSpec);
			keyPair = keyPairGen.generateKeyPair();
			
		}
		catch(Exception e)
		{
			throw new IllegalStateException();
		}
		return keyPair;
	}
	
	
}
