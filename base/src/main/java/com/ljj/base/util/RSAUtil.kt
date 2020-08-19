package com.common.lib.kit.util

import android.util.Base64
import android.util.Log
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * RSA加解密
 */

object RSAUtil {
    private const val TAG = "RSA"

    //加密算法
    private const val RSA = "RSA"

    //加密方式
    private const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"

    //使用公钥加密
    fun encryptByPublicKey(data: String, publicKey: String): String? {
        try {
            //获取公钥字节数组
            val publickBuf: ByteArray = Base64.decode(publicKey, Base64.DEFAULT)
            //获得公钥规范
            val keySpec = X509EncodedKeySpec(publickBuf)
            //获取钥匙工厂
            val keyFactory = KeyFactory.getInstance(RSA)
            //获取公钥
            val pk = keyFactory.generatePublic(keySpec)
            //加密方式
            val cliper = Cipher.getInstance(TRANSFORMATION)
            cliper.init(Cipher.ENCRYPT_MODE, pk)
            return IBase64Utils.encode(cliper.doFinal(data.toByteArray()))
        } catch (e: Exception) {
            Log.e(TAG, "e:==>${e.message}")
            return null
        }
    }


    //使用私钥解密
    fun decryptByPrivateKey(encrypted: ByteArray, privateKey: ByteArray): ByteArray? {
        try {
            val privateBuf: ByteArray = Base64.decode(privateKey, Base64.DEFAULT)
            //获取私钥规范
            val keySpec = PKCS8EncodedKeySpec(privateBuf)
            //获取钥匙工厂
            val keyFactory = KeyFactory.getInstance(RSA, "BC")
            //获取私钥
            val pk = keyFactory.generatePrivate(keySpec)
            //加密方式
            val cliper = Cipher.getInstance(TRANSFORMATION)
            cliper.init(Cipher.ENCRYPT_MODE, pk)
            return cliper.doFinal(encrypted)
        } catch (e: Exception) {
            Log.e(TAG, "e:==>${e.message}")
            return null
        }
    }

}