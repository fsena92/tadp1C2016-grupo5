package scala

case class Inventario(var items: List[Item] = Nil) {
   
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
  
  def cantidadItems = items.size
  def equiparTalisman(item: Item) = copy(item :: items)
  def equiparCabeza(item: Item) = copy(item :: items.filterNot(x => x.isInstanceOf[Cabeza]))
  def equiparArmadura(item: Item) = copy(item :: items.filterNot(x => x.isInstanceOf[Armadura]))
  def equiparArmaDoble(item: Item) = {
    copy (item :: items.filterNot(x => x.isInstanceOf[ArmaSimple] || x.isInstanceOf[ArmaDoble]))
  }

  def equiparArmaSimple(item: Item) = {
    val armas = items.filter(x => x.isInstanceOf[ArmaSimple])
    val armaDoble = items.filter(x => x.isInstanceOf[ArmaDoble])
    if (armaDoble.size < 1) {
      if (armas.size < 2) copy(item :: items)
      else copy(item :: armas.head :: items.filterNot(x => x.isInstanceOf[ArmaSimple]))
    }
    else copy(item :: items.filterNot(x => x.isInstanceOf[ArmaDoble]))
  }
  
  def fuerzaFinal(heroe:Heroe, parametro:Double): Double = {
    items.foldLeft(parametro)((param, item) => item.fuerza(heroe, param))
  } 
  
  def HPFinal(heroe: Heroe, parametro: Double): Double = {
    items.foldLeft(parametro)((param, item) => item.HP(heroe, param))
  }
  
  def velocidadFinal(heroe: Heroe, parametro: Double): Double = {
    items.foldLeft(parametro)((param, item) => item.velocidad(heroe, param))
  }
  
  def inteligenciaFinal(heroe: Heroe, parametro: Double): Double = {
    items.foldLeft(parametro)((param, item) => item.inteligencia(heroe, param))
  }
  
}