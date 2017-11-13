/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.ConexionMySQL;
import static Formularios.ImportarAnimales.id_corral;
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
        myModel.addColumn("1");
        myModel.addColumn("2");
        myModel.addColumn("3");
        myModel.addColumn("4");
        myModel.addColumn("5");
        /*myModel.addColumn("racion");
        myModel.addColumn("estado");*/
        tabla_importar.setModel(myModel);
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
                        /////////////////////////////////////////////////////////////////////////////////

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

        panel_importar = new javax.swing.JPanel();
        txt_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_importar = new javax.swing.JTable();
        btn_importar = new javax.swing.JButton();
        btn_procesar = new javax.swing.JButton();
        btn_volver = new javax.swing.JButton();
        barra_progresar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_importar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Importar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        txt_buscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });

        tabla_importar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabla_importar);

        btn_importar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Enter_728934.png"))); // NOI18N
        btn_importar.setText("Importar");
        btn_importar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importarActionPerformed(evt);
            }
        });

        btn_procesar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Application_728900.png"))); // NOI18N
        btn_procesar.setText("Procesar");
        btn_procesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_procesarActionPerformed(evt);
            }
        });

        btn_volver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Home_728980.png"))); // NOI18N
        btn_volver.setText("Volver");
        btn_volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_volverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_importarLayout = new javax.swing.GroupLayout(panel_importar);
        panel_importar.setLayout(panel_importarLayout);
        panel_importarLayout.setHorizontalGroup(
            panel_importarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_importarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_importarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                    .addGroup(panel_importarLayout.createSequentialGroup()
                        .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_importarLayout.createSequentialGroup()
                        .addComponent(btn_importar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104)
                        .addComponent(btn_procesar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_volver, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(barra_progresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_importarLayout.setVerticalGroup(
            panel_importarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_importarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barra_progresar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(panel_importarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_importar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_procesar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_volver, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_importar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_importar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void agrega_id() {
        ConexionMySQL cc = new ConexionMySQL();
        Connection cn = cc.Conectar();
        ////////////////////////////////////////////////////////////////////////////     
        int row = 0;
        String sql1 = "";
        while (row < myModel.getRowCount()) {
            if (myModel.getValueAt(row, 0).toString().equals("2")) {
                sql1 = "Select id From animales where tag=" + myModel.getValueAt(row, 2).toString();
            }
            if (myModel.getValueAt(row, 0).toString().equals("3")) {
                sql1 = "Select id From animales where tag=" + myModel.getValueAt(row, 1).toString();
            }

            try {
                Statement st1 = cn.createStatement();
                ResultSet rs1 = st1.executeQuery(sql1);
                rs1.next();
                myModel.setValueAt(rs1.getString(1), row, 4);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex + "1");
            }
            row++;
        }
        return;
    }

    private void btn_importarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importarActionPerformed
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
        agrega_id();
        /////////////////////////////////////////////////////////////
        tabla_importar.getColumnModel().getColumn(4).setMaxWidth(0);
        tabla_importar.getColumnModel().getColumn(4).setMinWidth(0);
        tabla_importar.getColumnModel().getColumn(4).setPreferredWidth(0);
        /////////////////////////////////////////////////////////////
        ///cargatotalesordenes();
    }//GEN-LAST:event_btn_importarActionPerformed

    private void btn_volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_volverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_volverActionPerformed

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        TableRowSorter sorter = new TableRowSorter(myModel);
        sorter.setRowFilter(RowFilter.regexFilter(".*" + txt_buscar.getText() + ".*"));
        tabla_importar.setRowSorter(sorter);        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscarKeyReleased

    public class HiloImportar extends Thread {

        JProgressBar jProgressBar1;

        public HiloImportar(JProgressBar progreso1) {
            super();
            this.jProgressBar1 = progreso1;

        }

        public void run() {
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            int fila = tabla_importar.getRowCount(), indice = 1;
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

                    if (myModel.getValueAt(i, 0).toString().equals("2")) {
                        peso = Double.valueOf(myModel.getValueAt(i, 1).toString());
                        fecha = String.valueOf(myModel.getValueAt(i, 3).toString());
                        id_animales = Integer.valueOf(myModel.getValueAt(i, 4).toString());
                    }
                    if (myModel.getValueAt(i, 0).toString().equals("3")) {
                        id_animales = Integer.valueOf(myModel.getValueAt(i, 4).toString());
                        fecha = String.valueOf(myModel.getValueAt(i, 2).toString());
                    }

                    /* id_corrales = Integer.valueOf(myModel.getValueAt(i, 4).toString());
                    
                    id_racion = Integer.valueOf(myModel.getValueAt(i, 5).toString());
                    estado = String.valueOf(myModel.getValueAt(i, 6).toString());*/
                    try {
                        String sSQL = "INSERT INTO eventos (peso_promedio, "
                                + "fecha, id_animales, id_corrales, ESTADO) VALUES (?,?,?,?,?)";
                        PreparedStatement pst = cn.prepareStatement(sSQL);                                                
                        if (myModel.getValueAt(i, 0).toString().equals("2")) {
                            pst.setDouble(1, peso);
                        }
                        if (myModel.getValueAt(i, 0).toString().equals("3")) {
                            pst.setString(1, null);
                        }
                        pst.setString(2, fecha);
                        pst.setInt(3, id_animales);
                        pst.setInt(4, id_corral);
                        if (myModel.getValueAt(i, 0).toString().equals("2")) {
                            pst.setString(5, "PESAJE");
                        }
                        if (myModel.getValueAt(i, 0).toString().equals("3")) {
                            pst.setString(5, "VACUNACION");
                        }

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
        return barra_progresar;
    }

    public void iniciarSplash() {
        this.getjProgressBar1().setBorderPainted(false);
        this.getjProgressBar1().setForeground(new Color(100, 100, 100, 100));
        //[77,239,38]
        this.getjProgressBar1().setStringPainted(true);
    }

    private void btn_procesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_procesarActionPerformed
        new EligeCorral(null, true).setVisible(true);
        iniciarSplash();
        hilo = new HiloImportar(barra_progresar);
        hilo.start();
        hilo = null;


    }//GEN-LAST:event_btn_procesarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barra_progresar;
    private javax.swing.JButton btn_importar;
    private javax.swing.JButton btn_procesar;
    private javax.swing.JButton btn_volver;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_importar;
    private javax.swing.JTable tabla_importar;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
