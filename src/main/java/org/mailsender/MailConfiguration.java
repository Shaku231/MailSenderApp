package org.mailsender;

import java.util.Properties;

public class MailConfiguration {

    public static Properties getConfiguration(){
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", "smtp.poczta.onet.pl");
        prop.put("mail.smtp.auth", "true");
        //prop.put("mail.debug","true");

        return prop;
    }
}