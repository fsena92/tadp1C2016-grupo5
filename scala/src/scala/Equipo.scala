package scala

import scala.util.{Try, Success, Failure}


case class Equipo(val nombre: String, var heroes: List[Heroe] = Nil, var pozoComun: Double = 0, 
             val tareaFallida: Option[Tarea] = None) {
  
  def agregarMiembro(unMiembro: Heroe) = copy(heroes = unMiembro :: heroes) 

  def miembrosConTrabajo = heroes.filter(h => h.job != Desempleado)
  
  def reemplazarMiembro(viejo: Heroe, nuevo: Heroe) = copy(heroes = nuevo :: heroes.filterNot(h => h equals viejo))
  
  def lider: Option[Heroe] = {    
    mejorHeroeSegun(heroe => heroe.job match {
      case Desempleado => 0
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
      if(heroe.job != Desempleado)
        heroe.equipar(item).statPrincipal - heroe.statPrincipal
      else 0
    })  
    if(maximoHeroe.isDefined) reemplazarMiembro(maximoHeroe.get, maximoHeroe.get.equipar(item))
    else incrementarPozo(item)
  }
  
  def equiparATodos(item: Item) = copy(heroes = heroes.map(h => h.equipar(item)))
  
  def unMiembroRealizaTareaSiPuede(tarea: Tarea): Option[Heroe] = {
    mejorHeroeSegun(h => tarea.facilidadPara(this) match {
      case Some(facilidad) => facilidad(h)
      case None => 0
    }) match {
      case None => None
      case Some(heroe) => 
        reemplazarMiembro(heroe, heroe.realizarTarea(tarea))
        Some(heroe.realizarTarea(tarea))
    }
  }
    
  def cobrarRecompensa(mision: Mision): Equipo = mision.recompensa match {
    case GanarOroParaElPozoComun(cantidadOro) => copy(pozoComun = pozoComun + cantidadOro, tareaFallida = None)
    case EncontrarUnItem(item) => copy(tareaFallida = None).obtenerItem(item)
    case IncrementarStats(condicion, recompensaDeStats) => 
      copy(tareaFallida = None, 
          heroes = heroes.filter(h => condicion(h)).map(h => h.agregarRecompensaStats(recompensaDeStats)))
    case EncontrarNuevoMiembro(nuevoMiembro) => copy(tareaFallida = None).agregarMiembro(nuevoMiembro)
  }
  
  def realizarMision(mision: Mision): Equipo = {
    val equipoActual = heroes
    val tareasRealizadas = mision.tareas.takeWhile(t => unMiembroRealizaTareaSiPuede(t).isDefined)
    val tareasFallidas = mision.tareas.filterNot(t => tareasRealizadas.contains(t))
    if (mision.tareas.size == tareasRealizadas.size) 
      cobrarRecompensa(mision)
    else 
      copy(heroes = equipoActual, tareaFallida = Some(tareasFallidas.head))
  }
  
  
  
}



