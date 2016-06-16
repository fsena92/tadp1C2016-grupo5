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
  
  def desequipar(item: Item) = copy(items.filterNot(i => i eq item))
  
  def cantidadItems = items.size
  def equiparTalisman(item: Item) = copy(item :: items)
  def equiparCabeza(item: Item) = copy(item :: items.filterNot(i => i.isInstanceOf[Cabeza]))
  def equiparArmadura(item: Item) = copy(item :: items.filterNot(i => i.isInstanceOf[Armadura]))
  def equiparArmaDoble(item: Item) = {
    copy (item :: items.filterNot(i => i.isInstanceOf[ArmaSimple] || i.isInstanceOf[ArmaDoble]))
  }

  def equiparArmaSimple(item: Item) = {
    val armas = items.filter(i => i.isInstanceOf[ArmaSimple])
    val armaDoble = items.filter(i => i.isInstanceOf[ArmaDoble])
    if (armaDoble.size < 1) {
      if (armas.size < 2) copy(item :: items)
      else copy(item :: armas.head :: items.filterNot(i => i.isInstanceOf[ArmaSimple]))
    }
    else copy(item :: items.filterNot(x => x.isInstanceOf[ArmaDoble]))
  }
  
  def fuerzaFinal(heroe: Heroe, parametro: Double) = {
    items.foldLeft(parametro)((param, item) => item.fuerza(heroe, param))
  } 
  
  def HPFinal(heroe: Heroe, parametro: Double) = {
    items.foldLeft(parametro)((param, item) => item.HP(heroe, param))
  }
  
  def velocidadFinal(heroe: Heroe, parametro: Double) = {
    items.foldLeft(parametro)((param, item) => item.velocidad(heroe, param))
  }
  
  def inteligenciaFinal(heroe: Heroe, parametro: Double) = {
    items.foldLeft(parametro)((param, item) => item.inteligencia(heroe, param))
  }
  
}