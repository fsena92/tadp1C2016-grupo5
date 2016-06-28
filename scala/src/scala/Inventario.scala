package scala

import Sector._
import scala.util.{Try, Success, Failure}

object NoSePudoEquiparUnItem extends Exception

case class Inventario(val items: List[Item] = Nil) {
   
  def equipar(heroe: Heroe, item: Item): Try[Inventario] = Try(
    if(item cumpleCondicion heroe) {
      item.sector match {    
        case Cabeza => equiparItem(item, _.sector eq Cabeza)
        case Armadura => equiparItem(item, _.sector eq Armadura)
        case ArmaSimple => equiparArmaSimple(item)
        case ArmaDoble => equiparItem(item, i => i.sector == ArmaSimple | i.sector == ArmaDoble)
        case Talisman => equiparItem(item, i => false)
      }
    }
    else throw NoSePudoEquiparUnItem
  )
  
  def actualizarInventario(heroe: Heroe): Inventario = copy(items = items.filter(_.cumpleCondicion(heroe)))
  
  def equiparItem(item: Item, condicion: Item => Boolean): Inventario = copy(item :: items.filterNot(condicion))
  
  def equiparArmaSimple(item: Item): Inventario = {
    val armas = items.filter(_.sector eq ArmaSimple)
    val armaDoble = items.filter(_.sector eq ArmaDoble)
    if (armaDoble.size < 1) {
      if (armas.size < 2) equiparItem(item, i => false)
      else copy(item :: armas.head :: items.filterNot(_.sector eq ArmaSimple))
    }
    else equiparItem(item, _.sector eq ArmaDoble)
  }

  def desequipar(item: Item): Inventario = copy(items.filterNot(_ eq item))
  
  def obtenerDeItems(heroe: Heroe, valor: Double)(i: (Item, Heroe, Double) => Double): Double = {
    items.foldLeft(valor)((v, item) => i(item, heroe, v))  
  }
  
  def fuerzaFinal = obtenerDeItems(_:Heroe, _:Double)(_ fuerza(_, _))
  def HPFinal = obtenerDeItems(_:Heroe, _:Double)(_ HP(_, _))
  def velocidadFinal = obtenerDeItems(_:Heroe, _:Double)(_ velocidad(_, _))
  def inteligenciaFinal = obtenerDeItems(_:Heroe, _:Double)(_ inteligencia(_, _))
  
  def cantidadItems: Double = items.size
}