package logic;

public class Maze {
	private char[][] lab;
	private int SIZE;
	private final Ponto exit;
	public String labirinto;


	
	public Maze(){
		Gerador gerador = new Gerador();
		this.lab = gerador.gerarLabirintoPreset();
		this.SIZE = 10;
		exit = gerador.getExit();
	}
	
	public Maze(int size){
		Gerador gerador = new Gerador(size);
		this.SIZE = size;
		this.lab = gerador.gerarLabirinto();
		exit = gerador.getExit();
	}

	public void getExitCoord(){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(lab[y][x] == 'S'){
					exit.setXpos(x);
					exit.setYpos(y);
				}
			}
		}
	}

	public void print(){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				System.out.print(lab[y][x]); 
			}
			System.out.print('\n');
		}
	}

	public int getSIZE() {
		return SIZE;
	}

	public void setSymbol(Ponto p, char sym){
		lab[p.getYpos()][p.getXpos()] = sym;
	}
	

	public char[][] getLab() {
		return lab;
	}

	public Ponto getExit(){
		return exit;
	}

}
