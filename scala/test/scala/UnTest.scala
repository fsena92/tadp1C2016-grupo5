package scala

import org.junit.Test
import org.junit.Assert._


class UnTest {
  
  
  @Test
  def heroeSeConvierteEnGuerrero {
     val leonidas = Heroe(1,2,3,4)
     assertEquals(11, leonidas.asignarTrabajo(Guerrero).HP,  0.01)    
  }
  
  
}