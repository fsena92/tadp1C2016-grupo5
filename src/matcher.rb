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


class Objeto
  attr_accessor :bloque, :matchers ,:aplicar_match
  def initialize
    self.aplicar_match = true
  end
end

module Matcher
  attr_accessor :un_objeto

  def with(*matchers, un_bloque)
    self.un_objeto.bloque = un_bloque
    self.un_objeto.matchers = matchers
    #llamar a match y bind
  end

  def otherwise(un_bloque)
    self.un_objeto.bloque = un_bloque
    #llamar a match y bind
  end

  def match
    matchers.all? {|p| p.call(self.un_objeto)}
  end

end

class Object
  include Valor
  include Tipo
  include Lista
  include Duck_Typing
  include Matcher
end
