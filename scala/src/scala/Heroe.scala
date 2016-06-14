package scala

case class Heroe(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double, val inteligenciaBase: Double,
                 val job: Option[Trabajo] = None, val inventario: Inventario = new Inventario) {
    
  def asignarTrabajo(trabajo: Trabajo) = copy(job = Some(trabajo))
    
  def equipar(unItem: Item) = copy(inventario = inventario.equipar(this, unItem))
  
  def cantidadItems = inventario.cantidadItems
    
  def fuerzaFinal = {
    if(job.isDefined) positivos(inventario.fuerzaFinal(this, job.get.fuerza(fuerzaBase)))
    else positivos(inventario.fuerzaFinal(this, fuerzaBase))
  }
   
  def HPFinal = {
    if(job.isDefined) positivos(inventario.HPFinal(this, job.get.HP(HPBase)))
    else positivos(inventario.HPFinal(this, HPBase))
  }
   
  def velocidadFinal = {
    if(job.isDefined) positivos(inventario.velocidadFinal(this, job.get.velocidad(velocidadBase)))
    else positivos(inventario.velocidadFinal(this, velocidadBase))
  }
    
  def inteligenciaFinal = {
    if(job.isDefined) positivos(inventario.inteligenciaFinal(this, job.get.inteligencia(inteligenciaBase)))
    else positivos(inventario.inteligenciaFinal(this, inteligenciaBase))
  }
  
  def positivos(valorStat: Double) = {
    if(valorStat < 1) 1
    else valorStat
  }
  
  
}