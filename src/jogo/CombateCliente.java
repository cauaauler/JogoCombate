package jogo;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;


public class CombateCliente {
	
	boolean perdeu = true;
	
	JButton backupInimigo;
	
	String nome = "";
	String forca = "0";
	
	Socket cliente;
	PrintWriter saida;
	BufferedReader entrada;

	boolean enviarResposta = false;
	
	boolean gameStart = false;

	AgenteSecreto agente = new AgenteSecreto(1,"Agente",1);
	Soldado soldado = new Soldado(2,"Soldado",8);
	Cabo cabo = new Cabo(3,"Cabo",5);
	Sargento sargento = new Sargento(4,"Sargento",4);
	Subtenente subtenente = new Subtenente(5,"Subtenente",4);
	Tenente tenente = new Tenente(6,"Tenente",4);
	Capitao capitao = new Capitao(7,"Capit�o",3);
	Major major = new Major(8,"Major",2);
	Coronel coronel = new Coronel(9,"Coronel",1);
	General general = new General(10,"General",1);
	Mina mina = new Mina(0,"Mina",6);
	Prisioneiro prisioneiro = new Prisioneiro(999, "Prisioneiro", 1);


	ArrayList<Exercito> exercito = new ArrayList<>();

	JButton[][] botoes = new JButton[10][10];

	JButton lastButton = null;

	int z = 0; //auxiliar

	JButton direita = null;
	JButton esquerda = null;
	JButton cima = null;
	JButton baixo = null;
	
	public Combate combate;

	boolean caminhando = false;
	JButton backup = null;

	JButton botao;

	boolean umaVez;
	
	String buttonName = "";

	//PROPOR��ES DOS BOTOES
	int tamanhoButtonX; 
	int tamanhoButtonY;
	
	boolean minhaVez = false;
	
