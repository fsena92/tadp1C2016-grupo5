package scala

import Sector._

case class Inventario(val items: List[Item] = Nil) {
   
  def equipar(heroe: Heroe, item: Item) = {
    if(item.cumpleCondicion(heroe)) {
      item.sector match {    
        case Cabeza => equiparCabeza(item)
        case Armadura => equiparArmadura(item)
        case ArmaSimple => equiparArmaSimple(item)
        case ArmaDoble => equiparArmaDoble(item)
        case Talisman => equiparTalisman(item)
      }
    }
    else this
  }
  
  def equiparTalisman(item: Item) = copy(item :: items)
  def equiparCabeza(item: Item) = copy(item :: items.filterNot(_.sector eq Cabeza))
  def equiparArmadura(item: Item) = copy(item :: items.filterNot(_.sector eq Armadura))
  def equiparArmaDoble(item: Item) = copy (item :: items.filterNot(i => i.sector == ArmaSimple | i.sector == ArmaDoble))
  def equiparArmaSimple(item: Item) = {
    val armas = items.filter(_.sector eq ArmaSimple)
    val armaDoble = items.filter(_.sector eq ArmaDoble)
    if (armaDoble.size < 1) {
      if (armas.size < 2) copy(item :: items)
      else copy(item :: armas.head :: items.filterNot(_.sector eq ArmaSimple))
    }
    else copy(item :: items.filterNot(_.sector eq ArmaDoble))
  }

  def desequipar(item: Item) = copy(items.filterNot(_ eq item))
  
  def obtenerDeItems(heroe: Heroe, valor: Double)(i: (Item, Heroe, Double) => Double) = {
    items.foldLeft(valor)((v, item) => i(item, heroe, v))  
  }
  
  def fuerzaFinal(heroe: Heroe, valor: Double) = obtenerDeItems(heroe, valor)(_ fuerza(_, _))
  def HPFinal(heroe: Heroe, valor: Double) = obtenerDeItems(heroe, valor)(_ HP(_, _))
  def velocidadFinal(heroe: Heroe, valor: Double) = obtenerDeItems(heroe, valor)(_ velocidad(_, _))
  def inteligenciaFinal(heroe: Heroe, valor: Double) = obtenerDeItems(heroe, valor)(_ inteligencia(_, _))
 
  //opcion 2
//  def fuerzaFinal = obtenerDeItems(_:Heroe, _:Double)(_ fuerza(_, _))
//  def HPFinal = obtenerDeItems(_:Heroe, _:Double)(_ HP(_, _))
//  def velocidadFinal = obtenerDeItems(_:Heroe, _:Double)(_ velocidad(_, _))
//  def inteligenciaFinal = obtenerDeItems(_:Heroe, _:Double)(_ inteligencia(_, _))
//  
  
  
  def cantidadItems = items.size
}