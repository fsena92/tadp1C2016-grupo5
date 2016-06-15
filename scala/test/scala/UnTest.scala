package scala

import org.junit.Test
import org.junit.Assert._
import org.junit.Before

class UnTest  {
   
   var spiderman:Heroe = null
   var ironMan:Heroe = null
   var capitanAmerica:Heroe = null
   var wolverine:Heroe = null
   var kratos: Heroe = null
   var equipo: Equipo = null
   
  @Before
  def setup = {
    kratos = new Heroe(50, 45, 10, 10)
    spiderman = new Heroe(10, 35, 60, 40)
    ironMan = new Heroe(50, 10, 40, 100)
    capitanAmerica = new Heroe(70, 30, 60, 20)
    wolverine = new Heroe(1000, 60, 50, 20)
    equipo = new Equipo("vengadores_2", List(spiderman, ironMan))
  }
  
  @Test
  def heroeSeConvierteEnGuerrero {
     assertEquals(Some(Guerrero) ,capitanAmerica.asignarTrabajo(Guerrero).job)    
  }
  
  @Test
  def heroeCambiaDeStatsCuandoSeConvierteEnMago {
    assertEquals(40 ,capitanAmerica.asignarTrabajo(Mago).inteligenciaFinal, 0.01)
  }
  
  @Test
  def heroeNoSeEquipaCascoVikingoPorNoCumplirCondicion {
     assertFalse(ironMan.equipar(CascoVikingo).inventario.items.contains(CascoVikingo))
  }
  
  @Test
  def heroeSeEquipaCascoVikingoYCumpleCondicion {
     assertTrue(spiderman.equipar(CascoVikingo).inventario.items.contains(CascoVikingo))
  }
  
  @Test
  def heroeSeEquipaCascoVikingoYCambiaSusStats {
     assertEquals(spiderman.equipar(CascoVikingo).HPFinal, 20, 0.01)
  }
  
  @Test
  def heroeSeEquipaCascoVikingoYEscudoAntiRoboYCambiaSusStats {
     assertEquals(spiderman.asignarTrabajo(Guerrero).equipar(CascoVikingo).equipar(EscudoAntiRobo).
         HPFinal, 50, 0.01)
  }
  
  @Test
  def heroeNoCumpleCondicion {
     assertEquals(ironMan.asignarTrabajo(Guerrero).equipar(CascoVikingo).HPFinal, 60, 0.01)
  }
  
  @Test
  def equiparEspadaDeLaVida {
    assertEquals(ironMan.equipar(EspadaDeLaVida).fuerzaFinal, 50, 0.01)
  }

  @Test
  def equiparTalismanMinimalismo {
    assertEquals(ironMan.asignarTrabajo(Guerrero).equipar(Minimalismo).HPFinal, 110, 0.01)
  }
  
  @Test
  def equiparUnItemYUnTalisman {
    assertEquals(spiderman.asignarTrabajo(Guerrero).equipar(CascoVikingo).equipar(Minimalismo).
        HPFinal, 70, 0.01)
  }
  
  @Test
  def heroeSeConvierteEnMagoYPuedeUsarCascoVikingo {
     assertEquals(spiderman.asignarTrabajo(Mago).equipar(CascoVikingo).HPFinal, 20, 0.01)
  }
  
  @Test
  def heroeSeEquipaArmadura {
     assertEquals(ironMan.equipar(ArmaduraEleganteSport).velocidadFinal, 70, 0.01)
  }
  
  @Test
  def heroeSeEquipaPaloMagicoSiendoLadron {
     assertEquals(spiderman.asignarTrabajo(Ladron).equipar(PalitoMagico).inteligenciaFinal, 60, 0.01)
  }
  
  @Test
  def heroeSiendoGuerreroNoPuedeEquiparseEscudoSinFuerzaBaseNecesaria {
     assertEquals(ironMan.asignarTrabajo(Guerrero).equipar(EscudoAntiRobo).HPFinal, 60, 0.01)
  }

  @Test
  def heroeConVariosItemsEsAfectadoPorElTalismanDelMinimalismo {
     assertEquals(capitanAmerica.equipar(EspadaDeLaVida).equipar(ArmaduraEleganteSport).
         equipar(Minimalismo).HPFinal, 70, 0.01)
  }
  
  @Test
  def heroeSeEquipaEspadaDeLaVida {
     assertEquals(wolverine.asignarTrabajo(Guerrero).equipar(EspadaDeLaVida).fuerzaFinal, 1010, 0.01)
  }
  
