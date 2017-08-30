package br.com.will.gestao.util;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.will.gestao.componente.ESistema;
import br.com.will.gestao.jms.ProdutorMensagem;

public final class SendEmail implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final String FROM_NAME = "William Novaes";
	private static String fromAddress = "williamf.novaes@gmail.com";
	private static String password = "13579w1ll1aw2468";
	private static Session session;

	public SendEmail(String from, String password) {
		this();
		SendEmail.fromAddress = from;
		SendEmail.password = password;

	}

	public SendEmail(String from, String password, Properties props) {
		SendEmail.fromAddress = from;
		SendEmail.password = password;
		session = Session.getInstance(props, new SocialAuth());
	}

	public SendEmail() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port", "587");
		session = Session.getInstance(props, new SocialAuth());
	}

	public boolean enviarEmail(List<String> destinatario, List<String> destinatarioCC, String assunto, String mensagem,
			List<File> anexos, List<String> replyTo) {
		try {
			ProdutorMensagem produtorMensagem = new ProdutorMensagem();
			produtorMensagem.enfileirarMensagem(destinatario, destinatarioCC, assunto, mensagem, anexos, replyTo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean dispararEmail(List<String> destinatario, List<String> destinatarioCC, String assunto,
			String mensagem, List<File> anexos, List<String> replyTo) throws Exception {
		try {
			Message message = new MimeMessage(session);
			String charset = "text/html; charset=utf-8";

			InternetAddress remetenteEmail = new InternetAddress(fromAddress, FROM_NAME);
			message.setFrom(remetenteEmail);

			Address[] destinatarios = new Address[destinatario.size()];
			for (int i = 0; i < destinatario.size(); i++) {
				destinatarios[i] = new InternetAddress(destinatario.get(i).toLowerCase());
			}

			message.setRecipients(RecipientType.TO, destinatarios);

			// Address[] destinatariosBCC = new Address[1];
			// destinatariosBCC[0] = new
			// InternetAddress("andre@nxmultiservicos.com.br");

			// message.setRecipients(RecipientType.BCC, destinatariosBCC);

			if (destinatarioCC != null && !destinatarioCC.isEmpty()) {
				InternetAddress[] destinatariosCC = new InternetAddress[destinatarioCC.size()];
				for (int j = 0; j < destinatarioCC.size(); j++) {
					destinatariosCC[j] = new InternetAddress(destinatarioCC.get(j));
				}
				message.setRecipients(Message.RecipientType.CC, destinatariosCC);
			}

			if (replyTo != null && !replyTo.isEmpty()) {
				Address[] addresses = new Address[replyTo.size()];
				for (int i = 0; i < replyTo.size(); i++) {
					InternetAddress address = new InternetAddress();
					address.setAddress(replyTo.get(i));
					addresses[i] = address;
				}
				message.setReplyTo(addresses);
			}

			MimeMultipart mimeMultipart = new MimeMultipart("mixed");
			MimeMultipart mimeMultipartContent = new MimeMultipart("alternative");
			MimeBodyPart contentPartRoot = new MimeBodyPart();
			contentPartRoot.setContent(mimeMultipartContent);
			mimeMultipart.addBodyPart(contentPartRoot);

			StringBuilder corpoEmail = new StringBuilder();

			corpoEmail
					.append("<div style=\"margin:0 auto;font-family:sans-serif;color:#666;background-color:#f9f9f9\">");
			corpoEmail.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" ");
			corpoEmail.append("style=\"margin:0 auto;font-size:14px;border:1px solid #d8d8d8;");
			corpoEmail.append("box-shadow:0 3px 1px -2px rgba(0,0,0,.15);width:780px\">");
			corpoEmail.append("<tr>");
			corpoEmail.append("<td style=\"padding:10px;background-color:#fff;width:145px\">");
			corpoEmail.append("<img src=\"http://multisales.com.br/resources/images/cliente/nx/logo-email.png\" ");
			corpoEmail.append("alt=\"\" style=\"display:block;width:145px;height:78px\" />");
			corpoEmail.append("</td>");
			corpoEmail.append("<td style=\"padding-right:20px;background-color:#fff\">");
			corpoEmail.append("<h1 style=\"margin:0;color:#999;font-weight:normal;text-align:right\">");
			corpoEmail.append("Multi<strong>Sales</strong>");
			SimpleDateFormat dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			corpoEmail.append("<span style=\"display:block;font-size:12px;color:#ccc\">Email enviado em ");
			corpoEmail.append(dataHora.format(Calendar.getInstance().getTime()));
			corpoEmail.append("</span>"); /* 31/12/2099 23:59 */
			corpoEmail.append("</h1>");
			corpoEmail.append("</td>");
			corpoEmail.append("</tr>");
			corpoEmail.append("<tr>");
			corpoEmail.append("<td colspan=\"2\">");
			corpoEmail.append("<div style=\"padding:20px 30px;background-color:#fff\">");

			corpoEmail.append(mensagem);

			corpoEmail.append("</div>");
			corpoEmail.append("</td>");
			corpoEmail.append("</tr>");
			corpoEmail.append("</table>");
			corpoEmail.append("</div>");

			corpoEmail.append("<p style=\"color:#ccc;font-size:12px;text-align:center\">");
			corpoEmail.append("Este email foi enviado automaticamente pelo sistema. Favor nao responder, ");
			corpoEmail.append("pois o mesmo nao e monitorado.<br />");
			corpoEmail.append("Em caso de duvidas entre em contato com o Help Desk pelo telefone <strong>");
			corpoEmail.append(ESistema.MULTISALES.getTelefone() + "</strong>. ");
			corpoEmail.append("</p>");
			corpoEmail.append("</div>");//

			// enviando o texto
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(corpoEmail.toString());
			mbp1.setHeader("Content-Type", charset);
			mimeMultipartContent.addBodyPart(mbp1);

			if (anexos != null && !anexos.isEmpty()) {
				for (File anexo : anexos) {
					MimeBodyPart mbp3 = new MimeBodyPart();
					DataSource fds = new FileDataSource(anexo);
					mbp3.setDisposition(Part.ATTACHMENT);
					mbp3.setDataHandler(new DataHandler(fds));
					if (anexo != null) {
						mbp3.setFileName(anexo.getName());
					}
					mbp3.setHeader("Content-Type", charset);
					mimeMultipart.addBodyPart(mbp3);
				}
			}
			message.setSubject(assunto);
			message.setContent(mimeMultipart, charset);
			Transport.send(message);

			System.out.println("Enviando email!");
			return true;
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;

		} catch (MessagingException ex) {
			Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public boolean dispararEmailAbsoluta(List<String> destinatario, List<String> destinatarioCC, String assunto,
			String mensagem, List<File> anexos, List<String> replyTo) throws Exception {
		try {
			Message message = new MimeMessage(session);
			String charset = "text/html; charset=utf-8";

			InternetAddress remetenteEmail = new InternetAddress(fromAddress, "REDE ABSOLUTA");
			message.setFrom(remetenteEmail);

			Address[] destinatarios = new Address[destinatario.size()];
			for (int i = 0; i < destinatario.size(); i++) {
				destinatarios[i] = new InternetAddress(destinatario.get(i).toLowerCase());
			}

			message.setRecipients(RecipientType.TO, destinatarios);

			// Address[] destinatariosBCC = new Address[1];
			//
			// message.setRecipients(RecipientType.BCC, destinatariosBCC);

			if (destinatarioCC != null && !destinatarioCC.isEmpty()) {
				InternetAddress[] destinatariosCC = new InternetAddress[destinatarioCC.size()];
				for (int j = 0; j < destinatarioCC.size(); j++) {
					destinatariosCC[j] = new InternetAddress(destinatarioCC.get(j));
				}
				message.setRecipients(Message.RecipientType.CC, destinatariosCC);
			}

			if (replyTo != null && !replyTo.isEmpty()) {
				Address[] addresses = new Address[replyTo.size()];
				for (int i = 0; i < replyTo.size(); i++) {
					InternetAddress address = new InternetAddress();
					address.setAddress(replyTo.get(i));
					addresses[i] = address;
				}
				message.setReplyTo(addresses);
			}

			MimeMultipart mimeMultipart = new MimeMultipart("mixed");
			MimeMultipart mimeMultipartContent = new MimeMultipart("alternative");
			MimeBodyPart contentPartRoot = new MimeBodyPart();
			contentPartRoot.setContent(mimeMultipartContent);
			mimeMultipart.addBodyPart(contentPartRoot);

			StringBuilder corpoEmail = new StringBuilder();
			SimpleDateFormat dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			corpoEmail
					.append("<div style=\"margin:0 auto;font-family:sans-serif;color:#666;background-color:#f9f9f9\">");
			corpoEmail.append("   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" ");
			corpoEmail.append("          style=\"margin:0 auto;font-size:14px;border:1px solid #d8d8d8;");
			corpoEmail.append("          box-shadow:0 3px 1px -2px rgba(0,0,0,.15);width:780px\">");
			corpoEmail.append("      <tr>");
			corpoEmail.append("         <td style=\"padding:10px;background-color:#fff;width:145px\">");
			corpoEmail.append(
					"            <img src=\"http://www.nxmultiservicos.com.br/downloads/rede-absoluta-logo-email.png\" ");
			corpoEmail.append("			        alt=\"\" style=\"display:block;width:232px;height:66px\" />");
			corpoEmail.append("         </td>");
			corpoEmail.append("         <td style=\"padding-right:20px;background-color:#fff\">");
			corpoEmail.append("			   <h1 style=\"margin:0;color:#999;font-weight:normal;text-align:right\">");
			corpoEmail.append("				   <strong>REDE ABSOLUTA</strong>");
			corpoEmail.append(
					"			      <span style=\"display:block;font-size:12px;color:#ccc\">Email enviado em ");
			corpoEmail.append(dataHora.format(Calendar.getInstance().getTime()));
			corpoEmail.append("			      </span>"); /* 31/12/2099 23:59 */
			corpoEmail.append("			   </h1>");
			corpoEmail.append("			</td>");
			corpoEmail.append("		 </tr>");
			corpoEmail.append("		 <tr>");
			corpoEmail.append("         <td colspan=\"2\">");
			corpoEmail.append("			   <div style=\"padding:20px 30px;background-color:#fff\">");
			corpoEmail.append(mensagem);
			corpoEmail.append("           </div>");
			corpoEmail.append("         </td>");
			corpoEmail.append("      </tr>");
			corpoEmail.append("   </table>");
			corpoEmail.append("</div>");

			corpoEmail.append("<p style=\"color:#ccc;font-size:12px;text-align:center\">");
			corpoEmail.append("Caso tenha dificuldades em visualizar o boleto, clique ");
			corpoEmail.append(
					"<a href=\"https://get.adobe.com/br/reader/\">Adobe Reader</a> para fazer o download do programa.<br />");
			corpoEmail.append("</p>");

			corpoEmail.append("<p style=\"color:#ccc;font-size:12px;text-align:center\">");
			corpoEmail.append("Este email foi enviado automaticamente pelo sistema. Favor nao responder, ");
			corpoEmail.append("pois o mesmo nao e monitorado.<br />");
			corpoEmail.append("Em caso de duvidas entre em contato com o Call Center Absoluta pelos telefones ");
			corpoEmail.append(
					"<strong>(44) 3032-7400 / (43) 3032-8700 / (42) 3087-7021 / (41) 3025-5300 / (49) 99922-7553</strong>.");
			corpoEmail.append("</p>");
			corpoEmail.append("</div>");

			// enviando o texto
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(corpoEmail.toString());
			mbp1.setHeader("Content-Type", charset);
			mimeMultipartContent.addBodyPart(mbp1);

			if (anexos != null && !anexos.isEmpty()) {
				for (File anexo : anexos) {
					MimeBodyPart mbp3 = new MimeBodyPart();
					DataSource fds = new FileDataSource(anexo);
					mbp3.setDisposition(Part.ATTACHMENT);
					mbp3.setDataHandler(new DataHandler(fds));
					if (anexo != null) {
						mbp3.setFileName(anexo.getName());
					}
					mbp3.setHeader("Content-Type", charset);
					mimeMultipart.addBodyPart(mbp3);
				}
			}
			message.setSubject(assunto);
			message.setContent(mimeMultipart, charset);
			Transport.send(message);

			System.out.println("Enviando email!");
			return true;
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;

		} catch (MessagingException ex) {
			Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public boolean dispararEmailSgd(List<String> destinatario, List<String> destinatarioCC, String assunto,
			String mensagem, List<File> anexos, List<String> replyTo) throws Exception {
		try {
			Message message = new MimeMessage(session);
			String charset = "text/html; charset=utf-8";

			InternetAddress remetenteEmail = new InternetAddress(fromAddress, FROM_NAME);
			message.setFrom(remetenteEmail);

			Address[] destinatarios = new Address[destinatario.size()];
			for (int i = 0; i < destinatario.size(); i++) {
				destinatarios[i] = new InternetAddress(destinatario.get(i).toLowerCase());
			}

			message.setRecipients(RecipientType.TO, destinatarios);

			Address[] destinatariosBCC = new Address[1];
			// destinatariosBCC[0] = new
			// InternetAddress("andre@nxmultiservicos.com.br");

			message.setRecipients(RecipientType.BCC, destinatariosBCC);

			if (destinatarioCC != null && !destinatarioCC.isEmpty()) {
				InternetAddress[] destinatariosCC = new InternetAddress[destinatarioCC.size()];
				for (int j = 0; j < destinatarioCC.size(); j++) {
					destinatariosCC[j] = new InternetAddress(destinatarioCC.get(j));
				}
				message.setRecipients(Message.RecipientType.CC, destinatariosCC);
			}

			if (replyTo != null && !replyTo.isEmpty()) {
				Address[] addresses = new Address[replyTo.size()];
				for (int i = 0; i < replyTo.size(); i++) {
					InternetAddress address = new InternetAddress();
					address.setAddress(replyTo.get(i));
					addresses[i] = address;
				}
				message.setReplyTo(addresses);
			}

			MimeMultipart mimeMultipart = new MimeMultipart("mixed");
			MimeMultipart mimeMultipartContent = new MimeMultipart("alternative");
			MimeBodyPart contentPartRoot = new MimeBodyPart();
			contentPartRoot.setContent(mimeMultipartContent);
			mimeMultipart.addBodyPart(contentPartRoot);

			StringBuilder corpoEmail = new StringBuilder();
			SimpleDateFormat dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			corpoEmail
					.append("<div style=\"margin:0 auto;font-family:sans-serif;color:#666;background-color:#f9f9f9\">");
			corpoEmail.append("   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" ");
			corpoEmail.append("          style=\"margin:0 auto;font-size:14px;border:1px solid #d8d8d8;");
			corpoEmail.append("          box-shadow:0 3px 1px -2px rgba(0,0,0,.15);width:780px\">");
			corpoEmail.append("      <tr>");
			corpoEmail.append("         <td style=\"padding:10px;background-color:#fff;width:145px\">");
			corpoEmail.append(
					"            <img src=\"http://multisales.com.br/resources/images/cliente/nx/logo-email.png\" ");
			corpoEmail.append("			        alt=\"\" style=\"display:block;width:145px;height:78px\" />");
			corpoEmail.append("         </td>");
			corpoEmail.append("         <td style=\"padding-right:20px;background-color:#fff\">");
			corpoEmail.append("			   <h1 style=\"margin:0;color:#999;font-weight:normal;text-align:right\">");
			corpoEmail.append("				   <strong>SGD</strong>");
			corpoEmail.append(
					"			      <span style=\"display:block;font-size:12px;color:#ccc\">Email enviado em ");
			corpoEmail.append(dataHora.format(Calendar.getInstance().getTime()));
			corpoEmail.append("			      </span>"); /* 31/12/2099 23:59 */
			corpoEmail.append("			   </h1>");
			corpoEmail.append("			</td>");
			corpoEmail.append("		 </tr>");
			corpoEmail.append("		 <tr>");
			corpoEmail.append("         <td colspan=\"2\">");
			corpoEmail.append("			   <div style=\"padding:20px 30px;background-color:#fff\">");

			corpoEmail.append(mensagem);

			corpoEmail.append("           </div>");
			corpoEmail.append("         </td>");
			corpoEmail.append("      </tr>");
			corpoEmail.append("   </table>");
			corpoEmail.append("</div>");

			corpoEmail.append("<p style=\"color:#ccc;font-size:12px;text-align:center\">");
			corpoEmail.append("Este email foi enviado automaticamente pelo sistema. Favor nao responder, ");
			corpoEmail.append("pois o mesmo nao e monitorado.<br />");
			corpoEmail.append("Em caso de duvidas entre em contato com o Help Desk pelo telefone <strong>");
			corpoEmail.append(ESistema.NOVO.getTelefone() + "</strong>. ");
			corpoEmail.append("</p>");
			corpoEmail.append("</div>");

			// enviando o texto
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(corpoEmail.toString());
			mbp1.setHeader("Content-Type", charset);
			mimeMultipartContent.addBodyPart(mbp1);

			if (anexos != null && !anexos.isEmpty()) {
				for (File anexo : anexos) {
					MimeBodyPart mbp3 = new MimeBodyPart();
					DataSource fds = new FileDataSource(anexo);
					mbp3.setDisposition(Part.ATTACHMENT);
					mbp3.setDataHandler(new DataHandler(fds));
					if (anexo != null) {
						mbp3.setFileName(anexo.getName());
					}
					mbp3.setHeader("Content-Type", charset);
					mimeMultipart.addBodyPart(mbp3);
				}
			}
			message.setSubject(assunto);
			message.setContent(mimeMultipart, charset);
			Transport.send(message);

			System.out.println("Enviando email!");
			return true;
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;

		} catch (MessagingException ex) {
			Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	static class SocialAuth extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(fromAddress, password);
		}
	}
}