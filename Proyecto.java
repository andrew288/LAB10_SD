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

public class Proyecto {
	
	private DefaultTableModel modelo = new DefaultTableModel();
	private Map<String, String> departamentos = new HashMap<>();
	private ConeccionMYSQL mysql = new ConeccionMYSQL();
	private JButton acciones[] = new JButton[6];
	private int CarFlaAct=0;
	private int CarFlaAdi=0;
	private int CarFlaMod=0;
	private int CarFlaEli=0;
	private int CarFlaCan=0;
	private int CarFlaSal=0;
	
	public void setDataProyecto() {
		//Añadimos las columnas
		modelo.addColumn("Código");
		modelo.addColumn("Código\n departamento");
		modelo.addColumn("Nombre");
		modelo.addColumn("Fecha inicio");
		modelo.addColumn("Fecha final");
		
		//Realizamos la conexion
		Statement st;
		ResultSet rs;
		try {
			st = mysql.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery("select * from PROYECTO");
			while(rs.next()) {
				Object[] aux= {rs.getInt("idProy"), rs.getString("idDpto"),rs.getString("nombre"),rs.getString("fec_inicio"), rs.getString("fec_final")};
				modelo.addRow(aux);
			}
			mysql.getConn().close();
		} catch(Exception e) {
			System.out.println("ERROR: "+ e);
		}
		
		try {
			st = mysql.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery("select * from DEPARTAMENTO");
			while(rs.next()) {
				departamentos.put(rs.getString("idDpto"), rs.getString("nombre"));
			}
		} catch(Exception e) {
			System.out.println("ERROR: "+ e);
		}
		
	}
	
