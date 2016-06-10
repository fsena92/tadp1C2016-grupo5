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
     leonidas.asignarTrabajo(Guerrero)
     assertEquals(11, leonidas.HP,  0.01)    
  }
  
  @Test
  def agregarUnItemYCambiaHP {
    val hercules = new Heroe(1,2,3,4)
    hercules.asignarTrabajo(Guerrero)
    hercules.agregarItem(ArcoViejo)
    assertEquals(19, hercules.fuerza, 0.01)
  }
  
  
}