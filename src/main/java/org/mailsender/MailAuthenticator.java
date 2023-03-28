package org.mailsender;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {

    protected static final String username = "your@mail.com";
    private final String password = "password";
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(username, password);
    }
}