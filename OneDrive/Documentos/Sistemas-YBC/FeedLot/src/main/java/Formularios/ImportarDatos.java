/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.ConexionMySQL;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ImportarDatos extends javax.swing.JDialog {

    String url;
    public static DefaultTableModel myModel;
    HiloImportar hilo;

    public ImportarDatos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    void leer_archivo() {

        myModel = new DefaultTableModel() {
            ////Celdas no editables////////
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        myModel.addColumn("Peso");
        myModel.addColumn("Fecha");
        myModel.addColumn("animal");
        myModel.addColumn("corral");
        myModel.addColumn("vacuna");
        myModel.addColumn("racion");
        myModel.addColumn("estado");
        jTable1.setModel(myModel);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(url));
            String line = br.readLine();
            int row;
            for (row = 0; row < 10; row++) {
                for (int column = 0; column < 5; column++) {
                    while (line != null) {
                        String[] rowfields = line.split(";");
                        myModel.addRow(rowfields);
                        line = br.readLine();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ///////////////////////////////////////////////////////////////////////////////////
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Importar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Enter_728934.png"))); // NOI18N
        jButton1.setText("Importar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Application_728900.png"))); // NOI18N
        jButton3.setText("Procesar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Home_728980.png"))); // NOI18N
        jButton4.setText("Volver");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        File archivo;
        JFileChooser flcAbrirArchivo;
        flcAbrirArchivo = new JFileChooser();
        flcAbrirArchivo.setFileFilter(new FileNameExtensionFilter("Documento de texto", "txt", "txt"));
        int respuesta = flcAbrirArchivo.showOpenDialog(this);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            archivo = flcAbrirArchivo.getSelectedFile();
            url = (archivo.getAbsolutePath());
        }
        leer_archivo();
        ///cargatotalesordenes();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        TableRowSorter sorter = new TableRowSorter(myModel);
        sorter.setRowFilter(RowFilter.regexFilter(".*" + jTextField1.getText() + ".*"));
        jTable1.setRowSorter(sorter);        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyReleased

    public class HiloImportar extends Thread {

        JProgressBar jProgressBar1;

        public HiloImportar(JProgressBar progreso1) {
            super();
            this.jProgressBar1 = progreso1;

        }

        public void run() {
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            int fila = jTable1.getRowCount(), indice = 1;
            jProgressBar1.setMaximum(fila);
           

            jProgressBar1.setValue(1);
            double peso = 0.0;
            String fecha = "0000-00-00", estado = "";
            int id_animales = 0, id_corrales = 0, id_vacunas = 0, id_racion = 0;
            if (fila == 0) {
                JOptionPane.showMessageDialog(null, "No hay datos en la tabla");
            } else {
                int i = 0;
                while (i < fila) {
                    peso = Double.valueOf(myModel.getValueAt(i, 0).toString());
                    fecha = String.valueOf(myModel.getValueAt(i, 1).toString());
                    id_animales = Integer.valueOf(myModel.getValueAt(i, 2).toString());
                    id_corrales = Integer.valueOf(myModel.getValueAt(i, 3).toString());
                    id_vacunas = Integer.valueOf(myModel.getValueAt(i, 4).toString());
                    id_racion = Integer.valueOf(myModel.getValueAt(i, 5).toString());
                    estado = String.valueOf(myModel.getValueAt(i, 6).toString());
                    try {
                        String sSQL = "INSERT INTO animales_eventos (peso_promedio, "
                                + "fecha, id_animales, id_corrales, id_vacunas, id_racion, estado) VALUES (?,?,?,?,?,?,?)";
                        PreparedStatement pst = cn.prepareStatement(sSQL);
                        pst.setDouble(1, peso);
                        pst.setString(2, fecha);
                        pst.setInt(3, id_animales);
                        pst.setInt(4, id_corrales);
                        pst.setInt(5, id_vacunas);
                        pst.setInt(6, id_racion);
                        pst.setString(7, estado);
                        pst.executeUpdate();
                        ////////////////////////////////////////////////////////////////////////
                        myModel.removeRow(i);
                        i--;
                        fila--;
                        //aumentamos la barra
                        jProgressBar1.setValue(indice);
                        indice++;
                        i++;

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        JOptionPane.showMessageDialog(null, "Error en la base de datos...");
                    }
                }
            }
        }

        public void pausa(int mlSeg) {
            try {
                // pausa para el splash
                Thread.sleep(mlSeg);
            } catch (Exception e) {
            }

        }

    }

    public javax.swing.JProgressBar getjProgressBar1() {
        return jProgressBar1;
    }

    public void iniciarSplash() {
        this.getjProgressBar1().setBorderPainted(false);
        this.getjProgressBar1().setForeground(new Color(100, 100, 100, 100));
        //[77,239,38]
        this.getjProgressBar1().setStringPainted(true);
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        iniciarSplash();
        hilo = new HiloImportar(jProgressBar1);
        hilo.start();
        hilo = null;


    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
