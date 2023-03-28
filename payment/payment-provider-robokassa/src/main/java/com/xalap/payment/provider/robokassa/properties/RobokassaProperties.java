/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.provider.robokassa.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Usov Andrey
 * @since 2020-04-16
 */
@ConfigurationProperties(prefix = "xalap.payment.robokassa", ignoreUnknownFields = false)
public class RobokassaProperties {

    private String merchantLogin = null;
    private String signatureAlgorithm = "md5";
    private String password1 = null;
    private String password2 = null;
    private String testPassword1 = null;
    private String testPassword2 = null;

    public String getMerchantLogin() {
        if (merchantLogin == null) {
            throw new IllegalStateException("merchantLogin is null");
        }
        return merchantLogin;
    }

    public void setMerchantLogin(String merchantLogin) {
        this.merchantLogin = merchantLogin;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getPassword1() {
        if (password1 == null) {
            throw new IllegalStateException("password1 is null");
        }
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        if (password2 == null) {
            throw new IllegalStateException("password2 is null");
        }
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getTestPassword1() {
        if (testPassword1 == null) {
            throw new IllegalStateException("testPassword1 is null");
        }
        return testPassword1;
    }

    public void setTestPassword1(String testPassword1) {
        this.testPassword1 = testPassword1;
    }

    public String getTestPassword2() {
        if (testPassword2 == null) {
            throw new IllegalStateException("testPassword2 is null");
        }
        return testPassword2;
    }

    public void setTestPassword2(String testPassword2) {
        this.testPassword2 = testPassword2;
    }
}
