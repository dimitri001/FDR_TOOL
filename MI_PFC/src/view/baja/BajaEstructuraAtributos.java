package view.baja;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import utils.MessageConfirmationUtils;
import utils.MessageUtils;
import view.error.ErrorGenerico;


import controller.BajaController;
import controller.ConsultaController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import libreria.AtributoElemento;
import libreria.ListUniqueID;
import libreria.MyConstants;
import libreria.ParametrosEliminarElemento;
import libreria.ParametrosListaBBDD;

public class BajaEstructuraAtributos extends JFrame implements PropertyChangeListener{

	
	private String[] nombresColum = {"ID Estr. Atributos Elemento",  "ID Elemento Especifico", "ID Sistema Especifico", "ID Serie", "ID Modelo", "ID Fabricante", "Nombre Atributo", "Tipo Atributo", "visible"};
	private JScrollPane scrollPane;	
	private DefaultTableModel modeloTablaEstructuraAtributos;	
	private List <AtributoElemento> listaEstructuraAtributosElemento = new ArrayList<AtributoElemento>();	
	private UUID miIdEstructuraAtributos;//Identificador unico de cada objeto
	private ConsultaController miConsultaController;
	private BajaController miBajaController;	
	public static final String PROP_LISTAR_ESTRUCTURA_ATRIBUTO = "listarEstructuraAtributo";
	public static final String PROP_ELIMINAR_SELECCION_ESTRUCTURA_ATRIBUTO = "eliminarEstructuraAtributo";	
	private List<Object []> dataEstructuraAtributos = new ArrayList();  
	private Object idEstructuraSeleccionado;//Es el objeto perteneciente al elemento seleccionado en la tabla
	private Object idElementoEspecificoSeleccionado;//Es el objeto perteneciente al elemento especifico al que esta estructura de atributos pertecen
	private List <Object> listaIdEstructuraSeleccionado = new ArrayList(); //Son los objetos pertenecientes a los elementos seleccionados en la tabla
	private JTable tblEstructuraAtributos;	
	private int intIdFabricante;
	private int intIdModelo;
	private int intIdSerie;
	private int intIdSistemaEspecifico;	
	private int intIdElementoEspecifico;	
	private UUID idElementoEspecifico;
	private ListUniqueID listUniqueIdBajaElementoEspecifico;
	private LinkedHashSet<Integer> myUniqueListID;
	private boolean pintaTabla = false;
	private int counterVisibleRowTrue = 0;//Cuenta las filas que tienen el atributo visible = true, de entre las filas seleccionadas
	public BajaEstructuraAtributos(BajaController bajaController, ConsultaController consultaController, 
			UUID idEstructuraAtributos,int intIdFabricante,int intIdModelo,int intIdSerie,
			int intIdSistemaEspecifico,int intIdElementoEspecifico, UUID idElementoEspecifico, ListUniqueID listUniqueIdBajaElementoEspecifico){
		miBajaController = bajaController;		
		miConsultaController = consultaController;		
		miIdEstructuraAtributos = idEstructuraAtributos;
		this.intIdFabricante = intIdFabricante;
		this.intIdModelo = intIdModelo;
		this.intIdSerie = intIdSerie;
		this.intIdSistemaEspecifico = intIdSistemaEspecifico;
		this.intIdElementoEspecifico = intIdElementoEspecifico;
		this.idElementoEspecifico = idElementoEspecifico;
		this.listUniqueIdBajaElementoEspecifico = listUniqueIdBajaElementoEspecifico;
		//this.myUniqueListID = myUniqueListID;
		setTitle("Baja Estructura Atributos");
		//Inicializacion de los arrays del arrayList
		for (int i=0;i<1;i++ ){
			Object [] dataArray = {"  ","  ","  ","  ","  ","  ","  ","  "};
			dataEstructuraAtributos.add(dataArray);	
		}			
		getContentPane().setLayout(null);		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 812, 323);
		getContentPane().add(scrollPane);	
		Object [][] data = new Object [dataEstructuraAtributos.size()][];
		dataEstructuraAtributos.toArray(data);
		
