package net.juanxxiii.j23guiappframework.gui;

import javax.swing.JPanel;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.ListCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import java.awt.Font;


/**
 * Se crea el primer JPanel con las opciones de añadir, introduciento todos los campos, y eliminar,
 * con varias combinaciones disponibles, siendo siempre obligatorios el campo marca y eficiencia
 * @author Raquel Cabanyes
 */
public class Panel1 extends JPanel{
    private JTextField txtConsumo;
    private JTextField txtEmisiones;
	private JComboBox cbMarca;
	private JTextField txtModelo;
	private JComboBox cbCenergetica;
        private JButton btnAnadir;
	private JButton btnBorrar;
        
	ImageIcon[] images;
    String[] array_ce = {"coches_A", "coches_B", "coches_C", "coches_D", "coches_E", "coches_F", "coches_G", "coches_sinclasificacion"};
        
    //Se crean las variables de la dirección, usuario y contraseña para crear la conexion con la base de datos
    private static String base="bbdd_gestmotor";
	private static String user="daw2017";
	private static String pass="daw2017";
	private static String host="localhost";
	private static String server="jdbc:mysql://"+host+"/"+base;
	
	

	
	/**
     * Se crea el Panel1 con tus sus componentes
     */
	public Panel1() {				
		setBackground(SystemColor.info);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 248, 255));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		lblMarca.setBounds(14, 27, 54, 15);
		panel.add(lblMarca);
		
		cbMarca = new JComboBox();
		cbMarca.setMaximumRowCount(4);
		cbMarca.setFont(new Font("GeosansLight", Font.PLAIN, 16));
		cbMarca.setBounds(110, 20, 459, 30);
		panel.add(cbMarca);
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		lblModelo.setBounds(14, 162, 61, 15);
		panel.add(lblModelo);
		
		JLabel lblConsumo = new JLabel("Consumo");
		lblConsumo.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		lblConsumo.setBounds(14, 283, 73, 15);
		panel.add(lblConsumo);
		
		txtConsumo = new JTextField();
		txtConsumo.setFont(new Font("GeosansLight", Font.PLAIN, 16));
		txtConsumo.setBounds(110, 268, 347, 30);
		panel.add(txtConsumo);
		txtConsumo.setColumns(10);
		
		JLabel lblEmisiones = new JLabel("Emisiones");
		lblEmisiones.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		lblEmisiones.setBounds(14, 222, 73, 15);
		panel.add(lblEmisiones);
		
		txtEmisiones = new JTextField();
		txtEmisiones.setFont(new Font("GeosansLight", Font.PLAIN, 16));
		txtEmisiones.setBounds(110, 207, 459, 30);
		panel.add(txtEmisiones);
		txtEmisiones.setColumns(10);
		
		btnAnadir = new JButton("");
		btnAnadir.setBackground(new Color(248, 248, 255));
		btnAnadir.setHorizontalAlignment(SwingConstants.LEFT);
		btnAnadir.setIcon(new ImageIcon(Panel1.class.getResource("/mios/anadir.png")));
		//Se configura el boton añadir para que al pulsarlo cree la sentencia para añadir un nuevo modelo
		btnAnadir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int[] id =new int[5];
			int posicion=0, anadir=0;
			String info2="", valor="";
			
			try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        try {
	            Connection conexion=DriverManager.getConnection(server,user,pass);
	            Statement comando = conexion.createStatement();
	            
	            //Comparamos lo obtenido con getSelectedItem con el numero de posicion .getItemAt, y con esto sacamos la calificacion energetica para poder crear la nueva insercion
		        if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(0)){
		        	valor="A";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(1)){
		        	valor="B";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(2)){
		        	valor="C";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(3)){
		        	valor="D";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(4)){
		        	valor="E";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(5)){
		        	valor="F";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(6)){
		        	valor="G";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(7)){
		        	valor="NA";
		        }
	        
	            //Hacemos la busqueda del ID de la marca a traves del nombre de la marca almacenado en el JComboBox
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
	            
                        //Comprobamos si el nuevo modelo existe y sino, añade mediante la sentencia el modelo a la base de datos
	            if(txtModelo.getText().length()!=0 && txtConsumo.getText().length()!=0 && txtEmisiones.getText().length()!=0){
					info2="insert into modelos (ID_MARCA,MODELO,CONSUMO,EMISIONES,C_ENERGETICA) values ("
	            		+ id[0] + ",'"
	            		+ txtModelo.getText() + "',"		
	            		+ txtConsumo.getText() + ","
	            		+ txtEmisiones.getText() + ",'"
	            		+ valor +"');";
			
					anadir = comando.executeUpdate(info2);
				}else if(txtModelo.getText().length()==0 || txtConsumo.getText().length()==0 || txtEmisiones.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos para creacion del modelo.");
				}
	            
                        //Lanza un mensaje si no ha habido ningun error en la creacion del modelo en la base de datos
	            if (anadir == 1) {
					JOptionPane.showMessageDialog(null, "El modelo ha sido creado correctamente.");
				}
	            
	            // Cerramos la conexion a la base de datos
	            conexion.close();
	            
	            txtModelo.setText("");
				txtConsumo.setText("");
				txtEmisiones.setText("");
	        } catch (SQLException ex) {
	        	JOptionPane.showMessageDialog(null, "Excepci�n de tipo SQL"
	        			+ ".", "Error", JOptionPane.ERROR_MESSAGE);
	        }
		}
		});
		btnAnadir.setBounds(469, 248, 100, 90);
		panel.add(btnAnadir);
		
		txtModelo = new JTextField();
		txtModelo.setFont(new Font("GeosansLight", Font.PLAIN, 16));
		txtModelo.setBounds(110, 147, 459, 30);
		panel.add(txtModelo);
		txtModelo.setColumns(10);
		
		btnBorrar = new JButton("");
		btnBorrar.setBackground(new Color(248, 248, 255));
		btnBorrar.setHorizontalAlignment(SwingConstants.LEFT);
		btnBorrar.setIcon(new ImageIcon(Panel1.class.getResource("/mios/eliminar.png")));
		//Se configura el boton borrar para que al pulsarlo elimine mediante la sentencia el modelo indicado
		btnBorrar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			int[] id =new int[5];
			int posicion=0;
			int eliminar=0;
			String info2 = "";
				
			try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        try {
	            Connection conexion=DriverManager.getConnection(server,user,pass);
	            Statement comando = conexion.createStatement();
	            String valor="";
	            
                        //Se asocia la imagen selecciona con la letra correspondencia
	            if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(0)){
		        	valor="A";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(1)){
		        	valor="B";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(2)){
		        	valor="C";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(3)){
		        	valor="D";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(4)){
		        	valor="E";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(5)){
		        	valor="F";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(6)){
		        	valor="G";
		        }else if(cbCenergetica.getSelectedItem()==cbCenergetica.getItemAt(7)){
		        	valor="NA";
		        }
		            
	            //Hacemos la busqueda del ID de la marca a traves del nombre de la marca almacenado en el JComboBox
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
					
				//Se crean las sentecias para cada uno de los casos posibles, siendo siempre obligatorios los campos de marca y emisiones
				if(txtModelo.getText().length()!=0 && txtConsumo.getText().length()!=0 && txtEmisiones.getText().length()!=0){
					info2="delete from MODELOS where ID_MARCA="
							+ id[0] 
							+ " and MODELO='"
							+ txtModelo.getText()
							+ "' and CONSUMO="
							+ txtConsumo.getText()
							+ " and EMISIONES="
							+ txtEmisiones.getText()
							+ " and C_ENERGETICA='"
							+ valor
							+ "';";
				
					eliminar = comando.executeUpdate(info2);
				}else if(txtModelo.getText().length()!=0 && txtConsumo.getText().length()!=0 && txtEmisiones.getText().length()==0){
					info2="delete from MODELOS where ID_MARCA="
							+ id[0] 
							+ " and MODELO='"
							+ txtModelo.getText()
							+ "' and CONSUMO="
							+ txtConsumo.getText()
							+ " and C_ENERGETICA='"
							+ valor
							+ "';";
			
					eliminar = comando.executeUpdate(info2);
				}else if(txtModelo.getText().length()!=0 && txtConsumo.getText().length()==0 && txtEmisiones.getText().length()!=0){
					info2="delete from MODELOS where ID_MARCA="
							+ id[0] 
							+ " and MODELO='"
							+ txtModelo.getText()
							+ "' and EMISIONES="
							+ txtEmisiones.getText()
							+ " and C_ENERGETICA='"
							+ valor
							+ "';";
			
					eliminar = comando.executeUpdate(info2);
				}else if(txtModelo.getText().length()==0 && txtConsumo.getText().length()!=0 && txtEmisiones.getText().length()!=0){
					info2="delete from MODELOS where ID_MARCA="
							+ id[0]
							+ " and CONSUMO="
							+ txtConsumo.getText()
							+ " and EMISIONES="
							+ txtEmisiones.getText()
							+ " and C_ENERGETICA='"
							+ valor
							+ "';";
			
					eliminar = comando.executeUpdate(info2);
				}else if(txtModelo.getText().length()!=0 && txtConsumo.getText().length()==0 && txtEmisiones.getText().length()==0){
					info2="delete from MODELOS where ID_MARCA="
							+ id[0] 
							+ " and MODELO='"
							+ txtModelo.getText()
							+ "' and C_ENERGETICA='"
							+ valor
							+ "';";
			
					eliminar = comando.executeUpdate(info2);
				}else if(txtModelo.getText().length()==0 && txtConsumo.getText().length()!=0 && txtEmisiones.getText().length()==0){
					info2="delete from MODELOS where ID_MARCA="
							+ id[0] 
							+ " and CONSUMO="
							+ txtConsumo.getText()
							+ " and C_ENERGETICA='"
							+ valor
							+ "';";
			
					eliminar = comando.executeUpdate(info2);
				}else if(txtModelo.getText().length()==0 && txtConsumo.getText().length()==0 && txtEmisiones.getText().length()!=0){
					info2="delete from MODELOS where ID_MARCA="
							+ id[0] 
							+ " and EMISIONES="
							+ txtEmisiones.getText()
							+ " and C_ENERGETICA='"
							+ valor
							+ "';";
			
					eliminar = comando.executeUpdate(info2);
				}else if(txtModelo.getText().length()==0 && txtConsumo.getText().length()==0 && txtEmisiones.getText().length()==0){
					info2="delete from MODELOS where ID_MARCA="
							+ id[0] 
							+ " and C_ENERGETICA='"
							+ valor
							+ "';";
			
					eliminar = comando.executeUpdate(info2);
				}
				//Se lanza el mensaje de si ha sido posible o no borrar el modelo
				if (eliminar == 1) {
					JOptionPane.showMessageDialog(null, "El modelo ha sido eliminado correctamente.");
				} else {
					JOptionPane.showMessageDialog(null, "No existe el modelo indicado.");
				}          
		            
	            // Cerramos la conexion a la base de datos
	            conexion.close();
	            
	            txtModelo.setText("");
				txtConsumo.setText("");
				txtEmisiones.setText("");
				
	            
	        } catch (SQLException ex) {
	        	JOptionPane.showMessageDialog(null, "Excepci�n de tipo SQL"
	        			+ ".", "Error", JOptionPane.ERROR_MESSAGE);
	        }
			}
		});
		btnBorrar.setBounds(469, 342, 100, 90);
		panel.add(btnBorrar);
		
		//Carga las imagenes en JComboBox y crea una matriz
        images = new ImageIcon[array_ce.length];
        Integer[] intArray = new Integer[array_ce.length];
        for (int i = 0; i < array_ce.length; i++) {
            intArray[i] = new Integer(i);
            images[i] = createImageIcon("/mios/" + array_ce[i] + ".gif");
            if (images[i] != null) {
                images[i].setDescription(array_ce[i]);
            }
        }
		
		cbCenergetica= new JComboBox(intArray);
		cbCenergetica.setFont(new Font("GeosansLight", Font.PLAIN, 16));
		cbCenergetica.setBounds(110, 328, 347, 30);
		ComboBoxRenderer renderer= new ComboBoxRenderer();
		renderer.setPreferredSize(new Dimension(100, 25));
		cbCenergetica.setRenderer(renderer);
		cbCenergetica.setMaximumRowCount(3);
		panel.add(cbCenergetica);
		
		JLabel lblCalificacin = new JLabel("Calificación");
		lblCalificacin.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		lblCalificacin.setBounds(14, 328, 89, 15);
		panel.add(lblCalificacin);
		
		JLabel lblEnergetica = new JLabel("Energetica");
		lblEnergetica.setFont(new Font("GeosansLight", Font.PLAIN, 18));
		lblEnergetica.setBounds(14, 348, 89, 18);
		panel.add(lblEnergetica);
	}
	
	
	/**
     * Devuelve un ImageIcon o null si la ruta no es valida.
     * @param path
     * @return 
     */
	protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Panel1.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se encontro el archivo: " + path);
                return null;
        }
    }
	
	
	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
        private Font fuenteError;

        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        /**
         * Busca la imagen y el texto correspondiente mostrando el texto y la imagen
         * @param list
         * @param value
         * @param index
         * @param isSelected
         * @param cellHasFocus
         * @return 
         */
        public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
            
            int selectedIndex = ((Integer)value).intValue();

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            //Selecciona el texto y el icono del comboBox
            ImageIcon icon = images[selectedIndex];
            //Variable de texto para colocar al lado del icono en comboBox de eficiencia
            String cEner = array_ce[selectedIndex];
            setIcon(icon);
            if (icon != null) {
            	//Aqui colocariamos el texto que quisieramos que acompañara al icon en el comboBox
                setText("");		
                setFont(list.getFont());
            } else {
            	noIcono("Imagen no disponible",
                            list.getFont());
            }

            return this;
        }

    /**
     * Metodo para cuando no se encuentran los icionos del comboBox de eficiencia
     * @param txtNoDisponible
     * @param fuente 
     */
    protected void noIcono(String txtNoDisponible, Font fuente) {
        if (fuenteError == null) {
        	fuenteError = fuente.deriveFont(Font.PLAIN);
        }
        setFont(fuenteError);
        setText(txtNoDisponible);
    	}
    }
	
	/**
    * Carga los nombres de las Marcas en JComboBox
    */
	public void cargaMarca(){
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
        	JOptionPane.showMessageDialog(null, "Excepci�n en el c�digo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}