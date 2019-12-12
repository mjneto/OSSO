package telas;

import java.sql.*;
import acessoBD.ConexaoBD;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import suporte.MD5;

/**
 *
 * @author manoel.neto
 */

/*Caixa de Login - preparação*/
public class Login extends javax.swing.JFrame {
    Connection conecta = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    /*Método para autenticação*/
    public void Logar(){
        String sql = "select * from usuarios where login_user = ? and senha_user = ?";
        String senhaMD5;
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, campouser.getText());
            //Comparar senha do BD e local com MD5
            pst.setString(2, senhaMD5 = MD5.gerarMD5(campopw.getText()));
            rs = pst.executeQuery();
            
            if (rs.next()){
                /*checagem no banco se o usuario é admin (is_admin = true)*/
                boolean perfil = rs.getBoolean(6);
                if (perfil == true) {
                    Principal telainicio = new Principal();
                    telainicio.setVisible(true);
                    Principal.menuRelatorio.setEnabled(true);
                    Principal.itemUsuarios.setEnabled(true);
                    Principal.textoTipoUser.setText("Administrador");
                    Principal.textoTipoUser.setForeground(Color.red);
                    this.dispose();
                } else {
                    Principal telainicio = new Principal();
                    telainicio.setVisible(true);
                    Principal.textoTipoUser.setVisible(false);
                    this.dispose();
                }
                Principal.nomeUsuario.setText(rs.getString(2));
                conecta.close();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou Senha inválidos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    /**
     * Cria novo formulario da classe Login
     * Conexão inicial com o banco e tratamento
     */
    
    public Login() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/OSSOicon.png")));
        
        conecta = ConexaoBD.conector();
        if (conecta == null) {
            JOptionPane.showMessageDialog(null, "Falha ao conectar ao banco de dados");
            System.exit(0);
        } else {
            statusbanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/ok.png")));
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

        textouser = new javax.swing.JLabel();
        textopw = new javax.swing.JLabel();
        campouser = new javax.swing.JTextField();
        botaoLogin = new javax.swing.JButton();
        campopw = new javax.swing.JPasswordField();
        statusbanco = new javax.swing.JLabel();
        textoTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OSSO Login");
        setResizable(false);

        textouser.setText("Usuário");
        textouser.setAutoscrolls(true);

        textopw.setText("Senha");
        textopw.setAutoscrolls(true);

        campouser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campouserActionPerformed(evt);
            }
        });

        botaoLogin.setText("Logar");
        botaoLogin.setAutoscrolls(true);
        botaoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLoginActionPerformed(evt);
            }
        });

        statusbanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/error.png"))); // NOI18N
        statusbanco.setText("Conexão com o banco de dados");

        textoTitulo.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        textoTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoTitulo.setText("OSSO - Ordem de Serviço da Sistema Online");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textoTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(statusbanco)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textouser)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textopw, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campouser, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campopw, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(61, 61, 61))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textouser, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campouser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textopw, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campopw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botaoLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(statusbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(360, 294));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void campouserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campouserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campouserActionPerformed

    private void botaoLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLoginActionPerformed
        /*Chamada do metodo para autenticar no banco quando tocar em LOGAR*/
        Logar();
    }//GEN-LAST:event_botaoLoginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Criar e mostrar o formulario */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoLogin;
    private javax.swing.JPasswordField campopw;
    private javax.swing.JTextField campouser;
    private javax.swing.JLabel statusbanco;
    private javax.swing.JLabel textoTitulo;
    private javax.swing.JLabel textopw;
    private javax.swing.JLabel textouser;
    // End of variables declaration//GEN-END:variables
}
