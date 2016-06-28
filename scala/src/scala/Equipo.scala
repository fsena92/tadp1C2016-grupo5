package scala

import scala.util.{Try, Success, Failure}

class TareaFallida(equipo: Equipo, tarea: Tarea) extends Exception 

case class Equipo(val nombre: String, val heroes: List[Heroe] = Nil, val pozoComun: Double = 0) {
  
  def agregarMiembro(unMiembro: Heroe) = copy(heroes = unMiembro :: heroes) 
  def miembrosConTrabajo = heroes.filter(_.job.isDefined)
  def reemplazar(viejo: Heroe, nuevo: Heroe) = copy(heroes = nuevo :: heroes.filterNot(_ equals viejo))
  
  def lider: Option[Heroe] = {
    if (miembrosConTrabajo isEmpty) None
    else copy(heroes = miembrosConTrabajo).mejorHeroeSegun(_.statPrincipal.get)
  }
 
  def mejorHeroeSegun(cuantificador: Heroe => Double): Option[Heroe] = {
    val maximo = heroes.map(h => cuantificador(h)).max
    heroes.filter(h => cuantificador(h) == maximo).headOption
  }
  
  def incrementarPozo(cantidad: Double): Equipo = copy(pozoComun = pozoComun + cantidad)
  
  def incrementarStatsMiembros(condicion: Heroe => Boolean, recompensa: StatsRecompensa): Equipo = {
    copy(heroes = heroes.filter(condicion(_)).map(_.agregarRecompensaStats(recompensa)))
  }
 
  def incrementoStat(heroe: Heroe, item: Item): Double = heroe.equipar(item).statPrincipal.get - heroe.statPrincipal.get
  
  def obtenerItem(item: Item): Equipo = {
    val equipoConItem = for {heroe <- mejorHeroeSegun(incrementoStat(_, item))
      if incrementoStat(heroe, item) > 0
    }
    yield reemplazar(heroe, heroe equipar item)
    equipoConItem.getOrElse(incrementarPozo(item.precio))
  }
  
  def equiparATodos(item: Item): Equipo = copy(heroes = heroes.map(_.equipar(item)))
  
  def elMejorPuedeRealizar(tarea: Tarea): Option[Heroe] = {
    for {facilidad <- tarea facilidadPara this; elMejor <- mejorHeroeSegun(facilidad)}
    yield elMejor
  }
  
  def cobrarRecompensa(mision: Mision): Equipo = mision.recompensa.cobrar(this)
  
  def realizarMision(mision: Mision): Try[Equipo] = Try (
    mision.tareas.foldLeft(this)((equipo, tarea) => {
      val puedeRealizar = for {heroe <- equipo elMejorPuedeRealizar tarea}
      yield reemplazar(heroe, heroe realizarTarea tarea)
      puedeRealizar.getOrElse(throw new TareaFallida(equipo, tarea)).cobrarRecompensa(mision)})    
  )

  def entrenar(taberna: Taberna, criterio: (Equipo, Equipo) => Boolean): Equipo = {
      val equipo = this
      val resultadoEntrenar = for {
        misionElegida <- taberna.elegirMision(criterio, this)
        equipo <- realizarMision(misionElegida).toOption
      }
      yield equipo.entrenar(taberna misionRealizada misionElegida, criterio)
      resultadoEntrenar.getOrElse(equipo)
  }
  
  
}