package scala

abstract class Trabajo(val HP: Double = 0, val fuerza: Double = 0, 
    val velocidad: Double = 0, val inteligencia: Double = 0) {
  
  def fuerza(valor: Double):Double = valor + fuerza
  def HP(valor: Double): Double = valor + HP
  def velocidad(valor: Double): Double = valor + velocidad
  def inteligencia(valor: Double): Double = valor + inteligencia
  
  def statPrincipal(heroe: Heroe):Double
  
}

case object Guerrero extends Trabajo(HP = 10, fuerza = 15, inteligencia = -10) {
  def statPrincipal(heroe: Heroe) = heroe.fuerzaFinal
}
case object Mago extends Trabajo(fuerza = 20, inteligencia = 20) {
  def statPrincipal(heroe: Heroe) = heroe.inteligenciaFinal
}
case object Ladron extends Trabajo (HP = -5, velocidad = 10) {
  def statPrincipal(heroe: Heroe) = heroe.velocidadFinal
}
