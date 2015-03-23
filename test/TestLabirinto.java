package test;

import static org.junit.Assert.*;

import java.util.TreeSet;

import logic.Dragao;
import logic.Espada;
import logic.EstadoJogo;
import logic.Heroi;
import logic.Maze;

import org.junit.Test;
public class TestLabirinto {

	@Test
	public void testMovimentoHeroi()
	{
		Heroi h1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		h1=j1.getHero();
		j1.moveHero('s');
		assertEquals(1,h1.getPonto().getXpos());
		assertEquals(2,h1.getPonto().getYpos());	
	}
	@Test
	public void testHeroiImovel()
	{
		Heroi h1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		h1=j1.getHero();
		j1.moveHero('w');
		assertEquals(1,h1.getPonto().getYpos());
		assertEquals(1,h1.getPonto().getXpos());
	}
	@Test
	public void testApanharEspada()
	{
		Maze m1;
		Heroi h1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('a');
		j1.moveHero('a');
		j1.moveHero('a');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		assertEquals(true,h1.getWeapon());
	}
	@Test
	public void testDerrota()
	{
		Maze m1;
		Heroi h1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		do
		{
			j1.moveHero('s');
		}while(j1.checkCollisionWithDragon()!=0);
		j1.checkCollisionWithDragon();
		assertEquals(false,h1.getState());
	}
	@Test
	public void testMatarDragao()
	{
		Maze m1;
		Heroi h1;
		Dragao d1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		d1=j1.getDragao();
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('a');
		j1.moveHero('a');
		j1.moveHero('a');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('w');
		j1.moveHero('w');
		j1.moveHero('w');
		j1.moveHero('w');
		j1.checkCollisionWithDragon();
		assertEquals(false,d1.getState());
	}
	@Test
	public void testVitoria()
	{
		Maze m1;
		Heroi h1;
		Dragao d1;
		EstadoJogo j1 = new EstadoJogo();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		d1=j1.getDragao();
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('a');
		j1.moveHero('a');
		j1.moveHero('a');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('w');
		j1.moveHero('w');
		j1.moveHero('w');
		j1.moveHero('w');
		j1.checkCollisionWithDragon();
		j1.moveHero('s');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('w');
		j1.moveHero('w');
		j1.moveHero('w');
		j1.moveHero('d');
		assertEquals(false,d1.getState());
		assertEquals(true,h1.getWeapon());
		assertEquals(m1.getExit().getXpos(),h1.getPonto().getXpos());
		assertEquals(m1.getExit().getYpos(),h1.getPonto().getYpos());
	}
	@Test
	public void testNaoTermina()
	{
		
		Maze m1;
		Heroi h1;
		Dragao d1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		d1=j1.getDragao();
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		j1.moveHero('s');
		assertEquals(m1.getExit().getXpos()-1,h1.getPonto().getXpos());
		assertEquals(m1.getExit().getYpos(),h1.getPonto().getYpos());
		assertEquals(true,d1.getState());
		assertEquals(false,h1.getWeapon());
	}
	@Test 
	public void testMoveDragao()
	{
		EstadoJogo e = new EstadoJogo();
		e.iniciar();
		e.moveDragaoDirection(e.getDragao(), ' ', 1, 4, 1, 3);
		e.moveDragaoDirection(e.getDragao(), ' ', 1, 5, 1, 4);
		e.moveDragaoDirection(e.getDragao(), ' ', 2, 5, 1, 5);
		assertEquals(5,e.getDragao().getPonto().getYpos());
		assertEquals(2,e.getDragao().getPonto().getXpos());
		assertEquals('D',e.getMaze().getLab()[5][2]);
	}

	//////////////////////////////////////////////////////////
	@Test 
	public void testDragaoDormir()
	{
		EstadoJogo e = new EstadoJogo();
		Dragao d = new Dragao('D',true,false);
		Heroi h = new Heroi('H',true,false);
		e.setHeroi(h);
		e.setDragao(d);
		
	}
	
	@Test 
	public void testMultiplosDragoes()
	{
		
	}
	@Test 
	public void testCuspirFogo()
	{
		
	}
	@Test 
	public void testApanhaEscudo()
	{
		Maze m1;
		Heroi h1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		j1.moveHero('d');
		j1.moveHero('d');
		assertEquals(true,h1.hasShield());
	}
	@Test 
	public void testApanhaDardos()
	{
		Maze m1;
		Heroi h1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('s');
		if(h1.getSymbol()=='T')
			h1.incDards();
		assertEquals(2,h1.getDardNumber());
			
	}
	@Test 
	public void testLancaDardos()
	{
		Maze m1;
		Heroi h1;
		EstadoJogo j1=new EstadoJogo();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('d');
		j1.moveHero('s');
		if(h1.getSymbol()=='T')
			h1.incDards();
		assertEquals(2,h1.getDardNumber());
		j1.shootDard(); 
		assertEquals(1,h1.getDardNumber());
		j1.shootDard(); 
		assertEquals(0,h1.getDardNumber());
	
	}
	
	//////////////////////////////////////////////////////////
	@Test 
	public void testSaida()
	{
		EstadoJogo e = new EstadoJogo();
		e.initialize(11);
		Maze m = e.getMaze();
		boolean test = false;
		for(int i = 0; i < m.getLab().length; i++){
			for(int j = 0; j < m.getLab().length; j++){
				if(m.getLab()[i][j] == 'S'){
					test = true;
				}
			}
		}
		assertEquals(true,test);
		
	}
	@Test 
	public void testDragaoAleatorio()
	{
		EstadoJogo e = new EstadoJogo();
		e.initialize(11);
		Dragao d = new Dragao('D', true,false);
		e.setDragao(d);
		e.getDragao().generate(e.getMaze(), 11);
		Maze m = e.getMaze();
		boolean test = false;
		for(int i = 0; i < m.getLab().length; i++){
			for(int j = 0; j < m.getLab().length; j++){
				if(m.getLab()[i][j] == 'D'){
					test = true;
				}
			}
		}
		assertEquals(true,test);
		
	}
	@Test 
	public void testHeroiAleatorio()
	{
		EstadoJogo e = new EstadoJogo();
		e.initialize(11);
		Heroi h = new Heroi('H', true,false);
		e.setHeroi(h);
		e.getHero().generate(e.getMaze(), 11);
		Maze m = e.getMaze();
		boolean test = false;
		for(int i = 0; i < m.getLab().length; i++){
			for(int j = 0; j < m.getLab().length; j++){
				if(m.getLab()[i][j] == 'H'){
					test = true;
				}
			}
		}
		assertEquals(true,test);
		
	}

}
