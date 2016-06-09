package scala

import scala.collection.mutable.ListBuffer

class Inventario(var cabeza: Cabeza,var armadura: Armadura,var armaSimple: (ArmaSimple,ArmaSimple),
                 var armaDoble: ArmaDoble, var talismanes: ListBuffer[Talisman], var items: ListBuffer[Item] = ListBuffer()) {
  
  
  def equipar(heroe: Heroe, item: Cabeza) = {
    if (cabeza != null) cabeza.desequipar(heroe)
    cabeza = item
    item.equipar(heroe)
    items += item
  }
  
  def equipar(heroe: Heroe, item: Armadura) = {
    if (armadura != null) armadura.desequipar(heroe)
    armadura = item
    item.equipar(heroe)
    items += item
  }
  
  def equipar(heroe: Heroe, item: ArmaSimple) = { 
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
  }
  
  def equipar(heroe: Heroe, item: ArmaDoble) = {
    if (armaDoble != null)
      armaDoble.desequipar(heroe)
      
    armaSimple match {
      case (null, arma2) => arma2.desequipar(heroe)
      case (arma1, null) => arma1.desequipar(heroe)
      case (arma1, arma2) => 
        arma1.desequipar(heroe)
        arma2.desequipar(heroe)
    }
    item.equipar(heroe)
    armaDoble = item
    items += item
  }
  
  def equipar(heroe: Heroe, item: Talisman) = {
    talismanes += item
    item.equipar(heroe)
    items += item
  }
  
}