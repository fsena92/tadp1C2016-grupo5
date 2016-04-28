class Symbol
  def call(valor)
    true
  end
end

module Valor
  def val(un_valor)
    proc {|v| v == un_valor}
  end
end

module Tipo
  def type(un_tipo)
    proc {|t| t.is_a?(un_tipo)}
  end
end

module Duck_Typing
  def duck(*metodos)
    proc {|un_objeto| metodos.all? { |metodo| un_objeto.methods.include?(metodo)}}
  end
end

module Lista
  def list(una_lista, *condicion)
    condicion == [true] || condicion == [] ? proc {|otra_lista| una_lista == otra_lista} :
        proc {|otra_lista| compare_lists(una_lista, otra_lista)}
  end

  def compare_lists(list_a, list_b)
    list_a[0, list_b.size] == list_b || list_b[0, list_a.size] == list_a
  end
end

class Proc
  def and(*procs)
    proc {|objeto| self.call(objeto) && procs.all? {|r| r.call(objeto)}}
  end

  def or(*procs)
    proc {|objeto| self.call(objeto) || procs.any? {|r| r.call(objeto)}}
  end

  def not
    proc {|objeto| !self.call(objeto)}
  end
end


class Object
  include Valor
  include Tipo
  include Lista
  include Duck_Typing
end

module Matcher
  attr_accessor :un_objeto, :diccionario
  def with(*matchers, &un_bloque)
    #if match(matchers)
      #binding(un_bloque)
      self.instance_eval &un_bloque
    #end
  end
  def otherwise(un_bloque)
    #llamar a match y bind
  end
  def match(matchers)
    matchers.all? {|m| m.call(objeto)}
  end
end


class Aux
  attr_accessor :diccionario, :simbolos
  def initialize
    self.diccionario = {}
    self.simbolos = []
  end

  def with(*matchers, &bloque)
    instance_eval &bloque
  end

  def binder(*objeto)
    i = 0
    simbolos.each do |s|
      diccionario[s] = objeto[i]
      i += 1
    end
  end

  #guarda el conjunto de cosas del bloque para ser bindeados
  def method_missing(sym, *args)
    self.diccionario[sym] = ''
  end

  #matchea y guarda los simbolos para bindear en orden con los objetos o el objeto
  def match(*matchers, un_objeto)
    matchers.all? {|m| m.call(un_objeto)}
    self.simbolos += matchers.select {|m| m.is_a? Symbol}
  end
end

c = Aux.new
c.with(1,2,4) {a + b + variable}
puts c.diccionario

c.match(val(4), duck(:+), :a, :b, :variable, 4)
c.binder(1,2,4)
puts c.diccionario

#puts c.simbolos