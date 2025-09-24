/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gestion_de_asistencia;

import Clases.Asistencia;
import Clases.CalculadoraHoras;
import Clases.Usuario;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Time;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author dolan
 */
public class ReporteAsistencia extends javax.swing.JPanel {

    private JTable table;
    private DefaultTableModel model;
    private Usuario usuario;
    /**
     * Creates new form ReporteAsistencia
     * @param Lista
     */
    public ReporteAsistencia(Usuario usuario, List<Asistencia> asistencias, Time inicioJornada, Time finJornada) {
    setLayout(new BorderLayout());

    // --- Cabecera ---
    JPanel panelCabecera = new JPanel(new GridLayout(2, 2, 10, 5));
    panelCabecera.setBorder(BorderFactory.createTitledBorder("Datos del Trabajador"));

    panelCabecera.add(new JLabel("Nombre: " + usuario.getNombre() + " " + usuario.getApellido()));
    panelCabecera.add(new JLabel("RUT: " + usuario.getRut()));
    panelCabecera.add(new JLabel("Correo: " + usuario.getCorreo()));
    panelCabecera.add(new JLabel("Rol: " + rolATexto(usuario.getRol())));

    add(panelCabecera, BorderLayout.NORTH);

    // --- Tabla de asistencias ---
    String[] columnas = {"Fecha", "Hora Entrada", "Hora Salida", "Tipo Asistencia", "Justificación",
            "Horas Trabajadas", "Horas Extras", "Atraso(min)"};
    model = new DefaultTableModel(columnas, 0);
    table = new JTable(model);

    // Renderer para Justificación con wrap
    table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JTextArea textArea = new JTextArea(value != null ? value.toString() : "");
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(true);
            if (isSelected) textArea.setBackground(table.getSelectionBackground());
            else textArea.setBackground(table.getBackground());
            return textArea;
        }
    });

    // Totales
    double totalHorasTrabajadas = 0.0;
    double totalHorasExtras = 0.0;
    long totalAtraso = 0;

    for (Asistencia a : asistencias) {
        Time hEntrada = a.getH_entrada();
        Time hSalida = a.getH_salida();

        CalculadoraHoras calc = new CalculadoraHoras(
                hEntrada,
                hSalida,
                inicioJornada,
                finJornada
        );

        double horasTrabajadas = (hEntrada != null && hSalida != null) ? calc.calcularHorasTrabajadas() : 0.0;
        double horasExtras = (hEntrada != null && hSalida != null) ? calc.calcularHorasExtras() : 0.0;
        long atraso = (hEntrada != null) ? calc.calcularAtraso() : 0;

        // Redondeo a 2 decimales
        horasTrabajadas = Math.round(horasTrabajadas * 100.0) / 100.0;
        horasExtras = Math.round(horasExtras * 100.0) / 100.0;

        String entradaStr = (hEntrada == null) ? "Licencia / Ausente" : hEntrada.toString();
        String salidaStr = (hSalida == null) ? "Licencia / Ausente" : hSalida.toString();

        Object[] fila = {
                a.getFecha_actual(),
                entradaStr,
                salidaStr,
                idTipoAsistenciaATexto(a.getId_tipo_asistencia()),
                a.getJustificacion(),
                String.format("%.2f", horasTrabajadas),
                String.format("%.2f", horasExtras),
                atraso
        };
        model.addRow(fila);

        totalHorasTrabajadas += horasTrabajadas;
        totalHorasExtras += horasExtras;
        totalAtraso += atraso;
    }

    // Ajustar altura de las filas según contenido
    table.setRowHeight(1);
    for (int row = 0; row < table.getRowCount(); row++) {
        int maxHeight = table.getRowHeight();
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            maxHeight = Math.max(comp.getPreferredSize().height, maxHeight);
        }
        table.setRowHeight(row, maxHeight);
    }

    JScrollPane scroll = new JScrollPane(table);
    scroll.setPreferredSize(new Dimension(750, 1000)); // limita tamaño estilo hoja carta
    add(scroll, BorderLayout.CENTER);

    // --- Panel contenedor para totales + botón ---
    JPanel panelInferior = new JPanel();
    panelInferior.setLayout(new BorderLayout());

    // Recuadro de totales
    JPanel panelTotales = new JPanel(new GridLayout(1, 3, 20, 5));
    panelTotales.setBorder(BorderFactory.createTitledBorder("Totales del Mes"));
    panelTotales.add(new JLabel("Horas Trabajadas: " + String.format("%.2f", totalHorasTrabajadas)));
    panelTotales.add(new JLabel("Horas Extras: " + String.format("%.2f", totalHorasExtras)));
    panelTotales.add(new JLabel("Atraso Total (min): " + totalAtraso));

    panelInferior.add(panelTotales, BorderLayout.NORTH);

    // Botón de imprimir
    JButton btnImprimir = new JButton("Imprimir Reporte");
    btnImprimir.addActionListener(e -> imprimirPanel(ReporteAsistencia.this));
    JPanel panelBoton = new JPanel();
    panelBoton.add(btnImprimir);

    panelInferior.add(panelBoton, BorderLayout.SOUTH);

    // Agregar todo al SOUTH del panel principal
    add(panelInferior, BorderLayout.SOUTH);
}


    
    
    public void actualizarTabla(List<Asistencia> lista) {
        model.setRowCount(0); // limpiar
        for (Asistencia a : lista) {
            Object[] fila = {
                    a.getFecha_actual(),
                    a.getH_entrada(),
                    a.getH_salida(),
                    a.getId_tipo_asistencia(),
                    a.getJustificacion()
            };
            model.addRow(fila);
        }
    }

    
    private String idTipoAsistenciaATexto(int idTipo) {
    switch (idTipo) {
        case 1: return "Presente";
        case 2: return "Ausente";
        case 3: return "Licencia";
        case 4: return "Tarde";
        case 5: return "Salida Anticipada";
        // Agrega más casos según tu tabla TipoAsistencia
        default: return "Desconocido";
    }
}
    private String rolATexto(int rol) {
    switch (rol) {
        case 1: return "Administrador";
        case 2: return "Empleado";
        case 3: return "Supervisor";
        default: return "Desconocido";
    }
}

    // --- Método para imprimir ---
    private void imprimirPanel(JPanel panel) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Reporte de Asistencia");

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) g;
                g2.translate(pf.getImageableX(), pf.getImageableY());

                double scaleX = pf.getImageableWidth() / panel.getWidth();
                double scaleY = pf.getImageableHeight() / panel.getHeight();
                double scale = Math.min(scaleX, scaleY);
                g2.scale(scale, scale);

                panel.printAll(g2);
                return PAGE_EXISTS;
            }
        });

        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
