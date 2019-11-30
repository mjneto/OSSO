/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.sql.*;
import acessoBD.ConexaoBD;
import javax.swing.JOptionPane;

/**
 *
 * @author manoel.neto
 */
public class Usuario extends javax.swing.JInternalFrame {

    Connection conecta = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form Cadastro
     */
    public Usuario() {
        initComponents();
        conecta = ConexaoBD.conector();
    }

    private void alterar() {
        String sql = "update usuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campocadastroUser.getText());
            pst.setString(2, campocadastroFone.getText());
            pst.setString(3, campocadastroLogin.getText());
            pst.setString(4, campocadastroSenha.getText());
            pst.setString(5, campocadastroPerfil.getSelectedItem().toString());
            pst.setString(6, campocadastroID.getText());
            if (campocadastroID.getText().isEmpty() || campocadastroUser.getText().isEmpty() || campocadastroLogin.getText().isEmpty() || campocadastroSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            } else {
                int alterado = pst.executeUpdate();

                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Os dados foram alterados com sucesso!");
                    campocadastroID.setText(null);
                    campocadastroUser.setText(null);
                    campocadastroFone.setText(null);
                    campocadastroLogin.setText(null);
                    campocadastroSenha.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from usuarios where iduser=?";
            try {
                pst = conecta.prepareStatement(sql);
                pst.setString(1, campocadastroUser.getText());
                int apagar = pst.executeUpdate();
                if (apagar > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Removido com sucesso!");
                    campocadastroID.setText(null);
                    campocadastroUser.setText(null);
                    campocadastroFone.setText(null);
                    campocadastroLogin.setText(null);
                    campocadastroSenha.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
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

        textoID = new javax.swing.JLabel();
        campocadastroID = new javax.swing.JTextField();
        textoUser = new javax.swing.JLabel();
        campocadastroUser = new javax.swing.JTextField();
        textoFone = new javax.swing.JLabel();
        campocadastroFone = new javax.swing.JTextField();
        textoLogin = new javax.swing.JLabel();
        campocadastroLogin = new javax.swing.JTextField();
        textoSenha = new javax.swing.JLabel();
        campocadastroSenha = new javax.swing.JPasswordField();
        textoPerfil = new javax.swing.JLabel();
        campocadastroPerfil = new javax.swing.JComboBox<>();
        botaoCreate = new javax.swing.JButton();
        botaoRead = new javax.swing.JButton();
        botaoUpdate = new javax.swing.JButton();
        botaoDelete = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de Usuários do Sistema");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(615, 445));

        textoID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoID.setText("CódigoID");

        campocadastroID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campocadastroIDActionPerformed(evt);
            }
        });

        textoUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoUser.setText("Nome");

        textoFone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoFone.setText("Telefone");

        textoLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoLogin.setText("Login");

        textoSenha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoSenha.setText("Senha");

        textoPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoPerfil.setText("Perfil");

        campocadastroPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Usuário Comum" }));

        botaoCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        botaoCreate.setToolTipText("Adicionar");

        botaoRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/read.png"))); // NOI18N
        botaoRead.setToolTipText("Pesquisar");

        botaoUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/update.png"))); // NOI18N
        botaoUpdate.setToolTipText("Alterar");
        botaoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoUpdateActionPerformed(evt);
            }
        });

        botaoDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        botaoDelete.setToolTipText("Excluir");
        botaoDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(111, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botaoCreate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoRead)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textoID)
                            .addComponent(textoLogin)
                            .addComponent(textoUser)
                            .addComponent(textoFone)
                            .addComponent(textoSenha)
                            .addComponent(textoPerfil))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campocadastroPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campocadastroFone)
                                .addComponent(campocadastroLogin)
                                .addComponent(campocadastroID)
                                .addComponent(campocadastroUser)
                                .addComponent(campocadastroSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(119, 119, 119))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textoFone, textoID, textoLogin, textoPerfil, textoSenha, textoUser});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botaoCreate, botaoDelete, botaoRead, botaoUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campocadastroID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campocadastroUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoUser))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campocadastroFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoFone))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campocadastroLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoLogin))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campocadastroSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoSenha))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campocadastroPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoPerfil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoCreate)
                    .addComponent(botaoRead)
                    .addComponent(botaoUpdate)
                    .addComponent(botaoDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {textoFone, textoID, textoLogin, textoPerfil, textoSenha, textoUser});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botaoCreate, botaoDelete, botaoRead, botaoUpdate});

        setBounds(0, 0, 615, 445);
    }// </editor-fold>//GEN-END:initComponents

    private void cadastroSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastroSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cadastroSenhaActionPerformed

    private void cadastroNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastroNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cadastroNomeActionPerformed

    private void cadastroLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastroLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cadastroLoginActionPerformed

    private void cadastroFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastroFoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cadastroFoneActionPerformed

    private void campocadastroIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campocadastroIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campocadastroIDActionPerformed

    private void botaoUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoUpdateActionPerformed
        // Vai alterar os dados colocados no banco
        alterar();
    }//GEN-LAST:event_botaoUpdateActionPerformed

    private void botaoDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDeleteActionPerformed
        // Vai deletar o usuario
        remover();
    }//GEN-LAST:event_botaoDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCreate;
    private javax.swing.JButton botaoDelete;
    private javax.swing.JButton botaoRead;
    private javax.swing.JButton botaoUpdate;
    private javax.swing.JTextField campocadastroFone;
    private javax.swing.JTextField campocadastroID;
    private javax.swing.JTextField campocadastroLogin;
    private javax.swing.JComboBox<String> campocadastroPerfil;
    private javax.swing.JPasswordField campocadastroSenha;
    private javax.swing.JTextField campocadastroUser;
    private javax.swing.JLabel textoFone;
    private javax.swing.JLabel textoID;
    private javax.swing.JLabel textoLogin;
    private javax.swing.JLabel textoPerfil;
    private javax.swing.JLabel textoSenha;
    private javax.swing.JLabel textoUser;
    // End of variables declaration//GEN-END:variables
}
