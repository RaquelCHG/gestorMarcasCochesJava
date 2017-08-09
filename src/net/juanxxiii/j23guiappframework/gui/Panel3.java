package net.juanxxiii.j23guiappframework.gui;

import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

/**
 * Crea el JPanel3 para gestionar usuario y contraseña
 * @author Luthiem
 *
 */
public class Panel3 extends JPanel {
	private JTextField txtUsuario;
	private JPasswordField passContrasena;

	public Panel3() {
		setBackground(new Color(248, 248, 255));
		setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		lblUsuario.setBounds(40, 87, 60, 14);
		add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		lblContrasea.setBounds(40, 143, 86, 14);
		add(lblContrasea);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("GeosansLight", Font.PLAIN, 16));
		txtUsuario.setBounds(141, 82, 264, 25);
		add(txtUsuario);
		txtUsuario.setColumns(10);
		
		passContrasena = new JPasswordField();
		passContrasena.setFont(new Font("GeosansLight", Font.PLAIN, 16));
		passContrasena.setBounds(141, 138, 264, 25);
		add(passContrasena);
		
		JButton btnIngresar = new JButton("Ingresar");
		//Se crea el boton Ingresar para autentificar al usuario
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Se introducen el usuario y la contraseña correcta
				String usuario="admin";
				String contrasena="admin";
				
				//Se recoge el texto escrito en el campo de la contraseña
				String pass = new String(passContrasena.getPassword());
				
				//Se comprueba que los datos introducios son correctos o incorrectos
				if(txtUsuario.getText().equals(usuario)&&pass.equals(contrasena)){
					Window w = SwingUtilities.getWindowAncestor(Panel3.this);
				    w.setVisible(false);
					
					JOptionPane.showMessageDialog(null, "El usuario y contrasena son correctas");
					
				}else{
					JOptionPane.showMessageDialog(null, "El usuario o la contraseña es erronea.");
				}
				
			}
		});
		btnIngresar.setBounds(316, 234, 89, 23);
		add(btnIngresar);

	}
}
