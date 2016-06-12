package scala

trait Item {
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

object EspadaDeLaVida extends Brazo {
  override def equipar(heroe: Heroe) = heroe.modificarStats(0, heroe.HP, 0, 0)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(0, -heroe.HP, 0, 0)
}

object EscudoAntiRobo extends Brazo {
  override def cumpleCondicion(heroe: Heroe) = heroe.job.get match {
    case Ladron => false
    case _ => heroe.fuerza >= 20
  }
  override def equipar(heroe: Heroe) = { 
    if(cumpleCondicion(heroe)) heroe.modificarStats(20, 0, 0, 0)
    else heroe
  }
  override def desequipar(heroe: Heroe) = heroe.modificarStats(-20, 0, 0, 0)
}

object PalitoMagico extends Brazo {
  override def cumpleCondicion(heroe: Heroe) = heroe.job.get match {
    case Mago => true
    case Ladron => heroe.inteligencia > 30
    case _ => false
  }
  override def equipar(heroe: Heroe) = { 
    if(cumpleCondicion(heroe)) heroe.modificarStats(0, 0, 0, 20)
    else heroe
  }
  override def desequipar(heroe: Heroe) = heroe.modificarStats(0, 0, 0, -20)
}

object ArcoViejo extends Brazos {
  override def equipar(heroe: Heroe) = heroe.modificarStats(0, 2, 0, 0)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(0, -2, 0, 0)
}

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
      if(heroe.fuerza > heroe.inteligencia) heroe.modificarStats(0, 0, 0, 30)
      else heroe.modificarStats(10, 10, 10, 0)
  }
  override def desequipar(heroe: Heroe) = {
    if(heroe.fuerza > heroe.inteligencia) heroe.modificarStats(0, 0, 0, -30)
    else heroe.modificarStats(-10, -10, -10, 0)
  }
}

object Dedicacion extends Talisman {
  def porcentaje(heroe:Heroe) = heroe.job.get.statPrincipal * 0.10
  override def equipar(heroe: Heroe) = {
    heroe.modificarStats(porcentaje(heroe), porcentaje(heroe), porcentaje(heroe), porcentaje(heroe))
  }
  override def desequipar(heroe: Heroe) = {
    heroe.modificarStats(-porcentaje(heroe), -porcentaje(heroe), -porcentaje(heroe), -porcentaje(heroe))
  }
}

object Minimalismo extends Talisman {
  override def equipar(heroe: Heroe) = {
    val incremento = 50 - heroe.inventario.itemsEquipados * 10
    heroe.modificarStats(incremento, 0, 0, 0)
  }
  override def desequipar(heroe: Heroe) = {
    val decremento = -50 + heroe.inventario.itemsEquipados * 10
    heroe.modificarStats(decremento, 0, 0, 0)
  }
}

object Maldito extends Talisman {
  var anterior:Heroe = ???
  override def equipar(heroe: Heroe) = {
    anterior = heroe
    heroe.modificarStats(1, 1, 1, 1)
  }
  override def desequipar(heroe: Heroe) = {
    heroe.modificarStats(anterior.HP, anterior.fuerza, anterior.velocidad, anterior.inteligencia)
  }
}
