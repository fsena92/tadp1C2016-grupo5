package scala

trait Item {
  def cumpleCondicion(heroe :Heroe) = true
  def desequipar(heroe: Heroe)
  def equipar(heroe: Heroe)
}

abstract class Armadura extends Item 
case object ArmaduraEleganteSport extends Armadura {
  def equipar(heroe: Heroe) = heroe.modificarStats(-30, 0, 30, 0)
  def desequipar(heroe: Heroe) = heroe.modificarStats(30, 0, -30, 0)
}

abstract class ArmaSimple extends Item // uno o dos
case object EspadaDeLaVida extends ArmaSimple {
  def equipar(heroe: Heroe) = heroe.modificarStats(0, heroe.HP, 0, 0)
  def desequipar(heroe: Heroe) = heroe.modificarStats(0, -heroe.HP, 0, 0)  
}
case object EscudoAntiRobo extends ArmaSimple {
  def cumpleCondicion(heroe: Heroe) = heroe.job match {
    case Ladron => false
    case _ => heroe.fuerza >= 20
  }
  def equipar(heroe: Heroe) = heroe.modificarStats(20, 0, 0, 0)
  def desequipar(heroe: Heroe) = heroe.modificarStats(-20, 0, 0, 0)
}
case object PalitoMagico extends ArmaSimple {
  def cumpleCondicion(heroe: Heroe) = heroe.job match {
    case Mago => true
    case Ladron => heroe.inteligencia > 30
    case _ => false
  }
  def equipar(heroe: Heroe):Heroe = heroe.modificarStats(0, 0, 0, 20)
  def desequipar(heroe: Heroe):Heroe = heroe.modificarStats(0, 0, 0, -20)
}

abstract class ArmaDoble extends Item // solo uno
case object ArcoViejo extends ArmaDoble {
  def equipar(heroe: Heroe) = heroe.modificarStats(0, 2, 0, 0)
  def desequipar(heroe: Heroe) = heroe.modificarStats(0, -2, 0, 0)
}

abstract class Cabeza extends Item // solo uno
case object CascoVikingo extends Cabeza {
  def cumpleCondicion(heroe: Heroe) = heroe.fuerza > 30
  def equipar(heroe: Heroe):Heroe = heroe.modificarStats(10, 0, 0, 0)
  def desequipar(heroe: Heroe):Heroe = heroe.modificarStats(-10, 0, 0, 0)
}

case object VinchaDelBufaloDelAgua extends Cabeza {
  def cumpleCondicion(heroe: Heroe) = heroe.job == null
  def equipar(heroe: Heroe) = {
    if(heroe.fuerza > heroe.inteligencia) heroe.modificarStats(0, 0, 0, 30)
    else heroe.modificarStats(10, 10, 10, 0)
  }
  def desequipar(heroe: Heroe) = {
    if(heroe.fuerza > heroe.inteligencia) heroe.modificarStats(0, 0, 0, -30)
    else heroe.modificarStats(-10, -10, -10, 0)
  }
}

abstract class Talisman extends Item // uno o varios
case object Dedicacion extends Talisman {
  def equipar(heroe: Heroe) = {
    val incremento = heroe.job.statPrincipal * 0.10
    heroe.modificarStats(incremento, incremento, incremento, incremento)
  }
  def desequipar(heroe: Heroe) = {
    val decremento = -heroe.job.statPrincipal * 0.10
    heroe.modificarStats(decremento, decremento, decremento, decremento)
  }
}
case object Minimalismo extends Talisman {
  def equipar(heroe: Heroe) = {
    val incremento = heroe.inventario.items.size
    heroe.modificarStats(50, incremento, incremento, incremento)
  }
  def desequipar(heroe: Heroe) = {
    val decremento = -heroe.inventario.items.size
    heroe.modificarStats(-50, decremento, decremento, decremento)
  }
}
case object Maldito extends Talisman {
  def equipar(heroe: Heroe) = 
    heroe.modificarStats(1 - heroe.HP, 1 - heroe.fuerza, 1 - heroe.velocidad, 1 - heroe.inteligencia)
}

