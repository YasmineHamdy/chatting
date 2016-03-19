import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame{

	JButton sendButton = new JButton("Send");
	JTextArea screen = new JTextArea();
	JTextField textField = new JTextField();
	
	ServerSocket serverSocket;
	Socket clientObject;
	InputStream in;
	OutputStream out;
	
	
	public Server() throws IOException {
		setupGUI();
		getConnection(6666);
		BufferedReader bufferedReader = (BufferedReader) getRequest();
		String clientMessage;
		while ((clientMessage = bufferedReader.readLine()) != null) {
			screen.append(" Client: " + clientMessage + "\n");
		}
		
	}
	
	private void setupGUI() {
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Server");
		setSize(600, 450);
		setLayout(new BorderLayout());
		
		add(sendButton, BorderLayout.EAST);
		screen.setPreferredSize(new Dimension(50, 400));
		screen.setLineWrap(true);
		screen.setWrapStyleWord(true);
		add(screen, BorderLayout.CENTER);
		textField.setPreferredSize(new Dimension(400, 50));
		add(textField, BorderLayout.SOUTH);
		
		sendButton.addActionListener(new SendButtonActionListener());
	}
	
	
	class SendButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent even) {
			JButton clickedButton = (JButton) even.getSource();
			if (clickedButton == sendButton) {
				screen.append(" Server: " + textField.getText() + "\n");
				setResponse(textField.getText());
				textField.setText("");
			}
			
		}
	}
	
	
	
	private void getConnection(int portNumber){
		try {
			serverSocket = new ServerSocket(portNumber);
			clientObject =  serverSocket.accept();
			//get requests
			in = clientObject.getInputStream();
			//set responses
			out = clientObject.getOutputStream();
		} catch (Exception e) {}
		
	}
	
	private Object getRequest(){
		InputStreamReader inputStreamReader = new InputStreamReader(in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		return bufferedReader;
	}
	
	
	private void setResponse(Object obj){
		PrintWriter printWriter = new PrintWriter(out, true);
		printWriter.println(obj);
		
	}
	public static void main(String[] args) throws IOException {
		Server server =  new Server();
		
	}
	
	
}