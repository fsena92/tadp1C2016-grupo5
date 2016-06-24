package scala

import scala.util.{Try, Success, Failure}

case class TareaFallida(equipoQueFallo: Equipo, tarea: Option[Tarea]) extends RuntimeException 

case class Equipo(val nombre: String, val heroes: List[Heroe] = Nil, var pozoComun: Double = 0) {
  
  def agregarMiembro(unMiembro: Heroe) = copy(heroes = unMiembro :: heroes) 

  def miembrosConTrabajo = heroes.filter(_.job.isDefined)
  
  def reemplazar(viejo: Heroe, nuevo: Heroe) = copy(heroes = nuevo :: heroes.filterNot(_ equals viejo))
  
  def lider: Option[Heroe] = {
    if (heroes.map(h => h.statPrincipal).forall(_== 0)) None
    else mejorHeroeSegun(_.statPrincipal)
  }
 
  def mejorHeroeSegun(cuantificador: Heroe => Double): Option[Heroe] = {
      val maximo = heroes.map(h => cuantificador(h)).max
      val heroe = heroes.filter(h => cuantificador(h) == maximo)
      if (heroe.size == 0) None
      else Some(heroe.head) 
  }
  
  def incrementarPozo(cantidad: Double) = copy(pozoComun = pozoComun + cantidad)
  
  def incrementarStatsMiembros(miembros: List[Heroe], recompensa: StatsRecompensa) = {
    copy(heroes = heroes.map(_.agregarRecompensaStats(recompensa)))
  }
 
  def obtenerItem(item: Item) = {
    val equipoConItem = for {heroe <- mejorHeroeSegun(h => h.equipar(item).statPrincipal - h.statPrincipal)}
    yield {
      if(heroe.equipar(item).statPrincipal - heroe.statPrincipal > 0) reemplazar(heroe, heroe.equipar(item))
      else incrementarPozo(item.precio)
    }
    equipoConItem.getOrElse(this)
  }
  
  def equiparATodos(item: Item) = copy(heroes = heroes.map(_.equipar(item)))
  
  def elMejorPuedeRealizar(tarea: Tarea): Option[Heroe] = {
    mejorHeroeSegun(h => tarea.facilidadPara(this) match {
      case Some(facilidad) => facilidad(h)
      case None => 0
    }) 
  }
  
  def cobrarRecompensa(mision: Mision): Equipo = mision.recompensa.cobrar(this)
  
  def realizarMision(mision: Mision): Try[Equipo] = {
   val equipoAnterior = this
   var tareaFallida: Option[Tarea] = None
   val equipoRealizaMision = mision.tareas.foldLeft(this)((equipo, tarea) => { 
     if(equipo.elMejorPuedeRealizar(tarea).isDefined) 
       reemplazar(equipo.elMejorPuedeRealizar(tarea).get, equipo.elMejorPuedeRealizar(tarea).get.realizarTarea(tarea))
     else {
       tareaFallida = Some(tarea)
       equipoAnterior
     }
   })
   if(equipoRealizaMision == equipoAnterior) Failure(TareaFallida(equipoAnterior, tareaFallida)) 
   else Success(equipoRealizaMision.cobrarRecompensa(mision))
  }
   
}