package scala

abstract class Trabajo(val HP: Double, val fuerza: Double, 
    val velocidad: Double, val inteligencia: Double ,val s: String) {
  
  def statPrincipal: Double = s match {
    case "Inteligencia" => inteligencia
    case "HP" => HP
    case "Fuerza" => fuerza
    case "Velocidad" => velocidad
  }
  
}

case object Guerrero extends Trabajo(10, 15, 0, -10, "Fuerza")
case object Mago extends Trabajo(0, 20, 0, 20, "Inteligencia")
case object Ladron extends Trabajo (-5, 0, 10, 0, "Velocidad") 