package com.hr.springboot.oauth2.authserver.jose;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.crypto.SecretKey;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;

public final class JWKs {

	public static RSAKey generateRSAKey()
	{
		KeyPair keyPair = KeyGenUtil.generateRSAKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return new RSAKey
				.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
	}
	
	public static ECKey generateECKey()
	{
		KeyPair keyPair = KeyGenUtil.generateECKey();
		ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
		ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
		Curve curve = Curve.forECParameterSpec(ecPublicKey.getParams());
		return new ECKey.Builder(curve, ecPublicKey)
					.privateKey(ecPrivateKey)
					.keyID(UUID.randomUUID().toString())
					.build();
	}
	
	public OctetSequenceKey generateSecretKey()
	{
		SecretKey secretKey = KeyGenUtil.generateSecretKey();
		return new OctetSequenceKey.Builder(secretKey)
					.keyID(UUID.randomUUID().toString())
					.build();
	}
}
