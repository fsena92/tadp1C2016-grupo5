package scala

import scala.util.{Try, Success, Failure}


case class Taberna(val misiones: List[Mision]) {
  
  def elegirMision(criterio: (Equipo, Equipo) => Boolean, equipo: Equipo): Option[Mision] = {
    val misionesRealizables = misiones.filter(equipo.realizarMision(_).isSuccess)
    
    if(!misionesRealizables.isEmpty) {
      val semilla: Option[Mision] = Some(misionesRealizables.head)
      misionesRealizables.foldLeft(semilla){(unaMision, otraMision) => {      
          if (criterio(equipo.realizarMision(unaMision.get).get, equipo.realizarMision(otraMision).get)) unaMision 
          else Some(otraMision)
        }
      }
    }
    else None
  }

  def misionRealizada(mision: Mision) = copy(misiones = misiones.filterNot(_ eq mision))
  
}