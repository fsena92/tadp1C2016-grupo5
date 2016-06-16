package scala

case class Heroe(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double,
                 val inteligenciaBase: Double, val job: Option[Trabajo] = None,
                 val inventario: Inventario = new Inventario, val tareasRealizadas: List[Tarea] = Nil) {
    
  def asignarTrabajo(trabajo: Trabajo) = copy(job = Some(trabajo))
    
  def equipar(item: Item) = copy(inventario = inventario.equipar(this, item))
  
  def cantidadItems = inventario.cantidadItems
    
  def fuerzaFinal = {
    val incTarea = tareasRealizadas.foldLeft(0: Double)((c, tarea) => c + tarea.fuerza(this))
    if(job.isDefined) positivos(inventario.fuerzaFinal(this, job.get.fuerza(fuerzaBase + incTarea)))
    else positivos(inventario.fuerzaFinal(this, fuerzaBase + incTarea))
  }
   
  def HPFinal = {
    val incTarea = tareasRealizadas.foldLeft(0: Double)((c, tarea) => c + tarea.HP(this))
    if(job.isDefined) positivos(inventario.HPFinal(this, job.get.HP(HPBase + incTarea)))
    else positivos(inventario.HPFinal(this, HPBase + incTarea))
  }
   
  def velocidadFinal = {
    val incTarea = tareasRealizadas.foldLeft(0: Double)((c, tarea) => c + tarea.velocidad(this))
    if(job.isDefined) positivos(inventario.velocidadFinal(this, job.get.velocidad(velocidadBase + incTarea)))
    else positivos(inventario.velocidadFinal(this, velocidadBase + incTarea))
  }
    
  def inteligenciaFinal = {
    val incTarea = tareasRealizadas.foldLeft(0: Double)((c, tarea) => c + tarea.inteligencia(this))
    if(job.isDefined) positivos(inventario.inteligenciaFinal(this, job.get.inteligencia(inteligenciaBase + incTarea)))
    else positivos(inventario.inteligenciaFinal(this, inteligenciaBase + incTarea))
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
    
  def stats = println("HP " + HPFinal, "Fuerza " + fuerzaFinal, "velocidodad " + velocidadFinal, "intel " + inteligenciaFinal) 

}