  @Test
  def heroeSeEquipaTalismanDeDedicacion {
     assertEquals(capitanAmerica.asignarTrabajo(Guerrero).equipar(Dedicacion).HPFinal, 84.5, 0.0001) 
     assertEquals(capitanAmerica.asignarTrabajo(Guerrero).equipar(Dedicacion).fuerzaFinal, 49.5, 0.0001)
     assertEquals(capitanAmerica.asignarTrabajo(Guerrero).equipar(Dedicacion).velocidadFinal, 64.5, 0.0001)
     assertEquals(capitanAmerica.asignarTrabajo(Guerrero).equipar(Dedicacion).inteligenciaFinal, 14.5, 0.0001) 
  }
  
  @Test
  def noCumpleConEquiparEspadaYEscudo {
    assertEquals(ironMan.equipar(EscudoAntiRobo).equipar(PalitoMagico).cantidadItems, 0, 0.001)
  }
  
  @Test
  def cumpleConEquiparEspadaYEscudo {
    assertEquals(capitanAmerica.asignarTrabajo(Mago).equipar(EscudoAntiRobo).
        equipar(PalitoMagico).cantidadItems, 2, 0.01)
  }
  
  @Test
  def equiparArmaDobleReemplazaArmaSimple {
    assertEquals(capitanAmerica.equipar(EscudoAntiRobo).equipar(PalitoMagico).
        equipar(ArcoViejo).cantidadItems, 1, 0.001)
  }
  
  @Test
  def equiparTresArmasSimplesDiferentes {
    assertEquals(capitanAmerica.equipar(EscudoAntiRobo).equipar(PalitoMagico).
        equipar(EspadaDeLaVida).cantidadItems, 2, 0.001)
  }
  
  @Test
  def equiparDosArmasSimplesIgualesYUnaDiferenteQueReemplazaAUna {
    assertEquals(capitanAmerica.equipar(EscudoAntiRobo).equipar(EscudoAntiRobo).
        equipar(EspadaDeLaVida).cantidadItems, 2, 0.001)
  }
  
  @Test
  def equiparDosArmasSimplesIgualesYUnaDiferenteQueReemplazaAUnaObtengoHP {
    assertEquals(capitanAmerica.equipar(EscudoAntiRobo).equipar(EscudoAntiRobo).
        equipar(EspadaDeLaVida).HPFinal, 90, 0.001)
  }
  
  @Test 
  def HeroeCambiaDeTrabajo {
    assertEquals(capitanAmerica.asignarTrabajo(Mago).asignarTrabajo(Guerrero).job.get, Guerrero) 
  }
  
  @Test
  def MejorHeroeSegun {
    assertEquals(equipo.mejorHeroeSegun { heroe => heroe.HPBase }.get, ironMan)
  }
  
  @Test
  def ObtenerMiembro {
    equipo.agregarMiembro(wolverine)
    assertTrue(equipo.heroes.contains(wolverine)) 
  }
  
  @Test
  def SeReemplazaUnMiembroDelEquipoPorOtro {
    equipo.reemplazarMiembro(spiderman, capitanAmerica)
    assertFalse(equipo.heroes.contains(spiderman))
    assertTrue(equipo.heroes.contains(capitanAmerica))
  }
  
  @Test
  def EquipoNoTieneLiderDefinido {
    assertEquals(equipo.lider, None)
  }
  
  @Test
  def EquipoTieneUnLider {
    equipo.agregarMiembro(capitanAmerica.asignarTrabajo(Guerrero))
    equipo.agregarMiembro(wolverine.asignarTrabajo(Mago))
    assertEquals(equipo.lider.get, capitanAmerica.asignarTrabajo(Guerrero))
  }
  
  @Test
  def incrementarElPozoComunDelEquipo {
    equipo.incrementarPozo(CascoVikingo)
    assertEquals(equipo.pozoComun, 5, 0.01)
  }
  //TODO: probar obtenerItem
  
  @Test
  def HeroeRealizaTareaNoAfectaSusStats {
    assertEquals(wolverine.realizarTarea(PelearContraMonstruo).HPFinal, 1000, 0.01)
  }
  
  @Test 
  def HeroeRealizaTareaYAfectaSusStats {
    assertEquals(ironMan.asignarTrabajo(Ladron).realizarTarea(PelearContraMonstruo).HPFinal, 35, 0.01)
  }
  
  @Test
  def HeroeFuerzaPuertaSeModificanSusStats {
    assertEquals(ironMan.asignarTrabajo(Guerrero).realizarTarea(ForzarPuerta).HPFinal, 55, 0.01)
    assertEquals(ironMan.asignarTrabajo(Guerrero).realizarTarea(ForzarPuerta).fuerzaFinal, 26, 0.01)
  }
  
  
  
  
  
  
  
  
 
  
}

