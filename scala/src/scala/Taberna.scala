package scala

import scala.util.{Try, Success, Failure}


case class Taberna(val misiones: List[Mision]) {
  
  def misionesRealizables(equipo: Equipo): List[Mision] = misiones.filter(m => equipo.realizarMision(m).isSuccess)
 
  def misionRealizada(mision: Mision): Taberna = copy(misiones = misiones.filterNot(_ eq mision))
  
  def elegirMision(criterio: (Equipo, Equipo) => Boolean, equipo: Equipo): Option[Mision] = {
    val realizables = misionesRealizables(equipo)
    if(realizables.nonEmpty) {
      realizables.foldLeft(Some(realizables.head)){(unaMision, otraMision) =>    
        if (criterio(equipo.realizarMision(unaMision.get).get, equipo.realizarMision(otraMision).get)) unaMision 
        else Some(otraMision)
      }
    }
    else None
  }

}