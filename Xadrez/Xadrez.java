package Xadrez;
import java.util.Scanner;
import javax.swing.*;

public class Xadrez {

    /*
		Usando notação algébrica em inglês para definir a posição inicial do tabuleiro
		Letras minúsculas indicam peças pretas e maiúsculas, brancas
		K = King
		Q = Queen
		N = Knight
		B = Bishop
		R = Rook
		P = Pawn
     */
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
        Interface ui = new Interface();
        System.out.println(alfaBeta(profMax, 100000,-100000,"",0));
        
        
        JFrame frame = new JFrame("Xadrez");
        frame.add(ui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 534);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static String movimentosValidos() {
        String list = "";
        for (int i = 0; i < 64; i++) {
            switch (TABULEIRO[i / 8][i % 8]) {
                case "P":
                    list += validoP(i);
                    break;
                case "R":
                    list += validoR(i);
                    break;
                case "N":
                    list += validoN(i);
                    break;
                case "B":
                    list += validoB(i);
                    break;
                case "Q":
                    list += validoQ(i);
                    break;
                case "K":
                    list += validoK(i);
                    break;
            }
        }
        return list;
    }

    public static String validoP(int i) {
        String list="", antiga;
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//captura(diagonal)
                if (Character.isLowerCase(TABULEIRO[r-1][c+j].charAt(0)) && i>=16) {
                    antiga=TABULEIRO[r-1][c+j];
                    TABULEIRO[r][c]=" ";
                    TABULEIRO[r-1][c+j]="P";
                    if (reiSeguro()) {
                        list=list+r+c+(r-1)+(c+j)+antiga;
                    }
                    TABULEIRO[r][c]="P";
                    TABULEIRO[r-1][c+j]=antiga;
                }
            } catch (Exception e) {}
            try {//promoção && captura
                if (Character.isLowerCase(TABULEIRO[r-1][c+j].charAt(0)) && i<16) {
                    String[] temp={"Q","T","B","C"};
                    for (int k=0; k<4; k++) {
                        antiga=TABULEIRO[r-1][c+j];
                        TABULEIRO[r][c]=" ";
                        TABULEIRO[r-1][c+j]=temp[k];
                        if (reiSeguro()) {
                            //column1,column2,captured-piece,new-piece,P
                            list=list+c+(c+j)+antiga+temp[k]+"P";
                        }
                        TABULEIRO[r][c]="P";
                        TABULEIRO[r-1][c+j]=antiga;
                    }
                }
            } catch (Exception e) {}
        }
        try {//move one up
            if (" ".equals(TABULEIRO[r-1][c]) && i>=16) {
                antiga=TABULEIRO[r-1][c];
                TABULEIRO[r][c]=" ";
                TABULEIRO[r-1][c]="P";
                if (reiSeguro()) {
                    list=list+r+c+(r-1)+c+antiga;
                }
                TABULEIRO[r][c]="P";
                TABULEIRO[r-1][c]=antiga;
            }
        } catch (Exception e) {}
        try {//promoção && sem captura
            if (" ".equals(TABULEIRO[r-1][c]) && i<16) {
                String[] temp={"Q","T","B","C"};
                for (int k=0; k<4; k++) {
                    antiga=TABULEIRO[r-1][c];
                    TABULEIRO[r][c]=" ";
                    TABULEIRO[r-1][c]=temp[k];
                    if (reiSeguro()) {
                        //column1,column2,captured-piece,new-piece,P
                        list=list+c+c+antiga+temp[k]+"P";
                    }
                    TABULEIRO[r][c]="P";
                    TABULEIRO[r-1][c]=antiga;
                }
            }
        } catch (Exception e) {}
        try {//mover duas casas(inicio)
            if (" ".equals(TABULEIRO[r-1][c]) && " ".equals(TABULEIRO[r-2][c]) && i>=48) {
                antiga=TABULEIRO[r-2][c];
                TABULEIRO[r][c]=" ";
                TABULEIRO[r-2][c]="P";
                if (reiSeguro()) {
                    list=list+r+c+(r-2)+c+antiga;
                }
                TABULEIRO[r][c]="P";
                TABULEIRO[r-2][c]=antiga;
            }
        } catch (Exception e) {}
        return list;
    }

    public static String validoR(int i) {
        String list = "", antiga;
        int temp =1;
        int L = i / 8, c = i % 8;
        for (int j = -1; j <= 1; j+=2){
            try{
                while(" ".equals(TABULEIRO[L][c+temp*j])){
                    antiga = TABULEIRO[L][c+temp*j];
                    TABULEIRO[L][c]=" ";
                    TABULEIRO[L][c+temp*j]="R";
                    if (reiSeguro()) {
                            list = list + L + c + L + (c + temp * j) + antiga;
                        }
                    TABULEIRO[L][c]="R";
                    TABULEIRO[L][c+temp*j]=antiga;
                    temp++;
                }
                if(Character.isLowerCase(TABULEIRO[L][c+temp*j].charAt(0))){
                    antiga = TABULEIRO[L][c+temp*j];
                    TABULEIRO[L][c]=" ";
                    TABULEIRO[L][c+temp*j]="R";
                    if (reiSeguro()) {
                            list = list + L + c + L + (c + temp * j) + antiga;
                        }
                    TABULEIRO[L][c]="R";
                    TABULEIRO[L][c+temp*j]=antiga;
                }
            } catch(Exception e){}
            temp = 1;
            try{
                while(" ".equals(TABULEIRO[L+temp*j][c])){
                    antiga = TABULEIRO[L+temp*j][c];
                    TABULEIRO[L][c]=" ";
                    TABULEIRO[L+temp*j][c]="R";
                    if (reiSeguro()) {
                            list = list + L + c + (L + temp * j) + c + antiga;
                        }
                    TABULEIRO[L][c]="R";
                    TABULEIRO[L+temp*j][c]=antiga;
                    temp++;
                }
                if(Character.isLowerCase(TABULEIRO[L+temp*j][c].charAt(0))){
                    antiga = TABULEIRO[L+temp*j][c];
                    TABULEIRO[L][c]=" ";
                    TABULEIRO[L+temp*j][c]="R";
                    if (reiSeguro()) {
                            list = list + L + c + (L + temp * j) + c + antiga;
                        }
                    TABULEIRO[L][c]="R";
                    TABULEIRO[L+temp*j][c]=antiga;
                }
            } catch(Exception e){}
            temp=1;
        }
        return list;
    }
    
    public static String validoN(int i) {
        String list="", antiga;
        int L=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    if (Character.isLowerCase(TABULEIRO[L+j][c+k*2].charAt(0)) || " ".equals(TABULEIRO[L+j][c+k*2])) {
                        antiga=TABULEIRO[L+j][c+k*2];
                        TABULEIRO[L][c]=" ";
                        if (reiSeguro()) {
                            list=list+L+c+(L+j)+(c+k*2)+antiga;
                        }
                        TABULEIRO[L][c]="N";
                        TABULEIRO[L+j][c+k*2]=antiga;
                    }
                } catch (Exception e) {}
                try {
                    if (Character.isLowerCase(TABULEIRO[L+j*2][c+k].charAt(0)) || " ".equals(TABULEIRO[L+j*2][c+k])) {
                        antiga=TABULEIRO[L+j*2][c+k];
                        TABULEIRO[L][c]=" ";
                        if (reiSeguro()) {
                            list=list+L+c+(L+j*2)+(c+k)+antiga;
                        }
                        TABULEIRO[L][c]="N";
                        TABULEIRO[L+j*2][c+k]=antiga;
                    }
                } catch (Exception e) {}
            }
        }
        return list;
    }

    public static String validoB(int i) {
        String list = "", antiga;
        int L = i / 8, c = i % 8;
        int temp = 1;
        for (int j = -1; j <= 1; j+=2) {
            for (int k = -1; k <= 1; k+=2) {
                try {
                    while (" ".equals(TABULEIRO[L + temp * j][c + temp * k])) {
                        antiga = TABULEIRO[L + temp * j][c + temp * k];
                        TABULEIRO[L][c] = " ";
                        TABULEIRO[L + temp * j][c + temp * k] = "B";
                        if (reiSeguro()) {
                            list = list + L + c + (L + temp * j) + (c + temp * k) + antiga;
                        }
                        TABULEIRO[L][c] = "B";
                        TABULEIRO[L + temp * j][c + temp * k] = antiga;
                        temp++;
                    }
                    if (Character.isLowerCase(TABULEIRO[L + temp * j][c + temp * k].charAt(0))) {
                        //é lower pq estamos tratando do oponente.
                        antiga = TABULEIRO[L + temp * j][c + temp * k];
                        TABULEIRO[L][c] = " ";
                        TABULEIRO[L + temp * j][c + temp * k] = "B";
                        if (reiSeguro()) {
                            list = list + L + c + (L + temp * j) + (c + temp * k) + antiga;
                        }
                        TABULEIRO[L][c] = "B";
                        TABULEIRO[L + temp * j][c + temp * k] = antiga;
                    }
                } catch (Exception e) {
                }
                temp = 1;
            }
        }
        return list;
    }

    public static String validoQ(int i) {
       String list = "", antiga;
        int L = i / 8, c = i % 8;
        int temp = 1;
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if (j != 0 || k != 0) {
                    try {
                        while (" ".equals(TABULEIRO[L + temp * j][c + temp * k])) {
                            antiga = TABULEIRO[L + temp * j][c + temp * k];
                            TABULEIRO[L][c] = " ";
                            TABULEIRO[L + temp * j][c + temp * k] = "Q";
                            if (reiSeguro()) {
                                list = list + L + c + (L + temp * j) + (c + temp * k) + antiga;
                            }
                            TABULEIRO[L][c] = "Q";
                            TABULEIRO[L + temp * j][c + temp * k] = antiga;
                            temp++;
                        }
                        if (Character.isLowerCase(TABULEIRO[L + temp * j][c + temp * k].charAt(0))) {
                            //é lower pq estamos tratando do oponente.
                            antiga = TABULEIRO[L + temp * j][c + temp * k];
                            TABULEIRO[L][c] = " ";
                            TABULEIRO[L + temp * j][c + temp * k] = "Q";
                            if (reiSeguro()) {
                                list = list + L + c + (L + temp * j) + (c + temp * k) + antiga;
                            }
                            TABULEIRO[L][c] = "Q";
                            TABULEIRO[L + temp * j][c + temp * k] = antiga;
                        }
                    } catch (Exception e) {}
                  temp = 1;
                }
            }
        }
        return list;
    }

    public static String validoK(int i) {
        String list = "", antiga;
        int L = i / 8, c = i % 8;
        for (int j = 0; j < 9; j++) {
            if (j != 4) {
                try {
                    if (Character.isLowerCase(TABULEIRO[L - 1 + j / 3][c - 1 + j % 3].charAt(0)) || " ".equals(TABULEIRO[L - 1 + j / 3][c - 1 + j % 3])) {
                        antiga = TABULEIRO[L - 1 + j / 3][c - 1 + j % 3];
                        TABULEIRO[L][c] = " ";
                        TABULEIRO[L - 1 + j / 3][c - 1 + j % 3] = "K";
                        int temp = posicaoReiMaior;
                        posicaoReiMaior = i + (j / 3) * 8 + j % 3 - 9;
                        if (reiSeguro()) {
                            list = list + L + c + (L - 1 + j / 3) + (c - 1 + j % 3) + antiga;
                        }
                        TABULEIRO[L][c] = "K";
                        TABULEIRO[L - 1 + j / 3][c - 1 + j % 3] = antiga;
                        posicaoReiMaior = temp;
                    }
                } catch (Exception e) {
                }
            }
        }
        //need to add casting later
        return list;
    }
    
    public static boolean reiSeguro() {
       //Movimentação diagonal caso[bispo(b)/rainha(q)]
        int temp=1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(" ".equals(TABULEIRO[posicaoReiMaior/8+temp*i][posicaoReiMaior%8+temp*j])) {temp++;}
                    if ("b".equals(TABULEIRO[posicaoReiMaior/8+temp*i][posicaoReiMaior%8+temp*j]) ||
                            "q".equals(TABULEIRO[posicaoReiMaior/8+temp*i][posicaoReiMaior%8+temp*j])) {
                        return false;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        //Movimentação vertical/hotizontal [torre(r)/rainha(q)]
        for (int i=-1; i<=1; i+=2) {
            try {
                while(" ".equals(TABULEIRO[posicaoReiMaior/8][posicaoReiMaior%8+temp*i])) {temp++;}
                if ("r".equals(TABULEIRO[posicaoReiMaior/8][posicaoReiMaior%8+temp*i]) ||
                        "q".equals(TABULEIRO[posicaoReiMaior/8][posicaoReiMaior%8+temp*i])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(TABULEIRO[posicaoReiMaior/8+temp*i][posicaoReiMaior%8])) {temp++;}
                if ("r".equals(TABULEIRO[posicaoReiMaior/8+temp*i][posicaoReiMaior%8]) ||
                        "q".equals(TABULEIRO[posicaoReiMaior/8+temp*i][posicaoReiMaior%8])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
        }
        //Movimento do Cavalo(n)
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("n".equals(TABULEIRO[posicaoReiMaior/8+i][posicaoReiMaior%8+j*2])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ("n".equals(TABULEIRO[posicaoReiMaior/8+i*2][posicaoReiMaior%8+j])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
        //peão
        if (posicaoReiMaior>=16) {
            try {
                if ("p".equals(TABULEIRO[posicaoReiMaior/80-1][posicaoReiMaior%8-1])) {
                    return false;
                }
            } catch (Exception e) {}
            try {
                if ("p".equals(TABULEIRO[posicaoReiMaior/80-1][posicaoReiMaior%8+1])) {
                    return false;
                }
            } catch (Exception e) {}
            //rei(k)
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("k".equals(TABULEIRO[posicaoReiMaior/8+i][posicaoReiMaior%8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        }
        return true;
    }

    public static void flipBoard(){
        String temp;
        for (int i = 0; i < 32; i++) {
            int L= i/8, c=i%8;
            if(Character.isUpperCase(TABULEIRO[L][c].charAt(0))){
                temp = TABULEIRO[L][c].toLowerCase();
            } else{
                temp = TABULEIRO[L][c].toUpperCase();
            }
            if(Character.isUpperCase(TABULEIRO[L][c].charAt(0))){
                TABULEIRO[L][c] = TABULEIRO[7-L][7-c].toLowerCase();
            } else{
                TABULEIRO[L][c] = TABULEIRO[7-L][7-c].toUpperCase();
            }
            TABULEIRO[7-L][7-c] = temp;
        }
        int rei = posicaoReiMaior;
        posicaoReiMaior = 63-posicaoReiMenor;
        posicaoReiMenor = 63-rei;
    }
    public static int pontuacao(){
        return 0;
    }
    
    public static String alfaBeta(int prof, int beta, int alfa, String jogada, int jogador){
        //tem que retornar a jogada e o score
        String list = movimentosValidos();
        if(prof == 0 || list.length()==0){
            //no caso, ou chegamos na raiz ou não temos mais movimentos(cheque)
            return jogada+(pontuacao());//*(jogador*2-1)
        }
        //criar uma ferramenta para organizar(mapa, talvez?)
        jogador = 0-jogador;// ou 1 ou -1
        for (int i = 0; i < list.length(); i+=5) {
            movimenta(list.substring(i,i+5));
            flipBoard();
            String retorno = alfaBeta(prof-1,beta, alfa,list.substring(i,i+5),jogador);
            int valor = Integer.valueOf(retorno.substring(5));// pega o valor da pontuação daquela jogada(substituir por getPont depois)
            flipBoard();
            desfazMovimento(list.substring(i,i+5));
            if(jogador==0){
                if(valor<=beta){
                    beta=valor;
                    if(prof==profMax){
                        jogada=retorno.substring(0,5);
                    }
                }
            }else{
                if(valor>alfa){
                    alfa=valor;
                    if(prof==profMax){
                        jogada=retorno.substring(0,5);
                    }
                }
            }
            if(alfa>=beta){
                if(jogador==0){
                    return jogada+beta;
                }else{
                    return jogada+alfa;
                }
            }
        }
        //se chegar aqui não achou jogada
        if(jogador==0){
                    return jogada+beta;
                }else{
                    return jogada+alfa;
                }
        
    } 
    
    public static void movimenta(String jogada) {
        /**
         * Lembrando, jogada na forma: x1,y1,x2,y2, c sendo c especial: 'p' para
         * peça capturada 'P' para promoção do peão
         */
        if (jogada.charAt(4) != 'P') {
            TABULEIRO[Character.getNumericValue(jogada.charAt(2))][Character.getNumericValue(jogada.charAt(3))] = TABULEIRO[Character.getNumericValue(jogada.charAt(0))][Character.getNumericValue(jogada.charAt(1))];
            TABULEIRO[Character.getNumericValue(jogada.charAt(0))][Character.getNumericValue(jogada.charAt(1))] = " ";
        } else {
            //se for a promoção de peão
            TABULEIRO[1][Character.getNumericValue(jogada.charAt(0))] = " ";
            TABULEIRO[1][Character.getNumericValue(jogada.charAt(1))] = String.valueOf(jogada.charAt(3));
        }

        /**
         * if (m.charAt(4)!='P') {
         * TABULEIRO[Character.getNumericValue(m.charAt(2))][Character.getNumericValue(m.charAt(3))]=TABULEIRO[Character.getNumericValue(m.charAt(0))][Character.getNumericValue(m.charAt(1))];
         * TABULEIRO[Character.getNumericValue(m.charAt(0))][Character.getNumericValue(m.charAt(1))]="
         * "; if
         * ("K".equals(TABULEIRO[Character.getNumericValue(m.charAt(2))][Character.getNumericValue(m.charAt(3))]))
         * {
         * posicaoReiBranco=8*Character.getNumericValue(m.charAt(2))+Character.getNumericValue(m.charAt(3));
         * } } /*else { //Promove peão
         * TABULEIRO[1][Character.getNumericValue(m.charAt(0))]=" ";
         * TABULEIRO[0][Character.getNumericValue(m.charAt(1))]=String.valueOf(m.charAt(3));
        }
         */
    }
    
    public static void desfazMovimento(String jogada){
        if(jogada.charAt(4)!= 'P'){
            TABULEIRO[Character.getNumericValue(jogada.charAt(0))][Character.getNumericValue(jogada.charAt(1))]=TABULEIRO[Character.getNumericValue(jogada.charAt(2))][Character.getNumericValue(jogada.charAt(3))];
            TABULEIRO[Character.getNumericValue(jogada.charAt(2))][Character.getNumericValue(jogada.charAt(3))]=String.valueOf(jogada.charAt(4));
            
        }else{
            //se for a promoção de peão
            TABULEIRO[1][Character.getNumericValue(jogada.charAt(0))]="p";
            TABULEIRO[0][Character.getNumericValue(jogada.charAt(1))]=String.valueOf(jogada.charAt(2));
        }
    }
}
