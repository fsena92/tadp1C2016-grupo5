package scala

case class Heroe(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double, val inteligenciaBase: Double,
                 val job: Option[Trabajo] = None, val inventario: Inventario = new Inventario) { 
  
  def statTrabajo(base: Double, delta: (Trabajo, Double) => Double) = job.foldLeft(base)((b, j) => delta(j, b))
  
  def fuerzaFinal = mayorAUno(inventario fuerzaFinal(this, statTrabajo(fuerzaBase, _ fuerza _ )))
  def HPFinal = mayorAUno(inventario HPFinal(this, statTrabajo(HPBase, _ HP _)))
  def velocidadFinal = mayorAUno(inventario velocidadFinal(this, statTrabajo(velocidadBase,  _ velocidad _ )))
  def inteligenciaFinal = mayorAUno(inventario inteligenciaFinal(this, statTrabajo(inteligenciaBase, _ inteligencia _ )))
  
  def mayorAUno(valorStat: Double) = if(valorStat < 1) 1 ; else valorStat
  
  def equipar(item: Item) = copy(inventario = inventario.equipar(this, item))
  def asignarTrabajo(trabajo: Trabajo) = copy(job = Some(trabajo))
  def cantidadItems = inventario.cantidadItems
  def desequipar(item: Item) = copy(inventario = inventario.desequipar(item))
   
  def statPrincipal = job.foldLeft(0:Double)((base, trabajo) => trabajo.statPrincipal(this))
  
  def modificarStats(hp: Double, fuerza: Double, velocidad: Double ,inteligencia: Double) = {
     copy(HPBase = HPBase + hp,
          fuerzaBase = fuerzaBase + fuerza, 
          velocidadBase = velocidadBase + velocidad,
          inteligenciaBase = inteligenciaBase + inteligencia)
  }
  
  def realizarTarea(unaTarea: Tarea) = unaTarea.afectar(this)
  
  def agregarRecompensaStats(recompensa: StatsRecompensa) = {
    modificarStats(recompensa.HP, recompensa.fuerza, recompensa.velocidad, recompensa.inteligencia)
  }
  
}