package comp.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;
	
import com.rummikub.Tile;

class TileTest {
	
	private static Tile tile1;
	private static ByteArrayOutputStream outContent; 
	
	@BeforeAll
	static void setUpAll()
	{
		Logger.info("setUpAll");
		tile1 = new Tile(1,"red");
		outContent= new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	void printTest() 
	{		
		tile1.print();
		assertTrue(outContent.toString().contains("red"));
		assertTrue(outContent.toString().contains("1"));
	}
	
	@Test
	void randomTest()
	{
		assertEquals(1,1);
	}
	
}
