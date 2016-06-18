package scala

abstract class Trabajo(val HP: Double = 0, val fuerza: Double, 
    val velocidad: Double, val inteligencia: Double, val statPrincipal: Heroe => Double) {
  
  def fuerza(valor: Double):Double = valor + fuerza
  def HP(valor: Double): Double = valor + HP
  def velocidad(valor: Double): Double = valor + velocidad
  def inteligencia(valor: Double): Double = valor + inteligencia
  
}

case object Guerrero extends Trabajo(10, 15, 0, -10, _.fuerzaFinal)
case object Mago extends Trabajo(0, 20, 0, 20, _.inteligenciaFinal)
case object Ladron extends Trabajo (-5, 0, 10, 0, _.velocidadFinal)
