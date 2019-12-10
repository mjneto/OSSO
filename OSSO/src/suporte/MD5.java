/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suporte;
    
import java.security.*;
import java.math.*;
import javax.swing.JOptionPane;

public class MD5 {
    public static String gerarMD5(String texto) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(texto.getBytes(),0,texto.length());
            return new BigInteger(1,m.digest()).toString(16);
       
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
}