	private JFrame frmCliente;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)  {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CombateCliente window = new CombateCliente();
					window.frmCliente.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CombateCliente() {
		initialize();

		exercito.add(agente);
		for (int i = 0; i < soldado.quantidade; i++) {
			exercito.add(soldado);
		}
		for (int i = 0; i < cabo.quantidade; i++) {
			exercito.add(cabo);
		}
		for (int i = 0; i < sargento.quantidade; i++) {
			exercito.add(sargento);
		}
		for (int i = 0; i < subtenente.quantidade; i++) {
			exercito.add(subtenente);
		}
		for (int i = 0; i < tenente.quantidade; i++) {
			exercito.add(tenente);
		}
		for (int i = 0; i < capitao.quantidade; i++) {
			exercito.add(capitao);
		}
		for (int i = 0; i < major.quantidade; i++) {
			exercito.add(major);
		}
		exercito.add(coronel);
		exercito.add(general);
		for (int i = 0; i < mina.quantidade; i++) {
			exercito.add(mina);
		}
		exercito.add(prisioneiro);



	}
	public void habilitar(){
		for (int k = 0; k < botoes.length; k++) {
			for (int l = 0; l < botoes.length; l++) {
				botoes[k][l].setEnabled(true);
			}
		}
	}
	public void desabilitar() {
		for (int k = 0; k < botoes.length; k++) {
			for (int l = 0; l < botoes.length; l++) {
				botoes[k][l].setEnabled(false);
			}
		}
	}
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		
try {
			
			cliente = new Socket("127.0.0.1", 82);
			saida = new PrintWriter(cliente.getOutputStream());
			entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			
			

			Thread remetente = new Thread(new Runnable() {
				public void run() {
				}
			});

			remetente.start();

			Thread receptor = new Thread(new Runnable() {
				public void run() {

					try {
						
						String[] mensagem = entrada.readLine().split(" ");
						
						//System.out.println(mensagem[0]);
						//System.out.println(mensagem[1]);
						//System.out.println(entrada.readLine());
						
						
						while (mensagem != null) {
							//System.out.println(mensagem[0]);
							
							//ANDAR SEM DEIXAR RASTROS
							if(mensagem[0].startsWith("B")) {
								//System.out.println("ENTROU " + mensagem);
								for (int i = 0; i < botoes.length; i++) {
									for (int j = 0; j < botoes.length; j++) {
										//PEGAR SOMENTE A POSI��O DOS BOTOES
										String[] arrayBotoesNomes = botoes[i][j].getName().split(" ");
										//System.out.println(mensagem[1]);
										String nomeBotao = arrayBotoesNomes[0];
										//System.out.println(nomeBotao);
										if(nomeBotao.equals(mensagem[1])) {
											botoes[i][j].setText("");
											botoes[i][j].setBackground(null);
											botoes[i][j].setName(nomeBotao);
										}
									}
								}
							}else if(!mensagem[0].startsWith("B")){
								
							}
							
							if(mensagem.length > 1) {
								nome = mensagem[0];
								if(!mensagem[0].startsWith("B")) {
									forca = mensagem[1];
								}
								//System.out.println("FOR�A: " + forca);
							}else {
								minhaVez = true;
							}
							
							for (int i = 0; i < botoes.length; i++) {
								for (int j = 0; j < botoes.length; j++) {
									//System.out.println(botoes[i][j].getName() + " " + nome);
									//botoes[i][j].setText("");
									String[] arrayBotoesNomes = botoes[i][j].getName().split(" ");
									String nomeBotao = arrayBotoesNomes[0];
									
									if (nomeBotao.equals(nome)){
										//botoes[i][j].setName(nome + " " + forca);
										botoes[i][j].setText("                        " + forca);
										botoes[i][j].setBackground(Color.red);
										//System.out.println(botoes[i][j].getName());
										//if(minhaVez) {
										//	backupInimigo = botoes[i][j];
										//}
										
									}
								}
								
							}
							//System.out.println("teste");
							//System.out.println("Servidor: " + mensagem[0]);
							mensagem = entrada.readLine().split(" ");
						
						}

						//System.out.println("Servidor offline.");
						saida.close();
						cliente.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			receptor.start();

			//System.out.println("Cliente online.");

		
		} catch (IOException e) {
			e.printStackTrace();
		}

		frmCliente = new JFrame();
		frmCliente.setTitle("Cliente - PLAYER 2");
		frmCliente.setResizable(false);
		frmCliente.setBounds(100, 100, 714, 525);
		frmCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCliente.getContentPane().setLayout(new GridLayout(10, 10, 0, 0));


		//CRIA��O DOS BOTOES
		z = 100;
		for (int  i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				botao = new JButton("");
				botao.setName(String.valueOf(z));
				botoes[i][j] = botao; 
				//System.out.println(botoes[i][j].getName());
				frmCliente.getContentPane().add(botao);
				z--;
				
			}

		}
		z = 0;
		umaVez = true; //variavel usada no gameStart
		for (int i = 0; i < botoes.length; i++) {
			for (int j = 0; j < botoes.length; j++) {
				//PRECISA FICAR CRIANDO BOTOES DIFERENTES
				JButton botaoClicado = botoes[i][j]; //� o bot�o que est� sendo clicado
				backupInimigo = botaoClicado;

				//desabilita todos os bot�es onde n�o pode ser colocado os personagens
				if(Integer.parseInt(botaoClicado.getName()) > 40) {
					botaoClicado.setEnabled(false);
				}
				
				botaoClicado.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						if(z < 40) { //mudar o 33 pra 40
							if(exercito.get(z).nome.equals("Mina") || exercito.get(z).nome.equals("Prisioneiro")) {
								botaoClicado.setText(exercito.get(z).nome);
							}else {
								botaoClicado.setText(exercito.get(z).forca + "");
							}
							botaoClicado.setName(botaoClicado.getName() + " " + botaoClicado.getText());
							saida.println(botaoClicado.getName());
							saida.flush();
							botaoClicado.setEnabled(false);
							if(z == 39) { // = a 32 vai virar = 39
								
								habilitar();

							}
							z++;
						}else {
							gameStart = true; //acabou de posicionar
							
							//System.out.println(minhaVez);
							
							
							//JOGO INICIADO	
						}if(gameStart && minhaVez) {				
							
							//System.out.println("PASSOU NA PRIMEIRA");
							
							//Para funcionar em qualquer tamanho de tela
							tamanhoButtonY= (int)Math.round(botaoClicado.getBounds().getHeight());
							tamanhoButtonX = (int)Math.round(botaoClicado.getBounds().getWidth());
							
							int x = botaoClicado.getX()/tamanhoButtonX; 
							int y = botaoClicado.getY()/tamanhoButtonY; 

							if (x != 0) { 
								direita = botoes[y][x-1];
							}
							if (x != 9) { 
								esquerda = botoes[y][x+1];
							}
							if (y != 0) { 
								cima = botoes[y-1][x];
							}
							if (y != 9) { 
								baixo = botoes[y+1][x];
							}

							desabilitar();
							
							//Essa vari�vel resolve o problema de poder dar teleporte
							boolean antiTP = true;
							if(caminhando) {
								//Ao clicar para caminhar, vai desabilitar todos os lugares n�o poss�veis
								desabilitar();
								//System.out.println("x");
								//umaVez resolve o problema de andar infinitamente
								if(umaVez) {
									if(botaoClicado == backup){ //se clicar no bot�o duas vezes
										habilitar();
										
										caminhando = false;
										backup = null; 
										umaVez = false;
										
										antiTP = false;

									}
								}
								else if(backup != null){ //se clicar em qualquer outro bot�o    
									//esse habilitar resolve o problema de andar infinitamente
									habilitar();
									
									if(botaoClicado.getText() == "" || botaoClicado.getBackground().equals(Color.red)) { 
										
										String semEspacos = botaoClicado.getText().trim();
										
										if(botaoClicado.getBackground().equals(Color.red)) {
											
											if(semEspacos.equals("Prisioneiro")) {
												desabilitar();
											}
											//SE ENCOSTAR NA MINA
											//System.out.println(botaoClicado.getText());
											if(semEspacos.equals("Mina") && !backup.getText().equals(String.valueOf(3))) {
												//System.out.println("MINA");
												backup.setText("");
												minhaVez = false;
												
												saida.println("PRONTO");
												saida.flush();
												//SE FOR O DESARMADOR
											}else if (semEspacos.equals("Mina") && backup.getText().equals(String.valueOf(3))){
												//System.out.println("LA");
												botaoClicado.setText(backup.getText());
												backup.setText("");
												minhaVez = false;
												
												botaoClicado.setBackground(null);
												
												saida.println("PRONTO");
												saida.flush();
												//O ESPIAO MATA O COM 10 DE PODER
											}else if(Integer.parseInt(backup.getText()) == 1 && Integer.parseInt(semEspacos) == 10) {
												//System.out.println("ALI");
												botaoClicado.setText(backup.getText());
												backup.setText("");
												minhaVez = false;
												
												botaoClicado.setBackground(null);
												
												saida.println("PRONTO");
												saida.flush();
											}
											else if(Integer.parseInt(backup.getText()) > Integer.parseInt(semEspacos)) {
												//System.out.println("AQUI");
												saida.println(botaoClicado.getName() + " " + botaoClicado.getText());
												botaoClicado.setText(backup.getText());
												backup.setText("");
												minhaVez = false;
											//	perdeu = false;
												saida.println("B " + botaoClicado.getName());
												saida.flush();
												
												botaoClicado.setBackground(null);
												//saida.println("B " + backup.getName());
												//saida.flush();
												
												saida.println("PRONTO");
												saida.flush();
											}else if(Integer.parseInt(backup.getText()) < Integer.parseInt(semEspacos)){
												//System.out.println("BEM AQUI");
												
												//perdeu = true;
												backup.setText("");
												minhaVez = false;
												
												saida.println("PRONTO");
												saida.flush();
											}else if(Integer.parseInt(backup.getText()) == Integer.parseInt(semEspacos)) {
												botaoClicado.setText("");
												backup.setText("");
												minhaVez = false;
											//	perdeu = false;
												saida.println("B " + botaoClicado.getName());
												saida.flush();
												
												botaoClicado.setBackground(null);
												//saida.println("B " + backup.getName());
												//saida.flush();
												
												saida.println("PRONTO");
												saida.flush();
											}
										}
										//ANDANDO PELO MAPA
										else {
											botaoClicado.setText(backup.getText());
											backup.setText("");
											//Nome que ser� enviado ao servidor
											buttonName = botaoClicado.getName();
											//System.out.println("PASSOU");
											
											saida.println(buttonName + " " + botaoClicado.getText());
											saida.flush();
											minhaVez = false;
											
											saida.println("PRONTO");
											saida.flush();
											
											botaoClicado.setBackground(null);
										}
										
										
										
										//combate.minhaVez = true;
									}
									
									caminhando = false;
									backup = null;
									umaVez = false;
									//minhaVez = false;
									
								}else if (antiTP){
									caminhando = false;
										umaVez = true; 
										
									
								}
							}
							
							if(botaoClicado.getText() == "" || botaoClicado.getBackground().equals(Color.red)) {
								habilitar();

							}else {
								if(direita != null && /*Tem algo na direita*/(direita.getText() == "" || direita.getBackground().equals(Color.red)) /*A casa esta vazia*/
										&& botaoClicado.getText() != "" /*O botaoClicado clicado n�o est� vazio*/
										&& umaVez && direita.getX()/tamanhoButtonX == botaoClicado.getX()/tamanhoButtonX - 1 /*Isso � para "RESOLVER" 
										o bug de andar para mais longe do que os quadrados das pontas*/
										) {
									direita.setEnabled(true);
									//lastButton = botaoClicado;
								}
								if(esquerda != null && (esquerda.getText() == "" || esquerda.getBackground().equals(Color.red)) && botaoClicado.getText() != "" && umaVez
										&& esquerda.getX()/tamanhoButtonX == botaoClicado.getX()/tamanhoButtonX + 1 
										) {
									esquerda.setEnabled(true);
									//lastButton = botaoClicado;
								}
								if(cima != null && (cima.getText() == "" || cima.getBackground().equals(Color.red)) && botaoClicado.getText() != "" && umaVez
										&& cima.getY()/tamanhoButtonY == botaoClicado.getY()/tamanhoButtonY - 1 
										) {
									cima.setEnabled(true);
									//lastButton = botaoClicado;
								}
								if(baixo != null && (baixo.getText() == "" || baixo.getBackground().equals(Color.red)) && botaoClicado.getText() != "" && umaVez
										&& baixo.getY()/tamanhoButtonY == botaoClicado.getY()/tamanhoButtonY + 1
										) {
									baixo.setEnabled(true);
									//lastButton = botaoClicado;
								}
							}
							botaoClicado.setEnabled(true); //Habilitar o bot�o central

							if(botaoClicado.getText() != "" && !botaoClicado.getBackground().equals(Color.red)) {
								if(umaVez) {
									backup = botaoClicado;
									//if(perdeu) {
										saida.println("B " + backup.getName());
									//}
									
									//System.out.println(backup.getName());
									umaVez = false;
								}

							}

							if(botaoClicado.getText() != "" && !botaoClicado.getText().equals("Mina") && !botaoClicado.getText().equals("Prisioneiro")) {
								caminhando = true;
								//habilitar();

							}

						}else if(gameStart){
							
							saida.println("PRONTO");
							//System.out.println("Pronto");
						}

					}

				});

			}

		}

	}
}
