package scala

import scala.util.{Try, Success, Failure}


class Equipo(val nombre: String, var heroes: List[Heroe] = Nil, var pozoComun: Double = 0, 
             val misiones: List[Mision] = Nil) {
  
  def agregarMiembro(unMiembro: Heroe) {
    heroes = unMiembro :: heroes 
  }

  def miembrosConTrabajo = heroes.filter(h => h.job.isDefined)
  
  def reemplazarMiembro(unMiembro: Heroe, nuevoMiembro: Heroe) {
    heroes = heroes.filterNot(h => h equals unMiembro)
    agregarMiembro(nuevoMiembro)
  }
  
  def lider: Option[Heroe] = {    
    mejorHeroeSegun(heroe => heroe.job match {
      case None => 0
      case _ => heroe.statPrincipal
    })
  }
 
  def mejorHeroeSegun(cuantificador: Heroe => Double): Option[Heroe] = {
    val maximo = heroes.map(h => cuantificador(h)).max
    val heroe = heroes.filter(h => cuantificador(h) == maximo)
    if (heroe.size > 1) None
    else Some(heroe.head)
  }
  
  def incrementarPozo(item: Item) = pozoComun = pozoComun + item.valor 
  
  def obtenerItem(item: Item) {
    val maximoHeroe = mejorHeroeSegun(heroe => {
      if(heroe.job.isDefined)
        heroe.equipar(item).statPrincipal - heroe.statPrincipal
      else 0
    })  
    if(maximoHeroe.isDefined) reemplazarMiembro(maximoHeroe.get, maximoHeroe.get.equipar(item))
    else incrementarPozo(item)
  }
  
  def equiparATodos(item: Item) {
    heroes = heroes.map(h => h.equipar(item))
  }
  
  def unMiembroRealizaTareaSiPuede(tarea: Tarea): Boolean = {
    mejorHeroeSegun(h => tarea.facilidadPara(h, this) match {
      case Success(_) => tarea.facilidadPara(h, this).get
      case Failure(_) => 0
    }) match {
      case None => false
      case Some(heroe) => {
        heroe.realizarTarea(tarea)
        true
      }
    }
  }
  
  // TODO: ver este reclamarRecompensa
  def reclamarRecompensa = 1
  
  def realizarMision(mision: Mision): Option[Tarea] = {
    val equipoAnterior = heroes
    val tareasRealizadas = mision.tareas.takeWhile(t => unMiembroRealizaTareaSiPuede(t))
    val tareasFallidas = mision.tareas.filterNot(t => tareasRealizadas.contains(t))
    if (mision.tareas.size == tareasRealizadas.size) {
      reclamarRecompensa
      None
    }
    else {
      heroes = equipoAnterior
      Some(tareasFallidas.head)
    }
  }
  
}



