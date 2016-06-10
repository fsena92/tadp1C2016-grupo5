package scala

import scala.collection.mutable.ListBuffer

class Inventario(var cabeza: Cabeza = null, var armadura: Armadura = null, 
                 var armaSimple: (ArmaSimple,ArmaSimple) = null,
                 var armaDoble: ArmaDoble = null, var talismanes: ListBuffer[Talisman] = null,
                 var items: ListBuffer[Item] = ListBuffer()) {
  
  def equipar(heroe: Heroe, item: Item):Inventario = item match{
    case Cabeza() => equiparCabeza(heroe,item.asInstanceOf[Cabeza])
    case Armadura() => equiparArmadura(heroe, item.asInstanceOf[Armadura])
    case ArmaSimple() => equiparArmaSimple(heroe,item.asInstanceOf[ArmaSimple])
    case ArmaDoble() => equiparArmaDoble(heroe,item.asInstanceOf[ArmaDoble])
    case Talisman() => equiparTalisman(heroe,item.asInstanceOf[Talisman])
  }
  
  def equiparCabeza(heroe: Heroe, item: Cabeza):Inventario = {
    if (cabeza != null) 
      cabeza.desequipar(heroe)
  
    cabeza = item
    item.equipar(heroe)
    items += item
    this
  }
  
  def equiparArmadura(heroe: Heroe, item: Armadura):Inventario = {
    if (armadura != null) armadura.desequipar(heroe)
    armadura = item
    item.equipar(heroe)
    items += item
    this
  }
  
  def equiparArmaSimple(heroe: Heroe, item: ArmaSimple):Inventario = { 
    if (armaDoble != null)
      armaDoble.desequipar(heroe)  
      
    armaSimple match {
      case (null, arma2) => armaSimple = (item, arma2)
      case (arma1, null) => armaSimple = (arma1, item)
      case null => armaSimple = (item, null)
      case (arma1, arma2) => 
        arma1.desequipar(heroe)
        armaSimple = (item, arma2)
    }
    item.equipar(heroe)
    items += item
    this
  }
  
  def equiparArmaDoble(heroe: Heroe, item: ArmaDoble):Inventario = {
    if (armaDoble != null)
      armaDoble.desequipar(heroe)
      
    armaSimple match {
      case null =>
      case (null, arma2) => arma2.desequipar(heroe)
      case (arma1, null) => arma1.desequipar(heroe)
      case (arma1, arma2) => 
        arma1.desequipar(heroe)
        arma2.desequipar(heroe)
    }
    item.equipar(heroe)
    armaDoble = item
    items += item
    this
  }
  
  def equiparTalisman(heroe: Heroe, item: Talisman):Inventario = {
    talismanes += item
    item.equipar(heroe)
    items += item
    this
  }
  
}