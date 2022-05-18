# Email-generator

<h4>1. Gmail SMTP server settings</h4>

Before launching the application, you need to configure the mail service. <br>
In the file application.properties, you must specify your username and password for gmail. <br>
For correct operation, it is necessary to generate a password for an external application. <br>
(you can also use a regular mail password, but in order to avoid unwanted problems when using the application, I recommend creating a separate password) <br>

<li>step 1: https://support.google.com/mail/answer/7126229 </li>
<li>step 2: enable two-factor authentication at login </li>
<li>step 3: https://support.google.com/accounts/answer/185833 </li>
<br>
After creating a password, add data to the file

<pre>
#Gmail SMTP server
spring.mail.host=smtp.gmail.com
spring.mail.port=465
spring.mail.username=  <b>here mail in the format sergey@gmail.com</b>
spring.mail.password= <b>here password from mail or generated password for external application</b>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.protocol=smtps
mail.debug=true
</pre>


<h4>2. Working with the database</h4>
<br>
The project uses the H2 base, which is in the main java package - all settings for its operation are already in pom.xml
If necessary, change the database:
<li>add the corresponding dependency to pom.xml </li>
<li>make changes to the file application.properties</li>
<br>
<pre>
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
</pre>

<br>
<h4>3. Changing the message text</h4>
<br>
To change the message text, you need to make changes to the void send() and void sendAll() methods in the MailSender class - the second one, currently not used by the application

<pre>
    public void send(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(<b>"Вітання!");</b>
        mailMessage.setText(<b>"Ім'я користувача:" + user.getUsername() + "\n"
                            + "Дата та час створення:" + user.getCreatedOn());</b>
        mailSender.send(mailMessage);

        logController.saveLog(user.getId(), TypeMail.rest);
    }
</pre>
