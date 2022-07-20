import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VistaIngenieros {
	
	private DefaultTableModel modelo = new DefaultTableModel();
	private Map<String, String> proyectos = new HashMap<>();
	private ConeccionMYSQL mysql = new ConeccionMYSQL();

	public void setDataIngeniero() {
		
		//Añadimos las columnas
		modelo.addColumn("Código");
		modelo.addColumn("Código\n Proyecto");
		modelo.addColumn("Especialidad");
		modelo.addColumn("Cargo");
		
		//Realizamos la conexion
		Statement st;
		ResultSet rs;
		try {
			st = mysql.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery("select * from PROYECTO");
			while(rs.next()) {
				proyectos.put(rs.getString("idProy"), rs.getString("nombre"));
			}
		} catch(Exception e) {
			System.out.println("ERROR: "+ e);
		}
		
	}
	
	public void filtroVistaIngenieros() {
		//VENTANA
		JFrame ventana= new JFrame();
		//propiedades de la ventana
		ventana.setSize(550, 550);
		ventana.setTitle("Ingeniero");
		ventana.setDefaultCloseOperation(ventana.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null);
		ventana.setLayout( new GridLayout(3,1,0,10));
		
		//PANELES
		JPanel panelA = new JPanel();
		JPanel panelB = new JPanel();
		
		//BOTONES
		JButton filtrar = new JButton("Filtrar");
		filtrar.setFont(new Font("arial",Font.PLAIN,10));
		
		//PANEL A
		panelA.setLayout(new BorderLayout());
		JPanel containerInput = new JPanel();
		containerInput.setLayout(null);
		JLabel subtitleA= new JLabel();
		subtitleA.setText("Vista Ingenieros");
		JPanel containerHead1= new JPanel();
		containerHead1.setLayout(new BorderLayout());
		containerHead1.add(new JLabel("  "),BorderLayout.WEST);
		containerHead1.add(subtitleA,BorderLayout.CENTER);
		containerHead1.add(new JLabel(" "),BorderLayout.NORTH);
		containerHead1.add(new JLabel(" "),BorderLayout.SOUTH);
		
		//subtitulos
		JLabel proyecto= new JLabel();
		JComboBox<String> input_codProy = new JComboBox<String>();
		for (HashMap.Entry<String, String> entry : proyectos.entrySet()) {
			input_codProy.addItem(entry.getKey() + "-" + entry.getValue());
		}
		
		proyecto.setText("Proyectos:");
		proyecto.setFont(new Font("arial",Font.PLAIN,10));
		proyecto.setBounds(0,10,100,20);
		input_codProy.setBounds(120,10,400,20);
		input_codProy.setSelectedItem(null);
		input_codProy.setEditable(false);
		filtrar.setBounds(200,60,100,30);
		containerInput.add(proyecto);
		containerInput.add(input_codProy);
		containerInput.add(filtrar);
		
		panelA.add(containerHead1, BorderLayout.NORTH);
		panelA.add(new JLabel("   "),BorderLayout.WEST);
		panelA.add(containerInput,BorderLayout.CENTER);
		panelA.add(new JLabel("   "),BorderLayout.EAST);
		
		//PANEL B
		panelB.setLayout(new BorderLayout());
		//grilla
		JTable tablaData= new JTable(this.modelo);
		JPanel contenedorTable= new JPanel();
		contenedorTable.setLayout(new BorderLayout());
		contenedorTable.add(new JLabel(" "), BorderLayout.NORTH);
		contenedorTable.add(new JScrollPane(tablaData),BorderLayout.CENTER);
		//subtitulo
		JLabel subtitleB= new JLabel();
		subtitleB.setText("Tabla_Ingenieros");
		JPanel containerHead2= new JPanel();
		containerHead2.setLayout(new BorderLayout());
		containerHead2.add(new JLabel("  "),BorderLayout.WEST);
		containerHead2.add(subtitleB,BorderLayout.CENTER);
		
		panelB.add(new JLabel("   "),BorderLayout.WEST);
		panelB.add(new JLabel("   "),BorderLayout.EAST);
		panelB.add(containerHead2,BorderLayout.NORTH);
		panelB.add(contenedorTable,BorderLayout.CENTER);
				
		ventana.getContentPane().add(panelA);
		ventana.getContentPane().add(panelB);
		ventana.setVisible(true);
		
		filtrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(input_codProy.getSelectedItem()!=null) {
					String codDptoS = input_codProy.getSelectedItem().toString();
					int codDpto = Integer.valueOf(codDptoS.substring(0,codDptoS.indexOf("-")));
					try {
						removerFilas();
						PreparedStatement st;
						ResultSet rs;
						
						st= mysql.getConn().prepareStatement("SELECT * FROM INGENIERO WHERE idProy=?");
						st.setInt(1, codDpto);
						rs = st.executeQuery();
								
						while(rs.next()) {
							Object[] aux= {rs.getInt("idIng"), rs.getString("idProy"),rs.getString("especialidad"),rs.getString("cargo")};
							modelo.addRow(aux);
						}
						tablaData.setModel(modelo);
						modelo.fireTableDataChanged();
						//cerramos la conexion
						mysql.getConn().close();
						
					} catch (Exception el){
						System.out.println("Error: "+el);
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Seleccione un ṕroyecto");
				}
			}
		});
	}
	
	public void removerFilas() {
		DefaultTableModel dm = (DefaultTableModel) this.modelo;
		int rowCount = dm.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
	}
	
	public VistaIngenieros() {
		setDataIngeniero();
		filtroVistaIngenieros();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new VistaIngenieros();
	}

}
