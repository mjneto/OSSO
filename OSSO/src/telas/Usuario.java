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
     * Cria novo formulário da classe Cadastro
     */
    public Usuario() {
        initComponents();
        conecta = ConexaoBD.conector();
    }

    private void alterar() {
        String sql = "update usuarios set usuario = ?, fone = ?, login = ?, senha = ?, is_admin = ? where iduser = ?";
        
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campocadastroUser.getText());
            pst.setString(2, campocadastroFone.getText());
            pst.setString(3, campocadastroLogin.getText());
            pst.setString(4, campocadastroSenha.getText());
            if (campocadastroPerfil.getSelectedItem() == (String) "Administrador") {
                pst.setBoolean(5, true);
            } else {
                pst.setBoolean(5, false);
            }
            pst.setString(6, campocadastroID.getText());
            if (campocadastroUser.getText().isEmpty() || campocadastroLogin.getText().isEmpty() || campocadastroSenha.getText().isEmpty() || campocadastroID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {
                int alterado = pst.executeUpdate();
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados");
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
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from usuarios where iduser = ?";
            try {
                pst = conecta.prepareStatement(sql);
                pst.setString(1, campocadastroID.getText());
                int apagar = pst.executeUpdate();
                if (apagar > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Removido");
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
    
    public void pesquisar(){
        String sql = "select * from usuarios where iduser = ?";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campocadastroID.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                campocadastroUser.setText(rs.getString(2));
                campocadastroFone.setText(rs.getString(3));
                campocadastroLogin.setText(rs.getString(4));
                campocadastroSenha.setText(rs.getString(5));
                if (rs.getBoolean(6) == true){
                    campocadastroPerfil.setSelectedItem("Administrador");
                } else {
                    campocadastroPerfil.setSelectedItem("Usuário Comum");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado");
                campocadastroUser.setText(null);
                campocadastroFone.setText(null);
                campocadastroLogin.setText(null);
                campocadastroSenha.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void cadastrar(){
        /*Função de insert sql, lendo dos campos*/
        String sql = "insert into usuarios(usuario, fone, login, senha, is_admin) values(?, ?, ?, ?, ?)";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campocadastroUser.getText());
            pst.setString(2, campocadastroFone.getText());
            pst.setString(3, campocadastroLogin.getText());
            pst.setString(4, campocadastroSenha.getText());
            if (campocadastroPerfil.getSelectedItem() == (String) "Administrador") {
                pst.setBoolean(5, true);
            } else {
                pst.setBoolean(5, false);
            }
            /*Tratamento de campos obrigatórios*/
            if ((campocadastroID.getText().isEmpty() || (campocadastroUser.getText().isEmpty() || (campocadastroLogin.getText().isEmpty() || campocadastroSenha.getText().isEmpty())))){
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {
                /*Execução do insert*/
                int adicionado = pst.executeUpdate();
                /*Informando cadastro correto e limpando campos*/
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
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
        setTitle("Usuários do Sistema");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(615, 445));

        textoID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoID.setText("CódigoID");

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
        campocadastroPerfil.setToolTipText("");

        botaoCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        botaoCreate.setToolTipText("Adicionar");
        botaoCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        botaoCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCreateActionPerformed(evt);
            }
        });

        botaoRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/read.png"))); // NOI18N
        botaoRead.setToolTipText("Pesquisar");
        botaoRead.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        botaoRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoReadActionPerformed(evt);
            }
        });

        botaoUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/update.png"))); // NOI18N
        botaoUpdate.setToolTipText("Alterar");
        botaoUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        botaoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoUpdateActionPerformed(evt);
            }
        });

        botaoDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        botaoDelete.setToolTipText("Excluir");
        botaoDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
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

    private void botaoReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoReadActionPerformed
        /*Lê uma entrada do banco*/
        pesquisar();
    }//GEN-LAST:event_botaoReadActionPerformed

    private void botaoCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCreateActionPerformed
        /*Cria um usuario no banco*/
        cadastrar();
    }//GEN-LAST:event_botaoCreateActionPerformed

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
