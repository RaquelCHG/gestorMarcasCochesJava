package net.juanxxiii.j23guiappframework.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.ImageIcon;

/**
 * En esta clase se controla la ejecucion de todo el programa, creando el JFrame y controlado la llamada
 * de cada uno de los JPnael que componen el proyecto.
 * @author Raquel Cabanyes
 */
public class J23App extends JFrame {

	private JPanel contentPane;
	Panel1 p1 = new Panel1();
	Panel2 p2 = new Panel2();
	Panel3 p3 = new Panel3();

	/**
    * Controla la ejecucion del programa
    * @param args 
    */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					J23App frame = new J23App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
    * Creacion del JFrame, que esta compuesto por un JMenuBar
    */
	public J23App() {
		getContentPane().setLayout(new CardLayout(0, 0));
		setTitle("J23 - App Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Windows".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    System.out.println("Problema con el interfaz grafico");
		}
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnPanel = new JMenu("Panel");
		menuBar.add(mnPanel);
		
		JMenuItem mntmPanel_1 = new JMenuItem("Panel 1");
		mntmPanel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)contentPane.getLayout();
				cl.show(contentPane, "Pantalla1");
			}
		});
		mnPanel.add(mntmPanel_1);
		
		JMenuItem mntmPanel = new JMenuItem("Panel 2");
		mntmPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)contentPane.getLayout();
				cl.show(contentPane, "Pantalla2");
			}
		});
		mnPanel.add(mntmPanel);
		
		JMenuItem Panel_3 = new JMenuItem("Panel 3");
		Panel_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)contentPane.getLayout();
				cl.show(contentPane, "Pantalla3");
			}
		});
		mnPanel.add(Panel_3);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(J23App.this,
					    "By Raquel Cabanyes - 2017.",
					    "J23 GUI App FrameWork",
					    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		//Se configura el Panel1 para que sea visible y posteriormente se cargan los metodos de carga del JComboBox
		p1.setVisible(true);
		p1.cargaMarca();
		
        //Se configura el Panel2 para que sea visible y posteriormente se cargan los metodos de carga del JComboBox
		p2.setVisible(true);
		p2.cargaMarcaB();
		
        //Se configura el Panel3 para que sea visible
		p3.setVisible(true);
		
		contentPane.add(p1,"Pantalla1");
		contentPane.add(p2,"Pantalla2");
		contentPane.add(p3,"Pantalla3");
	}

}
