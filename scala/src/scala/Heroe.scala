package scala


case class Heroe(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double, val inteligenciaBase: Double,
                 val job: Option[Trabajo] = Some(Desempleado), val inventario: Inventario = new Inventario) { 
    
  
  def velocidadTrabajo(base: Double) = job.foldLeft(base)((b, elJob) => elJob.velocidad(b))
  def HPTrabajo(base: Double) = job.foldLeft(base)((b, elJob) => elJob.HP(b))
  def inteligenciaTrabajo(base: Double) = job.foldLeft(base)((b, elJob) => elJob.inteligencia(b))
  def fuerzaTrabajo(base: Double) = job.foldLeft(base)((b, elJob) => elJob.fuerza(b))
  
  
  def asignarTrabajo(trabajo: Trabajo) = copy(job = Some(trabajo))
    
  def equipar(item: Item) = copy(inventario = inventario.equipar(this, item))
  
  def cantidadItems = inventario.cantidadItems
  
  def fuerzaFinal = positivos(inventario.fuerzaFinal(this, fuerzaTrabajo(fuerzaBase)))

  def HPFinal = positivos(inventario.HPFinal(this, HPTrabajo(HPBase)))

  def velocidadFinal = positivos(inventario.velocidadFinal(this, velocidadTrabajo(velocidadBase)))
    
  def inteligenciaFinal = positivos(inventario.inteligenciaFinal(this, inteligenciaTrabajo(inteligenciaBase)))
 
  def positivos(valorStat: Double) = {
    if(valorStat < 1) 1
    else valorStat
  }
  
  def desequipar(item: Item) = copy(inventario = inventario.desequipar(item))
   
  def statPrincipal: Double = job.get.statPrincipal(this)
  
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