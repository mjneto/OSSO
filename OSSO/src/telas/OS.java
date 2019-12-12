/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.sql.*;
import acessoBD.ConexaoBD;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author manoel.neto
 */

public class OS extends javax.swing.JInternalFrame {
    Connection conecta = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    /**
     * Cria novo form de OS
     */
    
    public OS() {
        initComponents();
        conecta = ConexaoBD.conector();
        preencherComboBox();
    }
    
    private void preencherComboBox() {
        List<String> list = new ArrayList<>();
        String sql = "select desc_servico from servicos";
        try {
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            campoOSServico.removeAllItems();
            while (rs.next()) {
                list.add(rs.getString("desc_servico"));
            }
            for (String desc : list) {
                campoOSServico.addItem(desc);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void preencherCamposTabela(){
        /*Preencher os campos da tabela a partir do campo de pesquisa*/
        String sql = "select id_cliente as ID, nome_cliente as Nome, fone_cliente as Fone from clientes where nome_cliente like ?";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campoOSNomecliente.getText() + "%");
            rs = pst.executeQuery();
            tabelaOSCliente.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void usarDadosCampos() {
        /*Colocar a ID do cliente no campo a partir de uma seleção da tabela*/
        int usar = tabelaOSCliente.getSelectedRow();
        campoIDCliente.setText(tabelaOSCliente.getModel().getValueAt(usar, 0).toString());
    }
    
    private void cadastrar(){
        /*Cadastra a OS*/
        String sql = "insert into os(orcamento, situacao, responsavel, cod_servico, unidade_total, id_cliente) values (?, ?, ?, (select cod from servicos where desc_servico = ?), ?, ?)";
        try {
            pst = conecta.prepareStatement(sql);
            if (checkOSOrcamento.isSelected()) {
                pst.setBoolean(1, true);
            } else {
                pst.setBoolean(1, false);
            }
            pst.setString(2, (String) campoOSSituacao.getSelectedItem());
            pst.setString(3, campoOSResp.getText());
            pst.setString(4, (String) campoOSServico.getSelectedItem());
            pst.setString(5, campoOSnumUN.getText());
            pst.setString(6, campoIDCliente.getText());
            /*Verificação de ID vinculada a OS e o serviço*/
            if (campoIDCliente.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS Cadastrada");
                    campoIDCliente.setText(null);
                    campoOSSituacao.setSelectedIndex(0);
                    campoOSServico.setSelectedIndex(0);
                    campoOSResp.setText(null);
                    campoOSValor.setText("48.17");
                    checkOSOrcamento.setSelected(false);
                }
            }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
        }
    }
    private void pesquisar(){
        /*Abre uma caixa de input para pesquisar no banco uma OS*/
        String id_os = JOptionPane.showInputDialog("Numero da OS");
        String sql  = "select os.id_os,data_os,orcamento,situacao,responsavel,unidade_total,id_cliente,servicos.desc_servico,unidade,valor from os inner join servicos on os.cod_servico = servicos.cod where id_os = " + id_os;
        
        try {
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                campoOSID.setText(rs.getString(1));
                campoOSData.setText(rs.getString(2));
                checkOSOrcamento.setSelected(rs.getBoolean(3));
                campoOSSituacao.setSelectedItem(rs.getString(4));
                campoOSResp.setText(rs.getString(5));
                campoOSnumUN.setText(rs.getString(6));
                campoIDCliente.setText(rs.getString(7));
                campoOSServico.setSelectedItem(rs.getString(8));
                campoOSUN.setText(rs.getString(1));
                campoOSValor.setText(Float.toString(Float.parseFloat(campoOSnumUN.getText()) * Float.parseFloat(rs.getString(2))));
                botaoCreate.setEnabled(false);
                campoOSNomecliente.setEnabled(false);
                checkOSOrcamento.setEnabled(false);
                botaoPrint.setEnabled(true);
            } else if (id_os != null){
                JOptionPane.showMessageDialog(null, "OS não encontrada");
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException i){
            JOptionPane.showMessageDialog(null, "OS Invalida");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void alterar(){
        /*Altera alguns campos da OS*/
        String sql = "update os set orcamento = ?, situacao = ?, responsavel = ?, cod_servico = (select cod from servicos where desc_servico = ?), unidade_total = ? where id_os = ?";
        try {
            pst = conecta.prepareStatement(sql);
            if (checkOSOrcamento.isSelected()) {
                pst.setBoolean(1, true);
            } else {
                pst.setBoolean(1, false);
            }
            pst.setString(2, (String) campoOSSituacao.getSelectedItem());
            pst.setString(3, campoOSResp.getText());
            pst.setString(4, (String) campoOSServico.getSelectedItem());
            pst.setString(5, campoOSnumUN.getText());
            pst.setString(6, campoOSID.getText());
            /*Verificação de ID vinculada a OS e o serviço*/
            if (campoIDCliente.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS Alterada");
                    campoIDCliente.setText(null);
                    campoOSServico.setSelectedIndex(0);
                    campoOSResp.setText(null);
                    campoOSValor.setText("48.17");
                    checkOSOrcamento.setSelected(false);
                    campoOSID.setText(null);
                    campoOSData.setText(null);
                    botaoCreate.setEnabled(true);
                    checkOSOrcamento.setEnabled(true);
                }
            }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void remover(){
        /*Remove a OS do banco*/
        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja excluir?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            String sql = "delete from os where id_os = ?";
            try {
                pst = conecta.prepareStatement(sql);
                pst.setString(1, campoOSID.getText());
                int deletado = pst.executeUpdate();
                if (deletado > 0) {
                    JOptionPane.showMessageDialog(null, "OS Apagada");
                    campoIDCliente.setText(null);
                    campoOSServico.setSelectedIndex(0);
                    campoOSResp.setText(null);
                    campoOSValor.setText("48.17");
                    checkOSOrcamento.setSelected(false);
                    campoOSID.setText(null);
                    campoOSData.setText(null);
                    botaoCreate.setEnabled(true);
                    checkOSOrcamento.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
        }
    }
    
    private void emitirOS() {
        /*Pega o conteudo do campoOSID para gerar um relatório*/
        int confirmacao = JOptionPane.showConfirmDialog(null, "Emitir OS?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                HashMap numOS = new HashMap();
                numOS.put("ID-OS", Integer.parseInt(campoOSID.getText()));
                InputStream path = getClass().getResourceAsStream("/relatorios/ireport/emissaoOS.jasper");
                JasperPrint emite = JasperFillManager.fillReport(path, numOS, conecta);
                JasperViewer.viewReport(emite, false);
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

        textoOSID = new javax.swing.JLabel();
        campoOSID = new javax.swing.JTextField();
        textoOSData = new javax.swing.JLabel();
        campoOSData = new javax.swing.JTextField();
        checkOSOrcamento = new javax.swing.JCheckBox();
        textoOSSituacao = new javax.swing.JLabel();
        campoOSSituacao = new javax.swing.JComboBox<>();
        painelOSCliente = new javax.swing.JPanel();
        campoOSNomecliente = new javax.swing.JTextField();
        textoIDCliente = new javax.swing.JLabel();
        campoIDCliente = new javax.swing.JTextField();
        textobusca = new javax.swing.JLabel();
        scrollpaneOS = new javax.swing.JScrollPane();
        tabelaOSCliente = new javax.swing.JTable();
        textoOSServico = new javax.swing.JLabel();
        textoOSResp = new javax.swing.JLabel();
        campoOSResp = new javax.swing.JTextField();
        textoOSValor = new javax.swing.JLabel();
        campoOSValor = new javax.swing.JTextField();
        botaoCreate = new javax.swing.JButton();
        botaoRead = new javax.swing.JButton();
        botaoUpdate = new javax.swing.JButton();
        botaoDelete = new javax.swing.JButton();
        botaoPrint = new javax.swing.JButton();
        clearOS = new javax.swing.JLabel();
        textoOSUn = new javax.swing.JLabel();
        campoOSServico = new javax.swing.JComboBox<>();
        campoOSUN = new javax.swing.JTextField();
        campoOSnumUN = new javax.swing.JTextField();
        botaoMais = new javax.swing.JButton();
        botaoMenos = new javax.swing.JButton();

        setClosable(true);
        setTitle("Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(780, 448));

        textoOSID.setText("ID OS");

        campoOSID.setEnabled(false);

        textoOSData.setText("Data");

        campoOSData.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        campoOSData.setEnabled(false);

        checkOSOrcamento.setText("Ordem de Serviço");
        checkOSOrcamento.setToolTipText("Clique para mudar para Orçamento");
        checkOSOrcamento.setFocusable(false);
        checkOSOrcamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        checkOSOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkOSOrcamentoActionPerformed(evt);
            }
        });

        textoOSSituacao.setText("Situação");

        campoOSSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contatação Inicial", "Aguardando Aprovação", "Trabalho em andamento", "Orçamento Aprovado", "Orçamento Negado", "Levantamento do Serviço", "Compra de Materiais", "Trabalho Finalizado", "Desistencia do Cliente" }));

        painelOSCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Vincular Cliente"));

        campoOSNomecliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoOSNomeclienteKeyReleased(evt);
            }
        });

        textoIDCliente.setText("* ID");

        campoIDCliente.setToolTipText("Obrigatório");
        campoIDCliente.setEnabled(false);

        textobusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/search.png"))); // NOI18N

        tabelaOSCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Fone"
            }
        ));
        tabelaOSCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaOSClienteMouseClicked(evt);
            }
        });
        scrollpaneOS.setViewportView(tabelaOSCliente);

        javax.swing.GroupLayout painelOSClienteLayout = new javax.swing.GroupLayout(painelOSCliente);
        painelOSCliente.setLayout(painelOSClienteLayout);
        painelOSClienteLayout.setHorizontalGroup(
            painelOSClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOSClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelOSClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpaneOS, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(painelOSClienteLayout.createSequentialGroup()
                        .addComponent(campoOSNomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textobusca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                        .addComponent(textoIDCliente)
                        .addGap(18, 18, 18)
                        .addComponent(campoIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        painelOSClienteLayout.setVerticalGroup(
            painelOSClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOSClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelOSClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(painelOSClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(campoOSNomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(campoIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textoIDCliente))
                    .addComponent(textobusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrollpaneOS, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );

        textoOSServico.setText("* Serviços");

        textoOSResp.setText("Responsável");

        textoOSValor.setText("Valor Total");

        campoOSValor.setText("0");
        campoOSValor.setEnabled(false);

        botaoCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        botaoCreate.setToolTipText("Cadastrar OS");
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
        botaoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoUpdateActionPerformed(evt);
            }
        });

        botaoDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        botaoDelete.setToolTipText("Excluir OS");
        botaoDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDeleteActionPerformed(evt);
            }
        });

        botaoPrint.setText("Emitir OS");
        botaoPrint.setToolTipText("Imprimir");
        botaoPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoPrint.setEnabled(false);
        botaoPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPrintActionPerformed(evt);
            }
        });

        clearOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/clear.png"))); // NOI18N
        clearOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearOSMouseClicked(evt);
            }
        });

        textoOSUn.setText("Un.");

        campoOSServico.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                campoOSServicoItemStateChanged(evt);
            }
        });

        campoOSUN.setEnabled(false);

        campoOSnumUN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoOSnumUN.setText("1");
        campoOSnumUN.setEnabled(false);

        botaoMais.setText("+");
        botaoMais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMaisActionPerformed(evt);
            }
        });

        botaoMenos.setText("-");
        botaoMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMenosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(textoOSServico, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textoOSResp))
                            .addComponent(textoOSValor, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campoOSResp, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                                    .addComponent(campoOSServico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(campoOSValor)
                                        .addGap(18, 18, 18)
                                        .addComponent(textoOSUn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(botaoMenos)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoOSnumUN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoOSUN, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botaoMais)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clearOS))
                            .addComponent(botaoPrint))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(botaoCreate)
                                .addGap(18, 18, 18)
                                .addComponent(botaoRead))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(botaoUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(botaoDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textoOSID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoOSID, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textoOSData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(campoOSData))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textoOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(campoOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(checkOSOrcamento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addComponent(painelOSCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botaoCreate, botaoDelete, botaoRead, botaoUpdate});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textoOSResp, textoOSServico, textoOSSituacao, textoOSValor});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {campoOSUN, campoOSnumUN});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoOSID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(campoOSData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textoOSData)
                                .addComponent(textoOSID)))
                        .addGap(18, 18, 18)
                        .addComponent(checkOSOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoOSSituacao)))
                    .addComponent(painelOSCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoCreate)
                            .addComponent(botaoRead))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botaoUpdate, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textoOSServico)
                                .addComponent(campoOSServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(clearOS))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoOSResp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoOSResp))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoOSValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoOSValor)
                            .addComponent(textoOSUn)
                            .addComponent(campoOSnumUN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botaoMenos)
                            .addComponent(botaoMais)
                            .addComponent(campoOSUN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(botaoPrint)))
                .addGap(54, 54, 54))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botaoCreate, botaoDelete, botaoRead, botaoUpdate});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {campoOSUN, campoOSnumUN});

        setBounds(0, 0, 780, 448);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCreateActionPerformed
        /*Cria uma OS no banco*/
        if (campoOSID.getText().equals("")) {
            String sql = "select auto_increment from information_schema.tables where table_schema = 'dbosso' and table_name = 'os'";
            try {
                pst = conecta.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    campoOSID.setText(rs.getString(1));
                    java.util.Date datas = new java.util.Date();
                    DateFormat dataformatado = DateFormat.getDateTimeInstance();
                    campoOSData.setText(dataformatado.format(datas));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            cadastrar();
        }
    }//GEN-LAST:event_botaoCreateActionPerformed

    private void botaoReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoReadActionPerformed
        /*Pesquisa uma OS a partir do ID*/
        pesquisar();
    }//GEN-LAST:event_botaoReadActionPerformed

    private void botaoUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoUpdateActionPerformed
        /*Altera uma entrada no banco*/
        botaoPrint.setEnabled(false);
        alterar();
    }//GEN-LAST:event_botaoUpdateActionPerformed

    private void botaoDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDeleteActionPerformed
        /*Remove uma entrada do banco*/
        botaoPrint.setEnabled(false);
        remover();
    }//GEN-LAST:event_botaoDeleteActionPerformed

    private void campoOSNomeclienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoOSNomeclienteKeyReleased
        /*Enquanto digita na caixa de pesquisa, preenche a tabela*/
        preencherCamposTabela();
    }//GEN-LAST:event_campoOSNomeclienteKeyReleased

    private void tabelaOSClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaOSClienteMouseClicked
        /*Ao clicar em uma linha da tabela, extrai a ID para vincular a OS*/
        usarDadosCampos();
    }//GEN-LAST:event_tabelaOSClienteMouseClicked

    private void checkOSOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkOSOrcamentoActionPerformed
        /*Muda estado da label checkOSOrcamento de Ordem de Serviço para Orçamento*/
        if (checkOSOrcamento.isSelected()) {
            checkOSOrcamento.setText("Orçamento");
            checkOSOrcamento.setToolTipText("Clique para mundar para Ordem de Serviço");
        } else {
            checkOSOrcamento.setText("Ordem de Serviço");
            checkOSOrcamento.setToolTipText("Clique para mundar para Orçamento");
        }
    }//GEN-LAST:event_checkOSOrcamentoActionPerformed

    private void botaoPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPrintActionPerformed
        /*Seleciona as informações da OS para emissão*/
        emitirOS();
    }//GEN-LAST:event_botaoPrintActionPerformed

    private void clearOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearOSMouseClicked
        /*Limpar campos*/
        campoOSID.setText(null);
        campoOSData.setText(null);
        checkOSOrcamento.setSelected(false);
        campoOSNomecliente.setText(null);
        campoOSNomecliente.setEnabled(true);
        campoIDCliente.setText(null);
        campoOSServico.setSelectedIndex(0);
        campoOSResp.setText(null);
        campoOSSituacao.setSelectedIndex(0);
        campoOSValor.setText("48.17");
        campoOSnumUN.setText("1");
        botaoCreate.setEnabled(true);
        botaoPrint.setEnabled(false);
    }//GEN-LAST:event_clearOSMouseClicked

    private void campoOSServicoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_campoOSServicoItemStateChanged
        String sql = "select unidade, valor from servicos where desc_servico = ?";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campoOSServico.getSelectedItem().toString());
            rs = pst.executeQuery();
            if (rs.next()) {
                campoOSUN.setText(rs.getString(1));
                campoOSValor.setText(rs.getString(2));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_campoOSServicoItemStateChanged

    private void botaoMaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMaisActionPerformed
        int cont = Integer.parseInt(campoOSnumUN.getText()) + 1;
        campoOSnumUN.setText(Integer.toString(cont));
        float valorTotal = Float.parseFloat(campoOSValor.getText());
        valorTotal += valorTotal;
        campoOSValor.setText(Float.toString(valorTotal));
    }//GEN-LAST:event_botaoMaisActionPerformed

    private void botaoMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMenosActionPerformed
        if (!"1".equals(campoOSnumUN.getText())) {
            int cont = Integer.parseInt(campoOSnumUN.getText()) - 1;
            campoOSnumUN.setText(Integer.toString(cont));
            float valorTotal = Float.parseFloat(campoOSValor.getText());
            valorTotal = valorTotal/2;
            campoOSValor.setText(Float.toString(valorTotal));
        }
    }//GEN-LAST:event_botaoMenosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCreate;
    private javax.swing.JButton botaoDelete;
    private javax.swing.JButton botaoMais;
    private javax.swing.JButton botaoMenos;
    private javax.swing.JButton botaoPrint;
    private javax.swing.JButton botaoRead;
    private javax.swing.JButton botaoUpdate;
    private javax.swing.JTextField campoIDCliente;
    private javax.swing.JTextField campoOSData;
    private javax.swing.JTextField campoOSID;
    private javax.swing.JTextField campoOSNomecliente;
    private javax.swing.JTextField campoOSResp;
    private javax.swing.JComboBox<String> campoOSServico;
    private javax.swing.JComboBox<String> campoOSSituacao;
    private javax.swing.JTextField campoOSUN;
    private javax.swing.JTextField campoOSValor;
    private javax.swing.JTextField campoOSnumUN;
    private javax.swing.JCheckBox checkOSOrcamento;
    private javax.swing.JLabel clearOS;
    private javax.swing.JPanel painelOSCliente;
    private javax.swing.JScrollPane scrollpaneOS;
    private javax.swing.JTable tabelaOSCliente;
    private javax.swing.JLabel textoIDCliente;
    private javax.swing.JLabel textoOSData;
    private javax.swing.JLabel textoOSID;
    private javax.swing.JLabel textoOSResp;
    private javax.swing.JLabel textoOSServico;
    private javax.swing.JLabel textoOSSituacao;
    private javax.swing.JLabel textoOSUn;
    private javax.swing.JLabel textoOSValor;
    private javax.swing.JLabel textobusca;
    // End of variables declaration//GEN-END:variables
}
