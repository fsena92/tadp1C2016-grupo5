package scala

object Main {
  def main(args: Array[String]): Unit = {
   
    
        val atila = new Heroe(70,30,60,20)
    //    val aquiles = new Heroe(9,2,3,4)
    //    val spiderman = new Heroe(10,35,60,40)
    //    val ironMan = new Heroe(50,10,40,100)
    //   // println(atila.stats)
    //   
    //    //println(aquiles.equipar(CascoVikingo).stats)
    //    
    //
    //   println(ironMan.asignarTrabajo(Guerrero).equipar(EscudoAntiRobo).stats)
    //   //println(ironMan.asignarTrabajo(Guerrero).equipar(EscudoAntiRobo).statsBase)
    //   println(Minimalismo.equipar(spiderman).stats)
    //   println(spiderman.asignarTrabajo(Guerrero).equipar(Minimalismo).inventario.talismanes)    
   // 10 + 10 + -30 + 50 - 20 
        
        println(atila.equipar(VinchaDelBufaloDelAgua).equipar(ArmaduraEleganteSport).equipar(Minimalismo).cantidadItems)
  
    
  }   
  
}