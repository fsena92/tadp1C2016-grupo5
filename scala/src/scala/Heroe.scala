package scala

case class Heroe(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double,
                 val inteligenciaBase: Double, val job: Option[Trabajo] = None,
                 val inventario: Inventario = new Inventario, val tareas: List[Tarea] = Nil,
                 val recompensas: List[StatsRecompensa] = Nil) {
    
  def asignarTrabajo(trabajo: Trabajo) = copy(job = Some(trabajo))
    
  def equipar(item: Item) = copy(inventario = inventario.equipar(this, item))
  
  def cantidadItems = inventario.cantidadItems
  
  def statIntermedio(tipo: String, valor: Double) = {
    if(job isDefined) tipo match {
      case "h" => job.get.HP(valor) + recompensas.map(r => r.HP).sum + tareas.map(t => t.HP(this)).sum
      case "f" => job.get.fuerza(valor) + recompensas.map(r => r.fuerza).sum + tareas.map(t => t.fuerza(this)).sum
      case "v" => job.get.velocidad(valor) + recompensas.map(r => r.velocidad).sum + tareas.map(t => t.velocidad(this)).sum
      case "i" => job.get.inteligencia(valor) + recompensas.map(r => r.inteligencia).sum + tareas.map(t => t.inteligencia(this)).sum  
    }
    else tipo match {
      case "h" => valor + recompensas.map(r => r.HP).sum + tareas.map(t => t.HP(this)).sum
      case "f" => valor + recompensas.map(r => r.fuerza).sum + tareas.map(t => t.fuerza(this)).sum
      case "v" => valor + recompensas.map(r => r.velocidad).sum + tareas.map(t => t.velocidad(this)).sum
      case "i" => valor + recompensas.map(r => r.inteligencia).sum + tareas.map(t => t.inteligencia(this)).sum    
    }
  }
  
  def fuerzaFinal = positivos(inventario.fuerzaFinal(this, statIntermedio("f", fuerzaBase)))

  def HPFinal = positivos(inventario.HPFinal(this, statIntermedio("h", HPBase)))

  def velocidadFinal = positivos(inventario.velocidadFinal(this, statIntermedio("v", velocidadBase)))
    
  def inteligenciaFinal = positivos(inventario.inteligenciaFinal(this, statIntermedio("i", inteligenciaBase)))
 
  def positivos(valorStat: Double) = {
    if(valorStat < 1) 1
    else valorStat
  }
  
  def desequipar(item: Item) = copy(inventario = inventario.desequipar(item))
  
  def statPrincipal: Double = job.get.statPrincipal match {
    case "Inteligencia" => inteligenciaFinal
    case "HP" => HPFinal
    case "Fuerza" => fuerzaFinal
    case "Velocidad" => velocidadFinal
  }
  
  def realizarTarea(unaTarea: Tarea) = copy(tareas = unaTarea :: tareas)
  
  def agregarRecompensaStats(unaRecompensa: StatsRecompensa) = copy(recompensas = unaRecompensa :: recompensas)
  
}