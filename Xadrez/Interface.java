/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xadrez;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Interface extends JPanel implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int tamanhoCasa = 64;
	static int mouseX, mouseY, novoMouseX, novoMouseY;

	public void paintComponent(Graphics g){
		for(int i=0; i<64; i+=2){
			g.setColor(new Color(255, 255, 255));
			g.fillRect((i%8+(i/8)%2)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa);
			g.setColor(new Color(100, 100, 100));
			g.fillRect(((i+1)%8-((i+1)/8)%2)*tamanhoCasa, ((i+1)/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa);
		}
		for(int i=0; i<64; i++){
			switch (Main.TABULEIRO[i/8][i%8]) {
	            case "P": 
	            	Image peaoBranco;
	            	peaoBranco = new ImageIcon("src/img/white_pawn.png").getImage();
	            	g.drawImage(peaoBranco, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "p": 
	            	Image peaoPreto;
	            	peaoPreto = new ImageIcon("src/img/black_pawn.png").getImage();
	            	g.drawImage(peaoPreto, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "R":
	            	Image torreBranca;
	            	torreBranca = new ImageIcon("src/img/white_rook.png").getImage();
	            	g.drawImage(torreBranca, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "r":
	            	Image torrePreta;
	            	torrePreta = new ImageIcon("src/img/black_rook.png").getImage();
	            	g.drawImage(torrePreta, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "N": 
	            	Image cavaloBranco;
	            	cavaloBranco = new ImageIcon("src/img/white_knight.png").getImage();
	            	g.drawImage(cavaloBranco, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "n": 
	            	Image cavaloPreto;
	            	cavaloPreto = new ImageIcon("src/img/black_knight.png").getImage();
	            	g.drawImage(cavaloPreto, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "B": 
	            	Image bispoBranco;
	            	bispoBranco = new ImageIcon("src/img/white_bishop.png").getImage();
	            	g.drawImage(bispoBranco, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "b": 
	            	Image bispoPreto;
	            	bispoPreto = new ImageIcon("src/img/black_bishop.png").getImage();
	            	g.drawImage(bispoPreto, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "Q": 
	            	Image rainhaBranca;
	            	rainhaBranca = new ImageIcon("src/img/white_queen.png").getImage();
	            	g.drawImage(rainhaBranca, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "q": 
	            	Image rainhaPreta;
	            	rainhaPreta = new ImageIcon("src/img/black_queen.png").getImage();
	            	g.drawImage(rainhaPreta, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "K": 
	            	Image reiBranco;
	            	reiBranco = new ImageIcon("src/img/white_king.png").getImage();
	            	g.drawImage(reiBranco, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
	            case "k": 
	            	Image reiPreto;
	            	reiPreto = new ImageIcon("src/img/black_king.png").getImage();
	            	g.drawImage(reiPreto, (i%8)*tamanhoCasa, (i/8)*tamanhoCasa, tamanhoCasa, tamanhoCasa, this);
	                break;
			}
		}
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getX()<8*tamanhoCasa &&e.getY()<8*tamanhoCasa) {
            //if inside the board
            mouseX=e.getX();
            mouseY=e.getY();
            repaint();
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getX()<8*tamanhoCasa &&e.getY()<8*tamanhoCasa) {
            //Confere se está dentro do tabuleiro
            novoMouseX=e.getX();
            novoMouseY=e.getY();
            String arrasto;
            //if (novoMouseY/tamanhoCasa==0 && mouseY/tamanhoCasa==1 && "P".equals(Main.TABULEIRO[mouseY/tamanhoCasa][mouseX/tamanhoCasa])) {
                //Promove peão
            	//arrasto=""+mouseX/tamanhoCasa+novoMouseX/tamanhoCasa+Main.TABULEIRO[novoMouseY/tamanhoCasa][novoMouseX/tamanhoCasa]+"QP";
            //} else {
                //regular move
            	arrasto=""+mouseY/tamanhoCasa+mouseX/tamanhoCasa+novoMouseY/tamanhoCasa+novoMouseX/tamanhoCasa+Main.TABULEIRO[novoMouseY/tamanhoCasa][novoMouseX/tamanhoCasa];
            //}
            //String posibilidades=Main.movimentosValidos();
            //if (posibilidades.replaceAll(arrasto, "").length()<posibilidades.length()) {
                //Movimento válido
                Main.movimenta(arrasto);
            //}
            repaint();
        }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}