package scala


case class Equipo(val nombre: String, var heroes: List[Heroe] = Nil, var pozoComun: Double = 0) {
  
  def agregarMiembro(unMiembro: Heroe) = copy(heroes = unMiembro :: heroes) 

  def miembrosConTrabajo = heroes.filter(_.job != Desempleado)
  
  def reemplazarMiembro(viejo: Heroe, nuevo: Heroe) = copy(heroes = nuevo :: heroes.filterNot(_ equals viejo))
  
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
  
  def incrementarPozo(cantidad: Double) = copy(pozoComun = pozoComun + cantidad)
  
  def incrementarStatsDeLosMiembros(miembros: List[Heroe], recompensa: StatsRecompensa) = {
    copy(heroes = heroes.map(_.agregarRecompensaStats(recompensa)))
  }
 
  def obtenerItem(item: Item): Equipo = {
    val maximoHeroe = mejorHeroeSegun(heroe => {
      if(heroe.job != Desempleado)
        heroe.equipar(item).statPrincipal - heroe.statPrincipal
      else 0
    })  
    if(maximoHeroe.isDefined) reemplazarMiembro(maximoHeroe.get, maximoHeroe.get.equipar(item))
    else incrementarPozo(item.precio)
  }
  
  def equiparATodos(item: Item) = copy(heroes = heroes.map(_.equipar(item)))
  
  def elMejorPuedeRealizar(tarea: Tarea): Option[Heroe] = {
    mejorHeroeSegun(h => tarea.facilidadPara(this) match {
      case Some(facilidad) => facilidad(h)
      case None => 0
    }) 
  }
  
  def cobrarRecompensa(mision: Mision): Equipo = mision.recompensa.cobrar(this)
  
  def realizarMision(mision: Mision): Equipo = {
    var flag = true
    val equipoConMision = mision.tareas.foldLeft(this)((equipo, t) => {
      if(flag) {
        if (equipo.elMejorPuedeRealizar(t).isDefined)
          reemplazarMiembro(equipo.elMejorPuedeRealizar(t).get, equipo.elMejorPuedeRealizar(t).get.realizarTarea(t))
        else
          flag = false
          this
      }
      else this
    })
    if(equipoConMision != this) equipoConMision.cobrarRecompensa(mision)
    else this    
  }
  
  
  
}



