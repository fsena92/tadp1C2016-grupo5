package scala

case class Inventario(var cabeza: Option[Item] = None, var brazos: (Option[Item] , Option[Item]) = (None, None),
    var torso: Option[Item] = None, var talismanes: List[Item] = Nil) {
 
  def equiparItem(item: Item, heroe: Heroe): Inventario = {
    if (item.cumpleCondicion(heroe)) {
      item match {
        case Cabeza() => ocuparCabeza(item)
        case Torso() => ocuparTorso(item)
        case Brazo() => 
          if(brazoOcupado) desocuparBrazo
          ocuparBrazo(item)
        case Brazos() => ocuparBrazos(item)
        case Talisman() => agregarTalisman(item)
      }
    }
    else this
  }
  
  def agregarTalisman(unTalisman: Item): Inventario = copy(talismanes = unTalisman :: talismanes)  
  
  def ocuparCabeza(item: Item):Inventario = copy(cabeza = Some(item))
  def ocuparTorso(item: Item):Inventario = copy(torso = Some(item))
  def ocuparBrazos(item: Item):Inventario = copy(brazos = (Some(item), Some(item)))
  def ocuparBrazo(item: Item):Inventario = brazos match {
    case (None, None) => copy(brazos = (Some(item), None))
    case (None, unItem) => copy(brazos = (Some(item), unItem))
    case (unItem, None) => copy(brazos = (unItem, Some(item)))
    case (unItem, otroItem) => this
  }
  
  def desocuparBrazo = brazos match {
    case (None, None) =>
    case (None, unItem) => 
    case (unItem, None) =>
    case (unItem, otroItem) => 
      if(unItem == otroItem) brazos = (None, None) 
      else brazos = (None, otroItem)
  }
   
  def cabezaOcupada = cabeza.isDefined
  def torsoOcupado = torso.isDefined
  def brazoOcupado = brazos match {
    case (Some(_), Some(_)) => true
    case (Some(_), None) => false
    case (None, Some(_)) => false
    case (None, None) => false
  }
  def brazosOcupados = brazos match {
    case (Some(_),Some(_)) => true
    case (Some(_), None) => true
    case (None, Some(_)) => true
    case (None, None) => false
  }
  
  def itemsEquipados: Int = {
    val items = List(cabeza,torso,brazos) :: talismanes
    items.foldLeft(0)((cantidadItems, item) => { item match {
      case Cabeza() if(cabezaOcupada) => cantidadItems + 1
      case Torso() if(torsoOcupado) => cantidadItems + 1
      case Talisman() => cantidadItems + 1
      case Brazos() => brazos match {
          case (Some(item), Some(otroItem)) => 
            if (item == otroItem) cantidadItems + 1 
            else cantidadItems + 2
          case (Some(_), None) => cantidadItems + 1
          case (None, Some(_)) => cantidadItems + 1
          case (None, None) => cantidadItems
          }
      } })
  }
 
}
