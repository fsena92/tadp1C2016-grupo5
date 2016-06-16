package scala

import scala.util.control.Breaks._

class Taberna(var misiones: Set[Mision]) {
  
  def elegirMision(criterio: (Equipo, Equipo) => Boolean, unEquipo: Equipo): Mision = {
      misiones.foldLeft(misiones.head){(mision1, mision2) => 
        if (criterio(unEquipo.realizarMision(mision1), unEquipo.realizarMision(mision2))) mision1 
        else mision2
      }
  }
  
  def entrenar(unEquipo: Equipo, unCriterio: (Equipo, Equipo) => Boolean) = {
    var equipoIntermedio = unEquipo
    while(misiones.size > 0) {
      equipoIntermedio = unEquipo.realizarMision(elegirMision(unCriterio, unEquipo))
      if(equipoIntermedio.tareaFallida.isDefined) break
      else misiones = misiones.filterNot(m => m eq elegirMision(unCriterio, unEquipo))
    }
    equipoIntermedio
  }
  
  
}