package scala

object Main {
  def main(args: Array[String]): Unit = {
    val kratos = new Heroe(50000, 1, 10000, 10000)
    val spiderman = new Heroe(10, 35, 60, 40)
    val ironMan = new Heroe(50, 10, 40, 100)
    val m1 = new Mision(List(PelearContraMonstruo, ForzarPuerta), GanarOroParaElPozoComun(100))
    val m2 = new Mision(List(PelearContraMonstruo, ForzarPuerta, PelearContraMonstruo), EncontrarNuevoMiembro(spiderman))
    
    val ta = new Taberna(Set(m1, m2))
    
    val equi = new Equipo("sad", List(kratos.asignarTrabajo(Guerrero), spiderman))
    val eq = new Equipo("sfd", List(kratos))
    var sdfsd = new Equipo("dff", List(spiderman))
    sdfsd = sdfsd.incrementarPozo(30)
    val equipo = new Equipo("vengadores_2", List(spiderman, ironMan))
//    println(sdfsd.pozoComun)
//    println(RealizoTarea(sdfsd).filter(_.pozoComun == 30).get)
    
    println(equipo.elMejorPuedeRealizar(RobarTalisman(Dedicacion)))
   
    println(ta.elegirMision({(equi, eq) => equi.pozoComun > eq.pozoComun}, equi) == m1)
   
  }
  
}