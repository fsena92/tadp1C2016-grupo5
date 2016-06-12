package scala


trait Item{
  val valor = 0
  def cumpleCondicion(heroe :Heroe):Boolean = true
  def desequipar(heroe: Heroe):Heroe = heroe
  def equipar(heroe: Heroe):Heroe = heroe
}

case class Cabeza() extends Item 
case class Torso() extends Item
case class Brazo() extends Item
case class Brazos() extends Item
case class Talisman() extends Item


object ArmaduraEleganteSport extends Torso {
  override def equipar(heroe: Heroe) = heroe.modificarStats(-30, 0, 30, 0)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(30, 0, -30, 0)
}

object EspadaDeLaVida extends Brazo
object EscudoAntiRobo extends Brazo
object PalitoMagico extends Brazo
object ArcoViejo extends Brazos

object CascoVikingo extends Cabeza {
  override def cumpleCondicion(heroe: Heroe) = heroe.fuerza > 30
  override def equipar(heroe: Heroe): Heroe = {
    if(cumpleCondicion(heroe)) heroe.modificarStats(10, 0, 0, 0)
    else heroe
  }
  override def desequipar(heroe: Heroe): Heroe = heroe.modificarStats(-10, 0, 0, 0)
}

object VinchaDelBufaloDelAgua extends Cabeza {
  override def cumpleCondicion(heroe: Heroe) = heroe.job.isDefined
  override def equipar(heroe: Heroe) = {
    if(cumpleCondicion(heroe)) {
      if(heroe.fuerza > heroe.inteligencia) heroe.modificarStats(0, 0, 0, 30)
      else heroe.modificarStats(10, 10, 10, 0)
    }
    else heroe
  }
  override def desequipar(heroe: Heroe) = {
    if(heroe.fuerza > heroe.inteligencia) heroe.modificarStats(0, 0, 0, -30)
    else heroe.modificarStats(-10, -10, -10, 0)
  }
}
object Dedicacion extends Talisman
object Minimalismo extends Talisman
object Maldito extends Talisman