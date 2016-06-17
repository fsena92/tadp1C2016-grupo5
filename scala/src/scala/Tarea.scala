package scala

import scala.util.{Try, Success, Failure}

trait Tarea {
  def facilidadPara(heroe: Heroe, equipo: Equipo): Try[Double]
  def afectar(heroe: Heroe): Heroe = heroe
}

case object PelearContraMonstruo extends Tarea {
  def facilidadPara(heroe: Heroe, equipo: Equipo): Try[Double] = {
    if(equipo.lider.isDefined)
      equipo.lider.get.job match {
        case Guerrero => Success(20)
        case _ => Success(10)
    }
    else Success(10)
  }
  override def afectar(heroe: Heroe) = {
    if(heroe.fuerzaFinal < 20) heroe.modificarStats(-10, 0, 0, 0)
    else heroe
  }
  
}

case object ForzarPuerta extends Tarea {
  def facilidadPara(heroe: Heroe, equipo: Equipo): Try[Double] = {
    val incremento = equipo.miembrosConTrabajo.filter(h => h.job eq Ladron).size
    Success(heroe.inteligenciaFinal + 10 * incremento)
  }
  override def afectar(heroe: Heroe) = heroe.modificarStats(-5, 1, 0, 0)
}

case object ErrorRoboTalisman extends Exception

case class RobarTalisman(val talisman: Talisman) extends Tarea {
  def facilidadPara(heroe: Heroe, equipo: Equipo): Try[Double] = {
    if(equipo.lider.isDefined)
     equipo.lider.get.job match {
      case Ladron => Success(heroe.velocidadFinal)
      case _ => Failure(ErrorRoboTalisman)
    }
    else Failure(ErrorRoboTalisman)
  }
}
