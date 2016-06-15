package scala

import scala.util.{Try, Success, Failure}

trait Tarea {
  def facilidadPara(heroe: Heroe, equipo: Equipo): Try[Double]
  def HP(heroe: Heroe): Double = 0
  def fuerza(heroe: Heroe): Double = 0
  def inteligencia(heroe: Heroe): Double = 0
  def velocidad(heroe: Heroe): Double = 0
}

case object PelearContraMonstruo extends Tarea {
  def facilidadPara(heroe: Heroe, equipo: Equipo): Try[Double] = {
    if(equipo.lider.isDefined)
      equipo.lider.get.job.get match {
        case Guerrero => Success(20)
        case _ => Success(10)
    }
    else Success(10)
  }
  override def HP(heroe: Heroe) = {
    if(heroe.fuerzaFinal < 20) - 10
    else 0
  }
}

case object ForzarPuerta extends Tarea {
  def facilidadPara(heroe: Heroe, equipo: Equipo): Try[Double] = {
    Success(heroe.inteligenciaFinal + 10 * equipo.heroes.filter(h => h.job.get eq Ladron).size)
  }
  override def HP(heroe: Heroe) = - 5
  override def fuerza(heroe: Heroe) = 1 
}

case class RobarTalisman(val talisman: Talisman) extends Tarea {
  def facilidadPara(heroe: Heroe, equipo: Equipo): Try[Double] = {
    if(equipo.lider.isDefined)
     equipo.lider.get.job.get match {
      case Ladron => Success(heroe.velocidadFinal)
      case _ => Failure(throw new RuntimeException)
    }
    else Failure(throw new RuntimeException)
  }
}
