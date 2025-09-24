/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Customizer.java to edit this template
 */
package gestion_de_asistencia;

import Clases.Licencia;
import Clases.Usuario;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;
import Clases_BD.Comm_BD;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Clases.Sesion_Usuario;
/**
 *
 * @author Ralabast
 */
public class Interfaz_RevisionLicencias extends javax.swing.JFrame {

    String Seguro;
    private Comm_BD bd;
    private Usuario U_Loggeado;
    private DefaultTableModel modeloTabla;
    
    public Interfaz_RevisionLicencias(JFrame Anterior, String Contrasena, Usuario usuario) {
        initComponents();
        initComponentesPersonalizados();
        bd = new Comm_BD();
        Seguro = Contrasena;
        this.U_Loggeado = usuario;  
        if (usuario != null) {
            Sesion_Usuario.setUsuario(usuario);
        }
        cargarSolicitudesPendientes();
        setLocationRelativeTo(null);
    }
    
    
   public Interfaz_RevisionLicencias() {
        initComponents();
        initComponentesPersonalizados();
        bd = new Comm_BD();
        Seguro = "";
        
        // Crear usuario de testing
        U_Loggeado = new Usuario();
        U_Loggeado.setNombre("Admin Testing");
        U_Loggeado.setApellido("Sistema");
        Sesion_Usuario.setUsuario(U_Loggeado);
        
        cargarSolicitudesPendientes();
        setLocationRelativeTo(null);
    }
   private void configurarVentana() {
        setSize(800, 600);
        setMinimumSize(new java.awt.Dimension(800, 600));
    }
   @SuppressWarnings("unchecked")
    private void initComponentesPersonalizados() {
        // Crear y configurar JDesktopPane
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jDesktopPane1.setBackground(new java.awt.Color(245, 245, 245));
        
        // Agregarlo al JFrame como componente principal
        getContentPane().removeAll();
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);
        
