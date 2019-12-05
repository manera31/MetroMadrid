import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import inserts.HibernateUtil;
import inserts.InsertLineaEstacion;
import lists.ConsultaAccesos;
import lists.ConsultaCocheras;
import lists.ConsultaEstaciones;
import lists.ConsultaLineaEstaciones;
import lists.ConsultaLineas;
import lists.ConsultaTrenes;
import lists.ConsultaViajes;
import models.TEstaciones;
import models.TViajes;
import updates.ModificarEstaciones;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Main extends JFrame implements ActionListener{
    private JMenuBar mb;
    private JMenu menuConsultas, menuInserts, menuUpdates;
    private JMenuItem accesos, estaciones, lineaEstaciones, lineas, trenes, viajes, cocheras;
    private JMenuItem insertLineaEstaciones;
    private JMenuItem updateEstaciones;
    private JSeparator separator_1;
    private JLabel lblDatosViajesDestino;
    private JTable tablaDestino, tablaOrigen;
    private JScrollPane scroll;
    private JLabel lblDatosViajesProcedencia;
    private JTable tablaProcedencia;
    private JLabel numLineas;
    private JLabel numAccesos;
    private JLabel numDestino;
    private JLabel numProcedencia;
    private JLabel nombreEstacion;
    private JTextField codigoEstacion;
    private JButton btnPrimer, btnAnterior, btnSiguiente, btnUltimo;
    private int posicion = 0;
    private ArrayList<TEstaciones> arrayEstaciones;
    private ArrayList<TViajes> arrayViajes;
    private DefaultTableModel tabla1, tabla2;
    private JLabel lblNewLabel;
    private JButton btnIr;
    
    public static void main(String[] ar) {
        Main formulario1=new Main();
		formulario1.setBounds(100, 100, 710, 740);
        formulario1.setVisible(true);
    }  
    
    public Main() {
    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JLabel lblCodigoEstacion = new JLabel("CODIGO ESTACIÓN");
        lblCodigoEstacion.setBounds(22, 30, 151, 15);
        getContentPane().add(lblCodigoEstacion);
        
        JLabel lblNombreEstacion = new JLabel("NOMBRE ESTACION");
        lblNombreEstacion.setBounds(300, 30, 151, 15);
        getContentPane().add(lblNombreEstacion);
        
        codigoEstacion = new JTextField("");
        codigoEstacion.setBounds(193, 30, 29, 20);
        getContentPane().add(codigoEstacion);
        
        nombreEstacion = new JLabel("");
        nombreEstacion.setBounds(471, 30, 280, 15);
        getContentPane().add(nombreEstacion);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(35, 57, 642, 2);
        getContentPane().add(separator);
        
        JLabel lblNumLineas = new JLabel("NUM. LINEAS");
        lblNumLineas.setBounds(22, 70, 115, 15);
        getContentPane().add(lblNumLineas);
        
        JLabel lblNumAccesos = new JLabel("NUM. ACCESOS");
        lblNumAccesos.setBounds(22, 100, 125, 15);
        getContentPane().add(lblNumAccesos);
        
        JLabel lblNumViajesDestino = new JLabel("NUM. VIAJES DESTINO");
        lblNumViajesDestino.setBounds(22, 130, 200, 15);
        getContentPane().add(lblNumViajesDestino);
        
        JLabel lblNumViajesProcedencia = new JLabel("NUM. VIAJES PROCEDENCIA");
        lblNumViajesProcedencia.setBounds(22, 160, 200, 15);
        getContentPane().add(lblNumViajesProcedencia);
        
        numLineas = new JLabel("");
        numLineas.setBounds(256, 70, 66, 15);
        getContentPane().add(numLineas);
        
        numAccesos = new JLabel("");
        numAccesos.setBounds(256, 100, 66, 15);
        getContentPane().add(numAccesos);
        
        numDestino = new JLabel("");
        numDestino.setBounds(256, 130, 66, 15);
        getContentPane().add(numDestino);
        
        numProcedencia = new JLabel("");
        numProcedencia.setBounds(256, 160, 66, 15);
        getContentPane().add(numProcedencia);
        
        separator_1 = new JSeparator();
        separator_1.setBounds(35, 190, 642, 2);
        getContentPane().add(separator_1);
        
        lblDatosViajesDestino = new JLabel("DATOS VIAJES DESTINO");
        lblDatosViajesDestino.setBounds(22, 210, 172, 15);
        getContentPane().add(lblDatosViajesDestino);
        
        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(35, 350, 642, 2);
        getContentPane().add(separator_2);
        
        lblDatosViajesProcedencia = new JLabel("DATOS VIAJES PROCEDENCIA");
        lblDatosViajesProcedencia.setBounds(22, 370, 213, 15);
        getContentPane().add(lblDatosViajesProcedencia);
        
        tablaProcedencia = new JTable();
        tablaProcedencia.setBounds(32, 410, 1, 1);
        getContentPane().add(tablaProcedencia);
        mb=new JMenuBar();
        setJMenuBar(mb);
        
        menuConsultas=new JMenu("Consultas");
        mb.add(menuConsultas);
        
        menuInserts = new JMenu("Inserts");
        mb.add(menuInserts);
        
        menuUpdates = new JMenu("Updates");
        mb.add(menuUpdates);
        
        accesos=new JMenuItem("Accesos");
        accesos.addActionListener(this);
        menuConsultas.add(accesos);
        
        cocheras=new JMenuItem("Cocheras");
        cocheras.addActionListener(this);
        menuConsultas.add(cocheras);
        
        estaciones=new JMenuItem("Estaciones");
        estaciones.addActionListener(this);
        menuConsultas.add(estaciones);    

        lineaEstaciones=new JMenuItem("Linea Estaciones");
        lineaEstaciones.addActionListener(this);
        menuConsultas.add(lineaEstaciones);  
        
        lineas=new JMenuItem("Lineas");
        lineas.addActionListener(this);
        menuConsultas.add(lineas);  
        
        trenes=new JMenuItem("Trenes");
        trenes.addActionListener(this);
        menuConsultas.add(trenes);  
        
        viajes=new JMenuItem("Viajes");
        viajes.addActionListener(this);
        menuConsultas.add(viajes); 
        
        insertLineaEstaciones = new JMenuItem("Linea Estaciones");
        insertLineaEstaciones.addActionListener(this);
        menuInserts.add(insertLineaEstaciones);
        
        updateEstaciones = new JMenuItem("Estaciones");
        updateEstaciones.addActionListener(this);
        menuUpdates.add(updateEstaciones);
        
      //BOTONES
      		btnPrimer = new JButton("PRIMER");
      		btnPrimer.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent arg0) {
      				posicion = 0;
      				llenarCampos(0);
      			}
      		});
      		btnPrimer.setEnabled(false);
      		btnPrimer.setBounds(10, 550, 114, 25);
      		getContentPane().add(btnPrimer);
      		
      		btnAnterior = new JButton("ANTERIOR");
      		btnAnterior.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent arg0) {
      				if((posicion - 1) >= 0) {
      					posicion--;
      					llenarCampos(posicion);
      				} else {
      					JOptionPane.showMessageDialog(null, "Primer registro alcanzado");
      				}
      			}
      		});
      		btnAnterior.setEnabled(false);
      		btnAnterior.setBounds(194, 550, 114, 25);
      		getContentPane().add(btnAnterior);
      		
      		btnSiguiente = new JButton("SIGUIENTE");
      		btnSiguiente.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent arg0) {
      				if((posicion + 1) <= (arrayEstaciones.size()-1)) {
      					posicion++;
      					llenarCampos(posicion);
      				} else {
      					JOptionPane.showMessageDialog(null, "Ultimo registro alcanzado");
      				}
      			}
      		});
      		btnSiguiente.setEnabled(false);
      		btnSiguiente.setBounds(372, 550, 114, 25);
      		getContentPane().add(btnSiguiente);
      		
      		btnUltimo = new JButton("ÚLTIMO");
      		btnUltimo.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent arg0) {
      				llenarCampos((arrayEstaciones.size() - 1));
      				posicion = arrayEstaciones.size() - 1;
      			}
      		});
      		btnUltimo.setEnabled(false);
      		btnUltimo.setBounds(550, 550, 114, 25);
      		getContentPane().add(btnUltimo);
      		
      		JButton btnConsultar = new JButton("HACER CONSULTA");
      		btnConsultar.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent arg0) {
      				Session session = HibernateUtil.getSessionFactory().openSession();
      				Transaction transaction = session.beginTransaction();
      				Query<TEstaciones> query = session.createQuery("from TEstaciones");
      				arrayEstaciones = (ArrayList<TEstaciones>) query.list();
      				Query<TViajes> queryViajes = session.createQuery("from TViajes");
      				arrayViajes = (ArrayList<TViajes>) queryViajes.list();
      				transaction.commit();
      				session.close();
      	      		btnPrimer.setEnabled(true);
      	      		btnSiguiente.setEnabled(true);
      	      		btnAnterior.setEnabled(true);
      	      		btnUltimo.setEnabled(true);
      	      		btnIr.setEnabled(true);
      	      		llenarCampos(0);
      			}
      		});
      		btnConsultar.setBounds(200, 590, 300, 70);
      		getContentPane().add(btnConsultar);
      		
      		btnIr = new JButton("IR");
      		btnIr.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent arg0) {
      				boolean aux = false;
    				for(int i = 0 ; i < arrayEstaciones.size() ; i++) {
    					if(Integer.parseInt(codigoEstacion.getText()) == arrayEstaciones.get(i).getCodEstacion()) {
    						llenarCampos(i);
    						posicion = i;
    						aux = true;
    					}
    				}
    				if(!aux) {
    					JOptionPane.showMessageDialog(null, "La ID no se ha encontrado");
    				}
      			}
      		});
      		btnIr.setEnabled(false);
      		btnIr.setBounds(235, 25, 47, 25);
      		getContentPane().add(btnIr);
    }
    public void llenarCampos(int posicion) {
    	codigoEstacion.setText(String.valueOf(arrayEstaciones.get(posicion).getCodEstacion()));
		nombreEstacion.setText(arrayEstaciones.get(posicion).getNombre());
		numAccesos.setText(String.valueOf(arrayEstaciones.get(posicion).getNumaccesos()));
		numLineas.setText(String.valueOf(arrayEstaciones.get(posicion).getNumlineas()));
		numDestino.setText(String.valueOf(arrayEstaciones.get(posicion).getNumviajesdestino()));
		numProcedencia.setText(String.valueOf(arrayEstaciones.get(posicion).getNumviajesprocedencia()));
		
		
		tablaDestino = new JTable();
        tabla1 = new DefaultTableModel();
        tabla1.setColumnIdentifiers(new Object[] {"Codigo", "Nombre"});
        
        tablaOrigen = new JTable();
        tabla2 = new DefaultTableModel();
        tabla2.setColumnIdentifiers(new Object[] {"Codigo", "Nombre"});
        
		for(TViajes v: arrayViajes) {
			if(arrayEstaciones.get(posicion).getCodEstacion() == v.getTEstacionesByEstacionorigen().getCodEstacion()) {
				tabla2.addRow(new Object[] {v.getCodViaje(), v.getNombre()});
			}
			if(arrayEstaciones.get(posicion).getCodEstacion() == v.getTEstacionesByEstaciondestino().getCodEstacion()) {
				tabla1.addRow(new Object[] {v.getCodViaje(), v.getNombre()});
			}
		}
		tablaDestino.setModel(tabla1);
		JScrollPane panel = new JScrollPane(tablaDestino);
        panel.setBounds(32, 240, 500, 100);
        getContentPane().add(panel);
        
        tablaOrigen.setModel(tabla2);
        JScrollPane panel2 = new JScrollPane(tablaOrigen);
        panel2.setBounds(32, 400, 500, 100);
        getContentPane().add(panel2);
    }
    
    public void actionPerformed(ActionEvent e) {
    	Container f=this.getContentPane();
    	
        if (e.getSource()==accesos) {
        	ConsultaAccesos a = new ConsultaAccesos();
        	a.setVisible(true);
        }
        if (e.getSource()==cocheras) {
        	ConsultaCocheras c = new ConsultaCocheras();
        	c.setVisible(true);
        }
        if (e.getSource()==estaciones) {
        	ConsultaEstaciones es = new ConsultaEstaciones();
        	es.setVisible(true);
        }      
        if (e.getSource()==lineaEstaciones) {
        	ConsultaLineaEstaciones le = new ConsultaLineaEstaciones();
        	le.setVisible(true);
        }
        if (e.getSource()==lineas) {
        	ConsultaLineas l = new ConsultaLineas();
        	l.setVisible(true);
        }
        if (e.getSource()==trenes) {
        	ConsultaTrenes t = new ConsultaTrenes();
        	t.setVisible(true);
        }  
        if (e.getSource()==viajes) {
        	ConsultaViajes v = new ConsultaViajes();
        	v.setVisible(true);
        } 
        if(e.getSource() == insertLineaEstaciones) {
        	InsertLineaEstacion insert = new InsertLineaEstacion();
        	insert.setVisible(true);
        }
        if(e.getSource() == updateEstaciones) {
        	ModificarEstaciones update = new ModificarEstaciones();
        	update.setVisible(true);
        }
    }
}