		modeloTablaEstructuraAtributos = new DefaultTableModel( data , nombresColum){			
			@Override
			public boolean isCellEditable (int row, int column){				
				//all cells false
				return false;
			}
		};
		tblEstructuraAtributos = new JTable(modeloTablaEstructuraAtributos);
		tblEstructuraAtributos.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblEstructuraAtributos);		
		tblEstructuraAtributos.addMouseListener(new MouseAdapter(){
			public void mouseClicked (MouseEvent e){
				counterVisibleRowTrue =0;
				int fila = tblEstructuraAtributos.rowAtPoint(e.getPoint());
				int columna = 0;	
				int columnaVisible = 8;
				int []filas =  tblEstructuraAtributos.getSelectedRows(); 						
				listaIdEstructuraSeleccionado = new ArrayList<Object>();				
				//Varias Filas
				if (filas.length > 0){					
					for (int i = 0; i < filas.length ; i++){	
						boolean varVisible ;
						//Se guarda la lista de los id seleccionados
						listaIdEstructuraSeleccionado.add(modeloTablaEstructuraAtributos.getValueAt(filas[i], columna)); //inicializar ? new ArrayList();
						Object aux = modeloTablaEstructuraAtributos.getValueAt(filas[i], columna);						
						varVisible = (Boolean) modeloTablaEstructuraAtributos.getValueAt(filas[i], columnaVisible);
						if (varVisible)
							counterVisibleRowTrue++;					
					}					
				} 	
			}
		}
		);
		
		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnBaja_actionPerformed(arg0);
			}
		});
		btnBaja.setBounds(207, 345, 105, 23);
		getContentPane().add(btnBaja);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(519, 345, 105, 23);
		getContentPane().add(btnCancelar);		
		setVisible(false);  
		setSize(848, 417);		
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });
		
	}
	

	protected void do_btnBaja_actionPerformed(ActionEvent arg0) {	
		
		if (listaIdEstructuraSeleccionado.size() > 0){
			int response = MessageConfirmationUtils.deleteRowsMessageConfirmation();    
			if (response == JOptionPane.YES_OPTION) {
				miBajaController.eliminarEstructuraAtributo(listaIdEstructuraSeleccionado, miIdEstructuraAtributos,(Integer)idElementoEspecificoSeleccionado, counterVisibleRowTrue);
				miConsultaController.listaEstructuraAtributos((Integer)idElementoEspecificoSeleccionado, miIdEstructuraAtributos, true);
				miConsultaController.listaElementoEspecifico(intIdFabricante, intIdModelo,intIdSerie,intIdSistemaEspecifico, idElementoEspecifico, true);
				listaIdEstructuraSeleccionado.removeAll(listaIdEstructuraSeleccionado);			
			}		
		}	
	}
	
	
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {
		dispose();
		boolean uno = listUniqueIdBajaElementoEspecifico.getUniqueListID().remove(intIdElementoEspecifico);
		miConsultaController.getConsultasBBDD().removePropertyChangeListener(PROP_LISTAR_ESTRUCTURA_ATRIBUTO, this);
		miBajaController.getBorrarElemento().removePropertyChangeListener(PROP_ELIMINAR_SELECCION_ESTRUCTURA_ATRIBUTO, this);
		
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {		
		String propertyName = evt.getPropertyName();		
		ParametrosListaBBDD parametrosListaBBDD;
		ParametrosEliminarElemento parametrosEliminarElemento;	

		if (PROP_LISTAR_ESTRUCTURA_ATRIBUTO.equals(propertyName)){		 
			//OJO idElementoEspecificoSeleccionado =0;//Asi cada vez que se lista, se deselecciona el elemento seleccionado previamente
			parametrosListaBBDD = (ParametrosListaBBDD) evt.getNewValue();			
			List<Object> dataEstructuraAtributosBBDD ;
			if (parametrosListaBBDD.isActualizar())
				miConsultaController.listaEstructuraAtributos(intIdElementoEspecifico, miIdEstructuraAtributos, false);
			else if (parametrosListaBBDD.getIdElemento().equals(miIdEstructuraAtributos)){ //Se comprueba el id del objeto Estructura Frame
				// hace lo necesario para refrescar la tabla
				dataEstructuraAtributosBBDD = (ArrayList<Object>) parametrosListaBBDD.getDataListaBBDD();
				dataEstructuraAtributos.removeAll(dataEstructuraAtributos);//Se borran datos antiguos de la lista					
				int length = dataEstructuraAtributosBBDD.size();
				if (length>0){
					//Se añaden los nuevos datos, de la query, a la lista
					Iterator it = dataEstructuraAtributosBBDD.iterator();
					while (it.hasNext()){
						AtributoElemento estructuraAtributosElemento = (AtributoElemento) it.next();
						dataEstructuraAtributos.add(estructuraAtributosElemento.getArray());//Añade los Arrays cons los datos del elemento a la List
					}				
					this.setVisible(true);
					pintaTabla = true;
				}else if (!pintaTabla){
					String mensaje = "El Elemento de Sistema Específico escogido no tiene atributos asociados.";
					ErrorGenerico errorGenerico = new ErrorGenerico(MyConstants.StringConstant.BAJA.value(),mensaje );			
					this.do_btnCancelar_actionPerformed(null);
				}
				actualizaTabla((ArrayList<Object[]>) dataEstructuraAtributos, modeloTablaEstructuraAtributos);					
			}				
		}else if (PROP_ELIMINAR_SELECCION_ESTRUCTURA_ATRIBUTO.equals(propertyName)){				 
				parametrosEliminarElemento = (ParametrosEliminarElemento) evt.getNewValue();				
				//Se comprueba el id del objeto Modelo
				if (parametrosEliminarElemento.getIdElemento().equals(miIdEstructuraAtributos)){
					MessageUtils.mensajeEliminarSeleccionFila(parametrosEliminarElemento);
				}				
		}	
	}
	
	/*
	 * Actualiza el Jtable con las bases de datos que puede usar el usuario. 
	 * */
	private void actualizaTabla(ArrayList<Object []> dataLista, DefaultTableModel modeloTabla){		
		Object [][] data1 = new Object [dataLista.size()][];
		dataLista.toArray(data1);
		modeloTabla.setDataVector(data1 , nombresColum);
		//Set width of a column 
		tblEstructuraAtributos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblEstructuraAtributos.getColumnModel().getColumn(0).setPreferredWidth(150);
		tblEstructuraAtributos.getColumnModel().getColumn(1).setPreferredWidth(130);
		tblEstructuraAtributos.getColumnModel().getColumn(2).setPreferredWidth(130);
		tblEstructuraAtributos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tblEstructuraAtributos.getColumnModel().getColumn(4).setPreferredWidth(90);
		tblEstructuraAtributos.getColumnModel().getColumn(5).setPreferredWidth(100);
		tblEstructuraAtributos.getColumnModel().getColumn(6).setPreferredWidth(100);
		tblEstructuraAtributos.getColumnModel().getColumn(7).setPreferredWidth(100);
		tblEstructuraAtributos.getColumnModel().getColumn(8).setPreferredWidth(60);
		tblEstructuraAtributos.moveColumn(8, 0);//Así visible es la primera columna
		int columnaElemento = 1;
		int filaElemento = 0;
		if (modeloTabla.getRowCount()>0)
			idElementoEspecificoSeleccionado= modeloTabla.getValueAt(filaElemento, columnaElemento);
	}

	
	/*
	 * Cambiar el id de este objeto.
	 * @param idEstructuraAtributos, es una UUID (identificador unico)
	 * */	
	public void setIdEstructuraAtributos(UUID idEstructuraAtributos){		
		miIdEstructuraAtributos = idEstructuraAtributos;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdEstructuraAtributos(){
		
		return miIdEstructuraAtributos;
	}

}
