/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.sql.*;
import acessoBD.ConexaoBD;
import java.awt.Color;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import suporte.validadorCPF;

/**
 *
 * @author manoel.neto
 */

public class Cliente extends javax.swing.JInternalFrame {
    Connection conecta = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    /**
     * Cria novo formulario de Cliente
     */
    public Cliente() {
        initComponents();
        conecta = ConexaoBD.conector();
    }
    
    private void cadastrar(){
        /*Função de insert sql, lendo dos campos*/
        String sql = "insert into clientes(nome_cliente, cpf_cliente, end_cliente, fone_cliente, email_cliente) values(?, ?, ?, ?, ?)";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campoclienteNome.getText());
            pst.setString(2, campoclienteCPF.getText());
            pst.setString(3, campoclienteEndereco.getText());
            pst.setString(4, campoclienteFone.getText());
            pst.setString(5, campoclienteEmail.getText());
            /*Tratamento de campos obrigatórios*/
            if (campoclienteNome.getText().isEmpty() || campoclienteFone.getText().isEmpty() || campoclienteCPF.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {
                /*Execução do insert*/
                int adicionado = pst.executeUpdate();
                /*Informando cadastro correto e limpando campos*/
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente Cadastrado");
                    campoclienteNome.setText(null);
                    campoclienteCPF.setText(null);
                    campoclienteEndereco.setText(null);
                    campoclienteFone.setText(null);
                    campoclienteEmail.setText(null);
                }
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException i){
            JOptionPane.showMessageDialog(null, "CPF já cadastrado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void pesquisar(){
        /*Pesquisa uma entrada em texto do campo de busca no banco*/
        String sql = "select id_cliente as ID, nome_cliente as Nome, cpf_cliente as CPF, end_cliente as Endereco, fone_cliente as Contato, email_cliente as Email from clientes where nome_cliente like ? or cpf_cliente like ?";
        
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campoclientePesquisa.getText() + "%");
            pst.setString(2, campoclientePesquisa.getText() + "%");
            rs = pst.executeQuery();
            /*Usando o rs2xml para preencher tabela*/ 
            tabelaClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void alterar() {
        /*Altera entrada no banco a partir do id*/
        String sql = "update clientes set nome_cliente = ?, cpf_cliente = ?, end_cliente = ?, fone_cliente = ?, email_cliente = ? where id_cliente = ?";
        
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campoclienteNome.getText());
            pst.setString(2, campoclienteCPF.getText());
            pst.setString(3, campoclienteEndereco.getText());
            pst.setString(4, campoclienteFone.getText());
            pst.setString(5, campoclienteEmail.getText());
            pst.setString(6, campoclienteID.getText());
            if (campoclienteNome.getText().isEmpty() || campoclienteFone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {
                int alterado = pst.executeUpdate();
                botaoCreate.setEnabled(true);
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados");
                    campoclienteNome.setText(null);
                    campoclienteID.setText(null);
                    campoclienteCPF.setText(null);
                    campoclienteEndereco.setText(null);
                    campoclienteFone.setText(null);
                    campoclienteEmail.setText(null);
                }
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException i){
            JOptionPane.showMessageDialog(null, "CPF já cadastrado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void remover() {
        /*Remove uma entrada do banco e limpa os campos*/
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from clientes where id_cliente = ?";
            try {
                pst = conecta.prepareStatement(sql);
                pst.setString(1, campoclienteID.getText());
                int apagar = pst.executeUpdate();
                botaoCreate.setEnabled(true);
                if (apagar > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Removido");
                    campoclienteID.setText(null);
                    campoclienteNome.setText(null);
                    campoclienteEndereco.setText(null);
                    campoclienteFone.setText(null);
                    campoclienteEmail.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void usarcampos(){
        /*Preencher os campos da tabela a partir do campo de pesquisa*/
        int usar = tabelaClientes.getSelectedRow();
        campoclienteID.setText(tabelaClientes.getModel().getValueAt(usar, 0).toString());
        campoclienteNome.setText((String) tabelaClientes.getModel().getValueAt(usar, 1));
        campoclienteCPF.setText((String) tabelaClientes.getModel().getValueAt(usar, 2));
        campoclienteEndereco.setText((String) tabelaClientes.getModel().getValueAt(usar, 3));
        campoclienteFone.setText((String) tabelaClientes.getModel().getValueAt(usar, 4));
        campoclienteEmail.setText((String) tabelaClientes.getModel().getValueAt(usar, 5));
        botaoCreate.setEnabled(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaClientes = new javax.swing.JTable();
        campoclientePesquisa = new javax.swing.JTextField();
        campoclienteID = new javax.swing.JTextField();
        textoUser = new javax.swing.JLabel();
        campoclienteNome = new javax.swing.JTextField();
        textoEndereco = new javax.swing.JLabel();
        campoclienteEndereco = new javax.swing.JTextField();
        textoFone = new javax.swing.JLabel();
        campoclienteFone = new javax.swing.JTextField();
        textoCPF = new javax.swing.JLabel();
        campoclienteCPF = new javax.swing.JTextField();
        textoEmail = new javax.swing.JLabel();
        campoclienteEmail = new javax.swing.JTextField();
        botaoCreate = new javax.swing.JButton();
        botaoUpdate = new javax.swing.JButton();
        botaoDelete = new javax.swing.JButton();
        Pesquisa = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Cadastro de Clientes");
        setToolTipText(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(780, 448));

        tabelaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "CPF", "Endereço", "Fone", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaClientes);

        campoclientePesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoclientePesquisaKeyReleased(evt);
            }
        });

        campoclienteID.setEditable(false);
        campoclienteID.setToolTipText("Código ID");
        campoclienteID.setEnabled(false);

        textoUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoUser.setText("* Nome");
        textoUser.setToolTipText("Obrigatório");

        textoEndereco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoEndereco.setText("Endereço");

        textoFone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoFone.setText("* Fone");
        textoFone.setToolTipText("Obrigatório");

        textoCPF.setText("* CPF");
        textoCPF.setToolTipText("Obrigatório");

        campoclienteCPF.setToolTipText("APENAS NÚMEROS");
        campoclienteCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoclienteCPFKeyReleased(evt);
            }
        });

        textoEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoEmail.setText("Email");

        botaoCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        botaoCreate.setToolTipText("Adicionar");
        botaoCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoCreate.setDisabledIcon(null);
        botaoCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCreateActionPerformed(evt);
            }
        });

        botaoUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/update.png"))); // NOI18N
        botaoUpdate.setToolTipText("Alterar");
        botaoUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoUpdate.setDisabledIcon(null);
        botaoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoUpdateActionPerformed(evt);
            }
        });

        botaoDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        botaoDelete.setToolTipText("Excluir");
        botaoDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoDelete.setDisabledIcon(null);
        botaoDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDeleteActionPerformed(evt);
            }
        });

        Pesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/clear.png"))); // NOI18N
        Pesquisa.setToolTipText("Clique para limpar os campos após buscar");
        Pesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limparcampos(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textoFone)
                            .addComponent(textoUser)
                            .addComponent(textoEndereco)
                            .addComponent(textoEmail))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(campoclienteFone, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textoCPF)
                                .addGap(18, 18, 18)
                                .addComponent(campoclienteCPF))
                            .addComponent(campoclienteNome)
                            .addComponent(campoclienteEndereco)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(campoclientePesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(campoclienteEmail))
                        .addGap(31, 31, 31))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botaoDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Pesquisa)
                            .addComponent(campoclienteID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botaoCreate)))
                .addGap(68, 68, 68))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textoEmail, textoEndereco, textoFone, textoUser});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botaoCreate, botaoDelete, botaoUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campoclientePesquisa)
                            .addComponent(Pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoclienteNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoUser)
                            .addComponent(campoclienteID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoclienteEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoEndereco))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoclienteFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoFone)
                            .addComponent(campoclienteCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoCPF))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoEmail)
                            .addComponent(campoclienteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botaoCreate)
                        .addGap(18, 18, 18)
                        .addComponent(botaoUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(botaoDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {campoclienteFone, campoclienteNome, campoclientePesquisa, textoEmail, textoEndereco, textoFone, textoUser});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botaoCreate, botaoDelete, botaoUpdate});

        setBounds(0, 0, 780, 448);
    }// </editor-fold>//GEN-END:initComponents

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

    private void campoclientePesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoclientePesquisaKeyReleased
        /*Mostrar clientes na tabela*/
        pesquisar();
    }//GEN-LAST:event_campoclientePesquisaKeyReleased

    private void tabelaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaClientesMouseClicked
        /*Chama campoclientePesquisaKeyRealeased que usa os campos da tabela*/
        usarcampos();
    }//GEN-LAST:event_tabelaClientesMouseClicked

    private void limparcampos(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_limparcampos
        campoclienteID.setText(null);
        campoclienteNome.setText(null);
        campoclienteCPF.setText(null);
        campoclienteEndereco.setText(null);
        campoclienteFone.setText(null);
        campoclienteEmail.setText(null);
        botaoCreate.setEnabled(true);
    }//GEN-LAST:event_limparcampos

    private void campoclienteCPFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoclienteCPFKeyReleased
        String cpf = campoclienteCPF.getText();
        boolean isCPF = validadorCPF.isCPF(cpf);
        if (isCPF) {
            textoCPF.setForeground(Color.BLUE); 
        } else {
            textoCPF.setForeground(Color.red); 
        }
    }//GEN-LAST:event_campoclienteCPFKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Pesquisa;
    private javax.swing.JButton botaoCreate;
    private javax.swing.JButton botaoDelete;
    private javax.swing.JButton botaoUpdate;
    private javax.swing.JTextField campoclienteCPF;
    private javax.swing.JTextField campoclienteEmail;
    private javax.swing.JTextField campoclienteEndereco;
    private javax.swing.JTextField campoclienteFone;
    private javax.swing.JTextField campoclienteID;
    private javax.swing.JTextField campoclienteNome;
    private javax.swing.JTextField campoclientePesquisa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaClientes;
    private javax.swing.JLabel textoCPF;
    private javax.swing.JLabel textoEmail;
    private javax.swing.JLabel textoEndereco;
    private javax.swing.JLabel textoFone;
    private javax.swing.JLabel textoUser;
    // End of variables declaration//GEN-END:variables
}
