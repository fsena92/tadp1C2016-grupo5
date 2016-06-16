package scala

case class Heroe(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double,
                 val inteligenciaBase: Double, val job: Option[Trabajo] = None,
                 val inventario: Inventario = new Inventario, val tareasRealizadas: List[Tarea] = Nil,
                 val recompensas: List[StatsRecompensa] = Nil) {
    
  def asignarTrabajo(trabajo: Trabajo) = copy(job = Some(trabajo))
    
  def equipar(item: Item) = copy(inventario = inventario.equipar(this, item))
  
  def cantidadItems = inventario.cantidadItems
    
  def fuerzaFinal = {
    val incRecompensa = recompensas.foldLeft(0:Double)((c, recompensa) => c + recompensa.fuerza)
    val incTarea = tareasRealizadas.foldLeft(0: Double)((c, tarea) => c + tarea.fuerza(this))
    if(job.isDefined) positivos(inventario.fuerzaFinal(this, job.get.fuerza(fuerzaBase + incTarea + incRecompensa)))
    else positivos(inventario.fuerzaFinal(this, fuerzaBase + incTarea + incRecompensa))
  }
   
  def HPFinal = {
    val incRecompensa = recompensas.foldLeft(0:Double)((c, recompensa) => c + recompensa.HP)
    val incTarea = tareasRealizadas.foldLeft(0: Double)((c, tarea) => c + tarea.HP(this))
    if(job.isDefined) positivos(inventario.HPFinal(this, job.get.HP(HPBase + incTarea + incRecompensa)))
    else positivos(inventario.HPFinal(this, HPBase + incTarea + incRecompensa))
  }
   
  def velocidadFinal = {
    val incRecompensa = recompensas.foldLeft(0:Double)((c, recompensa) => c + recompensa.velocidad)
    val incTarea = tareasRealizadas.foldLeft(0: Double)((c, tarea) => c + tarea.velocidad(this))
    if(job.isDefined) positivos(inventario.velocidadFinal(this, job.get.velocidad(velocidadBase + incTarea + incRecompensa)))
    else positivos(inventario.velocidadFinal(this, velocidadBase + incTarea + incRecompensa))
  }
    
  def inteligenciaFinal = {
    val incRecompensa = recompensas.foldLeft(0:Double)((c, recompensa) => c + recompensa.inteligencia)
    val incTarea = tareasRealizadas.foldLeft(0: Double)((c, tarea) => c + tarea.inteligencia(this))
    if(job.isDefined) positivos(inventario.inteligenciaFinal(this, job.get.inteligencia(inteligenciaBase + incTarea + incRecompensa)))
    else positivos(inventario.inteligenciaFinal(this, inteligenciaBase + incTarea + incRecompensa))
  }
  
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
  
  def realizarTarea(tarea: Tarea) = copy(tareasRealizadas = tarea :: tareasRealizadas)
  
  def agregarRecompensaStats(recompensa: StatsRecompensa) = copy(recompensas = recompensa :: recompensas)
  
  def stats = println("HP " + HPFinal, "Fuerza " + fuerzaFinal, "velocidodad " + velocidadFinal, "intel " + inteligenciaFinal) 

}