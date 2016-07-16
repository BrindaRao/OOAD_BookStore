package com.cs521.team3.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.cs521.team3.dbconnection.DBConnection;
import com.cs521.team3.model.FinalWishModel;
import com.cs521.team3.model.Wishlist;

public class WishlistTable extends JFrame {

	private JPanel contentPane;
	private DBConnection dbconn;
	private JScrollPane scrollPane;
	private JTable table;
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
	public WishlistTable() {
		
		try{
			dbconn=new DBConnection();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Error:"+ex,"Error",JOptionPane.ERROR_MESSAGE);
		}
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Show Wishlist");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					List<Wishlist> wishlist = null;
					
					wishlist = dbconn.getWishlist();
					/*for(int i=0;i<wishlist.size();i++){
						System.out.println();
					}*/
					FinalWishModel mod = new FinalWishModel(wishlist);
					table.setModel(mod);
					
					}catch(Exception ex){
						JOptionPane.showMessageDialog(WishlistTable.this, "Error:"+ex,"Error",JOptionPane.ERROR_MESSAGE);

					}
			}
		});
		contentPane.add(btnNewButton, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

}
