package test;

import static org.junit.Assert.*;
import logic.Dragon;
import logic.GameState;
import logic.Hero;
import logic.Maze;
import logic.Point;

import org.junit.Test;
public class TestLabirinto {

	@Test
	public void testMovimentoHeroi()
	{
		Hero h1;
		GameState j1=new GameState();
		j1.iniciar();
		h1=j1.getHero();
		j1.moveHero('s');
		assertEquals(1,h1.getPonto().getXpos());
		assertEquals(2,h1.getPonto().getYpos());	
	}
	@Test
	public void testHeroiImovel()
	{
		Hero h1;
		GameState j1=new GameState();
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
		Hero h1;
		GameState j1=new GameState();
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
		Hero h1;
		GameState j1=new GameState();
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
		Hero h1;
		Dragon d1;
		GameState j1=new GameState();
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
		Hero h1;
		Dragon d1;
		GameState j1 = new GameState();
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
		Hero h1;
		Dragon d1;
		GameState j1=new GameState();
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
		GameState e = new GameState();
		e.iniciar();
		e.moveDragaoDirection(e.getDragao(), ' ', 1, 4, 1, 3);
		e.moveDragaoDirection(e.getDragao(), ' ', 1, 5, 1, 4);
		e.moveDragaoDirection(e.getDragao(), ' ', 2, 5, 1, 5);
		assertEquals(5,e.getDragao().getPonto().getYpos());
		assertEquals(2,e.getDragao().getPonto().getXpos());
		assertEquals('D',e.getMaze().getLab()[5][2]);
	}


