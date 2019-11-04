import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Userinterface extends JFrame {

	private JPanel contentPane;
	private JTextField fname;
	private JTextField sname;
	private JTextField birthday;
	private JLabel message;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Userinterface frame = new Userinterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Userinterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 746, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fname = new JTextField();
		fname.setText("Fname");
		fname.setBounds(78, 30, 86, 20);
		contentPane.add(fname);
		fname.setColumns(10);
		
		sname = new JTextField();
		sname.setText("Sname");
		sname.setBounds(307, 30, 86, 20);
		contentPane.add(sname);
		sname.setColumns(10);
		
		birthday = new JTextField();
		birthday.setText("Birthday");
		birthday.setBounds(536, 30, 86, 20);
		contentPane.add(birthday);
		birthday.setColumns(10);
		
		JButton addbuton = new JButton("Add");
		addbuton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection connect=null;
				PreparedStatement stat=null;
				try {
					connect=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/people","root","lpsycho21");
						String query="insert into birthday (Fname,Sname,Birthday) values(?,?,?)";
						PreparedStatement state=(PreparedStatement) connect.prepareStatement(query);
						state.setString(1,fname.getText());
						state.setString(2, sname.getText());
						state.setString(3,birthday.getText());
						state.execute();
						message.setText("Your friend is  inserted");
						connect.close();
						state.close();
					
				} catch(Exception ex) {
					System.out.println("not connected");
				}
			}
		});
		addbuton.setBounds(307, 84, 89, 23);
		contentPane.add(addbuton);
		
		JButton btnreminder = new JButton("Reminder");
		btnreminder.addActionListener(new ActionListener() {
			private Calendar calendar = Calendar.getInstance();
			public void actionPerformed(ActionEvent arg0) {
				Connection connected=null;
			String tt;
			
				try {
					
					connected=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/people","root","lpsycho21");
					Statement m = (Statement) connected.createStatement();
				    String query = "select *from birthday";
				    String timme;
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM");  
				    LocalDateTime zaman = LocalDateTime.now();  
				    timme=dtf.format(zaman).toString();
				   
				    
				    
				    ResultSet result = m.executeQuery(query);
				    while (result.next()) {
				    
					   
						   
				    	
				    	   if(result.getString(4).equals(timme)) {
				    		   message.setText("Bugün "+ result.getString(2)+ "'ın doğum günü");
						    	//System.out.println("bugün " +result.getString(2) +"'ın doğum günü");
						    }
				    	   else {
				    		   message.setText("Malasef bugün kimsenin doğum günü yok:/");
				    	   }
				    	
				    
				 
			    	
				    	
				    }
				    		
				    		
				    	
				    	
				    	
				    	}
				    
			
				    

					
			 catch(SQLException ex) {
				System.out.println("bağlantı olmadı"+ex);
			}
			
		
				
			}});
		btnreminder.setBounds(307, 145, 89, 23);
		contentPane.add(btnreminder);
		
		message = new JLabel("");
		message.setBounds(272, 205, 332, 36);
		contentPane.add(message);
	}
}
