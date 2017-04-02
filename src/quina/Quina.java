/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quina;

import classes.TblQuina;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Pattern;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author rubens
 */
public class Quina {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TblQuina dadosQuina = new TblQuina();
        String dado;
        int cont = 1;

        File arquivo = new File("d:/quina/D_QUINA.HTM");

        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            FileReader ler = new FileReader(arquivo);
            BufferedReader lerb = new BufferedReader(ler);
            HtmlToText htmlToText = new HtmlToText();
            s.beginTransaction();
            String linha;

            while (lerb.ready() && cont < 19) {
                linha = lerb.readLine();
                htmlToText.parse(linha);
                dado = htmlToText.getText();
                dado = dado.replace(".", "");
                dado = dado.replace(",", "");

                while (dado.contentEquals("") || !dado.matches("\\d+")) {
                    linha = lerb.readLine();
                        htmlToText.parse(linha);
                    dado = htmlToText.getText();
                    dado = dado.replace(".", "");
                    dado = dado.replace(",", "");
                }

                System.out.println(dado);

                if (cont == 1) {
                    dadosQuina.setConcurso(Integer.parseInt(dado));
                } else if (cont == 2) {
                    dadosQuina.setPrimeiraDez(Integer.parseInt(dado));
                } else if (cont == 3) {
                    dadosQuina.setSegundaDez(Integer.parseInt(dado));
                } else if (cont == 4) {
                    dadosQuina.setTerceiraDez(Integer.parseInt(dado));
                } else if (cont == 5) {
                    dadosQuina.setQuartaDez(Integer.parseInt(dado));
                } else if (cont == 6) {
                    dadosQuina.setQuintaDez(Integer.parseInt(dado));
                } else if (cont == 8) {
                    dadosQuina.setGanhadoresQuina(Integer.parseInt(dado));
                } else if (cont == 9) {
                    dadosQuina.setRateioQuina(Double.parseDouble(dado));
                } else if (cont == 10) {
                    dadosQuina.setGanhadoresQuadra(Integer.parseInt(dado));
                } else if (cont == 11) {
                    dadosQuina.setRateioQuadra(Double.parseDouble(dado));
                    s.save(dadosQuina);
                } else if (cont == 18) {
                    cont = 0;
                    dadosQuina = new TblQuina();
                }
                cont++;
            }
            lerb.close();
        } catch (Exception e) {
        }
            s.getTransaction().commit();

    }

}
