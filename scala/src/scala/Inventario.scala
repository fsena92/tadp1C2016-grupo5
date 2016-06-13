package scala

case class Inventario(var cabeza: Option[Item] = None, var brazos: Option[(Option[Item] , Option[Item])] = Some(None, None),
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
  
  def ocuparCabeza(item: Item) = copy(cabeza = Some(item))
  def ocuparTorso(item: Item) = copy(torso = Some(item))
  def ocuparBrazos(item: Item) = copy(brazos = Some((Some(item), Some(item))))
  def ocuparBrazo(item: Item) = brazos.get match {
    case (None, None) => copy(brazos = Some((Some(item), None)))
    case (None, unItem) => copy(brazos = Some((Some(item), unItem)))
    case (unItem, None) => copy(brazos = Some((unItem, Some(item))))
    case (unItem, otroItem) => this
  }
  
  def desocuparBrazo = brazos.get match {
    case (None, None) =>
    case (None, unItem) => 
    case (unItem, None) =>
    case (unItem, otroItem) => 
      if(unItem == otroItem) brazos = Some((None, None)) 
      else brazos = Some((None, otroItem))
  }
   
  def cabezaOcupada = cabeza.isDefined
  def torsoOcupado = torso.isDefined
  def brazoOcupado = brazos.get match {
    case (Some(_), Some(_)) => true
    case (Some(_), None) => false
    case (None, Some(_)) => false
    case (None, None) => false
  }
  def brazosOcupados = brazos.get match {
    case (Some(_),Some(_)) => true
    case (Some(_), None) => true
    case (None, Some(_)) => true
    case (None, None) => false
  }
 
  def itemsEquipados = {  
    var cantidad = talismanes.size
    if(cabezaOcupada) cantidad = cantidad + 1 
    if(torsoOcupado) cantidad = cantidad + 1
    brazos.get match {
      case (Some(item), Some(otroItem)) => 
        if (item eq otroItem) cantidad + 1 
        else cantidad + 2
      case (Some(_), None) => cantidad + 1
      case (None, Some(_)) => cantidad + 1
      case (None, None) => cantidad
    }
  }
 
}
