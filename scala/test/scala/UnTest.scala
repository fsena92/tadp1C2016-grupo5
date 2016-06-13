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
    wolverine = new Heroe(1000,60,50,20)
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
  
  @Test
  def heroeSeEquipaCascoVikingoYEscudoAntiRoboYCambiaSusStats = {
     assertEquals(spiderman.asignarTrabajo(Guerrero).equipar(CascoVikingo).equipar(EscudoAntiRobo).HP, 50, 0.01)
  }
  
  @Test
  def heroeNoCumpleCondicion = {
     assertEquals(ironMan.asignarTrabajo(Guerrero).equipar(CascoVikingo).HP, 60, 0.01)
  }
  
  @Test
  def equiparEspadaDeLaVida = {
    assertEquals(ironMan.equipar(EspadaDeLaVida).fuerza, 60, 0.01)
  }

   @Test
  def equiparTalismanMinimalismo = {
    assertEquals(ironMan.asignarTrabajo(Guerrero).equipar(Minimalismo).HP, 110, 0.01)
  }
  
  @Test
  def equiparUnItemYUnTalisman = {
    assertEquals(spiderman.asignarTrabajo(Guerrero).equipar(CascoVikingo).equipar(Minimalismo).HP, 70, 0.01)
  }
  
  @Test
  def heroeSeConvierteEnMagoYPuedeUsarCascoVikingo = {
     assertEquals(spiderman.asignarTrabajo(Mago).equipar(CascoVikingo).HP, 20, 0.01)
  }
  
  @Test
  def heroeSeEquipaArmadura = {
     assertEquals(ironMan.equipar(ArmaduraEleganteSport).velocidad, 70, 0.01)
  }
  
  @Test
  def heroeSeEquipaPaloMagicoSiendoLadron = {
     assertEquals(spiderman.asignarTrabajo(Ladron).equipar(PalitoMagico).inteligencia, 60, 0.01)
  }
  
  @Test
  def heroeSiendoGuerreroNoPuedeEquiparseEscudoSinFuerzaBaseNecesaria = {
     assertEquals(ironMan.asignarTrabajo(Guerrero).equipar(EscudoAntiRobo).HP, 60, 0.01)
  }
  
  @Test
  def heroeConVariosItemsEsAfectadoPorElTalismanDelMinimalismo = {
     assertEquals(capitanAmerica.equipar(VinchaDelBufaloDelAgua).equipar(ArmaduraEleganteSport).equipar(Minimalismo).HP, 70, 0.01)
  }
  
  @Test
  def heroeSeEquipaEspadaDeLaVida = {
     assertEquals(wolverine.asignarTrabajo(Guerrero).equipar(EspadaDeLaVida).fuerza, 1010, 0.01)
  }
  
  @Test
  def heroeSeEquipaTalismanDeDedicacion = {
     assertEquals(capitanAmerica.asignarTrabajo(Guerrero).equipar(Dedicacion).HP, 81.5, 0.01) 
     assertEquals(capitanAmerica.asignarTrabajo(Guerrero).equipar(Dedicacion).fuerza, 46.5, 0.01)
     assertEquals(capitanAmerica.asignarTrabajo(Guerrero).equipar(Dedicacion).velocidad, 61.5, 0.01)
     assertEquals(capitanAmerica.asignarTrabajo(Guerrero).equipar(Dedicacion).inteligencia, 11.5, 0.01) 
  }
  
  
}

