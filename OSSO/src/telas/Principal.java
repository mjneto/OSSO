/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author gabri
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Cria novo formulario da classe Principal
     */
    public Principal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        areadetrab = new javax.swing.JDesktopPane();
        img = new javax.swing.JLabel();
        nomeUsuario = new javax.swing.JLabel();
        data = new javax.swing.JLabel();
        barramenu = new javax.swing.JMenuBar();
        menuOpcoes = new javax.swing.JMenu();
        itemSair = new javax.swing.JMenuItem();
        menuCadastro = new javax.swing.JMenu();
        itemClientes = new javax.swing.JMenuItem();
        itemOS = new javax.swing.JMenuItem();
        itemUsuarios = new javax.swing.JMenuItem();
        menuRelatorio = new javax.swing.JMenu();
        itemServicos = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        itemSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OSSO");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout areadetrabLayout = new javax.swing.GroupLayout(areadetrab);
        areadetrab.setLayout(areadetrabLayout);
        areadetrabLayout.setHorizontalGroup(
            areadetrabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );
        areadetrabLayout.setVerticalGroup(
            areadetrabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        img.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/iconfinder_Untitled-3-14_3783075.png"))); // NOI18N

        nomeUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeUsuario.setText("usuario");

        data.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        data.setText("data");

        menuOpcoes.setText("Opções");

        itemSair.setText("Sair");
        itemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSairActionPerformed(evt);
            }
        });
        menuOpcoes.add(itemSair);

        barramenu.add(menuOpcoes);

        menuCadastro.setText("Cadastro");

        itemClientes.setText("Clientes");
        menuCadastro.add(itemClientes);

        itemOS.setText("Ordem de Serviço");
        itemOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemOSActionPerformed(evt);
            }
        });
        menuCadastro.add(itemOS);

        itemUsuarios.setText("Usuários do Sistema");
        itemUsuarios.setEnabled(false);
        menuCadastro.add(itemUsuarios);

        barramenu.add(menuCadastro);

        menuRelatorio.setText("Relatórios");
        menuRelatorio.setEnabled(false);

        itemServicos.setText("Serviços");
        menuRelatorio.add(itemServicos);

        barramenu.add(menuRelatorio);

        menuAjuda.setText("Ajuda");

        itemSobre.setText("Sobre");
        itemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSobreActionPerformed(evt);
            }
        });
        menuAjuda.add(itemSobre);

        barramenu.add(menuAjuda);

        setJMenuBar(barramenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(areadetrab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomeUsuario)
                    .addComponent(data)
                    .addComponent(img))
                .addGap(0, 27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nomeUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(data)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(img))
                    .addComponent(areadetrab))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(692, 481));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void itemOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemOSActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        /*Formatação de Data e Hora na tela principal*/
        Date datas = new Date();
        DateFormat dataformatado = DateFormat.getDateTimeInstance();
        data.setText(dataformatado.format(datas));
    }//GEN-LAST:event_formWindowActivated

    private void itemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSairActionPerformed
        /*Opção de sair*/
        int sair = JOptionPane.showConfirmDialog(null, "Deseja sair?","Sair",JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_itemSairActionPerformed

    private void itemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSobreActionPerformed
        /*Opção de sobre*/
        Sobre chamarsobre = new Sobre();
        chamarsobre.setVisible(true);
    }//GEN-LAST:event_itemSobreActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Cria e mostra o formulario */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane areadetrab;
    private javax.swing.JMenuBar barramenu;
    private javax.swing.JLabel data;
    private javax.swing.JLabel img;
    private javax.swing.JMenuItem itemClientes;
    private javax.swing.JMenuItem itemOS;
    private javax.swing.JMenuItem itemSair;
    private javax.swing.JMenuItem itemServicos;
    private javax.swing.JMenuItem itemSobre;
    public static javax.swing.JMenuItem itemUsuarios;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenu menuCadastro;
    private javax.swing.JMenu menuOpcoes;
    public static javax.swing.JMenu menuRelatorio;
    private javax.swing.JLabel nomeUsuario;
    // End of variables declaration//GEN-END:variables
}
