package cryptography;

import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

public class FileDecryptGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileDecryptGUI window = new FileDecryptGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileDecryptGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBackground(new Color(245, 245, 245));
		frame.getContentPane().setForeground(new Color(245, 245, 245));
		frame.getContentPane().setBackground(new Color(245, 245, 245));
		frame.setBounds(300, 300, 750, 402);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel1 = new JLabel("Select File to Decrypt  ------->  Get PRIVATE KEY from Owner");
		lblNewLabel1.setBounds(10, 21, 627, 31);
		lblNewLabel1.setForeground(new Color(0, 0, 128));
		lblNewLabel1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		frame.getContentPane().add(lblNewLabel1);
		
		textField = new JPasswordField();
		textField.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		textField.setToolTipText("Enter Private Key Here");
		textField.setBounds(10, 149, 434, 41);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		

		JLabel lblNewLabel4 = new JLabel("");
		lblNewLabel4.setForeground(new Color(64, 0, 0));
		lblNewLabel4.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		lblNewLabel4.setBounds(10, 281, 522, 31);
		frame.getContentPane().add(lblNewLabel4);
		
		JButton btnNewButton = new JButton("Decrypt EHR !!");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(64, 128, 128));
		btnNewButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		btnNewButton.setBounds(454, 158, 183, 31);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ZipFiles zf=new ZipFiles();
				try {
					String outputFile = textField_1.getText();
					int indexofdotenc= textField_1.getText().lastIndexOf(".");
					String fileOutPath = outputFile.substring(0, indexofdotenc);
					zf.decryptedFile(textField.getText(), textField_1.getText(), fileOutPath);
					lblNewLabel4.setText("File is Decrypted Successfully !!");
					
				} catch (IOException | GeneralSecurityException e1) {
					// TODO Auto-generated catch block
					lblNewLabel4.setText("Your password may be wrong or " + e1);
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Browse File to Decrypt");
		textField_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		textField_1.setColumns(10);
		textField_1.setBounds(10, 81, 434, 41);
		frame.getContentPane().add(textField_1);
		
		JButton btnBrowseFile = new JButton("Browse File....");
		btnBrowseFile.setForeground(Color.WHITE);
		btnBrowseFile.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		btnBrowseFile.setBackground(new Color(64, 128, 128));
		btnBrowseFile.setBounds(454, 91, 183, 31);
		frame.getContentPane().add(btnBrowseFile);
		
		btnBrowseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				// invoke the showsOpenDialog function to show the save dialog
	            int r = fileChooser.showOpenDialog(null);
	 
	            // if the user selects a file
	            if (r == JFileChooser.APPROVE_OPTION)
	 
	            {
	                // set the label to the path of the selected file
	            	textField_1.setText(fileChooser.getSelectedFile().getAbsolutePath());
	            }
	            // if the user cancelled the operation
	            else
	            	textField_1.setText("the user cancelled the operation");
	        
				
			}
		});
		
	}
}
