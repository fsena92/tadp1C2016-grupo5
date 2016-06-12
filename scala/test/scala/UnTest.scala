package scala

import org.junit.Test
import org.junit.Assert._


class UnTest {
  
 
  @Test
  def heroeSeConvierteEnGuerrero {
     val leonidas = new Heroe(1,2,3,4)
     leonidas.asignarTrabajo(Guerrero)
     assertEquals(11, leonidas.HP,  0.01)    
  }
  

  
  
}