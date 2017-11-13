/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.ConexionMySQL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author lucas.robles
 */
public class Main extends javax.swing.JFrame {

    JFreeChart Grafica;
    DefaultCategoryDataset Datos = new DefaultCategoryDataset();
    DefaultListModel modelo1 = new DefaultListModel();
    int id[] = new int[500];
    String estapa[] = new String[500];
    public static int id_corral = 0;

    public Main() {
        initComponents();
        ///setIconImage(new ImageIcon(getClass().getResource("file:/C:/Users/lucas.robles/OneDrive/Documentos/Sistemas YBC/FeedLot/src/main/java/Imagenes/if_Computer_728925.png")).getImage());
        this.setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        cargarcorrales();
        cargarestadistica();
    }

    void cargarcorrales() {
        int i = 0;
        String sql = "SELECT * FROM corrales";
        ConexionMySQL cc = new ConexionMySQL();
        Connection cn = cc.Conectar();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                id[i] = rs.getInt(1);
                modelo1.addElement(rs.getString(2));
                estapa[i] = rs.getString(3);
                i++;
            }
            lista_corrales.setModel(modelo1);
            ///  listanombres1.setSelectionModel(new ToggleSelectionModel());
            ///////////////////////////////////////////////////////////            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void cargarestadistica() {
        lista_corrales.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {

                    ConexionMySQL cc = new ConexionMySQL();
                    Connection cn = cc.Conectar();
                    ////////////////////////////////////////////////////////////////////////////                    
                    String sql1 = "Select ETAPA From CORRALES where id=" + id[lista_corrales.getSelectedIndex()];

                    try {
                        Statement st1 = cn.createStatement();
                        ResultSet rs1 = st1.executeQuery(sql1);
                        rs1.next();
                        txt_etapa.setText(rs1.getString(1));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex + "1");
                    }
                    //////////////////////////////////////////////////////////////////////////////////////
                    String sql2 = "Select count(distinct id_animales) From eventos where id_corrales=" + id[lista_corrales.getSelectedIndex()];

