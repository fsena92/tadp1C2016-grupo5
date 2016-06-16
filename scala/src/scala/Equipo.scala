package scala

import scala.util.{Try, Success, Failure}


case class Equipo(val nombre: String, var heroes: List[Heroe] = Nil, var pozoComun: Double = 0, 
             val misiones: List[Mision] = Nil) {
  
  def agregarMiembro(unMiembro: Heroe) = copy(heroes = unMiembro :: heroes) 

  def miembrosConTrabajo = heroes.filter(h => h.job.isDefined)
  
  def reemplazarMiembro(unMiembro: Heroe, nuevoMiembro: Heroe):Equipo =  {
    copy(heroes = nuevoMiembro :: heroes.filterNot(h => h equals unMiembro))
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
  
  def incrementarPozo(item: Item) = copy(pozoComun = pozoComun + item.valor) 
  
  def obtenerItem(item: Item): Equipo = {
    val maximoHeroe = mejorHeroeSegun(heroe => {
      if(heroe.job.isDefined)
        heroe.equipar(item).statPrincipal - heroe.statPrincipal
      else 0
    })  
    if(maximoHeroe.isDefined) reemplazarMiembro(maximoHeroe.get, maximoHeroe.get.equipar(item))
    else incrementarPozo(item)
  }
  
  def equiparATodos(item: Item) = copy(heroes = heroes.map(h => h.equipar(item)))
  
  def unMiembroRealizaTareaSiPuede(tarea: Tarea): Option[Heroe] = {
    mejorHeroeSegun(h => tarea.facilidadPara(h, this) match {
      case Success(_) => tarea.facilidadPara(h, this).get
      case Failure(_) => 0
    }) match {
      case None => None
      case Some(heroe) => 
        reemplazarMiembro(heroe, heroe.realizarTarea(tarea))
        Some(heroe.realizarTarea(tarea))
    }
  }
    
  def cobrarRecompensa(mision: Mision):Equipo = mision.recompensa match {
    case GanarOroParaElPozoComun(cantidadOro) => copy(pozoComun = pozoComun + cantidadOro)
    case EncontrarUnItem(item) => obtenerItem(item)
    case IncrementarStats(condicion, recompensaDeStats) => 
      copy(heroes = heroes.filter(h => condicion(h)).map(h => h.agregarRecompensaStats(recompensaDeStats)))
    case EncontrarNuevoMiembro(nuevoMiembro) => agregarMiembro(nuevoMiembro)
  }
  
  def realizarMision(mision: Mision): Option[Tarea] = {
    val equipoActual = heroes
    val tareasRealizadas = mision.tareas.takeWhile(t => unMiembroRealizaTareaSiPuede(t).isDefined)
    val tareasFallidas = mision.tareas.filterNot(t => tareasRealizadas.contains(t))
    if (mision.tareas.size == tareasRealizadas.size) {
      cobrarRecompensa(mision)
      None
    }
    else {
      copy(heroes = equipoActual)
      Some(tareasFallidas.head)
    }
  }
  
  
  
}



