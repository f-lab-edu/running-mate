package com.runningmate.Jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;


public class CreateJasyptTest {

    @Test
    void creater() {
        StandardPBEStringEncryptor spe = new StandardPBEStringEncryptor();
        spe.setAlgorithm("PBEWithMD5AndDES");
        spe.setPassword("password1234!@#$");


        System.out.println("db url = " + spe.encrypt("jdbc:mysql://db-8h724.pub-cdb.ntruss.com:3306/runningmate/serverTimeZone=UTC&CharacterEncoding=UTF-8"));
        System.out.println("db username = " + spe.encrypt("wonjunsu"));
        System.out.println("db password = " + spe.encrypt("flab1234!@#$"));

    }
}