        // Crear componentes
        crearComponentesAdicionales();
        configurarComponentesAdicionales();
        agregarEventosComponentes();
        configurarLayoutPersonalizado();
        configurarVentana();
    }
    
    private void crearComponentesAdicionales() {
        // Etiquetas
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        
        // Tabla para mostrar solicitudes
        tabla_Solicitudes = new javax.swing.JTable();
        scrollPane_Tabla = new javax.swing.JScrollPane(tabla_Solicitudes);
        
        // Campos para mostrar detalles
        lbl_EmpleadoSeleccionado = new javax.swing.JLabel();
        lbl_FechasSolicitud = new javax.swing.JLabel();
        lbl_TipoLicencia = new javax.swing.JLabel();
        ta_MotivoDetalle = new javax.swing.JTextArea();
        scrollPane_Motivo = new javax.swing.JScrollPane(ta_MotivoDetalle);
        
        // Botones de acción
        btn_Aprobar = new javax.swing.JButton();
        btn_Denegar = new javax.swing.JButton();
        btn_Refrescar = new javax.swing.JButton();
        btn_Volver = new javax.swing.JButton();
    }
    
    private void configurarComponentesAdicionales() {
        // Configurar ventana
        setTitle("Revisión de Solicitudes de Licencias");
        setResizable(true);
        
        // Título principal
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Revisión de Solicitudes de Licencias");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // Etiquetas de secciones
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Solicitudes Pendientes:");
        
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel3.setText("Detalles de la Solicitud:");
        
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabel4.setText("Empleado:");
        
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabel5.setText("Motivo/Descripción:");
        
        // Configurar tabla
        String[] columnas = {"ID", "RUT", "Empleado", "Tipo", "Fecha Inicio", "Fecha Fin", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tabla_Solicitudes.setModel(modeloTabla);
        tabla_Solicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla_Solicitudes.setFont(new java.awt.Font("Segoe UI", 0, 11));
        tabla_Solicitudes.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 11));
        tabla_Solicitudes.setRowHeight(25);
        
        // Configurar ancho de columnas
        tabla_Solicitudes.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabla_Solicitudes.getColumnModel().getColumn(1).setPreferredWidth(100); // RUT
        tabla_Solicitudes.getColumnModel().getColumn(2).setPreferredWidth(150); // Empleado
        tabla_Solicitudes.getColumnModel().getColumn(3).setPreferredWidth(120); // Tipo
        tabla_Solicitudes.getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha Inicio
        tabla_Solicitudes.getColumnModel().getColumn(5).setPreferredWidth(100); // Fecha Fin
        tabla_Solicitudes.getColumnModel().getColumn(6).setPreferredWidth(80);  // Estado
        
        scrollPane_Tabla.setPreferredSize(new java.awt.Dimension(750, 200));
        
        // Labels de información
        lbl_EmpleadoSeleccionado.setFont(new java.awt.Font("Segoe UI", 0, 12));
        lbl_EmpleadoSeleccionado.setText("Seleccione una solicitud para ver detalles");
        
        lbl_FechasSolicitud.setFont(new java.awt.Font("Segoe UI", 0, 12));
        lbl_FechasSolicitud.setText("");
        
        lbl_TipoLicencia.setFont(new java.awt.Font("Segoe UI", 0, 12));
        lbl_TipoLicencia.setText("");
        
        // Área de texto para motivo
        ta_MotivoDetalle.setColumns(25);
        ta_MotivoDetalle.setRows(4);
        ta_MotivoDetalle.setFont(new java.awt.Font("Segoe UI", 0, 12));
        ta_MotivoDetalle.setLineWrap(true);
        ta_MotivoDetalle.setWrapStyleWord(true);
        ta_MotivoDetalle.setEditable(false);
        ta_MotivoDetalle.setBackground(new java.awt.Color(250, 250, 250));
        scrollPane_Motivo.setPreferredSize(new java.awt.Dimension(350, 80));
        
        // Configurar botones
        btn_Aprobar.setText("Aprobar Solicitud");
        btn_Aprobar.setBackground(new java.awt.Color(76, 175, 80));
        btn_Aprobar.setForeground(java.awt.Color.WHITE);
        btn_Aprobar.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btn_Aprobar.setPreferredSize(new java.awt.Dimension(150, 35));
        btn_Aprobar.setEnabled(false);
        
        btn_Denegar.setText("Denegar Solicitud");
        btn_Denegar.setBackground(new java.awt.Color(244, 67, 54));
        btn_Denegar.setForeground(java.awt.Color.WHITE);
        btn_Denegar.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btn_Denegar.setPreferredSize(new java.awt.Dimension(150, 35));
        btn_Denegar.setEnabled(false);
        
        btn_Refrescar.setText("Refrescar Lista");
        btn_Refrescar.setBackground(new java.awt.Color(33, 150, 243));
        btn_Refrescar.setForeground(java.awt.Color.WHITE);
        btn_Refrescar.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btn_Refrescar.setPreferredSize(new java.awt.Dimension(120, 35));
        
        btn_Volver.setText("Volver");
        btn_Volver.setBackground(new java.awt.Color(156, 39, 176));
        btn_Volver.setForeground(java.awt.Color.WHITE);
        btn_Volver.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btn_Volver.setPreferredSize(new java.awt.Dimension(80, 35));
    }
    
    private void agregarEventosComponentes() {
        // Event listener para selección de tabla
        tabla_Solicitudes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetallesSolicitud();
            }
        });
        
        btn_Aprobar.addActionListener(evt -> btn_AprobarActionPerformed(evt));
        btn_Denegar.addActionListener(evt -> btn_DenegarActionPerformed(evt));
        btn_Refrescar.addActionListener(evt -> btn_RefrescarActionPerformed(evt));
        btn_Volver.addActionListener(evt -> btn_VolverActionPerformed(evt));
    }
    
    private void configurarLayoutPersonalizado() {
        // Usar layout absoluto
        getContentPane().setLayout(null);
        getContentPane().removeAll();
        
        // Agregar componentes
        getContentPane().add(jLabel1);
        getContentPane().add(jSeparator1);
        getContentPane().add(jLabel2);
        getContentPane().add(scrollPane_Tabla);
        getContentPane().add(jLabel3);
        getContentPane().add(jLabel4);
        getContentPane().add(lbl_EmpleadoSeleccionado);
        getContentPane().add(lbl_FechasSolicitud);
        getContentPane().add(lbl_TipoLicencia);
        getContentPane().add(jLabel5);
        getContentPane().add(scrollPane_Motivo);
        getContentPane().add(btn_Aprobar);
        getContentPane().add(btn_Denegar);
        getContentPane().add(btn_Refrescar);
        getContentPane().add(btn_Volver);
        
        // Posicionamiento
        jLabel1.setBounds(50, 20, 700, 25);
        jSeparator1.setBounds(20, 50, 750, 2);
        jLabel2.setBounds(20, 70, 200, 25);
        scrollPane_Tabla.setBounds(20, 100, 750, 200);
        jLabel3.setBounds(20, 320, 200, 25);
        jLabel4.setBounds(20, 350, 80, 25);
        lbl_EmpleadoSeleccionado.setBounds(100, 350, 300, 25);
        lbl_FechasSolicitud.setBounds(100, 375, 300, 25);
        lbl_TipoLicencia.setBounds(100, 400, 300, 25);
        jLabel5.setBounds(20, 430, 150, 25);
        scrollPane_Motivo.setBounds(20, 455, 500, 80);
        btn_Aprobar.setBounds(550, 455, 150, 35);
        btn_Denegar.setBounds(550, 500, 150, 35);
        btn_Refrescar.setBounds(20, 550, 120, 35);
        btn_Volver.setBounds(680, 550, 80, 35);
        
        revalidate();
        repaint();
    }
    
    // ============= MÉTODOS DE NEGOCIO =============
    
    private void cargarSolicitudesPendientes() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        try {
            // Obtener solicitudes pendientes de la base de datos
            List<Licencia> solicitudes = bd.obtenerSolicitudesPendientes();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (Licencia licencia : solicitudes) {
                String nombreEmpleado = bd.obtenerNombreUsuario(licencia.getRut());
                
                Object[] fila = {
                    licencia.getId_licencia(),
                    licencia.getRut(),
                    nombreEmpleado,
                    obtenerTipoLicencia(licencia.getMotivo()),
                    sdf.format(licencia.getFecha_inicio()),
                    sdf.format(licencia.getFecha_fin()),
                    licencia.getEstado()
                };
                
                modeloTabla.addRow(fila);
            }
            
            if (solicitudes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No hay solicitudes pendientes de revisión",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar las solicitudes: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private String obtenerTipoLicencia(String motivoCompleto) {
        if (motivoCompleto == null) return "Desconocido";
        
        if (motivoCompleto.contains(":")) {
            return motivoCompleto.split(":")[0];
        }
        return "Otro";
    }
    
    private void mostrarDetallesSolicitud() {
        int filaSeleccionada = tabla_Solicitudes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            lbl_EmpleadoSeleccionado.setText("Seleccione una solicitud para ver detalles");
            lbl_FechasSolicitud.setText("");
            lbl_TipoLicencia.setText("");
            ta_MotivoDetalle.setText("");
            btn_Aprobar.setEnabled(false);
            btn_Denegar.setEnabled(false);
            return;
        }
        
        // Obtener datos de la fila seleccionada
        String rut = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String empleado = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        String tipo = (String) modeloTabla.getValueAt(filaSeleccionada, 3);
        String fechaInicio = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
        String fechaFin = (String) modeloTabla.getValueAt(filaSeleccionada, 5);
        Integer idLicencia = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        
        // Mostrar información en los labels
        lbl_EmpleadoSeleccionado.setText(empleado + " (RUT: " + rut + ")");
        lbl_FechasSolicitud.setText("Período: " + fechaInicio + " al " + fechaFin);
        lbl_TipoLicencia.setText("Tipo: " + tipo);
        
        // Obtener el motivo completo de la base de datos
        try {
            Licencia licencia = bd.obtenerLicenciaPorId(idLicencia);
            if (licencia != null) {
                String motivoCompleto = licencia.getMotivo();
                if (motivoCompleto.contains(":")) {
                    ta_MotivoDetalle.setText(motivoCompleto.split(":", 2)[1].trim());
                } else {
                    ta_MotivoDetalle.setText(motivoCompleto);
                }
            }
        } catch (Exception ex) {
            ta_MotivoDetalle.setText("Error al cargar el motivo: " + ex.getMessage());
        }
        
        // Habilitar botones de acción
        btn_Aprobar.setEnabled(true);
        btn_Denegar.setEnabled(true);
    }
    
    // ============= MÉTODOS DE EVENTOS =============
    
    private void btn_AprobarActionPerformed(java.awt.event.ActionEvent evt) {
        int filaSeleccionada = tabla_Solicitudes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una solicitud primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idLicencia = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String empleado = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de aprobar la solicitud de licencia de " + empleado + "?",
            "Confirmar Aprobación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (bd.actualizarEstadoLicencia(idLicencia, "APPROVED")) {
                    JOptionPane.showMessageDialog(this,
                        "Solicitud aprobada exitosamente para: " + empleado,
                        "Aprobación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    cargarSolicitudesPendientes(); // Refrescar lista
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al aprobar la solicitud",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void btn_DenegarActionPerformed(java.awt.event.ActionEvent evt) {
        int filaSeleccionada = tabla_Solicitudes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una solicitud primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idLicencia = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String empleado = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        
        String motivo = JOptionPane.showInputDialog(this,
            "Ingrese el motivo de la denegación para " + empleado + ":",
            "Motivo de Denegación",
            JOptionPane.QUESTION_MESSAGE);
        
        if (motivo != null && !motivo.trim().isEmpty()) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de denegar la solicitud de licencia de " + empleado + "?\\n" +
                "Motivo: " + motivo,
                "Confirmar Denegación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    if (bd.actualizarEstadoLicencia(idLicencia, "REJECTED")) {
                        JOptionPane.showMessageDialog(this,
                            "Solicitud denegada para: " + empleado + "\\n" +
                            "Motivo: " + motivo,
                            "Denegación Registrada",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        cargarSolicitudesPendientes(); // Refrescar lista
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Error al denegar la solicitud",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Error inesperado: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void btn_RefrescarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarSolicitudesPendientes();
        JOptionPane.showMessageDialog(this,
            "Lista de solicitudes actualizada",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void btn_VolverActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        
        if (Sesion_Usuario.hayUsuarioLoggeado()) {
            new Interfaz_Asistencia(Sesion_Usuario.getUsuario()).setVisible(true);
        } else {
            Usuario usuarioTemp = new Usuario();
            usuarioTemp.setNombre("Usuario Temp");
            new Interfaz_Asistencia(usuarioTemp).setVisible(true);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz_RevisionLicencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Interfaz_RevisionLicencias().setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btn_Aprobar;
    private javax.swing.JButton btn_Denegar;
    private javax.swing.JButton btn_Refrescar;
    private javax.swing.JButton btn_Volver;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_EmpleadoSeleccionado;
    private javax.swing.JLabel lbl_FechasSolicitud;
    private javax.swing.JLabel lbl_TipoLicencia;
    private javax.swing.JScrollPane scrollPane_Motivo;
    private javax.swing.JScrollPane scrollPane_Tabla;
    private javax.swing.JTable tabla_Solicitudes;
    private javax.swing.JTextArea ta_MotivoDetalle;
    private javax.swing.JTextField jTextField1;
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        setLayout(new java.awt.BorderLayout());

    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
