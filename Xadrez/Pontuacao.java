package xadrez;

public class Pontuacao {
    static int pawnBoard[][]={//attribute to http://chessprogramming.wikispaces.com/Simplified+evaluation+function
        { 0,  0,  0,  0,  0,  0,  0,  0},
        {50, 50, 50, 50, 50, 50, 50, 50},
        {10, 10, 20, 30, 30, 20, 10, 10},
        { 5,  5, 10, 25, 25, 10,  5,  5},
        { 0,  0,  0, 20, 20,  0,  0,  0},
        { 5, -5,-10,  0,  0,-10, -5,  5},
        { 5, 10, 10,-20,-20, 10, 10,  5},
        { 0,  0,  0,  0,  0,  0,  0,  0}};
    static int rookBoard[][]={
        { 0,  0,  0,  0,  0,  0,  0,  0},
        { 5, 10, 10, 10, 10, 10, 10,  5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        { 0,  0,  0,  5,  5,  0,  0,  0}};
    static int knightBoard[][]={
        {-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,  0,  0,  0,  0,-20,-40},
        {-30,  0, 10, 15, 15, 10,  0,-30},
        {-30,  5, 15, 20, 20, 15,  5,-30},
        {-30,  0, 15, 20, 20, 15,  0,-30},
        {-30,  5, 10, 15, 15, 10,  5,-30},
        {-40,-20,  0,  5,  5,  0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50}};
    static int bishopBoard[][]={
        {-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5, 10, 10,  5,  0,-10},
        {-10,  5,  5, 10, 10,  5,  5,-10},
        {-10,  0, 10, 10, 10, 10,  0,-10},
        {-10, 10, 10, 10, 10, 10, 10,-10},
        {-10,  5,  0,  0,  0,  0,  5,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}};
    static int queenBoard[][]={
        {-20,-10,-10, -5, -5,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5,  5,  5,  5,  0,-10},
        { -5,  0,  5,  5,  5,  5,  0, -5},
        {  0,  0,  5,  5,  5,  5,  0, -5},
        {-10,  5,  5,  5,  5,  5,  0,-10},
        {-10,  0,  5,  0,  0,  0,  0,-10},
        {-20,-10,-10, -5, -5,-10,-10,-20}};
    static int kingMidBoard[][]={
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        { 20, 20,  0,  0,  0,  0, 20, 20},
        { 20, 30, 10,  0,  0, 10, 30, 20}};
    static int kingEndBoard[][]={
        {-50,-40,-30,-20,-20,-30,-40,-50},
        {-30,-20,-10,  0,  0,-10,-20,-30},
        {-30,-10, 20, 30, 30, 20,-10,-30},
        {-30,-10, 30, 40, 40, 30,-10,-30},
        {-30,-10, 30, 40, 40, 30,-10,-30},
        {-30,-10, 20, 30, 30, 20,-10,-30},
        {-30,-30,  0,  0,  0,  0,-30,-30},
        {-50,-30,-30,-30,-30,-30,-30,-50}};
    
    
    
    public static int pontuacao(int list, int prof){
                        
        int cont=0, material = pontMaterial();
        cont+=pontAtaque();
        cont+=material;
        cont+=pontMobilidade(list, prof, material);
        cont+=pontPosicional(material);
        Xadrez.flipBoard();
        cont-=pontAtaque();
        cont-=material;
        cont-=pontMobilidade(list, prof, material);
        cont-=pontPosicional(material);
        Xadrez.flipBoard();
        return -(cont+prof*50);
    }
    
    public static int pontAtaque() {
       int cont=0;
        int tempPositionC=Xadrez.posicaoReiMaior;
        for (int i=0;i<64;i++) {
            switch (Xadrez.TABULEIRO[i/8][i%8]) {
                case "P": {Xadrez.posicaoReiMaior=i; if (!Xadrez.reiSeguro()) {cont-=64;}}
                    break;
                case "R": {Xadrez.posicaoReiMaior=i; if (!Xadrez.reiSeguro()) {cont-=500;}}
                    break;
                case "K": {Xadrez.posicaoReiMaior=i; if (!Xadrez.reiSeguro()) {cont-=300;}}
                    break;
                case "B": {Xadrez.posicaoReiMaior=i; if (!Xadrez.reiSeguro()) {cont-=300;}}
                    break;
                case "Q": {Xadrez.posicaoReiMaior=i; if (!Xadrez.reiSeguro()) {cont-=900;}}
                    break;
            }
        }
        Xadrez.posicaoReiMaior=tempPositionC;
        if (!Xadrez.reiSeguro()) {cont-=200;}
        return cont/2;
    }
    public static int pontMaterial() {
        int cont=0, contBispo=0;
        for (int i=0;i<64;i++) {
            switch (Xadrez.TABULEIRO[i/8][i%8]) {
                case "P": cont+=100;
                    break;
                case "R": cont+=500;
                    break;
                case "K": cont+=300;
                    break;
                case "B": contBispo+=1;
                    break;
                case "Q": cont+=900;
                    break;
            }
        }
        if (contBispo>=2) {
            cont+=300*contBispo;
        } else {
            if (contBispo==1) {cont+=250;}
        }
        return cont;
    }
    public static int pontMobilidade(int listLength, int prof, int material) {
        int cont=0;
        cont+=listLength;//5 pointer per valid move
        if (listLength==0) {//Se o lado está em checkmate ou stalemate
            if (!Xadrez.reiSeguro()) {//se checkmate
                cont+=-200000*prof;
            } else {//se stalemate
                cont+=-150000*prof;
            }
        }
        return 0;
    }
    public static int pontPosicional(int material) {
        int counter=0;
        for (int i=0;i<64;i++) {
            switch (Xadrez.TABULEIRO[i/8][i%8]) {
                case "P": counter+=pawnBoard[i/8][i%8];
                    break;
                case "R": counter+=rookBoard[i/8][i%8];
                    break;
                case "K": counter+=knightBoard[i/8][i%8];
                    break;
                case "B": counter+=bishopBoard[i/8][i%8];
                    break;
                case "Q": counter+=queenBoard[i/8][i%8];
                    break;
                case "A": if (material>=1750) {counter+=kingMidBoard[i/8][i%8]; counter+=Xadrez.validoK(Xadrez.posicaoReiMaior).length()*10;} else
                {counter+=kingEndBoard[i/8][i%8]; counter+=Xadrez.validoK(Xadrez.posicaoReiMaior).length()*30;}
                    break;
            }
        }
        return counter;
    }
}
