package scala

abstract class Trabajo(val inteligencia: Double, val HP: Double, 
    val fuerza: Double, val velocidad: Double ,val statPrincipal: Double) 

case object Mago extends Trabajo(20, 0, -20, 0, 20) 
case object Guerrero extends Trabajo(-10, 10, 15, 0, 15)
case object Ladron extends Trabajo (0, -5, 0, 10, 10) 