package scala

case class Heroe(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double, val inteligenciaBase: Double,
                 val job: Trabajo = Desempleado, val inventario: Inventario = new Inventario) { 
    
  def asignarTrabajo(trabajo: Trabajo) = copy(job = trabajo)
    
  def equipar(item: Item) = copy(inventario = inventario.equipar(this, item))
  
  def cantidadItems = inventario.cantidadItems
  
  def fuerzaFinal = positivos(inventario.fuerzaFinal(this, job.fuerza(fuerzaBase)))

  def HPFinal = positivos(inventario.HPFinal(this, job.HP(HPBase)))

  def velocidadFinal = positivos(inventario.velocidadFinal(this, job.velocidad(velocidadBase)))
    
  def inteligenciaFinal = positivos(inventario.inteligenciaFinal(this, job.inteligencia(inteligenciaBase)))
 
  def positivos(valorStat: Double) = {
    if(valorStat < 1) 1
    else valorStat
  }
  
  def desequipar(item: Item) = copy(inventario = inventario.desequipar(item))
  
  def statPrincipal: Double = job.statPrincipal(this)
  
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