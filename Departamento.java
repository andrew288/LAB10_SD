import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Departamento {
	
	private DefaultTableModel modelo = new DefaultTableModel();
	private ConeccionMYSQL mysql = new ConeccionMYSQL();
	private JButton acciones[] = new JButton[6];
	private int CarFlaAct=0;
	private int CarFlaAdi=0;
	private int CarFlaMod=0;
	private int CarFlaEli=0;
	private int CarFlaCan=0;
	private int CarFlaSal=0;
	
	public void setDataDepartamento() {
		//Añadimos las columnas
		modelo.addColumn("Código");
		modelo.addColumn("Nombre");
		modelo.addColumn("Telefono");
		modelo.addColumn("Fax");
		
		//Realizamos la conexion
		Statement st;
		ResultSet rs;
		try {
			st = mysql.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery("select * from DEPARTAMENTO");
			while(rs.next()) {
				Object[] aux= {rs.getInt("idDpto"),rs.getString("nombre"),rs.getString("telefono"), rs.getString("fax")};
				modelo.addRow(aux);
			}
			mysql.getConn().close();
		} catch(Exception e) {
			System.out.println("ERROR: "+ e);
		}	
	}
	
	public void departamentoAdmin() {
		//VENTANA
		JFrame ventana= new JFrame();
		//propiedades de la ventana
		ventana.setSize(550, 550);
		ventana.setTitle("Departamento");
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
		subtitleA.setText("Registro Departamento");
		JPanel containerHead1= new JPanel();
		containerHead1.setLayout(new BorderLayout());
		containerHead1.add(new JLabel("  "),BorderLayout.WEST);
		containerHead1.add(subtitleA,BorderLayout.CENTER);
		containerHead1.add(new JLabel(" "),BorderLayout.NORTH);
		containerHead1.add(new JLabel(" "),BorderLayout.SOUTH);
		
		//subtitulos
		JLabel cod= new JLabel();
		JLabel nom= new JLabel();
		JLabel tel= new JLabel();
		JLabel fax = new JLabel();
		JTextField input_cod = new JTextField();
		JTextField input_nom = new JTextField();
		JTextField input_tel = new JTextField();
		JTextField input_fax = new JTextField();
		
		cod.setText("Codigo:");
		cod.setFont(new Font("arial",Font.PLAIN,10));
		cod.setBounds(0,10,50,20);
		input_cod.setBounds(100,10,100,20);
		containerInput.add(cod);
		containerInput.add(input_cod);
		
		nom.setText("Nombre:");
		nom.setFont(new Font("arial",Font.PLAIN,10));
		nom.setBounds(0,40,120,20);
		input_nom.setBounds(100,40,200,20);
		containerInput.add(nom);
		containerInput.add(input_nom);
		
		tel.setText("Teléfono:");
		tel.setFont(new Font("arial",Font.PLAIN,10));
		tel.setBounds(0,70,50,20);
		input_tel.setBounds(100,70,100,20);
		containerInput.add(tel);
		containerInput.add(input_tel);
		
		fax.setText("Fax:");
		fax.setFont(new Font("arial",Font.PLAIN,10));
		fax.setBounds(0,100,30,20);
		input_fax.setBounds(100,100,100,20);
		containerInput.add(fax);
		containerInput.add(input_fax);
		
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
		subtitleB.setText("Tabla_Departamento");
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
						if(input_nom.getText().length()!=0 && input_tel.getText().length()!=0 && input_fax.getText().length()!=0){
							PreparedStatement st;
							int cod = modelo.getRowCount();
							String nom, tel, fax;
							nom = input_nom.getText();
							tel = input_tel.getText();
							fax = input_fax.getText();
							Object[] newRow = {cod+1, nom, tel, fax};
							
							try {
								st = mysql.getConn().prepareStatement("INSERT INTO DEPARTAMENTO(nombre,telefono,fax) VALUES(?,?,?)");
								st.setString(1, nom);
								st.setString(2, tel);
								st.setString(3, fax);
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
							String nomFila, telFila, faxFila;
							nomFila = input_nom.getText();
							telFila = input_tel.getText();
							faxFila = input_fax.getText();
							
							try {
								st= mysql.getConn().prepareStatement("UPDATE DEPARTAMENTO SET nombre=?, telefono=?, fax =? WHERE idDpto=?");
								st.setString(1,nomFila);
								st.setString(2, telFila);
								st.setString(3, faxFila);
								st.setInt(4, codFila);
								int retorno = st.executeUpdate();
								
								//aplicamos los cambios en el modelo
								modelo.setValueAt(nomFila, fila,1);
								modelo.setValueAt(telFila, fila, 2);
								modelo.setValueAt(faxFila, fila, 3);
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
								st= mysql.getConn().prepareStatement("DELETE FROM DEPARTAMENTO WHERE idDpto=?");
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
					
					//limpiamos los caudros
					input_cod.setText("");
					input_cod.setEditable(true);
					input_nom.setText("");
					input_nom.setEditable(true);
					input_tel.setText("");
					input_tel.setEditable(true);
					input_fax.setText("");
					input_fax.setEditable(true);
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
				input_nom.setText(null);
				input_tel.setText(null);
				input_fax.setText(null);
				input_cod.setEditable(false);
				input_nom.setEditable(true);
				input_tel.setEditable(true);
				input_fax.setEditable(true);
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
					input_nom.setText(String.valueOf(tablaData.getValueAt(nFila, 1)));
					input_tel.setText(String.valueOf(tablaData.getValueAt(nFila, 2)));
					input_fax.setText(String.valueOf(tablaData.getValueAt(nFila, 3)));
					
					input_cod.setEditable(false);
					input_nom.setEditable(true);
					input_tel.setEditable(true);
					input_fax.setEditable(true);
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
					input_nom.setText(String.valueOf(tablaData.getValueAt(nFila, 1)));
					input_tel.setText(String.valueOf(tablaData.getValueAt(nFila, 2)));
					input_fax.setText(String.valueOf(tablaData.getValueAt(nFila, 3)));
					
					input_cod.setEditable(false);
					input_nom.setEditable(false);
					input_tel.setEditable(false);
					input_fax.setEditable(false);
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
				input_cod.setText("");
				input_cod.setEditable(true);
				input_nom.setText("");
				input_nom.setEditable(true);
				input_tel.setText("");
				input_tel.setEditable(true);
				input_fax.setText("");
				input_fax.setEditable(true);
				//limpiamos las banderas
				limpiarBanderas();
				limpiarBotones();
			}
		});
		
		//accion para salir
		acciones[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Salir");
				input_cod.setText("");
				input_cod.setEditable(false);
				input_nom.setText("");
				input_nom.setEditable(false);
				input_tel.setText("");
				input_tel.setEditable(false);
				input_fax.setText("");
				input_fax.setEditable(false);
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
	
	public Departamento() {
		setDataDepartamento();
		departamentoAdmin();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Departamento();
		
	}

}
