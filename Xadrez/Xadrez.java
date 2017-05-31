package Xadrez;

import javax.swing.JFrame;

public class Xadrez {
    static String TABULEIRO[][] = {
        {"r", "n", "b", "q", "k", "b", "n", "r"},
        {"p", "p", "p", "p", "p", "p", "p", "p"},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {"P", "P", "P", "P", "P", "P", "P", "P"},
        {"R", "N", "B", "Q", "K", "B", "N", "R"},};

    //Usado para conferir se o rei está em xeque
    static int posicaoReiMaior, posicaoReiMenor;
    static int profMax=4;//(prfundidade maxima que vai descer na arvore)

    public static void main(String[] args) {
        Interface ui = new Interface() {
        };

        JFrame frame = new JFrame("Xadrez");
        frame.add(ui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 534);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
