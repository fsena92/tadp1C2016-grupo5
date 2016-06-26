package scala
import scala.util.{Failure, Try, Success}

object Main {
  def main(args: Array[String]): Unit = {

//    println(List(1,2,3).filterNot(h => false))
//    
//    case class Hola() extends Exception
//    
//    var variable: Double = 0
//    println(Try(
//       if( (8/variable).isInfinite) throw new Hola()
//       else 8/variable
//    )
//        
//  
//    )
    
    val grupo = new Equipo("equipo", List(new Heroe(1,1,1,1), new Heroe(2,0,0,0)))
    val kratos = new Heroe(50, 45, 10, 10)
    val icaros = new Heroe(50000, 1, 10000, 10000)
    val spiderman = new Heroe(10, 35, 60, 40) // 10 ,35  ,60  ,40  
    val ironMan = new Heroe(50, 10, 40, 100) // 50  ,10  ,40  ,100
    val capitanAmerica = new Heroe(70, 30, 60, 20)
    val wolverine = new Heroe(1000, 60, 50, 20)
    val equipo2 = new Equipo("Konami", List(icaros.asignarTrabajo(Guerrero), spiderman))
    val equipo = new Equipo("vengadores_2", List(spiderman, ironMan))
    
    println(grupo.realizarMision(new Mision(List(RobarTalisman(Maldito)), GanarOroParaElPozoComun(10))).
  transform(e => Failure(TareaFallida(equipo, RobarTalisman(Maldito))), f => Try(equipo)).get)
    
    println(equipo.lider)

    println(equipo.realizarMision(new Mision(List(PelearContraMonstruo, ForzarPuerta, RobarTalisman(Dedicacion),
        PelearContraMonstruo),GanarOroParaElPozoComun(1000))))
  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  }
  
}