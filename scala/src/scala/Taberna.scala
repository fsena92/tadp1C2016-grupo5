package scala

import scala.util.{Try, Success, Failure}


case class Taberna(val misiones: List[Mision]) {
  
  def elegirMision(criterio: (Equipo, Equipo) => Boolean, unEquipo: Equipo): Option[Mision] = {
    val misionesRealizables = misiones.filter(unEquipo.realizarMision(_).isSuccess)
    
    if(!misionesRealizables.isEmpty) {
      val semilla: Option[Mision] = Some(misionesRealizables.head)
      misionesRealizables.foldLeft(semilla){(unaMision, otraMision) => {      
          if (criterio(unEquipo.realizarMision(unaMision.get).get, unEquipo.realizarMision(otraMision).get)) unaMision 
          else Some(otraMision)
        }
      }
    }
    else None
  }

  def misionRealizada(mision: Mision) = copy(misiones = misiones.filterNot(_ eq mision))
  
//  def entrenar(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Equipo = {
//    var m = misiones
//    misiones.foldLeft(Entreno(equipo, m): Entrenador)((entrenado, mision) => entrenado.entrenarMejor(criterio)).get
//  }  
//  
  
  // Opcion 2
//  def entrenamiento(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, misiones: List[Mision]): Equipo = {
//    if(!misiones.isEmpty) {
//      var mision = elegirMision(criterio, equipo, misiones)
//      if(mision.isDefined) {
//        entrenamiento(equipo.realizarMision(mision.get).get, criterio, misiones.filterNot(_ eq mision.get)) 
//      }
//      else entrenamiento(equipo, criterio, misiones.filterNot(_ eq misiones.head)) 
//    }
//    else equipo
//  }
//  
//  def entrenar2(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Equipo = entrenamiento(equipo, criterio, misiones)
  

}