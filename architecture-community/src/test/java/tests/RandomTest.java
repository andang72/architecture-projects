package tests;

import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomTest {

	private static Logger log = LoggerFactory.getLogger(RandomTest.class);
	@Test
	public void testRandomByMath(){
		boolean success = false;
		int successCount = 0;
		int failCount = 0;		
		for( int i = 0 ; i < 100 ; i++ ){
			double random = Math.random();
			
			if( random < 0.6 ){
				success = false;
				failCount ++;
			}else{
				success = true;
				successCount ++;
			}	
			//log.debug("won=" + success);
		}
		
		log.debug("total: {} , success : {}, fail : {}", 100, successCount, failCount);
		
	}
	
	@Test
	public void testRandom(){
		boolean success = false;
		int successCount = 0;
		int failCount = 0;		
		Random rand = new Random();		 
		for( int i = 0 ; i < 100 ; i++ ){
			double random = rand.nextDouble();			
			if( random < 0.6 ){
				success = false;
				failCount ++;
			}else{
				success = true;
				successCount ++;
			}	
			//log.debug("won=" + success);
		}
		
		log.debug("total: {} , success : {}, fail : {}", 100, successCount, failCount);
		
	}
}
