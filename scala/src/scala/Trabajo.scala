package scala

abstract class Trabajo(val _HP: Double = 0, val _fuerza: Double = 0, 
    val _velocidad: Double = 0, val _inteligencia: Double = 0) {
  
  def fuerza = (_:Double) + _fuerza
  def HP = (_:Double) + _HP
  def velocidad = (_:Double) + _velocidad
  def inteligencia = (_:Double) + _inteligencia
  
  def statPrincipal(heroe: Heroe):Double
  
}

case object Guerrero extends Trabajo(_HP = 10, _fuerza = 15, _inteligencia = -10) {
  def statPrincipal(heroe: Heroe) = heroe.fuerzaFinal
}
case object Mago extends Trabajo(_fuerza = 20, _inteligencia = 20) {
  def statPrincipal(heroe: Heroe) = heroe.inteligenciaFinal
}
case object Ladron extends Trabajo (_HP = -5, _velocidad = 10) {
  def statPrincipal(heroe: Heroe) = heroe.velocidadFinal
}
