package scala

case class Heroe(HPBase: Double, fuerzaBase: Double, velocidadBase: Double, inteligenciaBase: Double,
                 job: Option[Trabajo] = None, inventario: Inventario = new Inventario) { 
  
  def statTrabajo(base: Double, delta: (Trabajo, Double) => Double) = job.fold(base)(delta(_, base))
  
  def fuerzaFinal = mayorAUno(inventario.fuerzaFinal(this, statTrabajo(fuerzaBase, _ fuerza _)))
  def HPFinal = mayorAUno(inventario.HPFinal(this, statTrabajo(HPBase, _ HP _)))
  def velocidadFinal = mayorAUno(inventario.velocidadFinal(this, statTrabajo(velocidadBase,  _ velocidad _)))
  def inteligenciaFinal = mayorAUno(inventario.inteligenciaFinal(this, statTrabajo(inteligenciaBase, _ inteligencia _)))
  
  val mayorAUno = (_:Double) max 1
  
  def equipar(item: Item): Heroe = copy(inventario = inventario.equipar(this, item).get)
  
  def asignarTrabajo(trabajo: Trabajo): Heroe = copy(job = Some(trabajo)).actualizarEstado
    
  def cantidadItems = inventario.cantidadItems
  
  def desequipar(item: Item): Heroe = copy(inventario = inventario.desequipar(item))
   
  def statPrincipal: Option[Double] = job.fold(None: Option[Double])(j => Some(j.statPrincipal(this)))
  
  def modificarStats(hp: Double, fuerza: Double, velocidad: Double ,inteligencia: Double): Heroe = {
     copy(HPBase + hp, fuerzaBase + fuerza, velocidadBase + velocidad, inteligenciaBase + inteligencia)
  }
  
  def realizarTarea(tarea: Tarea): Heroe = tarea.afectar(this).actualizarEstado
  
  def agregarRecompensaStats(r: StatsRecompensa): Heroe = 
    modificarStats(r.HP, r.fuerza, r.velocidad, r.inteligencia).actualizarEstado
  
  def actualizarEstado: Heroe = copy(inventario = inventario.actualizarInventario(this))

}