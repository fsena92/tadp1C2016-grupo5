package scala

trait Estado
case class EstadoAnterior(heroe: Heroe) extends Estado

class StatsBase(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double , val inteligenciaBase: Double)

case class Heroe(val HP: Double, val fuerza: Double, val velocidad: Double, val inteligencia: Double,
                 val job:Trabajo, val inventario: Inventario) {
  
  var statsBase: StatsBase = null
  def this(HP: Double, fuerza:Double, velocidad:Double, inteligencia: Double) {
    this(HP, fuerza, velocidad, inteligencia, null, null)
    statsBase = new StatsBase(HP, fuerza, velocidad, inteligencia)
  }
  
  def asignarTrabajo(trabajo: Trabajo):Heroe = {
    copy(HP = HP + trabajo.HP,
        fuerza = fuerza +trabajo.fuerza,
        velocidad = velocidad + trabajo.velocidad,
        inteligencia = inteligencia + trabajo.inteligencia,
        job = trabajo) 
  }
  
  def modificarStats(h: Double, f: Double, v: Double ,i: Double):Heroe = 
    copy(HP = HP + h, fuerza = fuerza + f, velocidad = velocidad + v, inteligencia = inteligencia + i)
  

  
  
  
}