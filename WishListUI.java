package com.cs521.team3.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.cs521.team3.dbconnection.DBConnection;
import com.cs521.team3.model.Books;
import com.cs521.team3.model.WishListTableModel;

public class WishListUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DBConnection dbconn;
	private final JButton button = new JButton("New button");
	ArrayList<Integer> a = new ArrayList<>();
	String col;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WishListUI frame = new WishListUI();
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
	public WishListUI() {
		
		try{
			dbconn=new DBConnection();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Error:"+ex,"Error",JOptionPane.ERROR_MESSAGE);
		}
		
		//dbconn.createTablespace();
		//dbconn.createTables();
		
		setTitle("Create Wishlist App");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnCreateWishlist = new JButton("Create Wishlist");
		btnCreateWishlist.setBounds(5, 5, 202, 23);
		btnCreateWishlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				List<Books> books = null;
				
				books = dbconn.getAllBooks();
				
				WishListTableModel model = new WishListTableModel(books);
				table.setModel(model);
				
				}catch(Exception ex){
					JOptionPane.showMessageDialog(WishListUI.this, "Error:"+ex,"Error",JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnCreateWishlist);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 28, 404, 242);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		scrollPane.setColumnHeaderView(button);
		
		JButton btnNewButton = new JButton("New button");
		scrollPane.setColumnHeaderView(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		scrollPane.setColumnHeaderView(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Select Books");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selection = table.getSelectedRows();
				for(int i=0;i<selection.length;i++){
					System.out.println(selection[i]);
					//Object selectedObject = (Object)table.getModel().getValueAt(selection[i],0);
					col = table.getValueAt(selection[i], 0).toString();
					System.out.println(col);
				/*try{
					
					DBConnection dao = new DBConnection();
					
					int id = dao.getBookId(i);
					System.out.println("after queryyyy");
					a.add(id);
				}catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				}
			try{
				DBConnection dao = new DBConnection();
		         dao.insertWishlist(col);
		         
				}
			catch(SQLException ex){
				System.out.println(ex.getMessage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			WishlistTable wishlistTable = new WishlistTable();
			wishlistTable.setVisible(true);	
			
			}
			
			
		});
		btnNewButton_2.setBounds(212, 5, 192, 23);
		contentPane.add(btnNewButton_2);
		
	}
}
