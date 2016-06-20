package scala

object Sector extends Enumeration {
  type Sector = Value
  val Cabeza, Armadura, ArmaSimple, ArmaDoble, Talisman = Value
}
import Sector._

abstract class Item(val sector: Sector, val precio: Double) {
  def cumpleCondicion(heroe: Heroe): Boolean = true
  def fuerza(heroe: Heroe, valor: Double) = valor
  def HP(heroe: Heroe, valor: Double) = valor
  def velocidad(heroe: Heroe, valor: Double) = valor
  def inteligencia(heroe: Heroe, valor: Double) = valor  
}

object ArmaduraEleganteSport extends Item(Cabeza, precio = 10) {
  override def HP(heroe: Heroe, valor: Double) = valor - 30
  override def velocidad(heroe: Heroe, valor: Double) = valor + 30 
}

object EspadaDeLaVida extends Item(ArmaSimple, precio = 20) {
  override def fuerza(heroe: Heroe, valor: Double) = heroe.HPFinal
}

object EscudoAntiRobo extends Item(ArmaSimple, precio = 30) {
  override def cumpleCondicion(heroe: Heroe) = heroe.job match {
    case Some(Ladron) => false
    case _ => heroe.fuerzaBase >= 20
  }
  override def HP(heroe: Heroe, valor: Double) = {
    if(cumpleCondicion(heroe)) valor + 20 ; else valor
  }
}

object PalitoMagico extends Item(ArmaSimple, precio = 25) {
  override def cumpleCondicion(heroe: Heroe) = heroe.job match {
    case Some(Mago) => true
    case Some(Ladron) => heroe.inteligenciaBase > 30
    case _ => false
  }
  override def inteligencia(heroe: Heroe, valor: Double) = {
    if(cumpleCondicion(heroe)) valor + 20 ; else valor
  }
}

object ArcoViejo extends Item(ArmaDoble, precio = 15) {
  override def fuerza(heroe: Heroe, valor: Double) = valor + 2
}

object CascoVikingo extends Item(Cabeza, precio = 5) {
  override def cumpleCondicion(heroe: Heroe) = heroe.fuerzaBase > 30
  override def HP(heroe: Heroe, valor: Double) = valor + 10
}

object VinchaDelBufaloDelAgua extends Item(Cabeza, precio = 50) {
  override def cumpleCondicion(heroe: Heroe) = heroe.job.isDefined
  
  override def inteligencia(heroe: Heroe, valor: Double) = {
    if(heroe.fuerzaBase > heroe.inteligenciaBase) valor + 30 ; else valor
  }
  override def HP(heroe: Heroe, valor: Double) = {
    if(heroe.fuerzaBase <= heroe.inteligenciaBase) valor + 10 ; else valor
  }
  override def fuerza(heroe: Heroe, valor: Double) = {
    if(heroe.fuerzaBase <= heroe.inteligenciaBase) valor + 10 ; else valor
  }
  override def velocidad(heroe: Heroe, valor: Double) = {
    if(heroe.fuerzaBase <= heroe.inteligenciaBase) valor + 10 ; else valor
  }
}

object Dedicacion extends Item(Talisman, precio = 40) {
  def porcentaje(heroe: Heroe) = heroe.desequipar(this).statPrincipal * 0.1
  override def HP(heroe: Heroe, valor: Double) = valor + porcentaje(heroe)
  override def fuerza(heroe: Heroe, valor: Double) = valor + porcentaje(heroe)
  override def velocidad(heroe: Heroe, valor: Double) = valor + porcentaje(heroe)
  override def inteligencia(heroe: Heroe, valor: Double) = valor + porcentaje(heroe)
}

object Minimalismo extends Item(Talisman, precio = 5) {
  override def HP(heroe: Heroe, valor: Double) = valor + 50 - (heroe.cantidadItems - 1) * 10
}

object Maldito extends Item(Talisman, precio = 100) {  
  override def HP(heroe: Heroe, valor: Double) = 1
  override def fuerza(heroe: Heroe, valor: Double) = 1
  override def velocidad(heroe: Heroe, valor: Double) = 1
  override def inteligencia(heroe: Heroe, valor: Double) = 1
}
