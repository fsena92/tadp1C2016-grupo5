package scala

import scala.util.control.Breaks._
import scala.util.Try
import scala.util.Success

case class Taberna(var misiones: Set[Mision]) {
  
  def elegirMision(criterio: (Equipo, Equipo) => Boolean, unEquipo: Equipo): Mision = {
        misiones.foldLeft(misiones.head){(mision1, mision2) => 
        if (criterio(unEquipo.realizarMision(mision1).get, unEquipo.realizarMision(mision2).get)) mision1 
        else mision2
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