                    try {
                        Statement st2 = cn.createStatement();
                        ResultSet rs2 = st2.executeQuery(sql2);
                        rs2.next();
                        txt_cantidad.setText(rs2.getString(1));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "No posee animales este corral...");
                    }
                    //////////////////////////////////////////////////////////////////////////////                    
                    String sql3 = "SELECT FECHA FROM EVENTOS WHERE ID_CORRALES=" + id[lista_corrales.getSelectedIndex()] + " ORDER BY FECHA";

                    try {
                        Statement st3 = cn.createStatement();
                        ResultSet rs3 = st3.executeQuery(sql3);
                        rs3.last();
                        txt_fecha.setText(rs3.getString(1));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "No hay eventos de este corral...");
                    }
                    //////////////////////////////////////////////////////////////////////////////    
                    String sql4 = "SELECT peso_promedio FROM EVENTOS WHERE ID_CORRALES=" + id[lista_corrales.getSelectedIndex()] + " ORDER BY peso_promedio";

                    try {
                        Statement st4 = cn.createStatement();
                        ResultSet rs4 = st4.executeQuery(sql4);
                        rs4.first();
                        txt_peso_menor.setText(rs4.getString(1));
                        rs4.last();
                        txt_peso_mayor.setText(rs4.getString(1));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "No hay eventos de este corral...");
                    }
                    //////////////////////////////////////////////////////////////////////////////    
                    String sql5 = "SELECT round(sum(peso_promedio)/count(id_animales),2) FROM EVENTOS WHERE ID_CORRALES=" + id[lista_corrales.getSelectedIndex()];

                    try {
                        Statement st5 = cn.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql5);
                        rs5.next();
                        txt_peso_promedio.setText(rs5.getString(1));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex + "5");
                    }
                    ////////////////////////////////////////////////////////////////////////////// 
                    String sql6 = "SELECT EVENTOS.peso_promedio, (SELECT WEEK(EVENTOS.fecha)) - (SELECT WEEK(animales.fecha_ingreso)) FROM EVENTOS INNER JOIN animales on animales.id=EVENTOS.id_animales WHERE peso_promedio !=0 and ID_CORRALES=" + id[lista_corrales.getSelectedIndex()];
                    String sql7 = "SELECT count(*) FROM EVENTOS INNER JOIN animales on animales.id=EVENTOS.id_animales WHERE ID_CORRALES=" + id[lista_corrales.getSelectedIndex()];
                    try {
                        Statement st = cn.createStatement();
                        ResultSet rs = st.executeQuery(sql6);
                        /////////////////////////////////////////////////
                        Statement st2 = cn.createStatement();
                        ResultSet rs2 = st2.executeQuery(sql7);
                        rs2.next();
                        int tamaño = rs2.getInt(1), j = 0;
                        Object[] filas = new Object[tamaño];
                        Object[] columnas = new Object[tamaño];
                        ///////////////////////////////////////////////////////
                        while (rs.next()) {
                            filas[j] = rs.getDouble(1);
                            columnas[j] = rs.getString(2);
                            j++;
                        }
                        Datos.clear();
                        for (int i = 0; i < tamaño; i++) {
                            Datos.addValue((Number) filas[i], lista_corrales.getSelectedValue().toString(), String.valueOf(columnas[i]));
                        }
                        Grafica = ChartFactory.createLineChart("Evolución Corral",
                                "Semanas", "Peso Vivo(Kg)", Datos,
                                PlotOrientation.VERTICAL, true, true, false);
                        BufferedImage image = Grafica.createBufferedImage(740, 300);
                        panel_grafica.setIcon(new ImageIcon(image));
                    } catch (Exception ex2) {
                        JOptionPane.showMessageDialog(null, ex2 + "6");
                    }
                    /////////////////////////////////////////////////////////////////////////////////////////
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_color = new javax.swing.JPanel();
        panel_corrales = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_corrales = new javax.swing.JList<>();
        btn_agregar_corral = new javax.swing.JButton();
        btn_modificar_corral = new javax.swing.JButton();
        panel_estadisticas = new javax.swing.JPanel();
        panel_grafica = new javax.swing.JLabel();
        btn_imprimir_reporte = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();
        btn_estadistica_corral = new javax.swing.JButton();
        btn_detalle_corral = new javax.swing.JButton();
        panel_detalle = new javax.swing.JPanel();
        label_etapa = new javax.swing.JLabel();
        txt_etapa = new javax.swing.JTextField();
        label_cantidad = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        label_fecha = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JTextField();
        label_peso_mayor = new javax.swing.JLabel();
        label_peso_menor = new javax.swing.JLabel();
        label_peso_promedio = new javax.swing.JLabel();
        txt_peso_promedio = new javax.swing.JTextField();
        txt_peso_menor = new javax.swing.JTextField();
        txt_peso_mayor = new javax.swing.JTextField();
        barra_menu = new javax.swing.JMenuBar();
        menu_datos = new javax.swing.JMenu();
        menu_categoria = new javax.swing.JMenuItem();
        menu_proveedor = new javax.swing.JMenuItem();
        menu_corral = new javax.swing.JMenuItem();
        menu_racion = new javax.swing.JMenuItem();
        menu_vacuna = new javax.swing.JMenuItem();
        menu_animal = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel_color.setBackground(new java.awt.Color(0, 102, 153));
        panel_color.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_colorLayout = new javax.swing.GroupLayout(panel_color);
        panel_color.setLayout(panel_colorLayout);
        panel_colorLayout.setHorizontalGroup(
            panel_colorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_colorLayout.setVerticalGroup(
            panel_colorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
        );

        panel_corrales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Corrales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        lista_corrales.setFont(new java.awt.Font("Verdana", 1, 55)); // NOI18N
        lista_corrales.setSelectionBackground(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(lista_corrales);

        btn_agregar_corral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Add_728898.png"))); // NOI18N
        btn_agregar_corral.setText("Agregar Corral");
        btn_agregar_corral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_corralActionPerformed(evt);
            }
        });

        btn_modificar_corral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Pen_728966.png"))); // NOI18N
        btn_modificar_corral.setText("Modificar Corral");

        javax.swing.GroupLayout panel_corralesLayout = new javax.swing.GroupLayout(panel_corrales);
        panel_corrales.setLayout(panel_corralesLayout);
        panel_corralesLayout.setHorizontalGroup(
            panel_corralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_corralesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_corralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                    .addGroup(panel_corralesLayout.createSequentialGroup()
                        .addComponent(btn_agregar_corral, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_modificar_corral, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_corralesLayout.setVerticalGroup(
            panel_corralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_corralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_corralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_agregar_corral, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modificar_corral, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_estadisticas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estadisticas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        panel_grafica.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel_grafica.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_imprimir_reporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Document_728928.png"))); // NOI18N
        btn_imprimir_reporte.setText("Imprimir Reporte");

        btn_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Cancel_728918.png"))); // NOI18N
        btn_salir.setText("Salir");
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });

        btn_estadistica_corral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_BarChart_728905.png"))); // NOI18N
        btn_estadistica_corral.setText("Estadistica");

        btn_detalle_corral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_News_728959.png"))); // NOI18N
        btn_detalle_corral.setText("Detalle Corral");
        btn_detalle_corral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detalle_corralActionPerformed(evt);
            }
        });

        panel_detalle.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        label_etapa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_etapa.setForeground(new java.awt.Color(102, 102, 102));
        label_etapa.setText("ETAPA CORRAL");

        txt_etapa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_etapa.setForeground(new java.awt.Color(102, 102, 102));
        txt_etapa.setText("10");
        txt_etapa.setBorder(null);

        label_cantidad.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_cantidad.setForeground(new java.awt.Color(102, 102, 102));
        label_cantidad.setText("CANTIDAD ANIMALES");

        txt_cantidad.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_cantidad.setForeground(new java.awt.Color(102, 102, 102));
        txt_cantidad.setText("180");
        txt_cantidad.setBorder(null);

        label_fecha.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_fecha.setForeground(new java.awt.Color(102, 102, 102));
        label_fecha.setText("ULTIMA FECHA PESO");

        txt_fecha.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_fecha.setForeground(new java.awt.Color(102, 102, 102));
        txt_fecha.setText("2017-10-25");
        txt_fecha.setBorder(null);
        txt_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fechaActionPerformed(evt);
            }
        });

        label_peso_mayor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_peso_mayor.setForeground(new java.awt.Color(102, 102, 102));
        label_peso_mayor.setText("MAYOR PESO");

        label_peso_menor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_peso_menor.setForeground(new java.awt.Color(102, 102, 102));
        label_peso_menor.setText("MENOR PESO");

        label_peso_promedio.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_peso_promedio.setForeground(new java.awt.Color(102, 102, 102));
        label_peso_promedio.setText("PESO PROMEDIO");

        txt_peso_promedio.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_peso_promedio.setForeground(new java.awt.Color(102, 102, 102));
        txt_peso_promedio.setText("267.25");
        txt_peso_promedio.setBorder(null);

        txt_peso_menor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_peso_menor.setForeground(new java.awt.Color(102, 102, 102));
        txt_peso_menor.setText("156.14");
        txt_peso_menor.setBorder(null);

        txt_peso_mayor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_peso_mayor.setForeground(new java.awt.Color(102, 102, 102));
        txt_peso_mayor.setText("300.58");
        txt_peso_mayor.setBorder(null);

        javax.swing.GroupLayout panel_detalleLayout = new javax.swing.GroupLayout(panel_detalle);
        panel_detalle.setLayout(panel_detalleLayout);
        panel_detalleLayout.setHorizontalGroup(
            panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_detalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(label_etapa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_cantidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(label_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(txt_cantidad)
                    .addComponent(txt_etapa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_peso_mayor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_peso_promedio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_peso_menor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_peso_promedio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(txt_peso_menor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_peso_mayor, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        panel_detalleLayout.setVerticalGroup(
            panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_detalleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_detalleLayout.createSequentialGroup()
                        .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_peso_mayor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_peso_mayor, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_peso_menor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_peso_menor, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_peso_promedio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_peso_promedio, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_detalleLayout.createSequentialGroup()
                        .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_etapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_etapa, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_cantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_estadisticasLayout = new javax.swing.GroupLayout(panel_estadisticas);
        panel_estadisticas.setLayout(panel_estadisticasLayout);
        panel_estadisticasLayout.setHorizontalGroup(
            panel_estadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadisticasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_estadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_estadisticasLayout.createSequentialGroup()
                        .addComponent(btn_detalle_corral, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_estadistica_corral, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_imprimir_reporte)
                        .addGap(18, 18, 18)
                        .addComponent(btn_salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panel_grafica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_estadisticasLayout.setVerticalGroup(
            panel_estadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadisticasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_grafica, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_estadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_imprimir_reporte, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_estadistica_corral, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_detalle_corral, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        menu_datos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Attach_728902.png"))); // NOI18N
        menu_datos.setText("Datos");

        menu_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Copy_728927.png"))); // NOI18N
        menu_categoria.setText("Categorias");
        menu_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_categoriaActionPerformed(evt);
            }
        });
        menu_datos.add(menu_categoria);

        menu_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Earth_728931.png"))); // NOI18N
        menu_proveedor.setText("Proveedores");
        menu_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_proveedorActionPerformed(evt);
            }
        });
        menu_datos.add(menu_proveedor);

        menu_corral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Add_728898.png"))); // NOI18N
        menu_corral.setText("Corrales");
        menu_corral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_corralActionPerformed(evt);
            }
        });
        menu_datos.add(menu_corral);

        menu_racion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Calendar_728916.png"))); // NOI18N
        menu_racion.setText("Raciones");
        menu_racion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_racionActionPerformed(evt);
            }
        });
        menu_datos.add(menu_racion);

        menu_vacuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_PriceTag_728962.png"))); // NOI18N
        menu_vacuna.setText("Vacunas");
        menu_vacuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_vacunaActionPerformed(evt);
            }
        });
        menu_datos.add(menu_vacuna);

        menu_animal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Character_728921.png"))); // NOI18N
        menu_animal.setText("Animales");
        menu_animal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_animalActionPerformed(evt);
            }
        });
        menu_datos.add(menu_animal);

        barra_menu.add(menu_datos);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Download_728930.png"))); // NOI18N
        jMenu1.setText("SD");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Enter_728934.png"))); // NOI18N
        jMenuItem1.setText("Importar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Exit_728935.png"))); // NOI18N
        jMenuItem2.setText("Exportar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        barra_menu.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Magnifier_728952.png"))); // NOI18N
        jMenu2.setText("Buscar");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Location_728975.png"))); // NOI18N
        jMenuItem3.setText("Ubicar Animal");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        barra_menu.add(jMenu2);

        setJMenuBar(barra_menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_color, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_corrales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_estadisticas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_corrales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_estadisticas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btn_agregar_corralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_corralActionPerformed
        new AgregarCorral(null, true).setVisible(true);
        cargarcorrales();
    }//GEN-LAST:event_btn_agregar_corralActionPerformed

    private void menu_animalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_animalActionPerformed
        new ConsultaAnimales(null, true).setVisible(true);
    }//GEN-LAST:event_menu_animalActionPerformed

    private void menu_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_categoriaActionPerformed
        new ConsultaCategorias(null, true).setVisible(true);
    }//GEN-LAST:event_menu_categoriaActionPerformed

    private void menu_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_proveedorActionPerformed
        new ConsultaProveedores(null, true).setVisible(true);
    }//GEN-LAST:event_menu_proveedorActionPerformed

    private void menu_racionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_racionActionPerformed
        new ConsultaRaciones(null, true).setVisible(true);
    }//GEN-LAST:event_menu_racionActionPerformed

    private void menu_corralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_corralActionPerformed
        new ConsultaCorrales(null, true).setVisible(true);
    }//GEN-LAST:event_menu_corralActionPerformed

    private void menu_vacunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_vacunaActionPerformed
        new ConsultaVacunas(null, true).setVisible(true);
    }//GEN-LAST:event_menu_vacunaActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new UbicarAnimal(null, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new ImportarDatos(null, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new ExportarDatos(null, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salirActionPerformed

    private void txt_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fechaActionPerformed

    private void btn_detalle_corralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detalle_corralActionPerformed
        id_corral = id[lista_corrales.getSelectedIndex()];
        new ConsultaHistorica(null, true).setVisible(true);
    }//GEN-LAST:event_btn_detalle_corralActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barra_menu;
    private javax.swing.JButton btn_agregar_corral;
    private javax.swing.JButton btn_detalle_corral;
    private javax.swing.JButton btn_estadistica_corral;
    private javax.swing.JButton btn_imprimir_reporte;
    private javax.swing.JButton btn_modificar_corral;
    private javax.swing.JButton btn_salir;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_cantidad;
    private javax.swing.JLabel label_etapa;
    private javax.swing.JLabel label_fecha;
    private javax.swing.JLabel label_peso_mayor;
    private javax.swing.JLabel label_peso_menor;
    private javax.swing.JLabel label_peso_promedio;
    private javax.swing.JList<String> lista_corrales;
    private javax.swing.JMenuItem menu_animal;
    private javax.swing.JMenuItem menu_categoria;
    private javax.swing.JMenuItem menu_corral;
    private javax.swing.JMenu menu_datos;
    private javax.swing.JMenuItem menu_proveedor;
    private javax.swing.JMenuItem menu_racion;
    private javax.swing.JMenuItem menu_vacuna;
    private javax.swing.JPanel panel_color;
    private javax.swing.JPanel panel_corrales;
    private javax.swing.JPanel panel_detalle;
    private javax.swing.JPanel panel_estadisticas;
    private javax.swing.JLabel panel_grafica;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_etapa;
    private javax.swing.JTextField txt_fecha;
    private javax.swing.JTextField txt_peso_mayor;
    private javax.swing.JTextField txt_peso_menor;
    private javax.swing.JTextField txt_peso_promedio;
    // End of variables declaration//GEN-END:variables
}
