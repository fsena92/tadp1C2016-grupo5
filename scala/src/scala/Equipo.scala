package scala

import scala.util.{Try, Success, Failure}

case class TareaFallida(equipoQueFallo: Equipo, tarea: Option[Tarea]) extends RuntimeException 

case class Equipo(val nombre: String, val heroes: List[Heroe] = Nil, var pozoComun: Double = 0) {
  
  def agregarMiembro(unMiembro: Heroe) = copy(heroes = unMiembro :: heroes) 

  def miembrosConTrabajo = heroes.filter(_.job.isDefined)
  
  def reemplazar(viejo: Heroe, nuevo: Heroe) = copy(heroes = nuevo :: heroes.filterNot(_ equals viejo))
  
  def lider: Option[Heroe] = {
    if (miembrosConTrabajo.isEmpty) None
    else copy(heroes = miembrosConTrabajo).mejorHeroeSegun(_.statPrincipal.get)
  }
 
  def mejorHeroeSegun(cuantificador: Heroe => Double): Option[Heroe] = {
      val maximo = heroes.map(cuantificador(_)).max
      val heroe = heroes.filter(cuantificador(_) == maximo)
      if (heroe.size == 0) None
      else Some(heroe.head) 
  }
  
  def incrementarPozo(cantidad: Double) = copy(pozoComun = pozoComun + cantidad)
  
  def incrementarStatsMiembros(condicion: Heroe => Boolean, recompensa: StatsRecompensa) = {
    copy(heroes = heroes.filter(condicion(_)).map(_.agregarRecompensaStats(recompensa)))
  }
 
  def incrementoStat(heroe: Heroe, item: Item): Double = heroe.equipar(item).statPrincipal.get - heroe.statPrincipal.get
 
  def obtenerItem(item: Item): Equipo = {
    val equipoConItem = for {heroe <- mejorHeroeSegun(h => incrementoStat(h, item))
      if(incrementoStat(heroe, item) > 0)
    }
    yield reemplazar(heroe, heroe.equipar(item))
    equipoConItem.getOrElse(incrementarPozo(item.precio))
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