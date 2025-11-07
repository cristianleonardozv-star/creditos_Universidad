// service/UtilService.java
package com.previos.solicitudes.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class UtilService {
  private final Random rnd = new SecureRandom();
  private static final String ALPHA_NUM = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public String randomCode(int len) {
    StringBuilder sb = new StringBuilder(len);
    for (int i=0;i<len;i++) sb.append(ALPHA_NUM.charAt(rnd.nextInt(ALPHA_NUM.length())));
    return sb.toString();
  }
}
