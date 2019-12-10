/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.sql.*;
import acessoBD.ConexaoBD;
import java.io.InputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import suporte.MD5;

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
        botaoCancela.setVisible(false);
    }

    private void alterar() {
        /*Altera uma entrada no banco dado um id*/
        String sql = "update usuarios set usuario = ?, fone = ?, login = ?, senha = ?, is_admin = ? where iduser = ?";
        
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campocadastroNome.getText());
            pst.setString(2, campocadastroFone.getText());
            pst.setString(3, campocadastroLogin.getText());
            pst.setString(4, MD5.gerarMD5(campocadastroSenha.getText()));
            if (campocadastroPerfil.getSelectedItem() == (String) "Administrador") {
                pst.setBoolean(5, true);
            } else {
                pst.setBoolean(5, false);
            }
            pst.setString(6, campocadastroID.getText());
            if (campocadastroNome.getText().isEmpty() || campocadastroLogin.getText().isEmpty() || campocadastroSenha.getText().isEmpty() || campocadastroID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {
                int alterado = pst.executeUpdate();
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados");
                    campocadastroID.setText(null);
                    campocadastroNome.setText(null);
                    campocadastroFone.setText(null);
                    campocadastroLogin.setText(null);
                    campocadastroSenha.setText(null);
                }
                botaoCreate.setEnabled(true);
                botaoRead.setEnabled(true);
                botaoUpdate.setEnabled(false);
                botaoDelete.setEnabled(false);
                campocadastroID.setEnabled(false);
                campocadastroNome.setEnabled(false);
                campocadastroFone.setEnabled(false);
                campocadastroLogin.setEnabled(false);
                campocadastroSenha.setEnabled(false);
                campocadastroPerfil.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {
        /*Remove uma entrada no banco dado um id*/
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
                    campocadastroNome.setText(null);
                    campocadastroFone.setText(null);
                    campocadastroLogin.setText(null);
                    campocadastroSenha.setText(null);
                    botaoCreate.setEnabled(true);
                    botaoRead.setEnabled(true);
                    botaoUpdate.setEnabled(false);
                    botaoDelete.setEnabled(false);
                    campocadastroID.setEnabled(false);
                    campocadastroNome.setEnabled(false);
                    campocadastroFone.setEnabled(false);
                    campocadastroLogin.setEnabled(false);
                    campocadastroSenha.setEnabled(false);
                    campocadastroPerfil.setEnabled(false);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    public void pesquisar(){
        /*Pesquisa uma entrada no banco dado um id e seta as caixas de texto*/
        String sql = "select * from usuarios where iduser = ? or usuario like ?";
        
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campocadastroID.getText());
            pst.setString(2, campocadastroID.getText() + "%");
            rs = pst.executeQuery();
            if (rs.next()) {
                campocadastroNome.setText(rs.getString(2));
                campocadastroFone.setText(rs.getString(3));
                campocadastroLogin.setText(rs.getString(4));
                campocadastroSenha.setText(rs.getString(5));
                if (rs.getBoolean(6) == true){
                    campocadastroPerfil.setSelectedItem("Administrador");
                } else {
                    campocadastroPerfil.setSelectedItem("Usuário Comum");
                }
                botaoDelete.setEnabled(true);
                botaoUpdate.setEnabled(true);
                campocadastroNome.setEnabled(true);
                campocadastroNome.setEditable(false);
                campocadastroFone.setEnabled(true);
                campocadastroFone.setEditable(false);
                campocadastroLogin.setEnabled(true);
                campocadastroLogin.setEditable(false);
                campocadastroSenha.setEnabled(true);
                campocadastroSenha.setEditable(false);
                campocadastroPerfil.setEnabled(true);
                campocadastroPerfil.setEditable(false);
                textoID.setText("ID");
                textoID.setIcon(null);
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado");
                campocadastroNome.setText(null);
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
            pst.setString(1, campocadastroNome.getText());
            pst.setString(2, campocadastroFone.getText());
            pst.setString(3, campocadastroLogin.getText());
            pst.setString(4, MD5.gerarMD5(campocadastroSenha.getText()));
            if (campocadastroPerfil.getSelectedItem() == (String) "Administrador") {
                pst.setBoolean(5, true);
            } else {
                pst.setBoolean(5, false);
            }
            /*Tratamento de campos obrigatórios*/
            if (((campocadastroNome.getText().isEmpty() || (campocadastroLogin.getText().isEmpty() || campocadastroSenha.getText().isEmpty())))){
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {
                /*Execução do insert*/
                int adicionado = pst.executeUpdate();
                /*Informando cadastro correto e limpando campos*/
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Cadastrado");
                    campocadastroID.setText(null);
                    campocadastroNome.setText(null);
                    campocadastroFone.setText(null);
                    campocadastroLogin.setText(null);
                    campocadastroSenha.setText(null);
                botaoRead.setEnabled(true);
                campocadastroID.setEnabled(false);
                campocadastroNome.setEnabled(false);
                campocadastroFone.setEnabled(false);
                campocadastroLogin.setEnabled(false);
                campocadastroSenha.setEnabled(false);
                campocadastroPerfil.setEnabled(false);
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
        campocadastroNome = new javax.swing.JTextField();
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
        botaoCancela = new javax.swing.JButton();

        setClosable(true);
        setTitle("Usuários do Sistema");
        setToolTipText(null);
        setPreferredSize(new java.awt.Dimension(780, 448));

        textoID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoID.setText("ID");
        textoID.setToolTipText("Obrigatório");

        campocadastroID.setToolTipText(null);
        campocadastroID.setEnabled(false);

        textoUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoUser.setText("* Nome");
        textoUser.setToolTipText("Obrigatório");

        campocadastroNome.setToolTipText(null);
        campocadastroNome.setEnabled(false);

        textoFone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoFone.setText("Telefone");

        campocadastroFone.setToolTipText(null);
        campocadastroFone.setEnabled(false);

        textoLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoLogin.setText("* Login");
        textoLogin.setToolTipText("Obrigatório");

        campocadastroLogin.setToolTipText(null);
        campocadastroLogin.setEnabled(false);

        textoSenha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoSenha.setText("* Senha");
        textoSenha.setToolTipText("Obrigatório");

        campocadastroSenha.setToolTipText(null);
        campocadastroSenha.setEnabled(false);

        textoPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoPerfil.setText("* Perfil");
        textoPerfil.setToolTipText("Obrigatório");

        campocadastroPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Usuário Comum", "Administrador" }));
        campocadastroPerfil.setToolTipText(null);
        campocadastroPerfil.setEnabled(false);

        botaoCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        botaoCreate.setToolTipText("Adicionar");
        botaoCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCreateActionPerformed(evt);
            }
        });

        botaoRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/read.png"))); // NOI18N
        botaoRead.setToolTipText("Pesquisar");
        botaoRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoReadActionPerformed(evt);
            }
        });

        botaoUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/update.png"))); // NOI18N
        botaoUpdate.setToolTipText("Alterar");
        botaoUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoUpdate.setEnabled(false);
        botaoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoUpdateActionPerformed(evt);
            }
        });

        botaoDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        botaoDelete.setToolTipText("Excluir");
        botaoDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoDelete.setEnabled(false);
        botaoDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDeleteActionPerformed(evt);
            }
        });

        botaoCancela.setText("Cancelar");
        botaoCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botaoCancela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botaoDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textoID)
                            .addComponent(textoLogin)
                            .addComponent(textoUser)
                            .addComponent(textoFone)
                            .addComponent(textoSenha)
                            .addComponent(textoPerfil))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campocadastroLogin)
                            .addComponent(campocadastroNome)
                            .addComponent(campocadastroFone)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campocadastroPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campocadastroID, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                    .addComponent(campocadastroSenha))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoCreate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botaoRead, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botaoUpdate, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(76, 76, 76))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textoFone, textoID, textoLogin, textoPerfil, textoSenha, textoUser});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botaoCreate, botaoDelete, botaoRead, botaoUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botaoCreate)
                        .addGap(18, 18, 18)
                        .addComponent(botaoRead)
                        .addGap(18, 18, 18)
                        .addComponent(botaoUpdate))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campocadastroID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campocadastroNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(textoPerfil))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botaoDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoCancela))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {textoFone, textoID, textoLogin, textoPerfil, textoSenha, textoUser});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botaoCreate, botaoDelete, botaoRead, botaoUpdate});

        setBounds(0, 0, 780, 448);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoReadActionPerformed
        /*Lê uma entrada do banco*/
        if (campocadastroID.isEnabled() == true) {
            pesquisar();
        } else {
            ImageIcon path = new ImageIcon(getClass().getResource("/icones/search.png"));                    
            campocadastroID.setEnabled(true);
            textoID.setText(null);
            textoID.setIcon(path);
            botaoCreate.setEnabled(false);
            botaoCancela.setVisible(true);
        }
    }//GEN-LAST:event_botaoReadActionPerformed

    private void botaoCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCreateActionPerformed
        /*Cria um usuario no banco*/
         if (campocadastroNome.isEnabled() == true) {
            cadastrar();
        } else {
            String sql = "select auto_increment from information_schema.tables where table_schema = 'dbosso' and table_name = 'usuarios'";
            try {
                pst = conecta.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    campocadastroID.setText(rs.getString(1));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            botaoRead.setEnabled(false);
            campocadastroNome.setEnabled(true);
            campocadastroFone.setEnabled(true);
            campocadastroLogin.setEnabled(true);
            campocadastroSenha.setEnabled(true);
            campocadastroPerfil.setEnabled(true);
            botaoCancela.setVisible(true);
        }
    }//GEN-LAST:event_botaoCreateActionPerformed

    private void botaoUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoUpdateActionPerformed
        // Vai alterar os dados colocados no banco
        if (campocadastroID.isEnabled() == false) {
            alterar();
            botaoCancela.setVisible(false);
        } else {
            botaoDelete.setEnabled(false);
            botaoRead.setEnabled(false);
            campocadastroID.setEnabled(false);
            campocadastroNome.setEditable(true);
            campocadastroFone.setEditable(true);
            campocadastroLogin.setEditable(true);
            campocadastroSenha.setEditable(true);
            campocadastroPerfil.setEnabled(true);
        }
    }//GEN-LAST:event_botaoUpdateActionPerformed

    private void botaoDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDeleteActionPerformed
        // Vai deletar o usuario
        if (campocadastroID.isEnabled() == true) {
            remover();
            botaoCancela.setVisible(false);
        }
    }//GEN-LAST:event_botaoDeleteActionPerformed

    private void botaoCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelaActionPerformed
        campocadastroID.setEnabled(false);
        campocadastroID.setEditable(true);
        campocadastroNome.setEnabled(false);
        campocadastroNome.setEditable(true);
        campocadastroFone.setEnabled(false);
        campocadastroFone.setEditable(true);
        campocadastroLogin.setEnabled(false);
        campocadastroLogin.setEditable(true);
        campocadastroSenha.setEnabled(false);
        campocadastroSenha.setEditable(true);
        campocadastroPerfil.setEnabled(false);
        campocadastroID.setText(null);
        campocadastroNome.setText(null);
        campocadastroFone.setText(null);
        campocadastroLogin.setText(null);
        campocadastroSenha.setText(null);
        botaoCreate.setEnabled(true);
        botaoUpdate.setEnabled(false);
        botaoRead.setEnabled(true);
        botaoDelete.setEnabled(false);
        botaoCancela.setVisible(false);
    }//GEN-LAST:event_botaoCancelaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancela;
    private javax.swing.JButton botaoCreate;
    private javax.swing.JButton botaoDelete;
    private javax.swing.JButton botaoRead;
    private javax.swing.JButton botaoUpdate;
    private javax.swing.JTextField campocadastroFone;
    private javax.swing.JTextField campocadastroID;
    private javax.swing.JTextField campocadastroLogin;
    private javax.swing.JTextField campocadastroNome;
    private javax.swing.JComboBox<String> campocadastroPerfil;
    private javax.swing.JPasswordField campocadastroSenha;
    private javax.swing.JLabel textoFone;
    private javax.swing.JLabel textoID;
    private javax.swing.JLabel textoLogin;
    private javax.swing.JLabel textoPerfil;
    private javax.swing.JLabel textoSenha;
    private javax.swing.JLabel textoUser;
    // End of variables declaration//GEN-END:variables
}