	public void proyectoAdmin() {
		//VENTANA
		JFrame ventana= new JFrame();
		//propiedades de la ventana
		ventana.setSize(550, 650);
		ventana.setTitle("Proyecto");
		ventana.setDefaultCloseOperation(ventana.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null);
		ventana.setLayout( new GridLayout(3,1,0,10));
//		ventana.setVisible(true);
		//paneles
		JPanel panelA = new JPanel();
		JPanel panelB = new JPanel();
		JPanel panelC = new JPanel();
		
		//PANEL A
		panelA.setLayout(new BorderLayout());
		JPanel containerInput = new JPanel();
		containerInput.setLayout(null);
		JLabel subtitleA= new JLabel();
		subtitleA.setText("Registro Proyecto");
		JPanel containerHead1= new JPanel();
		containerHead1.setLayout(new BorderLayout());
		containerHead1.add(new JLabel("  "),BorderLayout.WEST);
		containerHead1.add(subtitleA,BorderLayout.CENTER);
		containerHead1.add(new JLabel(" "),BorderLayout.NORTH);
		containerHead1.add(new JLabel(" "),BorderLayout.SOUTH);
		
		//subtitulos
		JLabel cod= new JLabel();
		JLabel codDpto = new JLabel();
		JLabel nom= new JLabel();
		JLabel fec_inicio= new JLabel();
		JLabel fec_final = new JLabel();
		JTextField input_cod = new JTextField();
		JComboBox<String> input_codDpto = new JComboBox<String>();
		for (HashMap.Entry<String, String> entry : departamentos.entrySet()) {
			input_codDpto.addItem(entry.getKey() + "-" + entry.getValue());
		}
		JTextField input_nom = new JTextField();
		JTextField input_fec_inicio = new JTextField();
		JTextField input_fec_final = new JTextField();
		
		cod.setText("Codigo:");
		cod.setFont(new Font("arial",Font.PLAIN,10));
		cod.setBounds(0,10,50,20);
		input_cod.setBounds(120,10,100,20);
		containerInput.add(cod);
		containerInput.add(input_cod);
		
		codDpto.setText("Codigo\n departamento:");
		codDpto.setFont(new Font("arial",Font.PLAIN,10));
		codDpto.setBounds(0,40,120,20);
		input_codDpto.setBounds(120,40,300,20);
		input_codDpto.setSelectedItem(null);
		input_codDpto.setEditable(false);
		containerInput.add(codDpto);
		containerInput.add(input_codDpto);
		
		nom.setText("Nombre:");
		nom.setFont(new Font("arial",Font.PLAIN,10));
		nom.setBounds(0,70,50,20);
		input_nom.setBounds(120,70,400,20);
		containerInput.add(nom);
		containerInput.add(input_nom);
		
		fec_inicio.setText("Fecha\n inicio:");
		fec_inicio.setFont(new Font("arial",Font.PLAIN,10));
		fec_inicio.setBounds(0,100,60,20);
		input_fec_inicio.setBounds(120,100,100,20);
		containerInput.add(fec_inicio);
		containerInput.add(input_fec_inicio);
		
		fec_final.setText("Fecha\n final:");
		fec_final.setFont(new Font("arial",Font.PLAIN,10));
		fec_final.setBounds(0,130,60,20);
		input_fec_final.setBounds(120,130,100,20);
		containerInput.add(fec_final);
		containerInput.add(input_fec_final);
		
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
		subtitleB.setText("Tabla_Proyecto");
		JPanel containerHead2= new JPanel();
		containerHead2.setLayout(new BorderLayout());
		containerHead2.add(new JLabel("  "),BorderLayout.WEST);
		containerHead2.add(subtitleB,BorderLayout.CENTER);
		
		panelB.add(new JLabel("   "),BorderLayout.WEST);
		panelB.add(new JLabel("   "),BorderLayout.EAST);
		panelB.add(containerHead2,BorderLayout.NORTH);
		panelB.add(contenedorTable,BorderLayout.CENTER);
		
		//PANEL C
		panelC.setLayout(new BorderLayout());
		JPanel contenedorBt= new JPanel();
		contenedorBt.setLayout(new GridLayout(2,3,15,20));
		
		//propiedades de botones
		JButton adicionar= new JButton("Adicionar");
		adicionar.setFont(new Font("arial",Font.PLAIN,10));
		acciones[0]=adicionar;
		JButton modificar= new JButton("Modificar");
		modificar.setFont(new Font("arial",Font.PLAIN,10));
		acciones[1]=modificar;
		JButton eliminar= new JButton("Eliminar");
		eliminar.setFont(new Font("arial",Font.PLAIN,10));
		acciones[2]=eliminar;
		JButton cancelar= new JButton("Cancelar");
		cancelar.setFont(new Font("arial",Font.PLAIN,10));
		acciones[3]=cancelar;
		JButton actualizar= new JButton("Ejecutar");
		actualizar.setFont(new Font("arial",Font.PLAIN,10));
		acciones[4]=actualizar;
		JButton salir= new JButton("Salir");
		salir.setFont(new Font("arial",Font.PLAIN,10));
		acciones[5]=salir;
		
		//agregamos los botones
		for(int i=0;i<acciones.length;i++) {
			contenedorBt.add(acciones[i]);
		}
		JLabel subtitleC= new JLabel();
		subtitleC.setText("Acciones");
		JPanel containerHead3= new JPanel();
		containerHead3.setLayout(new BorderLayout());
		containerHead3.add(new JLabel("  "),BorderLayout.WEST);
		containerHead3.add(subtitleC,BorderLayout.CENTER);
		containerHead3.add(new JLabel(" "),BorderLayout.SOUTH);
		//añadimos componentes
		panelC.add(new JLabel("      "), BorderLayout.WEST);
		panelC.add(new JLabel("      "),BorderLayout.EAST);
		panelC.add(containerHead3, BorderLayout.NORTH);
		panelC.add(new JLabel("   "), BorderLayout.SOUTH);
		panelC.add(contenedorBt,BorderLayout.CENTER);
		
		//agregamos los componentes principales
		ventana.getContentPane().add(panelA);
		ventana.getContentPane().add(panelB);
		ventana.getContentPane().add(panelC);
		ventana.setVisible(true);
		//ACCIONES
		acciones[4].addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CarFlaAct != 0) {
					// ADICIONAR
					if(CarFlaAdi==1) {
						if(input_nom.getText().length()!=0 && input_fec_inicio.getText().length()!=0 && input_fec_final.getText().length()!=0){
							PreparedStatement st;
							int cod = modelo.getRowCount();
							String codDptoS = input_codDpto.getSelectedItem().toString();
							int codDpto = Integer.valueOf(codDptoS.substring(0,codDptoS.indexOf("-")));
							String nom, fec_inicio, fec_final;
							nom = input_nom.getText();
							fec_inicio = input_fec_inicio.getText();
							fec_final = input_fec_final.getText();
							Object[] newRow = {cod+1, codDpto, nom, fec_inicio, fec_final};
							
							try {
								st = mysql.getConn().prepareStatement("INSERT INTO PROYECTO(idDpto,nombre,fec_inicio,fec_final) VALUES(?,?,?,?)");
								st.setInt(1, codDpto);
								st.setString(2, nom);
								st.setString(3, fec_inicio);
								st.setString(4, fec_final);
								int rs= st.executeUpdate();
								modelo.addRow(newRow);
								tablaData.setModel(modelo);
								modelo.fireTableDataChanged();
								//cerramos la conexion
								mysql.getConn().close();
							} catch (Exception e1){
								System.out.println("Error: "+e1);
							}
							//desactivamos el boton Adicionar
							CarFlaAdi=0;
							CarFlaAct=0;
						} else {
							JOptionPane.showMessageDialog(null, "Debes completar los campos");
						}
					}
					
					//MODIFICAR
					if(CarFlaMod==1) {
						PreparedStatement st;
						int fila=tablaData.getSelectedRow();
						if(fila>=0) {
							int codFila=Integer.parseInt(input_cod.getText());
							String codDptoS = input_codDpto.getSelectedItem().toString();
							int codDpto = Integer.valueOf(codDptoS.substring(0,codDptoS.indexOf("-")));
							String nom, fec_inicio, fec_final;
							nom = input_nom.getText();
							fec_inicio = input_fec_inicio.getText();
							fec_final = input_fec_final.getText();
							
							try {
								st= mysql.getConn().prepareStatement("UPDATE PROYECTO SET idDpto=?, nombre=?, fec_inicio=?, fec_final=? WHERE idProy=?");
								st.setInt(1, codDpto);
								st.setString(2,nom);
								st.setString(3, fec_inicio);
								st.setString(4, fec_final);
								st.setInt(5, codFila);
								int retorno = st.executeUpdate();
								
								//aplicamos los cambios en el modelo
								modelo.setValueAt(codDpto, fila, 1);
								modelo.setValueAt(nom, fila,2);
								modelo.setValueAt(fec_inicio, fila, 3);
								modelo.setValueAt(fec_final, fila, 4);
								tablaData.setModel(modelo);
								modelo.fireTableDataChanged();
								//cerramos la conexion
								mysql.getConn().close();
							} catch (Exception el){
								System.out.println("Error: "+el);
							}
							CarFlaMod=0;
							CarFlaAct=0;
						} else {
							JOptionPane.showMessageDialog(null, "Seleccione un registro");
						}
					}
					
					//ELIMINAR
					if(CarFlaEli==1) {
						//preparamos la coneccion
						ConeccionMYSQL mysql = new ConeccionMYSQL();
						PreparedStatement st;
						int fila=tablaData.getSelectedRow();
						if(fila>=0) {
							int codFila=Integer.parseInt(input_cod.getText());
							try {
								st= mysql.getConn().prepareStatement("DELETE FROM PROYECTO WHERE idProy=?");
								st.setInt(1, codFila);
								int retorno = st.executeUpdate();
								//aplicamos los cambios en el modelo
								modelo.removeRow(fila);
								tablaData.setModel(modelo);
								modelo.fireTableDataChanged();
								//cerramos la conexion
								mysql.getConn().close();
							} catch (Exception el){
								System.out.println("Error: "+el);
							}
							CarFlaEli=0;
							CarFlaAct=0;
						} else {
							JOptionPane.showMessageDialog(null, "Seleccione un registro");
						}
					}
					
					//limpiamos los cuadros
					input_cod.setText(null);
					input_cod.setEditable(true);
					input_codDpto.setSelectedItem(null);
					input_nom.setText(null);
					input_nom.setEditable(true);
					input_fec_inicio.setText(null);
					input_fec_inicio.setEditable(true);
					input_fec_final.setText(null);
					input_fec_final.setEditable(true);
					limpiarBotones();
				}
				else {
					JOptionPane.showMessageDialog(null, "Seleccione una acción");
				}
				
			}
		});
		
		//accion para adicionar
		acciones[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("Añadir");
				//vaciamos los inputs
				input_cod.setText(null);
				input_cod.setEditable(false);
				input_codDpto.setSelectedItem(null);
				input_nom.setText(null);
				input_nom.setEditable(true);
				input_fec_inicio.setText(null);
				input_fec_inicio.setEditable(true);
				input_fec_final.setText(null);
				input_fec_final.setEditable(true);
				CarFlaAct=1;
				CarFlaAdi=1;
				CarFlaMod=0;
				CarFlaEli=0;
				limpiarBotones();
				acciones[0].setBackground(Color.lightGray);
			}
		});
		
		//accion para modificar
		acciones[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Modificar");
				int nFila = tablaData.getSelectedRow();
				if(nFila>=0) {
					input_cod.setText(String.valueOf(tablaData.getValueAt(nFila, 0)));
					input_codDpto.setSelectedItem(String.valueOf(tablaData.getValueAt(nFila, 1))+"-"+departamentos.get(String.valueOf(tablaData.getValueAt(nFila, 1))));
					input_nom.setText(String.valueOf(tablaData.getValueAt(nFila, 2)));
					input_fec_inicio.setText(String.valueOf(tablaData.getValueAt(nFila, 3)));
					input_fec_final.setText(String.valueOf(tablaData.getValueAt(nFila, 4)));
					
					input_cod.setEditable(false);
					input_nom.setEditable(true);
					input_fec_inicio.setEditable(true);
					input_fec_final.setEditable(true);
					
					CarFlaMod=1;
					CarFlaAct=1;
					CarFlaAdi=0;
					CarFlaEli=0;
					limpiarBotones();
					acciones[1].setBackground(Color.lightGray);
				}
				else {
					JOptionPane.showMessageDialog(null, "Seleccione un registro");
				}
			}
		});
		
		//accion para eliminar
		acciones[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Eliminar");
				int nFila = tablaData.getSelectedRow();
				if(nFila>=0) {
					input_cod.setText(String.valueOf(tablaData.getValueAt(nFila, 0)));
					input_codDpto.setSelectedItem(String.valueOf(tablaData.getValueAt(nFila, 1))+"-"+departamentos.get(String.valueOf(tablaData.getValueAt(nFila, 1))));
					input_nom.setText(String.valueOf(tablaData.getValueAt(nFila, 2)));
					input_fec_inicio.setText(String.valueOf(tablaData.getValueAt(nFila, 3)));
					input_fec_final.setText(String.valueOf(tablaData.getValueAt(nFila, 4)));
					
					input_cod.setEditable(false);
					input_nom.setEditable(false);
					input_fec_inicio.setEditable(false);
					input_fec_final.setEditable(false);
					
					CarFlaEli=1;
					CarFlaAct=1;
					CarFlaAdi=0;
					CarFlaMod=0;
					limpiarBotones();
					acciones[2].setBackground(Color.lightGray);
				}
				else {
					JOptionPane.showMessageDialog(null, "Seleccione un registro");
				}
			}
		});
		
		//accion para cancelar
		acciones[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancelar");
				//limpiamos los caudros
				input_cod.setText(null);
				input_cod.setEditable(true);
				input_codDpto.setSelectedItem(null);
				input_nom.setText(null);
				input_nom.setEditable(true);
				input_fec_inicio.setText(null);
				input_fec_inicio.setEditable(true);
				input_fec_final.setText(null);
				input_fec_final.setEditable(true);
				//limpiamos las banderas
				limpiarBanderas();
				limpiarBotones();
			}
		});
		
		//accion para salir
		acciones[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Salir");
				input_cod.setText(null);
				input_cod.setEditable(false);
				input_codDpto.setSelectedItem(null);
				input_codDpto.setEditable(false);
				input_nom.setText(null);
				input_nom.setEditable(false);
				input_fec_inicio.setText(null);
				input_fec_inicio.setEditable(false);
				input_fec_final.setText(null);
				input_fec_final.setEditable(false);
				limpiarBanderas();
				System.exit(0);
			}
		});
		
	}
	
	private void limpiarBotones() {
		acciones[0].setBackground(null);
		acciones[1].setBackground(null);
		acciones[2].setBackground(null);
	}
	
	private void limpiarBanderas() {
		CarFlaAct=0;
		CarFlaAdi=0;
		CarFlaMod=0;
		CarFlaEli=0;
		CarFlaCan=0;
		CarFlaSal=0;
	}
	
	public Proyecto() {
		setDataProyecto();
		proyectoAdmin();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Proyecto();
	}

}
