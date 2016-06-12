package scala

import org.junit.Test
import org.junit.Assert._
import org.junit.Before

class UnTest  {
   
   var spiderman:Heroe = null
   var ironMan:Heroe = null
   var capitanAmerica:Heroe = null
   var wolverine:Heroe = null
   
   
 
  
  @Before
  def setup = {
    spiderman = new Heroe(10,35,60,40)
    ironMan = new Heroe(50,10,40,100)
    capitanAmerica = new Heroe(70,30,60,20)
    
    
  }
  
  @Test
  def heroeSeConvierteEnGuerrero = {
     assertEquals(Some(Guerrero) ,capitanAmerica.asignarTrabajo(Guerrero).job)    
  }
  
  @Test
  def heroeCambiaDeStatsCuandoSeConvierteEnMago = {
    assertEquals(40 ,capitanAmerica.asignarTrabajo(Mago).inteligencia, 0.01)
  }
  
  @Test
  def heroeNoSeEquipaCascoVikingoPorNoCumplirCondicion = {
     assertEquals(ironMan.equipar(CascoVikingo).inventario.cabeza, None)
  }
  
  @Test
  def heroeSeEquipaCascoVikingoYCumpleCondicion = {
     assertEquals(spiderman.equipar(CascoVikingo).inventario.cabeza.get, CascoVikingo)
  }
  
  @Test
  def heroeSeEquipaCascoVikingoYCambiaSusStats = {
     assertEquals(spiderman.equipar(CascoVikingo).HP, 20, 0.01)
  }

  
  
}

