package scala

trait Tarea {
  def facilidadPara(equipo: Equipo): Option[Heroe => Double]
  def afectar(heroe: Heroe): Heroe = heroe
}

case object PelearContraMonstruo extends Tarea {
  def facilidadPara(equipo: Equipo): Option[Heroe => Double] = {
    if(equipo.lider.isDefined)
      equipo.lider.get.job match {
        case Guerrero => Some(h => 20)
        case _ => Some(h => 10)
    }
    else Some(h => 10)
  }
  override def afectar(heroe: Heroe) = {
    if(heroe.fuerzaFinal < 20) heroe.modificarStats(-10, 0, 0, 0)
    else heroe
  }
  
}

case object ForzarPuerta extends Tarea {
  def  facilidadPara(equipo: Equipo): Option[Heroe => Double] = {
    val incremento = equipo.miembrosConTrabajo.filter(_.job eq Ladron).size
    Some(_.inteligenciaFinal + 10 * incremento)
  }
  override def afectar(heroe: Heroe) = heroe.modificarStats(-5, 1, 0, 0)
}

case class RobarTalisman(val talisman: Talisman) extends Tarea {
   def facilidadPara(equipo: Equipo): Option[Heroe => Double] = {
    if(equipo.lider.isDefined)
     equipo.lider.get.job match {
      case Ladron => Some(h => h.velocidadFinal)
      case _ => None
    }
    else None
  }
}
