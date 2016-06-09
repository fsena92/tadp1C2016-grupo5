package scala

trait Estado
case class EstadoAnterior(heroe: Heroe) extends Estado

class HeroeBase(HP: Double, fuerza: Double, velocidad: Double ,inteligencia: Double)

case class Heroe(HP: Double, fuerza: Double, velocidad: Double, inteligencia: Double, job:Trabajo = null 
                ,inventario: Inventario = null) {
  
  
  def asignarTrabajo(trabajo: Trabajo):Heroe = {
    copy(HP = HP + trabajo.HP,
        fuerza = fuerza +trabajo.fuerza,
        velocidad = velocidad + trabajo.velocidad,
        inteligencia = inteligencia + trabajo.inteligencia,
        job = trabajo) 
  }
  
  def modificarStats(h: Double, f: Double, v: Double ,i: Double):Heroe = 
    copy(HP = HP + h, fuerza = fuerza + f, velocidad = velocidad + v, inteligencia = inteligencia + i)
  
  
  
 /* def equiparInventario(items: Item*) { 
    
    items.foldLeft(EstadoAnterior(this):Estado) {(heroeAnterior, item) => 
          
      
      item match {
        case Cabeza => 
          if (job == null) {
                     
            if (fuerza > inteligencia) sumarInteligencia(30)
          }
            
      
      
      }
     
        
        
    } 
    
  }
  */
  
  
  
}