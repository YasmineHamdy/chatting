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
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client extends JFrame{

	
	JButton sendButton = new JButton("Send");
	JTextArea screen = new JTextArea();
	JTextField textField = new JTextField();
	
	Socket socket;
	OutputStream outputStream;
	InputStream inputStream;
	
	public Client() throws IOException {
		setupGUI();
		getConnection("localhost", 6666);
		BufferedReader bufferedReader = (BufferedReader) getResponse();
		String serverMessage;
		while ((serverMessage = bufferedReader.readLine()) != null) {
			screen.append(" Server: " + serverMessage + "\n");
		}
		
	}
	
	private void setupGUI() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Client");
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
				screen.append(" Client: " + textField.getText() + "\n");
				setRequest(textField.getText());
				textField.setText("");
			}
			
		}
	}
	
	
	
	private void getConnection(String hostname, int portNumber) throws UnknownHostException, IOException{
		socket = new Socket(hostname, portNumber);
		// set request
		outputStream = socket.getOutputStream();
		// get response
		inputStream = socket.getInputStream();
	}
	
	private void setRequest(Object obj){
		PrintWriter printWriter = new PrintWriter(outputStream, true);
		printWriter.println(obj);
	}
	
	private Object getResponse() {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		return bufferedReader;
	}
	public static void main(String[] args) throws IOException {
		Client client = new Client();
		
	}
	
	
}
