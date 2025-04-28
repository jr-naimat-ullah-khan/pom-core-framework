package utils;

import java.io.File;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailUtils {

	private static final String SENDER_EMAIL = Config.getConfigValue("emailUtils.senderEmail", String.class);
	private static final String APP_PASSWORD = Config.getConfigValue("emailUtils.appPassword", String.class);
	private static final String RECIPIENT_EMAIL = Config.getConfigValue("emailUtils.recipientEmail", String.class);

	public static void sendTestReport(String reportPath, boolean isTestFailure, int totalTests, int passedTests, int failedTests) {
		if (!isTestFailure) {
			System.out.println("No test failures, not sending report.");
			return; // Don't send email if there are no test failures
		}

		// SMTP server properties
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");

		// Create session with authentication
		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
			}
		});

		try {
			// Create email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT_EMAIL));
			message.setSubject("Test Report - QA Automation");

			// Build email body with stats
			String bodyText = String.format(
					"Dear QA Team,\n\n" +
							"This is the automated test report for the latest test execution.\n\n" +
							"Test Execution Summary:\n" +
							"- Total Tests: %d\n" +
							"- Passed: %d\n" +
							"- Failed: %d\n\n" +
							"Please find the detailed report attached.\n\n" +
							"Best Regards,\nQA Automation Team",
					totalTests, passedTests, failedTests);

			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(bodyText);

			// Attachment part (ExtentReport)
			MimeBodyPart attachmentPart = new MimeBodyPart();
			File reportFile = new File(reportPath);
			if (reportFile.exists()) {
				attachmentPart.attachFile(reportFile);
			}

			// Combine body and attachment
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(textPart);
			multipart.addBodyPart(attachmentPart);
			message.setContent(multipart);

			// Send the email
			Transport.send(message);
			System.out.println("Test Report Email Sent!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
