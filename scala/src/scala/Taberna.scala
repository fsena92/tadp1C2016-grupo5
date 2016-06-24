package scala

import scala.util.{Try, Success, Failure}

case class Taberna(var misiones: Set[Mision]) {
  
  def elegirMision(criterio: (Equipo, Equipo) => Boolean, unEquipo: Equipo): Mision = {
        misiones.foldLeft(misiones.head){(unaMision, otraMision) => 
        if (criterio(unEquipo.realizarMision(unaMision).get, unEquipo.realizarMision(otraMision).get)) unaMision 
        else otraMision
      }
  }

//  def entrenar(unEquipo: Equipo, unCriterio: (Equipo, Equipo) => Boolean):Equipo = {
//    var equipoIntermedio: Try[Equipo] = Success(unEquipo)
//    var equipoAnterior = unEquipo
//
//    var equipoEntrenado = misiones.foldLeft(unEquipo)((equipo, mision) => {
//      equipoIntermedio = equipo.realizarMision(elegirMision(unCriterio, equipo))
//      equipoIntermedio.getOrElse(unEquipo)
//    })
//    
//    equipoEntrenado
//  }  

//  def entrenar(unEquipo: Equipo, unCriterio: (Equipo, Equipo) => Boolean):Equipo = {
//    var equipoIntermedio: Try[Equipo] = Success(unEquipo)
//    var equipoAnterior = unEquipo
//    var misionesRealizadas = misiones
//
//    misionesRealizadas = misionesRealizadas.filterNot(m => unEquipo.realizarMision(elegirMision(unCriterio, unEquipo)).failed)
//    var equipoEntrenado = misiones.takeWhile(m => unEquipo.realizarMision(elegirMision(unCriterio, unEquipo)))
//      equipoIntermedio = equipo.realizarMision(elegirMision(unCriterio, equipo))
//      equipoIntermedio.getOrElse(unEquipo)
//    })
//    
//    equipoEntrenado
//  }    
//  
}