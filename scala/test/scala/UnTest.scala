package scala

import org.junit.Test
import org.junit.Assert._


class UnTest {
  
  @Test
  def guardaLosStatsBase {
    val aquiles = new Heroe(1,2,3,4)
    aquiles.asignarTrabajo(Guerrero)
    assertEquals(1, aquiles.statsBase.HPBase, 0.01)
  }
  
  
  @Test
  def heroeSeConvierteEnGuerrero {
     val leonidas = new Heroe(1,2,3,4)
     assertEquals(11, leonidas.asignarTrabajo(Guerrero).HP,  0.01)    
  }
  
  
}