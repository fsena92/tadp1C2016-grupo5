package scala

trait Item {
  val valor = 0
  def cumpleCondicion(heroe: Heroe): Boolean = true
  def fuerza(heroe: Heroe, valor: Double) = valor
  def HP(heroe: Heroe, valor: Double) = valor
  def velocidad(heroe: Heroe, valor: Double) = valor
  def inteligencia(heroe: Heroe, valor: Double) = valor
}

case class Cabeza() extends Item 
case class Talisman() extends Item
case class ArmaSimple() extends Item
case class ArmaDoble() extends Item
case class Armadura() extends Item

object ArmaduraEleganteSport extends Armadura {
  override def HP(heroe: Heroe, valor: Double) = valor - 30
  override def velocidad(heroe: Heroe, valor: Double) = valor + 30 
}

object EspadaDeLaVida extends ArmaSimple {
  override def fuerza(heroe: Heroe, valor: Double) = heroe.HPFinal
}

object EscudoAntiRobo extends ArmaSimple {
  override def cumpleCondicion(heroe: Heroe) = heroe.job match {
    case Some(Ladron) => false
    case _ => heroe.fuerzaBase >= 20
  }
  override def HP(heroe: Heroe, valor: Double) = {
    if(cumpleCondicion(heroe)) valor + 20
    else valor
  }
}

object PalitoMagico extends ArmaSimple {
  override def cumpleCondicion(heroe: Heroe) = heroe.job match {
    case Some(Mago) => true
    case Some(Ladron) => heroe.inteligenciaBase > 30
    case _ => false
  }
  override def inteligencia(heroe: Heroe, valor: Double) = {
    if(cumpleCondicion(heroe)) valor + 20
    else valor
  }
}

object ArcoViejo extends ArmaDoble {
  override def fuerza(heroe: Heroe, valor: Double) = valor + 2
}

object CascoVikingo extends Cabeza {
  override def cumpleCondicion(heroe: Heroe) = heroe.fuerzaBase > 30
  override def HP(heroe: Heroe, valor: Double) = valor + 10
}

object VinchaDelBufaloDelAgua extends Cabeza {
  override def cumpleCondicion(heroe: Heroe) = heroe.job.isDefined
  override def inteligencia(heroe: Heroe, valor: Double) = {
    if(heroe.fuerzaBase > heroe.inteligenciaBase) valor + 30
    else valor
  }
  override def HP(heroe: Heroe, valor: Double) = {
    if(heroe.fuerzaBase <= heroe.inteligenciaBase) valor + 10
    else valor
  }
  override def fuerza(heroe: Heroe, valor: Double) = {
    if(heroe.fuerzaBase <= heroe.inteligenciaBase) valor + 10
    else valor
  }
  override def velocidad(heroe: Heroe, valor: Double) = {
    if(heroe.fuerzaBase <= heroe.inteligenciaBase) valor + 10
    else valor
  }
}

object Dedicacion extends Talisman {
  def porcentaje(heroe:Heroe) = heroe.job match {
    case Some(_) => heroe.job.get.statPrincipal * 0.10
    case _ => 0
  }
  override def HP(heroe: Heroe, valor: Double) = valor + porcentaje(heroe)
  override def fuerza(heroe: Heroe, valor: Double) = valor + porcentaje(heroe)
  override def velocidad(heroe: Heroe, valor: Double) = valor + porcentaje(heroe)
  override def inteligencia(heroe: Heroe, valor: Double) = valor + porcentaje(heroe)
}

object Minimalismo extends Talisman {
  override def HP(heroe: Heroe, valor: Double) = valor + 50 - (heroe.cantidadItems - 1) * 10
}

object Maldito extends Talisman {  
  override def HP(heroe: Heroe, valor: Double) = 1
  override def fuerza(heroe: Heroe, valor: Double) = 1
  override def velocidad(heroe: Heroe, valor: Double) = 1
  override def inteligencia(heroe: Heroe, valor: Double) = 1
}