	@Test 
	public void testCuspirFogo()
	{
		GameState j1=new GameState();
		j1.iniciar();
		assertEquals(true,j1.checkDragonRange());
		Hero h=j1.getHero();
		h.setPosition(1, 5);
		assertEquals(true,j1.checkDragonRange());
		Dragon d=j1.getDragao();
		h.setPosition(2, 1);
		assertEquals(false,j1.checkDragonRange());
	}
	@Test 
	public void testApanhaEscudo()
	{
		Maze m1;
		Hero h1;
		GameState j1=new GameState();
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
		Hero h1;
		GameState j1=new GameState();
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
		Hero h1;
		GameState j1=new GameState();
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
		GameState e = new GameState();
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
		GameState e = new GameState();
		e.initialize(11);
		assertEquals(0,e.getElements().size());
		assertEquals(0,e.getElements().size());
		Dragon d = new Dragon('D', true,false);
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
		GameState e = new GameState();
		e.initialize(11);
		Hero h = new Hero('H', true,false);
		e.setHero(h);
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
	@Test
	public void testValidDragonMovement()
	{
		GameState j1=new GameState();
		j1.iniciar();
		Dragon d=j1.getDragao();
		j1.validMovementDragon(1, d);
		assertEquals(0,j1.validMovementDragon(1, d));
		assertEquals(1,j1.validMovementDragon(3, d));
	}
	@Test 
	public void testDragonMoveRandom()
	{
		GameState j1=new GameState();
		j1.iniciar();
		Dragon d=j1.getDragao();
		Dragon d1=j1.getDragao();
		d1.setPosition(2, 5);
		j1.moveDragao(d);
		j1.moveDragao(d1);
		assertNotEquals(1, d.getPonto().getYpos());
		assertNotEquals(2, d.getPonto().getXpos());
	}
	@Test
	public void testPlay()
	{
		GameState j1=new GameState();
		j1.iniciar();
		Hero h=j1.getHero();
		Dragon d=j1.getDragao();
		j1.play("d");
		assertEquals(true,h.getState());
		h.incDards();
		assertEquals(1,h.getDardNumber());
		j1.play("f");
		assertEquals(0,h.getDardNumber());
	}
	@Test 
	public void testAddElements()
	{
		GameState e=new GameState();
		assertEquals(false,e.getState());
		e.initialize();
		assertEquals(true,e.getState());
		e.setNumOfDragons(3);
		assertEquals(3,e.getNumDragons());
		e.addElements();
		assertEquals(10,e.getSIZE());
		Maze m=e.getMaze();
		boolean hero=false;
		boolean dragon=false;
		boolean shield=false;
		boolean dard=false;
		boolean sword=false;
		for(int i = 0; i < m.getLab().length; i++)
		{
			for(int j = 0; j < m.getLab().length; j++)
			{
				if(m.getLab()[i][j] == 'H')
				{
					hero = true;
				}
				if(m.getLab()[i][j] == 'D')
				{
					dragon=true;
				}
				if(m.getLab()[i][j] == 'O')
				{
					shield=true;
				}
				if(m.getLab()[i][j] == '/')
				{
					dard=true;
				}
				if(m.getLab()[i][j] == 'E')
				{
					sword=true;
				}
			}
		}
		assertEquals(true,hero);
		assertEquals(true,dragon);
		assertEquals(true,shield);
		assertEquals(true,dard);
		assertEquals(true,sword);
	}
	@Test 
	public void testKillDragonDard()
	{
		Maze m1;
		Hero h1;
		GameState j1=new GameState();
		j1.iniciar();
		m1=j1.getMaze();
		h1=j1.getHero();
		h1.incDards();
		j1.shootDard();
		assertEquals(' ',m1.getLab()[3][1]);
		h1.setPosition(1, 6);
		h1.incDards();
		j1.shootDard();
		assertEquals(' ',m1.getLab()[3][1]);
	}
	@Test
	public void testCollisionDragonWest()
	{
		GameState j1=new GameState();
		j1.initialize();
		Point p1=new Point (1,1);
		Point p2=new Point (3,1);
		Hero h = new Hero('H',true,false);	
		h.setPosition(1, 1);
		h.setWeaponSymbol('E');
		j1.getMaze().setSymbol(p1, h.getSymbol());
		j1.setHero(h);
		Dragon d = new Dragon('D',true,false);
		d.setPosition(3, 1);
		j1.setDragao(d);
		j1.getMaze().setSymbol(p2, d.getSymbol());
		j1.moveHero('d');
		assertEquals(2,h.getPonto().getXpos());
		j1.checkCollisionWithDragon();
		assertEquals('A',h.getSymbol());
		assertEquals(3,d.getPonto().getXpos());
		assertEquals(false,d.getState());
	}
	@Test
	public void testCollisionDragonEst()
	{
		GameState j1=new GameState();
		j1.initialize();
		Point p1=new Point (1,1);
		Point p2=new Point (3,1);
		Hero h = new Hero('H',true,false);	
		h.setPosition(3, 1);
		h.setWeaponSymbol('E');
		j1.setHero(h);
		Dragon d = new Dragon('D',true,false);
		d.setPosition(1, 1);
		j1.setDragao(d);
		j1.getMaze().setSymbol(p2, h.getSymbol());
		j1.getMaze().setSymbol(p1, d.getSymbol());
		j1.moveHero('a');
		assertEquals(2,h.getPonto().getXpos());
		j1.checkCollisionWithDragon();
		assertEquals('A',h.getSymbol());
		assertEquals(1,d.getPonto().getXpos());
		assertEquals(false,d.getState());
	}
	@Test
	public void testCollisionDragonSouth()
	{
		GameState j1=new GameState();
		j1.initialize();
		Point p1=new Point (1,1);
		Point p2=new Point (1,3);
		Hero h = new Hero('H',true,false);	
		h.setPosition(1, 1);
		h.setWeaponSymbol('E');
		j1.setHero(h);
		Dragon d = new Dragon('D',true,false);
		d.setPosition(1, 3);
		j1.setDragao(d);
		j1.getMaze().setSymbol(p1, h.getSymbol());
		j1.getMaze().setSymbol(p2, d.getSymbol());
		j1.moveHero('s');
		assertEquals(2,h.getPonto().getYpos());
		j1.checkCollisionWithDragon();
		assertEquals('A',h.getSymbol());
		assertEquals(3,d.getPonto().getYpos());
		assertEquals(false,d.getState());
		
	}
	@Test
	public void testDragonSleep()
	{
		GameState g=new GameState();
		g.initialize();
		Dragon d = new Dragon('D',true,false);
		d.setPosition(1, 1);
		assertEquals(true,d.getActive());
		assertEquals('D',d.getSymbol());
		do
		{
			d.incCounter();
		}while(d.getActive());
		assertEquals(false,d.getActive());
		assertEquals('d',d.getSymbol());
		Dragon d1 = new Dragon('d',true,false);
		d1.setPosition(1, 4);
		d1.setActive(false);
		do
		{
			d1.incCounter();
		}while(!d1.getActive());
		assertEquals(true,d1.getActive());
		assertEquals('D',d1.getSymbol());
	}
	@Test
	public void testRestart()
	{
		GameState g=new GameState();
		g.initialize(11);
		assertEquals(11,g.getSIZE());
		g.setSIZE(13);
		g.restartGame();
		assertEquals(13,g.getSIZE());
		assertEquals(false,g.getPreset());
	}
	@Test
	public void testNewConstrutor()
	{
		GameState g=new GameState();
		g.initialize(11);
		g.addElements();
		GameState gm = new GameState(g.getMaze().getLab(),1,11,g.getMaze().getExit().getXpos(),g.getMaze().getExit().getYpos());
		assertEquals(1,gm.getDifficulty());
		assertEquals(2,gm.getNumDragons());
		Hero h=gm.getHero();
		assertEquals(true,h.getState());
		assertEquals(2,gm.getNumDards());
	}
}
