package lists;

import java.awt.BorderLayout;
import inserts.HibernateUtil;
import models.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ConsultaTrenes extends JFrame {

	private JPanel contentPane;
	private ArrayList<TTrenes> trenes;
	private JTextField numTren;
	private JLabel nombre;
	private JLabel tipo;
	private JLabel linea;
	private JLabel cochera;
	private JButton btnLanzarConsulta;
	private JButton btnCancelarConsulta;
	private JButton btnPrimer, btnUltimo, btnSiguiente, btnAnterior, btnIr;
	private int posicion = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaTrenes frame = new ConsultaTrenes();
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
	public ConsultaTrenes() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConusltaTrenes = new JLabel("CONUSLTA TRENES");
		lblConusltaTrenes.setBounds(30, 40, 160, 15);
		contentPane.add(lblConusltaTrenes);
		
		JLabel lblCodTren = new JLabel("COD TREN");
		lblCodTren.setBounds(30, 100, 88, 15);
		contentPane.add(lblCodTren);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(30, 130, 66, 15);
		contentPane.add(lblNombre);
		
		JLabel lblTipo = new JLabel("TIPO");
		lblTipo.setBounds(30, 160, 66, 15);
		contentPane.add(lblTipo);
		
		JLabel lblLinea = new JLabel("LINEA");
		lblLinea.setBounds(30, 190, 66, 15);
		contentPane.add(lblLinea);
		
		JLabel lblCochera = new JLabel("COCHERA");
		lblCochera.setBounds(30, 220, 66, 15);
		contentPane.add(lblCochera);
		
		numTren = new JTextField("");
		numTren.setBounds(188, 100, 66, 20);
		contentPane.add(numTren);
		
		nombre = new JLabel("");
		nombre.setBounds(188, 130, 139, 15);
		contentPane.add(nombre);
		
		tipo = new JLabel("");
		tipo.setBounds(188, 160, 139, 15);
		contentPane.add(tipo);
		
		linea = new JLabel("");
		linea.setBounds(188, 190, 66, 15);
		contentPane.add(linea);
		
		cochera = new JLabel("");
		cochera.setBounds(188, 220, 66, 15);
		contentPane.add(cochera);
		
		//BOTONES
		btnPrimer = new JButton("PRIMER");
		btnPrimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				posicion = 0;
				llenarCampos(0);
			}
		});
		btnPrimer.setEnabled(false);
		btnPrimer.setBounds(10, 266, 114, 25);
		contentPane.add(btnPrimer);
		
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
		btnAnterior.setBounds(140, 266, 114, 25);
		contentPane.add(btnAnterior);
		
		btnSiguiente = new JButton("SIGUIENTE");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((posicion + 1) <= (trenes.size()-1)) {
					posicion++;
					llenarCampos(posicion);
				} else {
					JOptionPane.showMessageDialog(null, "Ultimo registro alcanzado");
				}
			}
		});
		btnSiguiente.setEnabled(false);
		btnSiguiente.setBounds(270, 266, 114, 25);
		contentPane.add(btnSiguiente);
		
		btnUltimo = new JButton("ÚLTIMO");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				llenarCampos((trenes.size() - 1));
				posicion = trenes.size() - 1;
			}
		});
		btnUltimo.setEnabled(false);
		btnUltimo.setBounds(400, 266, 114, 25);
		contentPane.add(btnUltimo);
		
		btnLanzarConsulta = new JButton("LANZAR CONSULTA");
		btnLanzarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction transaction = session.beginTransaction();
				Query<TTrenes> query = session.createQuery("from TTrenes");
				trenes = (ArrayList<TTrenes>) query.list();
				transaction.commit();
				btnSiguiente.setEnabled(true);
				btnAnterior.setEnabled(true);
				btnPrimer.setEnabled(true);
				btnUltimo.setEnabled(true);
				llenarCampos(0);
				btnIr.setEnabled(true);
				session.close();
			}
		});
		btnLanzarConsulta.setBounds(312, 0, 202, 25);
		contentPane.add(btnLanzarConsulta);
		
		btnCancelarConsulta = new JButton("CANCELAR CONSULTA");
		btnCancelarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSiguiente.setEnabled(false);
				btnAnterior.setEnabled(false);
				btnPrimer.setEnabled(false);
				btnUltimo.setEnabled(false);
				btnIr.setEnabled(false);
			}
		});
		btnCancelarConsulta.setBounds(312, 52, 202, 25);
		contentPane.add(btnCancelarConsulta);
		
		btnIr = new JButton("IR");
		btnIr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean aux = false;
				for(int i = 0 ; i < trenes.size() ; i++) {
					if(Integer.parseInt(numTren.getText()) == trenes.get(i).getCodTren()) {
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
		btnIr.setBounds(248, 95, 47, 25);
		contentPane.add(btnIr);
	}
	
	private void llenarCampos(int posicion) {
		numTren.setText(String.valueOf(trenes.get(posicion).getCodTren()));
		nombre.setText(String.valueOf(trenes.get(posicion).getNombre()));
		tipo.setText(String.valueOf(trenes.get(posicion).getTipo()));
		linea.setText(String.valueOf(trenes.get(posicion).getTLineas().getCodLinea()));
		cochera.setText(String.valueOf(trenes.get(posicion).getTCocheras().getCodCochera()));
	}
}
