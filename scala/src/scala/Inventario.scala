package scala

case class Inventario(val items: List[Item] = Nil) {
   
  def equipar(heroe: Heroe, item: Item) = {
    if(item.cumpleCondicion(heroe)) {
      item match {    
        case Cabeza() => equiparCabeza(item)
        case Armadura() => equiparArmadura(item)
        case ArmaSimple() => equiparArmaSimple(item)
        case ArmaDoble() => equiparArmaDoble(item)
        case Talisman() => equiparTalisman(item)
      }
    }
    else this
  }
  
  def desequipar(item: Item) = copy(items.filterNot(_ eq item))
  
  def cantidadItems = items.size
  def equiparTalisman(item: Item) = copy(item :: items)
  def equiparCabeza(item: Item) = copy(item :: items.filterNot(_.isInstanceOf[Cabeza]))
  def equiparArmadura(item: Item) = copy(item :: items.filterNot(_.isInstanceOf[Armadura]))
  def equiparArmaDoble(item: Item) = {
    copy (item :: items.filterNot(i => i.isInstanceOf[ArmaSimple] || i.isInstanceOf[ArmaDoble]))
  }

  def equiparArmaSimple(item: Item) = {
    val armas = items.filter(_.isInstanceOf[ArmaSimple])
    val armaDoble = items.filter(_.isInstanceOf[ArmaDoble])
    if (armaDoble.size < 1) {
      if (armas.size < 2) copy(item :: items)
      else copy(item :: armas.head :: items.filterNot(_.isInstanceOf[ArmaSimple]))
    }
    else copy(item :: items.filterNot(_.isInstanceOf[ArmaDoble]))
  }
  
  def fuerzaFinal(heroe: Heroe, valor: Double) = items.foldLeft(valor)((v, item) => item.fuerza(heroe, v))
  
  def HPFinal(heroe: Heroe, valor: Double) = items.foldLeft(valor)((v, item) => item.HP(heroe, v))
  
  def velocidadFinal(heroe: Heroe, valor: Double) = items.foldLeft(valor)((v, item) => item.velocidad(heroe, v))
  
  def inteligenciaFinal(heroe: Heroe, valor: Double) = items.foldLeft(valor)((v, item) => item.inteligencia(heroe, v))
  
}