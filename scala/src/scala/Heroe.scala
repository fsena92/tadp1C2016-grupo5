package scala

class StatsBase(val HPBase: Double, val fuerzaBase: Double, val velocidadBase: Double , val inteligenciaBase: Double)

case class Heroe(var HP: Double, var fuerza: Double, var velocidad: Double, var inteligencia: Double,
                 var job:Trabajo = null, var inventario: Inventario = new Inventario) {

  var statsBase: StatsBase = new StatsBase(HP, fuerza, velocidad, inteligencia)
  
  def evitarNegativo(valor: Double):Double = if (valor < 1) 1 else valor
  
  def asignarTrabajo(trabajo: Trabajo):Heroe = {
    copy(HP = evitarNegativo(HP + trabajo.HP),
    fuerza = evitarNegativo(fuerza + trabajo.fuerza),
    velocidad = evitarNegativo(velocidad + trabajo.velocidad),
    inteligencia = evitarNegativo(inteligencia + trabajo.inteligencia),
    job = trabajo) 
  }
  
  def modificarStats(h: Double, f: Double, v: Double ,i: Double):Heroe = {
    copy(HP = evitarNegativo(HP + h),
    fuerza = evitarNegativo(fuerza + f),
    velocidad = evitarNegativo(velocidad + v),
    inteligencia = evitarNegativo(inteligencia + i))
  }
  
  def agregarItem(unItem: Item):Heroe = copy(inventario = inventario.equipar(this, unItem))
  
  def stats() ={
    (("hp",HP),("fuerza",fuerza),("velocidad",velocidad),("inteligencia",inteligencia))
  }
  
}