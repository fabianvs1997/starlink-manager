package com.starlink.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Servicio de Cifrado/Descifrado AES-256-CBC
 * Cifra datos sensibles antes de guardar en DB
 * Descifra datos al cargar desde DB
 */
@Service
public class CryptoService {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";
    private static final int IV_SIZE = 16;

    @Value("${app.encryption.key:MySecretKey12345MySecretKey12345}")
    private String secretKeyString;

    /**
     * Cifrar texto plano
     * @param plainText Texto a cifrar
     * @return Texto cifrado en Base64 (IV + datos cifrados)
     */
    public String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            return plainText;
        }

        try {
            // Generar IV aleatorio de 16 bytes
            byte[] iv = new byte[IV_SIZE];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Crear clave secreta desde string (32 bytes = 256 bits)
            SecretKey secretKey = new SecretKeySpec(
                    secretKeyString.getBytes(StandardCharsets.UTF_8),
                    KEY_ALGORITHM
            );

            // Configurar cifrador
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            // Cifrar datos
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // Concatenar IV + datos cifrados
            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            // Codificar en Base64 para almacenamiento
            return Base64.getEncoder().encodeToString(combined);

        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar datos: " + e.getMessage(), e);
        }
    }

    /**
     * Descifrar texto cifrado
     * @param encryptedText Texto cifrado en Base64
     * @return Texto plano descifrado
     */
    public String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return encryptedText;
        }

        try {
            // Decodificar Base64
            byte[] combined = Base64.getDecoder().decode(encryptedText);

            // Extraer IV (primeros 16 bytes)
            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(combined, 0, iv, 0, IV_SIZE);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Extraer datos cifrados (resto de bytes)
            byte[] encrypted = new byte[combined.length - IV_SIZE];
            System.arraycopy(combined, IV_SIZE, encrypted, 0, encrypted.length);

            // Crear clave secreta
            SecretKey secretKey = new SecretKeySpec(
                    secretKeyString.getBytes(StandardCharsets.UTF_8),
                    KEY_ALGORITHM
            );

            // Configurar descifrador
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            // Descifrar datos
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar datos: " + e.getMessage(), e);
        }
    }

    /**
     * Verificar si un texto está cifrado
     * @param text Texto a verificar
     * @return true si parece estar cifrado (Base64 válido)
     */
    public boolean isEncrypted(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        try {
            Base64.getDecoder().decode(text);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
