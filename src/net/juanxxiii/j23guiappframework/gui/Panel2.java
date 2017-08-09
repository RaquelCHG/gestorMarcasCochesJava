package net.juanxxiii.j23guiappframework.gui;

import javax.swing.JPanel;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

/**
 * Se crea el primer JPanel con las opciones de buscar, por marca y consumo, y borrar un elemento seleccion del JTable
 * @author Raquel Cabanyes
 */
public class Panel2 extends JPanel {
	private JTable tablaRdo;
	
	private static String base="bbdd_gestmotor";
	private static String user="daw2017";
	private static String pass="daw2017";
	private static String host="localhost";
	private static String server="jdbc:mysql://"+host+"/"+base;
	private JButton button;
	private JComboBox cbMarca;
	private JSlider slider;
	private JTable tableMostrar;

	/**
     * Se crea el Panel2
     */
	public Panel2() {
		setBackground(SystemColor.info);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 248, 255));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JScrollPane scrollMostrar = new JScrollPane();
		scrollMostrar.setBounds(6, 196, 563, 235);
		panel.add(scrollMostrar);
		
        //Se crea el Jtable con cada uno de los campos que lo componen
		DefaultTableModel modelo = new DefaultTableModel();
		JTable tableMostrar = new JTable(modelo);
		modelo.addColumn("Logo");
		modelo.addColumn("Modelo");
		modelo.addColumn("Consumo");
		modelo.addColumn("Emisiones");
		modelo.addColumn("Calificacion E.");
		scrollMostrar.setViewportView(tableMostrar);
		
		JLabel lblConsumo = new JLabel("Consumo");
		lblConsumo.setBounds(14, 16, 65, 23);
		lblConsumo.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		panel.add(lblConsumo);
		
		JLabel lblMximo = new JLabel("Maximo");
		lblMximo.setBounds(14, 36, 65, 23);
		lblMximo.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		panel.add(lblMximo);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setBounds(14, 73, 48, 23);
		lblMarca.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		panel.add(lblMarca);
		
		cbMarca = new JComboBox();
		cbMarca.setBounds(98, 69, 361, 27);
		cbMarca.setMaximumRowCount(5);
		panel.add(cbMarca);
		
		button = new JButton("");
		button.setBounds(469, 6, 100, 90);
        //Se crea el boton buscar, el cual realizar la busqueda en la base de datos de la sentencia por marca y un consumo menor al indicado
		button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int[] id =new int[5];
			int posicion=0, anadir=0, valorSlider = 0;
			String info2="";
			
			try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        try {
	        	Connection conexion=DriverManager.getConnection(server,user,pass);
	            Statement comando = conexion.createStatement();
	            
	            //Limpia el Jtable
	            for (int i = 0; i < tableMostrar.getRowCount(); i++) {
	                modelo.removeRow(i);
	                i-=1;
	            }
	            
	            String info = "SELECT ID FROM MARCAS WHERE lower(MARCA)='" + cbMarca.getSelectedItem() +"';";
	            ResultSet resultSet;
	            resultSet = comando.executeQuery(info);
				if (resultSet.next()) {
					//Con esto conseguimos el ID de la consulta
					id[posicion]=resultSet.getInt("ID");
					posicion++;
				} else {
					JOptionPane.showMessageDialog(null, "Error en la busqueda de la Marca.");
				}
					
				valorSlider = slider.getValue();
				
				String info3 = "Select ID_MARCA, MODELO, CONSUMO, EMISIONES, C_ENERGETICA FROM MODELOS where ID_MARCA=" + id[0] + " and CONSUMO < " + valorSlider + ";";
				
				ResultSet resultSet2;
				resultSet2 = comando.executeQuery(info3);
									
				if (resultSet2.next()){
					while (resultSet2.next()){
						// Se crea un array que sera una de las 5 filas de la tabla.
						Object [] fila = new Object[5]; 
						
						//Se anade cada fila con la informacion de la base de datos
						fila[0] =resultSet2.getObject("ID_MARCA"); 	//Cuando se escribe el numero del 
						fila[1] =resultSet2.getObject("MODELO");	//campo en la tabla y no el nombre del  
						fila[2] =resultSet2.getObject("CONSUMO");	//campo se empieza en 1, no en 0
						fila[3] =resultSet2.getObject("EMISIONES"); 
						fila[4] =resultSet2.getObject("C_ENERGETICA");
							
						// Se anade al modelo la fila completa.
						modelo.addRow(fila);
					}
				}else{
					JOptionPane.showMessageDialog(null, "No hay ningun resultado.");
				}				
				
				conexion.close();
	        } catch (SQLException ex) {
	        	JOptionPane.showMessageDialog(null, "Excepci�n de tipo SQL"
	        			+ ".", "Error", JOptionPane.ERROR_MESSAGE);
	        }
		}
		});
		button.setIcon(new ImageIcon(Panel2.class.getResource("/mios/buscar.png")));
		panel.add(button);
		
		slider = new JSlider();
		slider.setBounds(98, 17, 361, 38);
		//Valor minimo
		slider.setMinimum(0);
        //Valor máximo
		slider.setMaximum((int) 25.00);
        //valor inicial
		slider.setValue(5);
        //Valores para mostrar regla
        //mínimo para mostrar
		slider.setMinorTickSpacing(1);
        //máximo para mostrar
		slider.setMajorTickSpacing(5);
        //Especificamos que se muestren los valores
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		panel.add(slider);
		
		JButton Borrar = new JButton("");
        //Creamos el boton borrar, que al seleccionar una linea del Jtable mediante una sentencia nos elimina el modelo de la tabla
		Borrar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        try {
			
				// Obtenemos el modelo de tableMostrar
		        DefaultTableModel modelo = (DefaultTableModel) tableMostrar.getModel();
		        //Obtenemos la fila seleccionada
		        int fila_select = tableMostrar.getSelectedRow();
			        //Obtenemos el valor de cada campo
			        String marca=String.valueOf(modelo.getValueAt(fila_select,1));
			        String consumo=String.valueOf(modelo.getValueAt(fila_select,2));
			        String emisiones=String.valueOf(modelo.getValueAt(fila_select,3));
			        String calificacion=String.valueOf(modelo.getValueAt(fila_select,4));
			        
			        Connection conexion=DriverManager.getConnection(server,user,pass);
		            Statement comando = conexion.createStatement();
			       
			        if(fila_select<0){
			            JOptionPane.showMessageDialog(null,"No se ha seleccionado ninguna fila.");
			        }else {
			        	//Elimina la fila seleccionada en la base de datos
				        String info=("delete from MODELOS where modelo='" + marca + "' and consumo=" + consumo + " and emisiones=" + emisiones + " and c_energetica='" + calificacion + "';");
				        comando.executeUpdate(info);
			        	
			        	//Elimina la visualizacion de la fila en JTable
			        	modelo.removeRow(fila_select);
			        	
			        	JOptionPane.showMessageDialog(null, "El modelo ha sido eliminado correctamente.");
			        }
		            
		            conexion.close();
		            
		        } catch (SQLException ex) {
		        	JOptionPane.showMessageDialog(null, "Excepcion de tipo SQL"
		        			+ ".", "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		Borrar.setBounds(469, 100, 100, 90);
		Borrar.setIcon(new ImageIcon(Panel2.class.getResource("/mios/eliminar.png")));
		panel.add(Borrar);
	}
	
    /**
     * Carga los nombres de las Marcas en JComboBox
     */
	public void cargaMarcaB(){
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	Connection conexion=DriverManager.getConnection(server,user,pass);
            Statement sentencia = (Statement) conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = sentencia.executeQuery("SELECT MARCA FROM marcas;");
            
            while (rs.next()) {
            	cbMarca.addItem(rs.getObject("marca"));
            }
 
            conexion.close();
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Se ha producido un error de tipo SQL.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
        	JOptionPane.showMessageDialog(null, "No se ha encontrado la clase.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
        	JOptionPane.showMessageDialog(null, "Excepcion en el codigo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
