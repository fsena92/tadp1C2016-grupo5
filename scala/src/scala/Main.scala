package scala

object Main {
  def main(args: Array[String]): Unit = {
   
    val kratos = new Heroe(50, 45, 10, 10)
    val spiderman = new Heroe(10, 35, 60, 40)
    val ironMan = new Heroe(50, 10, 40, 100)
    val capitanAmerica = new Heroe(70, 30, 60, 20)
    val wolverine = new Heroe(1000, 60, 50, 20)
    
//    spiderman.asignarTrabajo(Mago).equipar(PalitoMagico).equipar(EscudoAntiRobo).stats
//    wolverine.asignarTrabajo(Ladron).equipar(EspadaDeLaVida).stats
//    capitanAmerica.asignarTrabajo(Guerrero).equipar(CascoVikingo).stats
//    kratos.equipar(VinchaDelBufaloDelAgua).equipar(ArcoViejo).stats
//    
    
    val otroEquipo = new Equipo("otro", List(spiderman.asignarTrabajo(Mago).equipar(PalitoMagico).equipar(EscudoAntiRobo),
    wolverine.asignarTrabajo(Ladron).equipar(EspadaDeLaVida), capitanAmerica.asignarTrabajo(Guerrero).equipar(CascoVikingo),
    kratos.equipar(VinchaDelBufaloDelAgua).equipar(ArcoViejo)))    
    
    println(otroEquipo.lider)
    println(otroEquipo.unMiembroRealizaTareaSiPuede(PelearContraMonstruo))
   
    
    
  }   
  
}