package scala

abstract class Trabajo(val HP: Double, val fuerza: Double, 
    val velocidad: Double, val inteligencia: Double ,val _statPrincipal: String) {
  
  def statPrincipal: Double = _statPrincipal match {
    case "Inteligencia" => inteligencia
    case "HP" => HP
    case "Fuerza" => fuerza
    case "Velocidad" => velocidad
  }
  
  def fuerza(valor: Double):Double = valor + fuerza
  def HP(valor: Double): Double = valor + HP
  def velocidad(valor: Double): Double = valor + velocidad
  def inteligencia(valor: Double): Double = valor + inteligencia
  
}

case object Guerrero extends Trabajo(10, 15, 0, -10, "Fuerza")
case object Mago extends Trabajo(0, 20, 0, 20, "Inteligencia")
case object Ladron extends Trabajo (-5, 0, 10, 0, "Velocidad") 