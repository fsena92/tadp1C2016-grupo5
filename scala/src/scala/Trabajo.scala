package scala

abstract class Trabajo(val inteligencia: Double, val HP: Double, 
    val fuerza: Double, val velocidad: Double ,val s: String) {
  
  def statPrincipal: Double = s match {
    case "Inteligencia" => inteligencia
    case "HP" => HP
    case "Fuerza" => fuerza
    case "Velocidad" => velocidad
  }
  
}

case object Mago extends Trabajo(20, 0, -20, 0, "Inteligencia")
case object Guerrero extends Trabajo(-10, 10, 15, 0, "Fuerza")
case object Ladron extends Trabajo (0, -5, 0, 10, "Velocidad") 