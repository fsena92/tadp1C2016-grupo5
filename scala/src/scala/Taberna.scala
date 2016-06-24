package scala

import scala.util.{Try, Success, Failure}

//Opcion 1
trait Entrenador {
  def map(f: Equipo => Equipo):Entrenador
  def get: Equipo
  def entrenarMejor(criterio: (Equipo, Equipo) => Boolean): Entrenador
}

case class Entreno(var equipo: Equipo, var misiones: List[Mision]) extends Entrenador {
  def map(f: Equipo => Equipo): Entrenador = Entreno(f(equipo), misiones)
  def get = equipo
  def entrenarMejor(criterio: (Equipo, Equipo) => Boolean): Entrenador = {
    val laMision = Taberna(misiones).elegirMision(criterio, equipo, misiones)
    if(laMision.isDefined) Entreno(equipo.realizarMision(laMision.get).get, misiones.filterNot(_ eq laMision))
    else NoEntrenaMas(equipo, misiones)
  }
}
case class NoEntrenaMas(var equipo: Equipo, var misiones: List[Mision]) extends Entrenador {
  def map(f: Equipo => Equipo): Entrenador = NoEntrenaMas(f(equipo), misiones)
  def get = equipo
  def entrenarMejor(criterio: (Equipo, Equipo) => Boolean): Entrenador = {
    NoEntrenaMas(equipo, misiones)
  }
}


case class Taberna(val misiones: List[Mision]) {
  
  def elegirMision(criterio: (Equipo, Equipo) => Boolean, unEquipo: Equipo, listaMisiones: List[Mision]) = {
    val misionElegida = listaMisiones.foldLeft(listaMisiones.head){(unaMision, otraMision) => {      
      if (criterio(unEquipo.realizarMision(unaMision).getOrElse(unEquipo), unEquipo.realizarMision(otraMision).getOrElse(unEquipo))) 
        unaMision 
       else otraMision
     }
   }
   if(unEquipo.realizarMision(misionElegida).isSuccess) Some(misionElegida)
   else None
 }

  def entrenar(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Equipo = {
    var m = misiones
    misiones.foldLeft(Entreno(equipo, m): Entrenador)((entrenado, mision) => {
        entrenado.entrenarMejor(criterio)
    }).get
  }  
  
  
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