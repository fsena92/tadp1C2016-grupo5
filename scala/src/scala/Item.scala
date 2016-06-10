package scala

trait Item {
  val valor = 0
  def cumpleCondicion(heroe :Heroe):Boolean = true
  def desequipar(heroe: Heroe) = {}
  def equipar(heroe: Heroe) = {}
}

case class Armadura() extends Item 
object ArmaduraEleganteSport extends Armadura {
  override def equipar(heroe: Heroe) = heroe.modificarStats(-30, 0, 30, 0)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(30, 0, -30, 0)
}

case class ArmaSimple() extends Item // uno o dos
object EspadaDeLaVida extends ArmaSimple {
  override def equipar(heroe: Heroe) = heroe.modificarStats(0, heroe.HP, 0, 0)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(0, -heroe.HP, 0, 0)  
}
object EscudoAntiRobo extends ArmaSimple {
  override def cumpleCondicion(heroe: Heroe) = heroe.job match {
    case Ladron => false
    case _ => heroe.fuerza >= 20
  }
  override def equipar(heroe: Heroe) = heroe.modificarStats(20, 0, 0, 0)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(-20, 0, 0, 0)
}
object PalitoMagico extends ArmaSimple {
  override def cumpleCondicion(heroe: Heroe) = heroe.job match {
    case Mago => true
    case Ladron => heroe.inteligencia > 30
    case _ => false
  }
  override def equipar(heroe: Heroe) = heroe.modificarStats(0, 0, 0, 20)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(0, 0, 0, -20)
}

case class ArmaDoble() extends Item // solo uno
object ArcoViejo extends ArmaDoble {
  override def equipar(heroe: Heroe) = heroe.modificarStats(0, 2, 0, 0)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(0, -2, 0, 0)
}

case class Cabeza() extends Item // solo uno
object CascoVikingo extends Cabeza {
  override def cumpleCondicion(heroe: Heroe) = heroe.fuerza > 30
  override def equipar(heroe: Heroe) = heroe.modificarStats(10, 0, 0, 0)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(-10, 0, 0, 0)
}

object VinchaDelBufaloDelAgua extends Cabeza {
  override def cumpleCondicion(heroe: Heroe) = heroe.job == null
  override def equipar(heroe: Heroe) = {
    if(heroe.fuerza > heroe.inteligencia) heroe.modificarStats(0, 0, 0, 30)
    else heroe.modificarStats(10, 10, 10, 0)
  }
  override def desequipar(heroe: Heroe) = {
    if(heroe.fuerza > heroe.inteligencia) heroe.modificarStats(0, 0, 0, -30)
    else heroe.modificarStats(-10, -10, -10, 0)
  }
}

case class Talisman() extends Item // uno o varios
object Dedicacion extends Talisman {
  override def equipar(heroe: Heroe) = {
    val incremento = heroe.job.statPrincipal * 0.10
    heroe.modificarStats(incremento, incremento, incremento, incremento)
  }
  override def desequipar(heroe: Heroe) = {
    val decremento = -heroe.job.statPrincipal * 0.10
    heroe.modificarStats(decremento, decremento, decremento, decremento)
  }
}
object Minimalismo extends Talisman {
  override def equipar(heroe: Heroe) = {
    val incremento = heroe.inventario.items.size
    heroe.modificarStats(50, incremento, incremento, incremento)
  }
  override def desequipar(heroe: Heroe) = {
    val decremento = -heroe.inventario.items.size
    heroe.modificarStats(-50, decremento, decremento, decremento)
  }
}
object Maldito extends Talisman {
  override def equipar(heroe: Heroe) = 
    heroe.modificarStats(1 - heroe.HP, 1 - heroe.fuerza, 1 - heroe.velocidad, 1 - heroe.inteligencia)
  override def desequipar(heroe: Heroe) = heroe.modificarStats(0,0,0,0)
}

