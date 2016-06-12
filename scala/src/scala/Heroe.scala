package scala

case class Heroe(val HP: Double, val fuerza: Double, val velocidad: Double, val inteligencia: Double,
                 val job: Option[Trabajo] = None, val inventario: Inventario = new Inventario()) {
  
  def asignarTrabajo(trabajo: Trabajo): Heroe = {
    copy(HP = HP + trabajo.HP,
    fuerza = fuerza + trabajo.fuerza,
    velocidad = velocidad + trabajo.velocidad,
    inteligencia = inteligencia + trabajo.inteligencia,
    job = Some(trabajo)) 
  }
  
  def modificarStats(h: Double, f: Double, v: Double ,i: Double): Heroe = {
    copy(HP = HP + h,
    fuerza = fuerza + f,
    velocidad = velocidad + v,
    inteligencia = inteligencia + i)
  }
  
  def stats = (("hp",HP),("fuerza",fuerza),("velocidad",velocidad),("inteligencia",inteligencia))
  
  def equipar(unItem: Item): Heroe = {
    val heroeIntermedio = unItem.equipar(this)
    copy(inventario = inventario.equiparItem(unItem, this),
    HP = heroeIntermedio.HP,
    fuerza = heroeIntermedio.fuerza,
    velocidad = heroeIntermedio.velocidad,
    inteligencia = heroeIntermedio.inteligencia)
  }
    
    
}