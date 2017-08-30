package br.com.will.gestao.jms;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ProdutorMensagem {
	
	public void enfileirarMensagem(List<String> destinatario, 
			  List<String> destinatarioCC, 
			  String assunto, 
			  String mensagem,
			  List<File> anexos,
			  List<String> replyTo) throws NamingException {
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		ObjectMessage message = null;
		try {
			Context context = new InitialContext();
			connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Queue fila = (Queue) context.lookup("queue/email");
			
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(fila);
			
			List<String> destinatarios = new ArrayList<String>();
			List<String> destinatariosCC = new ArrayList<String>();

			for (String email : destinatario) {
				if (email != null) {
					destinatarios.add(email.toLowerCase());
				}
			}
			if (destinatarioCC != null) {
				for (String email : destinatarioCC) {
					if (email != null) {
						destinatariosCC.add(email.toLowerCase());
					}
				}
			}
			Mensagem objMensagem = new Mensagem();
			objMensagem.setDestinatarios(destinatarios);
			objMensagem.setDestinatarioCC(destinatariosCC);
			objMensagem.setConteudo(mensagem);
			objMensagem.setAssunto(assunto);
			objMensagem.setAnexos(anexos);
			objMensagem.setReplyTo(replyTo);
			message = session.createObjectMessage(objMensagem);
			System.out.println("** Enfileirando: " + destinatario.size() + " Mensagens >>>");
			messageProducer.send(message);

			messageProducer.close();
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
                if (connection != null) {
					connection.close();
				}
            } catch (Exception ex) {
            	ex.printStackTrace();
			}
		}
	}
}