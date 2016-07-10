package scala

trait Tarea {
  def facilidadPara(equipo: Equipo): Option[Heroe => Double]
  def afectar(heroe: Heroe): Heroe = heroe
}

case object PelearContraMonstruo extends Tarea {
  def facilidadPara(equipo: Equipo): Option[Heroe => Double] = {
   val facilidad: Option[Heroe => Double] = for {
     lider <- equipo.lider; trabajo <- lider.job 
     if trabajo eq Guerrero
   } yield h => 20 
   facilidad.orElse(Some(h => 10))
  }
  override def afectar(heroe: Heroe): Heroe = {
    if(heroe.fuerzaFinal < 20) heroe.modificarStats(-10, 0, 0, 0)
    else heroe
  }
}

case object ForzarPuerta extends Tarea {
  def facilidadPara(equipo: Equipo) = {
    val incremento = equipo.miembrosConTrabajo.count(_.job.get == Ladron)
    Some(_.inteligenciaFinal + 10 * incremento)
  }
  override def afectar(heroe: Heroe) = {
    val tieneTrabajo = for{trabajo <- heroe.job
      if List(Mago, Ladron).contains(trabajo)
    } yield heroe
    tieneTrabajo.getOrElse(heroe.modificarStats(-5, 1, 0, 0))
  }
}

case class RobarTalisman(talisman: Item) extends Tarea {
  def facilidadPara(equipo: Equipo) = for {
    lider <- equipo.lider; trabajo <- lider.job 
    if trabajo eq Ladron
  } yield _.velocidadFinal
  override def afectar(heroe: Heroe) = heroe.equipar(talisman)
}
